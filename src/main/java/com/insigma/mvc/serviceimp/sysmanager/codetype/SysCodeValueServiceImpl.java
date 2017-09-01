package com.insigma.mvc.serviceimp.sysmanager.codetype;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.sysmanager.codetype.SysCodeTypeMapper;
import com.insigma.mvc.model.CodeType;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeValueService;

/**
 * ϵͳ����֮�������
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
	public AjaxReturnMsg<List<CodeValue>> getInitCodeValueList(CodeType codetype) {
		// TODO Auto-generated method stub
		return this.success(sysCodeTypeMapper.getInitCodeValueList(codetype));
	}

	
	
	
	@Override
	public AjaxReturnMsg<CodeValue> getCodeTypeDetailInfo(String code_seq) {
		return this.success(sysCodeTypeMapper.getCodeTypeDetailInfo(code_seq));
	}
	
	@Override
	public AjaxReturnMsg<String> saveOrUpdateCodeValue(CodeValue codevalue) {
		//����
		if(codevalue.getCode_seq() .equals("")){
			//�ж�codetype�Ƿ��ظ�
			if(sysCodeTypeMapper.getCodeTypeDetailByValue(codevalue)!=null){
                  return this.error("�Ѿ����ڴ���ֵΪ"+codevalue.getCode_value()+"�Ĵ���,�����ظ�,��ȷ������");				
			}
			sysCodeTypeMapper.addCodeTypeDetail(codevalue);
			return this.success_obj("�����ɹ�",codevalue);
		}
		//�޸�
		else{
			sysCodeTypeMapper.updateCodeTypeDetail(codevalue);
			return this.success_obj("�޸ĳɹ�",codevalue);
		}
	}
	
	
	@Override
	public AjaxReturnMsg<String> deleteCodeValue(String code_seq){
		CodeValue codevalue=sysCodeTypeMapper.getCodeTypeDetailInfo(code_seq);
		if(codevalue!=null){
			sysCodeTypeMapper.deleteCodeValueBySeq(code_seq);
			return this.success("ɾ��"+codevalue.getCode_type()+"��"+codevalue.getCode_name()+"����ɹ�");
		}else{
			return this.error("ɾ��ʧ��,");
		}
	}
	

	@Override
	public AjaxReturnMsg<CodeValue> getCodeValueByValue(CodeValue codevalue) {
		 return this.success(sysCodeTypeMapper.getCodeValueByValue(codevalue));
	}
	

	
}