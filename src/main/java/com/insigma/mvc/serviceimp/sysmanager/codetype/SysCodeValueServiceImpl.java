package com.insigma.mvc.serviceimp.sysmanager.codetype;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.sysmanager.codetype.SysCodeTypeMapper;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeValueService;

/**
 * 系统管理之代码管理
 * @author wengsh
 *
 */
@Service
public class SysCodeValueServiceImpl extends MvcHelper<CodeValue> implements SysCodeValueService {

	@Resource
	private SysCodeTypeMapper sysCodeTypeMapper;
	
	
	public AjaxReturnMsg<List<CodeValue>> queryCodeValueByCodeTypeAndParent(CodeValue codevalue){
		return this.success(sysCodeTypeMapper.queryCodeValueByCodeTypeAndParent(codevalue));
	}
	
	
	@Override
	public AjaxReturnMsg<List<CodeValue>> getCodeValueTree(CodeValue codevalue) {
		 return this.success(sysCodeTypeMapper.getCodeValueTree(codevalue));
	}



	@Override
	public AjaxReturnMsg<List<CodeValue>> getInitCodeValueList(CodeValue codevalue) {
		// TODO Auto-generated method stub
		return this.success(sysCodeTypeMapper.getInitCodeValueList(codevalue));
	}

	@Override
	public AjaxReturnMsg<List<CodeValue>> getCodeValueTreeData(CodeValue codevalue) {
		//初次加载
		if(StringUtils.isEmpty(codevalue.getId())&& StringUtils.isEmpty(codevalue.getCode_root_value())){
			return this.success(sysCodeTypeMapper.getCodeValueByType(codevalue));
		}else{
			if(!StringUtils.isEmpty(codevalue.getId())){
				codevalue.setCode_root_value(codevalue.getId());
			}
			return this.success(sysCodeTypeMapper.getCodeValueByTypeAndRoot(codevalue));
		}
	}
	
	
	
	@Override
	public AjaxReturnMsg<CodeValue> getCodeTypeDetailInfo(String code_seq) {
		return this.success(sysCodeTypeMapper.getCodeTypeDetailInfo(code_seq));
	}
	
	@Override
	public AjaxReturnMsg<String> saveOrUpdateCodeValue(CodeValue codevalue) {
		//新增
		if(codevalue.getCode_seq() .equals("")){
			//判断codetype是否重复
			if(sysCodeTypeMapper.getCodeTypeDetailByValue(codevalue)!=null){
                  return this.error("已经存在代码值为"+codevalue.getCode_value()+"的代码,不能重复,请确认输入");				
			}
			sysCodeTypeMapper.addCodeTypeDetail(codevalue);
			return this.success_obj("新增成功",codevalue);
		}
		//修改
		else{
			sysCodeTypeMapper.updateCodeTypeDetail(codevalue);
			return this.success_obj("修改成功",codevalue);
		}
	}
	
	
	@Override
	public AjaxReturnMsg<String> deleteCodeValue(String code_seq){
		CodeValue codevalue=sysCodeTypeMapper.getCodeTypeDetailInfo(code_seq);
		if(codevalue!=null){
			sysCodeTypeMapper.deleteCodeValueBySeq(code_seq);
			return this.success("删除"+codevalue.getCode_type()+"的"+codevalue.getCode_name()+"代码成功");
		}else{
			return this.error("删除失败,");
		}
	}
	

	@Override
	public AjaxReturnMsg<CodeValue> getCodeValueByValue(CodeValue codevalue) {
		 return this.success(sysCodeTypeMapper.getCodeValueByValue(codevalue));
	}
	

	
}