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

import org.springframework.stereotype.Component;

import com.insigma.mvc.model.SLog;
import com.insigma.mvc.service.log.LogService;

/**
 * ��Ϣ���շ���
 * @author wengsh
 *
 */
@Component
public class JmsQueueMessageListener  implements MessageListener {
	
    @Resource
	private LogService logservice;
     
    /**
     * ������Ϣ
     */
    public void onMessage(Message message) {
        try{
	        // ������ı���Ϣ
	        if (message instanceof TextMessage) {
	        }
	
	        // �����Map��Ϣ
	        if (message instanceof MapMessage) {
	            MapMessage mm = (MapMessage) message;
	            System.out.println("��Ĭ�϶����л�ȡMapMessage��\t" + mm.getString("msgId"));
	        }
	
	        // �����Object��Ϣ
	        if (message instanceof ObjectMessage) {
	            ObjectMessage om = (ObjectMessage) message;
	            SLog slog = (SLog) om.getObject();
	            logservice.saveLogInfo(slog);
	        }
	
	        // �����bytes��Ϣ
	        if (message instanceof BytesMessage) {
	            byte[] b = new byte[1024];
	            int len = -1;
	            BytesMessage bm = (BytesMessage) message;
	            while ((len = bm.readBytes(b)) != -1) {
	                System.out.println(new String(b, 0, len));
	            }
	        }
	
	        // �����Stream��Ϣ
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
