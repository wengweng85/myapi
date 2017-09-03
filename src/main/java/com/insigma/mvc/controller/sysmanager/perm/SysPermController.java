package com.insigma.mvc.controller.sysmanager.perm;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.service.sysmanager.perm.SysPermService;

/**
 * Ȩ�޹���
 * @author wengsh
 *
 */
@RestController
@RequestMapping("/sys/perm")
public class SysPermController extends MvcHelper<SPermission> {
	
	
	@Resource
	private SysPermService sysPermService;
	
	
	/**
	 * Ȩ��������
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/treedata",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<List<SPermission>> treedata(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return sysPermService.getPermTreeList();
	}
	
	
	/**
	 * ͨ��Ȩ�ޱ�Ż�ȡȨ������
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPermData/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<SPermission> getPermDataByid(HttpServletRequest request, HttpServletResponse response,@PathVariable String id) throws Exception {
		return  sysPermService.getPermDataById(id);
	}
	
	
	/**
	 * ���»򱣴�Ȩ��
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveorupdate",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> saveorupdate(HttpServletRequest request, @Valid SPermission spermission,BindingResult result) throws Exception {
		//��������
		if (result.hasErrors()){
			return validate(result);
		}
		return sysPermService.saveOrUpdatePermData(spermission);
		
	}
	
	/**
	 * ɾ��Ȩ���������
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deletePermDataById/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> deletePermDataById(HttpServletRequest request,@PathVariable String id) throws Exception {
		return   sysPermService.deletePermDataById(id);
	}
	
	/**
	 * Ȩ�����ƶ�����
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/moveNode/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> moveNode(HttpServletRequest request,@PathVariable String id) throws Exception {
		return   sysPermService.moveNode(id);
	}
}
