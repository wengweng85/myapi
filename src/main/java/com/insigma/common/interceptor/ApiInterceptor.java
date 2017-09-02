package com.insigma.common.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.insigma.dto.AjaxReturnMsg;

/**
 * 通用登录相关session Interceptor过滤器
 * @author wengsh
 * @date 2015-8-17
 *
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {

	Log log=LogFactory.getLog(ApiInterceptor.class);

	private NamedThreadLocal<Long>  startTimeThreadLocal =   new NamedThreadLocal<Long>("StopWatch-StartTime");  
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	long beginTime = System.currentTimeMillis();//1、开始时间  
        startTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）
    	if (handler instanceof HandlerMethod) {
			   //String appkey= request.getParameter("appkey")==null?"":request.getParameter("appkey");
			   String appkey= request.getHeader("appkey");
			 //生定向到重复提交页面
			  AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
			  if(appkey.equals("")){
				    PrintWriter writer = response.getWriter();
	                dto.setSuccess(false);
	                dto.setMessage("appkey为空,不能访问");
	                writer.write(JSONObject.fromObject(dto).toString());
	                writer.flush();
	                writer.close();
	                return false;
			 }else if(!appkey.equals("faaaac26-8f96-11e7-bb31-be2e44b06b34")){
				    PrintWriter writer = response.getWriter();
			    	dto.setSuccess(false);
			    	dto.setMessage("appkey过期或不成功,不能访问");
	                writer.write(JSONObject.fromObject(dto).toString());
	                writer.flush();
	                writer.close();
	                return false;
			}
             return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }
    
    @Override  
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,   
Object handler, Exception ex) throws Exception {  
        long endTime = System.currentTimeMillis();//2、结束时间  
        long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
        long consumeTime = endTime - beginTime;//3、消耗的时间  
        if(consumeTime > 500) {//此处认为处理时间超过500毫秒的请求为慢请求  
            //TODO 记录到日志文件  
            log.info(String.format("%s consume %d millis", request.getRequestURI(), consumeTime));  
        }          
    }  
    
}
