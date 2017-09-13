package com.insigma.common.interceptor;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.insigma.common.util.EhCacheUtil;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.dto.SysCode;
import com.insigma.mvc.component.log.LogUtil;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.model.SLog;
import com.insigma.mvc.service.jms.JmsProducerService;
import com.insigma.mvc.service.log.LogService;

/**
 * ApiInterceptor
 * @author wengsh
 *
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {

	Log log=LogFactory.getLog(ApiInterceptor.class);
	
	@Resource
	private JmsProducerService jmsProducerService;

	@Resource
	private LogService logservice;
	
	private NamedThreadLocal<Long>  startTimeThreadLocal =   new NamedThreadLocal<Long>("StopWatch-StartTime");  
	
	private List<CodeValue> appkeylist=(List<CodeValue>)EhCacheUtil.getManager().getCache("webcache").get("APPKEY").getValue();
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	long beginTime = System.currentTimeMillis();//1����ʼʱ��  
        startTimeThreadLocal.set(beginTime);//�̰߳󶨱�����������ֻ�е�ǰ������߳̿ɼ���
    	if (handler instanceof HandlerMethod) {
			  String appkey=request.getHeader("appkey")==null?"":request.getHeader("appkey");
			 //�������ظ��ύҳ��
			  AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
			  if(appkey.equals("")){
				    PrintWriter writer = response.getWriter();
				    dto.setSuccess("false");
				    dto.setSyscode(SysCode.SYS_APPKEY_EMPTY.getCode());
	                writer.write(JSONObject.fromObject(dto).toString());
	                writer.flush();
	                //writer.close();
	                return false;
			 }else if(!validateAppKeyIsValid(appkey,appkeylist)){
				    PrintWriter writer = response.getWriter();
				    dto.setSuccess("false");
				    dto.setSyscode(SysCode.SYS_APPKEY_ERROR.getCode());
	                writer.write(JSONObject.fromObject(dto).toString());
	                writer.flush();
	                //writer.close();
	                return false;
			}
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }
    
    /**
     * �ж�appkey�Ƿ���Ч
     * @param key
     * @param keylist
     * @return
     */
    public boolean validateAppKeyIsValid(String key,List<CodeValue> keylist){
    	for(int i=0;i<keylist.size();i++){
    		if(key.equals(keylist.get(i).getCode_value())){
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * afterCompletion
     */
    @Override  
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,  Object handler, Exception ex) throws Exception {  
        long endTime = System.currentTimeMillis();//2������ʱ��  
        long beginTime = startTimeThreadLocal.get();//�õ��̰߳󶨵ľֲ���������ʼʱ�䣩  
        long consumeTime = endTime - beginTime;//3�����ĵ�ʱ��  
        if(consumeTime > 500) {//�˴���Ϊ����ʱ�䳬��500���������Ϊ������  
            //TODO ��¼����־�ļ�  
            log.info(String.format("%s consume %d millis", request.getRequestURI(), consumeTime));  
        }     
        SLog slog=LogUtil.parseRequestToLog(request, response, ex);
        slog.setLogtype("api");//��־����
        slog.setCost(new Long(endTime - beginTime).toString());
        slog.setSuccess("true");
        jmsProducerService.sendMessage(JSONObject.fromObject(slog).toString());
		//logservice.saveLogInfo(slog);
    }  
    
}
