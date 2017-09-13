package com.insigma.mvc.component.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.CoyoteOutputStream;
import org.apache.catalina.connector.OutputBuffer;
import org.apache.tomcat.util.buf.ByteChunk;

import com.insigma.common.util.IPUtil;
import com.insigma.mvc.model.SLog;

public class LogUtil {
	
	/**
	 * 文件记录
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 */
	public static SLog parseRequestToLog(HttpServletRequest request, HttpServletResponse response, Exception e){
	        SLog slog=new SLog();
	        /*if(e!=null&&e.getMessage()!=null){
	        	 slog.setMessage(e.getMessage().length()>500?e.getMessage().substring(0,499):e.getMessage()); 
	        	 slog.setExceptiontype(e.getClass().getName());
	        	 slog.setStackmsg(getStackMsg(e));
	        }*/
	        //请求信息
	        slog.setUrl(request.getRequestURL().toString());
	        slog.setMethod(request.getMethod());
	        slog.setQueryparam(getQueryParam(request));
	        String ip=IPUtil.getClientIpAddr(request);
	        slog.setIpaddr(ip);
	        slog.setAppkey(request.getHeader("appkey"));
	        slog.setUsergent(request.getHeader("user-agent"));
	        slog.setReferer(request.getHeader("Referer"));
	        StringBuffer url=request.getRequestURL();
	        if(request.getQueryString()!=null&&!("").equals(request.getQueryString())){
	            url.append("?"+request.getQueryString());
	        }
	        slog.setUrl(url.toString());
	        String cookie="";
	        if(request.getCookies()!=null){
	             Cookie[] cookies=request.getCookies();
	             for(int i=0;i<cookies.length;i++){
	                 Cookie tempcookie=cookies[i];
	                 cookie+=tempcookie.getName()+":"+tempcookie.getValue();
	            }
	            slog.setCookie(cookie.length() >500?cookie.substring(0,499):cookie);
	        }
	        //response信息
	        try{
	            slog.setResponsemsg(parseResponse(response));
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        }
	    
	        return slog;
	}
	
	/**
	 * 
	 * @param response
	 * @throws Exception
	 */
	public static String  parseResponse(HttpServletResponse response) throws Exception{
		String val="";
		// 截取响应流  
	    CoyoteOutputStream os = (CoyoteOutputStream) response.getOutputStream();  
	    // 取到流对象对应的Class对象  
	    Class<CoyoteOutputStream> c = CoyoteOutputStream.class;  
	    // 取出流对象中的OutputBuffer对象，该对象记录响应到客户端的内容  
	    Field fs = c.getDeclaredField("ob");  
	    if (fs.getType().toString().endsWith("OutputBuffer")) {  
	        fs.setAccessible(true);// 设置访问ob属性的权限  
	        OutputBuffer ob = (OutputBuffer) fs.get(os);// 取出ob  
	        Class<OutputBuffer> cc = OutputBuffer.class;  
	        Field ff = cc.getDeclaredField("outputChunk");// 取到OutputBuffer中的输出流  
	        ff.setAccessible(true);  
	        if (ff.getType().toString().endsWith("ByteChunk")) {  
	            ByteChunk bc = (ByteChunk) ff.get(ob);// 取到byte流  
	             val = new String(bc.getBytes(), "UTF-8");// 最终的值  
	        }
	    }  
	    return val;
	}
	
    /**
     * 将异常打印出来
     * @param e
     * @return
     */
    private static String getStackMsg(Exception e) {
    	if(e!=null){
		    StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw);
	        e.printStackTrace(pw);
	        return sw.toString();
    	}else{
    		return "";
    	}
    }

    
    /**
     * 得到请求参数
     * @param request
     * @return
     */
    public static String getQueryParam(HttpServletRequest request){
	    StringBuffer sb=new StringBuffer();
    	Map map=request.getParameterMap();  
	    Set keSet=map.entrySet();  
	    for(Iterator itr=keSet.iterator();itr.hasNext();){  
	        Map.Entry me=(Map.Entry)itr.next();  
	        Object ok=me.getKey();  
	        Object ov=me.getValue();  
	        String[] value=new String[1];  
	        if(ov instanceof String[]){  
	            value=(String[])ov;  
	        }else{  
	            value[0]=ov.toString();  
	        }  
	        for(int k=0;k<value.length;k++){  
	        	sb.append(ok+"="+value[k]+"&");  
	        }  
	      }  
	    if(sb.length()>0){
	    	return sb.deleteCharAt(sb.length()-1).toString();
	    }else{
	    	return "";
	    }
    }

}
