package com.insigma.mvc.service.sysmanager.codetype;

import java.util.List;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.CodeType;
import com.insigma.mvc.model.CodeValue;


/**
 * Ö÷Ò³service
 * @author wengsh
 *
 */
public interface SysCodeValueService {
	
	public AjaxReturnMsg<List<CodeValue>> getInitCodeValueList(CodeType codetype);
	public AjaxReturnMsg<List<CodeValue>> queryCodeValueByCodeTypeAndParent(CodeValue codevalue);
	public AjaxReturnMsg<CodeValue> getCodeValueByValue(CodeValue codevalue);
	public AjaxReturnMsg<List<CodeValue>> getCodeValueTree(CodeValue codevalue);
	public AjaxReturnMsg<CodeValue> getCodeTypeDetailInfo(String code_seq);
	public AjaxReturnMsg<String> saveOrUpdateCodeValue(CodeValue codevalue);
	public AjaxReturnMsg<String> deleteCodeValue(String code_seq);
}
