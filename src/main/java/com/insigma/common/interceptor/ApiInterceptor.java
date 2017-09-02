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
 * ͨ�õ�¼���session Interceptor������
 * @author wengsh
 * @date 2015-8-17
 *
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {

	Log log=LogFactory.getLog(ApiInterceptor.class);

	private NamedThreadLocal<Long>  startTimeThreadLocal =   new NamedThreadLocal<Long>("StopWatch-StartTime");  
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	long beginTime = System.currentTimeMillis();//1����ʼʱ��  
        startTimeThreadLocal.set(beginTime);//�̰߳󶨱�����������ֻ�е�ǰ������߳̿ɼ���
    	if (handler instanceof HandlerMethod) {
			   //String appkey= request.getParameter("appkey")==null?"":request.getParameter("appkey");
			   String appkey= request.getHeader("appkey");
			 //�������ظ��ύҳ��
			  AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
			  if(appkey.equals("")){
				    PrintWriter writer = response.getWriter();
	                dto.setSuccess(false);
	                dto.setMessage("appkeyΪ��,���ܷ���");
	                writer.write(JSONObject.fromObject(dto).toString());
	                writer.flush();
	                writer.close();
	                return false;
			 }else if(!appkey.equals("faaaac26-8f96-11e7-bb31-be2e44b06b34")){
				    PrintWriter writer = response.getWriter();
			    	dto.setSuccess(false);
			    	dto.setMessage("appkey���ڻ򲻳ɹ�,���ܷ���");
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
        long endTime = System.currentTimeMillis();//2������ʱ��  
        long beginTime = startTimeThreadLocal.get();//�õ��̰߳󶨵ľֲ���������ʼʱ�䣩  
        long consumeTime = endTime - beginTime;//3�����ĵ�ʱ��  
        if(consumeTime > 500) {//�˴���Ϊ����ʱ�䳬��500���������Ϊ������  
            //TODO ��¼����־�ļ�  
            log.info(String.format("%s consume %d millis", request.getRequestURI(), consumeTime));  
        }          
    }  
    
}
