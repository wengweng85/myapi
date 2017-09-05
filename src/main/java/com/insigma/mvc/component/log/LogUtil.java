package com.insigma.mvc.component.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	        if(e!=null&&e.getMessage()!=null){
	        	 slog.setMessage(e.getMessage().length()>500?e.getMessage().substring(0,499):e.getMessage()); 
	        	 slog.setExceptiontype(e.getClass().getName());
	        	 slog.setStackmsg(getStackMsg(e));
	        }
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
	        return slog;
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
	    return sb.toString();
    }

}
