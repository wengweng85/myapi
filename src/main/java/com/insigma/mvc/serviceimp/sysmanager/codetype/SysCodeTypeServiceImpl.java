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
 * ϵͳ����֮�������
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
		//����
		if(codetype.getIsupdate() .equals("1")){
			sysCodeTypeMapper.updateCodeType(codetype);
			return this.success_obj("�޸ĳɹ�",codetype);
		}
		//����
		else{
			//�ж�codetype�Ƿ��ظ�
			if(sysCodeTypeMapper.getCodeTypeInfo(codetype.getCode_type())!=null){
                  return this.error("�Ѿ����ڴ�������Ϊ"+codetype.getCode_type()+"�Ĵ���,�����ظ�,��ȷ������");				
			}
			sysCodeTypeMapper.addCodeType(codetype);
			return this.success_obj("�����ɹ�",codetype);
		}
	}
	
	
	@Override
	@Transactional
	public AjaxReturnMsg<String> deleteCodeType(String code_type){
		sysCodeTypeMapper.deleteCodeTypeByType(code_type);
		sysCodeTypeMapper.deleteCodeValueByType(code_type);
		return this.success("ɾ������"+code_type+"�ɹ�");
	}
	
	
}