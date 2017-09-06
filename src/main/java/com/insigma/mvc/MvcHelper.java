package com.insigma.mvc;

import java.util.HashMap;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;


/**
 * mvc帮助类，主要用于包装controller以及serviceimp层返回的数据
 * 将其包装成类返回成json格式
 * @author wengsh
 *
 */
public class MvcHelper<T> {
	
	public AjaxReturnMsg<String> validate(BindingResult result){
		 FieldError fielderror=result.getFieldErrors().get(result.getErrorCount()-1);
		 AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
	     dto.setSuccess("false");
	     dto.setMessage(fielderror.getDefaultMessage());
	     dto.setObj(fielderror.getField());
	     return dto;
	}

    /**
     * 成功返回
     * @param message
     * @return
     */
    public AjaxReturnMsg<String> success(String message) {
        AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
        dto.setSuccess("true");
        dto.setMessage(message);
        return dto;
    }
    

    /**
     * 成功返回
     * @param o
     * @return
     */
    public AjaxReturnMsg<T> success(String message,T o) {
        AjaxReturnMsg<T> dto = new AjaxReturnMsg<T>();
        dto.setSuccess("true");
        dto.setMessage(message);
        dto.setObj(o);
        return dto;
    }
    
    
	public AjaxReturnMsg success_obj(String message,Object o) {
        AjaxReturnMsg dto = new AjaxReturnMsg();
        dto.setSuccess("true");
        dto.setMessage(message);
        dto.setObj(o);
        return dto;
    }
    
    /**
     * 成功返回
     * @param o
     * @return
     */
    public AjaxReturnMsg<T> success(T o) {
        AjaxReturnMsg<T> dto = new AjaxReturnMsg<T>();
        dto.setSuccess("true");
        dto.setObj(o);
        return dto;
    }
    
    /**
     * 成功返回
     * @param o
     * @return
     */
    public AjaxReturnMsg<List<T>> success(List<T> a) {
        AjaxReturnMsg<List<T>> dto = new AjaxReturnMsg<List<T>>();
        dto.setSuccess("true");
        dto.setObj(a);
        return dto;
    }
    /**
     * 成功返回
     * @param PageInfo pageinfo
     * @return
     */
    public AjaxReturnMsg<List<T>> success(PageInfo<T> pageinfo) {
        AjaxReturnMsg<List<T>> dto = new AjaxReturnMsg<List<T>>();
        dto.setSuccess("true");
        dto.setObj(pageinfo.getList());
        dto.setTotal(pageinfo.getTotal());
        return dto;
    }
    
    /**
     * 成功返回
     * @param PageInfo pageinfo
     * @return
     */
    public AjaxReturnMsg<HashMap<String,Object>> success_hashmap_response(PageInfo<T> pageinfo) {
    	AjaxReturnMsg<HashMap<String,Object>> dto =new AjaxReturnMsg<HashMap<String,Object>>();
    	HashMap<String,Object> hashmap=new HashMap<String,Object>();
    	hashmap.put("total", pageinfo.getTotal());
    	hashmap.put("rows", pageinfo.getList());
    	dto.setSuccess("true");
    	dto.setObj(hashmap);
    	return dto;
    }
    
    /**
     * 成功返回
     * @param PageInfo pageinfo
     * @return
     */
    public AjaxReturnMsg<HashMap<String,List<T>>> success_hashmap_response(HashMap hashmap) {
    	AjaxReturnMsg<HashMap<String,List<T>>> dto =new AjaxReturnMsg<HashMap<String,List<T>>>();
    	dto.setSuccess("true");
    	dto.setObj(hashmap);
    	return dto;
    }

    
    /**
     * 错误返回
     * @param message
     * @return
     */
    public AjaxReturnMsg<String> error(String message) {
        AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
        dto.setSuccess("false");
        dto.setMessage(message);
        return dto;
    }
    
    /**
     * 错误返回
     * @param messagecode 业务错误码
     * @param message 业务错误说明
     * @return
     */
    public AjaxReturnMsg<String> error(String messagecode,String message) {
        AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
        dto.setSuccess(messagecode);
        dto.setMessage(message);
        return dto;
    }

	 /**
	  * 错误返回
	  * @param messagecode 业务错误码
      * @param message 业务错误说明
	  * @param obj
	  * @return
	  */
    public AjaxReturnMsg<T> error(String messagecode,String message,T obj) {
        AjaxReturnMsg<T> dto = new AjaxReturnMsg<T>();
        dto.setSuccess(messagecode);
        dto.setMessage(message);
        dto.setObj(obj);
        return dto;
    }

    /**
     * 错误返回
     * @param o
     * @return
     */
    public AjaxReturnMsg<T> error(T o) {
        AjaxReturnMsg<T> dto = new AjaxReturnMsg<T>();
        dto.setSuccess("false");
        dto.setObj(o);
        return dto;
    }

    /**
     * 错误返回
     * @param e
     * @return
     */
    public AjaxReturnMsg<String> error(Exception e) {
        AjaxReturnMsg<String>  dto = new AjaxReturnMsg<String>();
        dto.setSuccess("false");
        dto.setMessage(e.getLocalizedMessage());
        return dto;
    }

}
