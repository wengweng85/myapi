package com.insigma.mvc.controller.sysmanager.perm;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.service.sysmanager.perm.SysPermService;

/**
 * Ȩ�޹���
 * @author wengsh
 *
 */
@Controller
@RequestMapping("/sys/perm")
public class SysPermController extends MvcHelper<SPermission> {
	
	
	@Resource
	private SysPermService sysPermService;
	
	
	/**
	 * Ȩ��������
	 * @param request
	 * @return
	 */
	@RequestMapping("/treedata")
	@RequiresRoles("admin")
	@ResponseBody
	public AjaxReturnMsg<List<SPermission>> treedata(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		return sysPermService.getPermTreeList();
	}
	
	
	/**
	 * ͨ��Ȩ�ޱ�Ż�ȡȨ������
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPermData/{id}")
	@RequiresRoles("admin")
	@ResponseBody
	public AjaxReturnMsg<SPermission> getPermDataByid(HttpServletRequest request, HttpServletResponse response,Model model,@PathVariable String id) throws Exception {
		return  sysPermService.getPermDataById(id);
	}
	
	
	/**
	 * ���»򱣴�Ȩ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveorupdate")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxReturnMsg<String> saveorupdate(HttpServletRequest request,Model model,@Valid SPermission spermission,BindingResult result) throws Exception {
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
	@RequestMapping("/deletePermDataById/{id}")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxReturnMsg<String> deletePermDataById(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		return   sysPermService.deletePermDataById(id);
	}
	
	/**
	 * Ȩ�����ƶ�����
	 * @param request
	 * @return
	 */
	@RequestMapping("/moveNode/{id}")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxReturnMsg<String> moveNode(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		return   sysPermService.moveNode(id);
	}
}