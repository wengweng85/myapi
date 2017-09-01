package com.insigma.mvc.controller.sysmanager.userrole;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.SGroup;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.service.sysmanager.userrole.SysUserRoleService;

/**
 * �û������û�����
 * @author wengsh
 *
 */
@Controller
@RequestMapping("/sys/userrole")
public class SysUserRoleController extends MvcHelper {
	
	@Resource
	private SysUserRoleService sysUserRoleService;
	
	
	/**
	 * ����������
	 * @param request
	 * @return
	 */
	@RequestMapping("/treedata")
	@RequiresRoles("admin")
	@ResponseBody
	public  AjaxReturnMsg<List<SGroup>>  getGroupTreeData(HttpServletRequest request,Model model) throws Exception {
		String parentid=request.getParameter("id");
		if(parentid.equals("")){
			parentid="G001";
		}
		return sysUserRoleService.getAllGroupList(parentid);
	}
	
	
	/**
	 * ͨ��������Ż�ȡ������Ϣ
	 * @param request
	 * @return
	 */
	@RequestMapping("/getgroupdatabyid/{id}")
	@RequiresRoles("admin")
	@ResponseBody
	public AjaxReturnMsg<String> getgroupdata(HttpServletRequest request,Model model,@PathVariable String id ) throws Exception {
		return sysUserRoleService.getGroupDataById(id);
	}
	
	/**
	 * ��ȡ��Ա��Ϣ�б�
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUserListDataByid")
	@RequiresRoles("admin")
	@ResponseBody
	public AjaxReturnMsg<HashMap<String,Object>> getUserListByGroupid(HttpServletRequest request,Model model,@RequestBody SGroup sgroup ) throws Exception {
		if(StringUtils.isEmpty(sgroup.getGroupid())){
			sgroup.setGroupid("G001");
		}
		return sysUserRoleService.getUserListByGroupid(sgroup);
	}
	
	
	/**
	 * ͨ���û�id��ȡ��ɫ�б���ѡ��״̬
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRoleByUserId")
	@RequiresRoles("admin")
	@ResponseBody
	public AjaxReturnMsg<HashMap<String,Object>> getRoleByUserId(HttpServletRequest request,Model model,@RequestBody SRole srole ) throws Exception {
		if(StringUtils.isEmpty(srole.getUserid())){
			srole.setUserid("");
		}
		return sysUserRoleService.getUserRoleByUserId(srole);
	}
	
	
	/**
	 * �����û���Ӧ��ɫ
	 * @param request
	 * @param model
	 * @param srole
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveUserRole")
	@RequiresRoles("admin")
	@ResponseBody
	public AjaxReturnMsg saveUserRole(HttpServletRequest request,Model model,@RequestBody SRole srole ) throws Exception {
		return sysUserRoleService.saveUserRole(srole);
	}
}