package com.insigma.mvc.controller.common.fileupload;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.controller.common.suggest.SuggestSearchController;
import com.insigma.mvc.model.SFileRecord;
import com.insigma.mvc.service.common.fileupload.FileLoadService;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeTypeService;

/**
 * 文件上传及下载
 */
@RestController
@RequestMapping(value = "/common/fileload")
public class FileLoadController extends MvcHelper<SFileRecord> {


    Log log = LogFactory.getLog(SuggestSearchController.class);

    @Resource
    private FileLoadService fileloadservice;
    
    @Resource
    private SysCodeTypeService syscodetypeservice;
    
	/**
	 * 通过人员id获取文件列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFileList",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<HashMap<String,Object>> getUserListByGroupid(HttpServletRequest request,@RequestBody SFileRecord sFileRecord ) throws Exception {
		return fileloadservice.getFileList(sFileRecord);
	}
	
	/**
	 * 通过业务id获取文件记录表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFileInfo/{bus_uuid}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<SFileRecord> getUserListByGroupid(HttpServletRequest request,@PathVariable String bus_uuid ) throws Exception {
		return fileloadservice.getFileInfo(bus_uuid);
	}

    
    /**
	 * 通过id删除文件信息
	 * @param request
	 * @param model
	 * @param aac001
	 * @return
	 */
	@RequestMapping(value="/deletebyid/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> deleteFileByid(HttpServletRequest request,@PathVariable String id){
		return fileloadservice.deleteFileByBusUuid(id);
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @param model
	 * @param aac001
	 * @return
	 */
	@RequestMapping(value="/batdelete",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> batdelete(HttpServletRequest request,@RequestBody SFileRecord sFileRecord){
		return fileloadservice.batDeleteData(sFileRecord);
	}

}
