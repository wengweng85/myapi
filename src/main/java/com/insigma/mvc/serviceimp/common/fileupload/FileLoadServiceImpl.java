package com.insigma.mvc.serviceimp.common.fileupload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.common.fileupload.FileLoadMapper;
import com.insigma.mvc.model.SFileRecord;
import com.insigma.mvc.service.common.fileupload.FileLoadService;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeTypeService;

/**
 *
 * @author wengsh
 *
 */
@Service
public class FileLoadServiceImpl  extends MvcHelper<SFileRecord> implements FileLoadService {

	@Value("${localdir}")
	private String localdir;

	@Resource
	private FileLoadMapper fileLoadMapper;
	
	@Resource
	private SysCodeTypeService sysCodeTypeService;

	public String getLocaldir() {
		return localdir;
	}

	public void setLocaldir(String localdir) {
		this.localdir = localdir;
	}
	
	
	/**
	 * ͨ��ҵ��id��ȡ�ļ��б�
	 */
	@Override
	public AjaxReturnMsg<HashMap<String, Object>> getFileList(SFileRecord sFileRecord) {
	    //PageHelper.offsetPage(sFileRecord.getOffset(), sFileRecord.getLimit());
		List<SFileRecord> list=fileLoadMapper.getBusFileRecordListByBusId(sFileRecord);
		PageInfo<SFileRecord> pageinfo = new PageInfo<SFileRecord>(list);
		return this.success_hashmap_response(pageinfo);
	}
	

	/**
	 * �õ��ļ���Ϣ
	 */
	@Override
	public AjaxReturnMsg<SFileRecord> getFileInfo(String file_uuid) {
		return this.success(fileLoadMapper.getBusFileRecordByBusId(file_uuid));
	}

	
	@Override
	public AjaxReturnMsg<String> deleteFileByBusUuid(String bus_uuid) {
		//��¼ɾ��
		int deletenum= fileLoadMapper.deleteFileByBusUuid(bus_uuid);
		if(deletenum==1){
			return this.success("ɾ���ɹ�");
		}else{
			return this.error("ɾ��ʧ��,��ȷ���ļ��Ƿ����");
		}
	}

	/**
	 * ����ɾ��
	 */
	@Override
	@Transactional
	public AjaxReturnMsg<String> batDeleteData(SFileRecord sFileRecord) {
		String [] ids=sFileRecord.getSelectnodes().split(",");
		for(int i=0;i<ids.length;i++){
			deleteFileByBusUuid(ids[i]);
		}
		int batdeletenum=fileLoadMapper.batDeleteData(ids);
		if(batdeletenum==ids.length){
			return this.success("����ɾ���ɹ�");
		}else{
			return this.success("����ɾ���ɹ�,������ʧ�ܵ�����,����");
		}
	}

	
	@Override
	@Transactional
	public AjaxReturnMsg<String> batupdateBusIdByBusUuidArray( Map<String,Object> map) {
		  fileLoadMapper.batupdateBusIdByBusUuidArray(map);
		  return this.success("���³ɹ�");
	}

}