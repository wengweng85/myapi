package com.insigma.mvc.service.sysmanager.codetype;

import java.util.List;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.CodeType;


/**
 * Ö÷Ò³service
 * @author wengsh
 *
 */
public interface SysCodeTypeService {
	
	public AjaxReturnMsg<List<CodeType>> getInitcodetypeList();
	public AjaxReturnMsg<List<CodeType>> getCodeTypeTreeData(CodeType codetype);
	public AjaxReturnMsg<String> saveOrUpdateCodeType(CodeType codetype);
	public AjaxReturnMsg<String> deleteCodeType(String code_type);
	public AjaxReturnMsg<CodeType> getCodeTypeInfo(String code_type);
}
