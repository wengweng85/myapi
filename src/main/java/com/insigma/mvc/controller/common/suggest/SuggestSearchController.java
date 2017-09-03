package com.insigma.mvc.controller.common.suggest;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.SuggestKey;
import com.insigma.mvc.service.common.suggest.SuggestSearchService;
import com.insigma.resolver.AppException;

/**
 * ��������controller
 */
@RestController
@RequestMapping(value = "/common/suggest")
public class SuggestSearchController extends MvcHelper<SuggestKey> {


    Log log = LogFactory.getLog(SuggestSearchController.class);

    @Resource
    private SuggestSearchService suggestSearchService;
    
    /* 
	 * ͨ�������ؼ���
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/searchcode",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<HashMap<String,List<SuggestKey>>> searchcodebykey(HttpServletRequest request, HttpServletResponse response, SuggestKey key) throws AppException {
		return suggestSearchService.searchByKey(key);
	}

}
