package com.insigma.mvc.controller.sysmanager.codetype;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.CodeType;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeTypeService;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeValueService;
import com.insigma.resolver.AppException;

/**
 * Created by wengsh on 2015-01-14.
 */
@Controller
@RequestMapping("/codetype")
public class SysCodeTypeController extends MvcHelper<CodeValue> {

	@Resource
	private SysCodeTypeService sysCodeTypeService;
	
	@Resource
	private SysCodeValueService sysCodeValueService;
	
	/**
	 * getInitcodetypeList 代码初始化
	 * @param request
	 * @param response
	 * @param code_type
	 * @return
	 * @throws AppException
	 */
	@RequestMapping(value = "/getInitcodetypeList")
	@ResponseBody
	public AjaxReturnMsg<List <CodeType>> getInitcodetypeList(HttpServletRequest request, HttpServletResponse response) throws AppException {
		return sysCodeTypeService.getInitcodetypeList();
	}
	
	/**
	 * getInitcodetypeList 代码初始化
	 * @param request
	 * @param response
	 * @param code_type
	 * @return
	 * @throws AppException
	 */
	@RequestMapping(value = "/getInitCodeValueList/{code_type}")
	@ResponseBody
	public AjaxReturnMsg<List <CodeValue>> getInitCodeValueList(HttpServletRequest request, HttpServletResponse response,@PathVariable String code_type) throws AppException {
		CodeType codetype=new CodeType();
		codetype.setCode_type(code_type);
		return sysCodeValueService.getInitCodeValueList(codetype);
	}
	
	
	/**
	 *codevalue 代码树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/treedata/{code_type}")
	@ResponseBody
	public AjaxReturnMsg<List<CodeValue>> treedata(HttpServletRequest request, HttpServletResponse response,@PathVariable String code_type) throws AppException {
		String id=request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			id="000000";
		}
		CodeValue codevalue=new CodeValue();
		codevalue.setPar_code_value(id);
		codevalue.setCode_type(code_type.toUpperCase());
		return sysCodeValueService.getCodeValueTree(codevalue);
	}
	
	/**
	 * 根据代码类型及代码父类名获取代码值
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/queryByCodeTypeAndParent")
	@ResponseBody
	public AjaxReturnMsg<List<CodeValue>> queryByCodeTypeAndParent(HttpServletRequest request, HttpServletResponse response,@RequestBody CodeValue codevalue) throws AppException {
		return sysCodeValueService.queryCodeValueByCodeTypeAndParent(codevalue);
	}
	
	
	
	/**
	 * 跳转至代码管理页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	@RequiresRoles("admin")
	public ModelAndView draglist(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("sysmanager/codevalue/sysCodeTypeIndex");
        return modelAndView;
	}
	
	
	/**
	 *codetype 代码树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/codetype_treedata")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxReturnMsg<List<CodeType>> codetype_treedata(HttpServletRequest request, HttpServletResponse response,@RequestBody CodeType codetype) throws AppException {
		return sysCodeTypeService.getCodeTypeTreeData(codetype);
	}
	

	
	/**
	 *codevalue 代码树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/codevalue_treedata")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxReturnMsg<List<CodeType>> codevalue_treedata(HttpServletRequest request, HttpServletResponse response,@RequestBody CodeType  codetype) throws AppException {
		return sysCodeTypeService.getCodeValueTreeData(codetype);
	}
	
	
	
	/**
	 * 更新或保存代码类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveOrUpdateCodeType")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxReturnMsg<String> saveOrUpdateCodeTypedata(HttpServletRequest request,Model model,@RequestBody @Valid CodeType codetype,BindingResult result) throws Exception {
		//检验输入
		if (result.hasErrors()){
			return validate(result);
		}
		return sysCodeTypeService.saveOrUpdateCodeType(codetype);
	}
	
	
	
	
	
	/**
	 * 更新或保存代码类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveOrUpdateCodeTypeDetail")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxReturnMsg<String> saveOrUpdateCodeTypeDetail(HttpServletRequest request,Model model,@RequestBody @Valid CodeValue codevalue,BindingResult result) throws Exception {
		//检验输入
		if (result.hasErrors()){
			return validate(result);
		}
		return sysCodeValueService.saveOrUpdateCodeValue(codevalue);
	}
	
	
	/**
	 * 删除代码类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteCodeType/{code_type}")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxReturnMsg<String> deleteCodeType(HttpServletRequest request,Model model,@PathVariable String code_type) throws Exception {
		return sysCodeTypeService.deleteCodeType(code_type);
	}
	
	
	/**
	 * 删除代码值
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteCodeValue/{code_seq}")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxReturnMsg<String> deleteCodeValue(HttpServletRequest request,Model model,@PathVariable String code_seq) throws Exception {
		return sysCodeValueService.deleteCodeValue(code_seq);
	}
	
	  
	 /**
	  * 通过代码类型、过滤条件获取代码 
	  * @param request
	  * @param response
	  * @param codevalue
	  * @return
	  * @throws AppException
	  */
	 @RequestMapping(value = "/getCodeValueList")
	 @ResponseBody
	 public AjaxReturnMsg<List<CodeValue>> getCodeValueList(HttpServletRequest request, HttpServletResponse response,@RequestBody CodeType codetype) throws AppException {
		   return sysCodeValueService.getInitCodeValueList(codetype);
	 } 
	
	
}