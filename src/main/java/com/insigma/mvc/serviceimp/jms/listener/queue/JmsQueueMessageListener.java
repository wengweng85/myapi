package com.insigma.mvc.serviceimp.jms.listener.queue;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.insigma.mvc.model.SLog;
import com.insigma.mvc.service.log.LogService;

/**
 * 消息接收服务
 * @author wengsh
 *
 */
public class JmsQueueMessageListener  implements MessageListener {
	
     @Resource
     private LogService logservice;
    /**
     * 接收消息
     */
    public void onMessage(Message message) {
        try{
	        // 如果是文本消息
	        if (message instanceof TextMessage) {
	        	  String text=((TextMessage) message).getText();
	        	  try
	        	  {
	        	     //将字符串转换成jsonObject对象
	        	     JSONObject myJsonObject =JSONObject.fromObject(text);
	        	     SLog slog = (SLog) JSONObject.toBean(myJsonObject, SLog.class);
			         logservice.saveLogInfo(slog);
	        	  }
	        	  catch (JSONException e)
	        	  {
	        		  e.printStackTrace();
	        	  }
	        }
	
	        // 如果是Map消息
	        if (message instanceof MapMessage) {
	            MapMessage mapmessage = (MapMessage) message;
	        }
	
	        // 如果是Object消息
	        if (message instanceof ObjectMessage) {
	            ObjectMessage om = (ObjectMessage) message;
	        }
	
	        // 如果是bytes消息
	        if (message instanceof BytesMessage) {
	            byte[] b = new byte[1024];
	            int len = -1;
	            BytesMessage bm = (BytesMessage) message;
	            while ((len = bm.readBytes(b)) != -1) {
	                System.out.println(new String(b, 0, len));
	            }
	        }
	
	        // 如果是Stream消息
	        if (message instanceof StreamMessage) {
	            StreamMessage sm = (StreamMessage) message;
	            System.out.println(sm.readString());
	            System.out.println(sm.readInt());
	        }
        }catch(JMSException e){
        	e.printStackTrace();
        }
    }
}
