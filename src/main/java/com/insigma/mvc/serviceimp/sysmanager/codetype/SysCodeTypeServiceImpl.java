package com.insigma.mvc.serviceimp.sysmanager.codetype;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.sysmanager.codetype.SysCodeTypeMapper;
import com.insigma.mvc.model.CodeType;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeTypeService;

/**
 * 系统管理之代码管理
 * @author wengsh
 *
 */
@Service
public class SysCodeTypeServiceImpl extends MvcHelper<CodeType> implements SysCodeTypeService {

	@Resource
	private SysCodeTypeMapper sysCodeTypeMapper;
	
	


	@Override
	public AjaxReturnMsg<List<CodeType>> getInitcodetypeList() {
		// TODO Auto-generated method stub
	   return this.success(sysCodeTypeMapper.getInitcodetypeList());
	}



	@Override
	public AjaxReturnMsg<List<CodeType>> getCodeTypeTreeData(CodeType codetype) {
		return this.success(sysCodeTypeMapper.getCodeTypeTreeData(codetype));
	}



	@Override
	public AjaxReturnMsg<CodeType> getCodeTypeInfo(String code_type) {
		return this.success(sysCodeTypeMapper.getCodeTypeInfo(code_type));
	}

	
	@Override
	public AjaxReturnMsg<String> saveOrUpdateCodeType(CodeType codetype) {
		//更新
		if(codetype.getIsupdate() .equals("1")){
			sysCodeTypeMapper.updateCodeType(codetype);
			return this.success_obj("修改成功",codetype);
		}
		//新增
		else{
			//判断codetype是否重复
			if(sysCodeTypeMapper.getCodeTypeInfo(codetype.getCode_type())!=null){
                  return this.error("已经存在代码类型为"+codetype.getCode_type()+"的代码,不能重复,请确认输入");				
			}
			sysCodeTypeMapper.addCodeType(codetype);
			return this.success_obj("新增成功",codetype);
		}
	}
	
	
	@Override
	@Transactional
	public AjaxReturnMsg<String> deleteCodeType(String code_type){
		sysCodeTypeMapper.deleteCodeTypeByType(code_type);
		sysCodeTypeMapper.deleteCodeValueByType(code_type);
		return this.success("删除代码"+code_type+"成功");
	}
	
	
}