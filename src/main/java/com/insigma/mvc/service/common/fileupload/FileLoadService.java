package com.insigma.mvc.service.common.fileupload;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.SFileRecord;




/**
 * 文件上传service
 * @author wengsh
 *
 */
public interface FileLoadService {
	 public AjaxReturnMsg<SFileRecord> getFileInfo(String file_uuid);  
	 public String upload(String originalFilename,String file_bus_type,InputStream in);
	 public byte[] download(String file_path);
 	 public AjaxReturnMsg<HashMap<String,Object>> getFileList( SFileRecord sFileRecord );
 	 public AjaxReturnMsg<String> deleteFileByBusUuid(String file_uuid);
 	 public AjaxReturnMsg<String> batDeleteData(SFileRecord sFileRecord) ;
 	 public AjaxReturnMsg<String> batupdateBusIdByBusUuidArray(Map<String,Object> map);
}
