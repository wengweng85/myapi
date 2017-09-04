package com.insigma.mvc.controller.common.fileupload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.controller.common.suggest.SuggestSearchController;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.model.SFileRecord;
import com.insigma.mvc.service.common.fileupload.FileLoadService;
import com.insigma.mvc.service.sysmanager.codetype.SysCodeValueService;
import com.insigma.resolver.AppException;

/**
 * �ļ��ϴ�������
 */
@RestController
@RequestMapping(value = "/common/fileload")
public class FileLoadController extends MvcHelper<SFileRecord> {


    Log log = LogFactory.getLog(SuggestSearchController.class);

    @Resource
    private FileLoadService fileloadservice;
    
    @Resource
    private SysCodeValueService syscodevalueservice;
    
    
	/**
     * �ļ��ϴ�
     *
     * @param request
     * @param response
     * @return
     * @throws AppException
     */
    @RequestMapping(value="/upload/{file_bus_type}",method=RequestMethod.POST, consumes=" multipart/form-data",  produces="application/json;charset=UTF-8")
    @ResponseBody
    public AjaxReturnMsg<String>  upload(HttpServletRequest request, HttpServletResponse response,@PathVariable String file_bus_type,@RequestParam("uploadFile") MultipartFile multipartFile) throws Exception {
		//String file_bus_id=request.getParameter("file_bus_id");
        //���ҵ���Ų���
       /*  if(null==file_bus_id||file_bus_id.equals("")){
        	return this.error( "ҵ���Ų���Ϊ��,����");
         }*/
        
      //���ҵ���Ų���
        if(null==file_bus_type||file_bus_type.equals("")){
        	return this.error( "�ļ�ҵ�����Ͳ���Ϊ��,����");
        }
        
        CodeValue codevalue=new CodeValue();
   		codevalue.setCode_type("FILE_BUS_TYPE");
   		codevalue.setCode_value(file_bus_type);
   		if(syscodevalueservice.getCodeValueByValue(codevalue)==null){
   			return this.error("ҵ��ͼƬ���ͱ���"+file_bus_type+"������,�����������FILE_BUS_TYPE���Ƿ����!");
   		}
        
		long MAX_SIZE = 20* 1024 * 1024L;//100m
    	try {
                if (multipartFile.getSize() > MAX_SIZE) {
                	return this.error( "�ļ��ߴ糬���涨��С:" + MAX_SIZE / 1024 / 1024 + "M");
                } else {
                    // �õ�ȥ��·�����ļ���
                    String originalFilename = multipartFile.getOriginalFilename();
                    int indexofdoute = originalFilename.lastIndexOf(".");
                    /**��ȡ�ļ��ĺ�׺**/
                    String endfix = "";
                    if (indexofdoute != -1) {
                        // β
                        endfix = originalFilename.substring(indexofdoute).toLowerCase();
                        if(endfix.equals(".jpg")||endfix.equals(".jpeg")||endfix.equals(".gif")||endfix.equals(".png") ||endfix.equals(".pdf")||endfix.equals(".doc")||endfix.equals(".docx")||endfix.equals(".xls")||endfix.equals(".xlsx")||endfix.equals(".rar")||endfix.equals(".zip")) {
     						//�ϴ�����¼��־
                        	String recordjson=fileloadservice.upload(originalFilename,file_bus_type,multipartFile.getInputStream() );
                        	System.out.println(recordjson);
                            return this.success(recordjson);
                        }else{
                        	return this.error("�ļ���ʽ����ȷ,��ȷ��,ֻ�����ϴ���ʽΪjpg��jpeg��gif��png��pdf��doc��docx��xls��xlsx��rar��zip��ʽ���ļ�");
                        }
                    }else{
                    	return this.error("�ļ���ʽ����");
                    }
                }
        } catch (Exception e) {
        	e.printStackTrace();
			// �����ļ��ߴ�����쳣
			if (e instanceof SizeLimitExceededException) {
				return this.error( "�ļ��ߴ糬���涨��С:" + MAX_SIZE / 1024 / 1024 + "M");
			}
			return this.error(e.getMessage());
        }
    }
    
    /**
     * �ļ�����
     * @param request
     * @param response
     * @throws AppException
     */
    @RequestMapping(value = "/download/{bus_uuid}",method=RequestMethod.GET,  produces="application/octet-stream")
    public void download(@PathVariable(value="bus_uuid") String bus_uuid, HttpServletRequest request ,HttpServletResponse response) throws  AppException{
    	try{
        	SFileRecord filerecord=fileloadservice.getFileInfo(bus_uuid).getObj();
        	if(filerecord!=null){
        		 byte[] temp=fileloadservice.download(filerecord.getFile_path());
                 if(temp!=null){
                 	//���д����Ƿ�ֹ��������Ĺؼ�����
                     response.setHeader("Content-disposition","attachment; filename="+URLEncoder.encode(filerecord.getFile_name(),"utf-8"));
                     BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(temp));
                     BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                     //�½�һ��2048�ֽڵĻ�����
                     byte[] buff = new byte[2048];
                     int bytesRead=0;
                     while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
                         bos.write(buff,0,bytesRead);
                     }
                     bos.flush();
                     if (bis != null)
                         bis.close();
                     if (bos != null)
                         bos.close();
                 }else{
                 	throw new Exception("���ش���,�����ڵ�id");
                 }
        	}
        }catch(Exception e){
            //log.error(e.getMessage());
        }
    }
    
    
	/**
	 * ͨ����Աid��ȡ�ļ��б�
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFileList",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<HashMap<String,Object>> getUserListByGroupid(HttpServletRequest request, SFileRecord sFileRecord ) throws Exception {
		return fileloadservice.getFileList(sFileRecord);
	}
	
	/**
	 * ͨ��ҵ��id��ȡ�ļ���¼��
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getFileInfo/{bus_uuid}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<SFileRecord> getUserListByGroupid(HttpServletRequest request,@PathVariable String bus_uuid ) throws Exception {
		return fileloadservice.getFileInfo(bus_uuid);
	}

    
    /**
	 * ͨ��idɾ���ļ���Ϣ
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
	 * ����ɾ��
	 * @param request
	 * @param model
	 * @param aac001
	 * @return
	 */
	@RequestMapping(value="/batdelete",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> batdelete(HttpServletRequest request, SFileRecord sFileRecord){
		return fileloadservice.batDeleteData(sFileRecord);
	}

}
