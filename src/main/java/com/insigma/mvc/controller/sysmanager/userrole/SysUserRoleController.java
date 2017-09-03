package com.insigma.mvc.controller.sysmanager.userrole;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.SGroup;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.service.sysmanager.userrole.SysUserRoleService;

/**
 * 用户组与用户管理
 * @author wengsh
 *
 */
@RestController
@RequestMapping("/sys/userrole")
public class SysUserRoleController extends MvcHelper {
	
	@Resource
	private SysUserRoleService sysUserRoleService;
	
	
	/**
	 * 机构树数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/treedata/{parentid}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public  AjaxReturnMsg<List<SGroup>>  getGroupTreeData(HttpServletRequest request,@PathVariable String parentid) throws Exception {
		if(parentid.equals("")){
			parentid="G001";
		}
		return sysUserRoleService.getAllGroupList(parentid);
	}
	
	
	/**
	 * 通过机构编号获取机构信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getgroupdatabyid/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> getgroupdata(HttpServletRequest request,@PathVariable String id ) throws Exception {
		return sysUserRoleService.getGroupDataById(id);
	}
	
	/**
	 * 获取人员信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getUserListDataByid",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<HashMap<String,Object>> getUserListByGroupid(HttpServletRequest request,@RequestBody SGroup sgroup ) throws Exception {
		if(StringUtils.isEmpty(sgroup.getGroupid())){
			sgroup.setGroupid("G001");
		}
		return sysUserRoleService.getUserListByGroupid(sgroup);
	}
	
	
	/**
	 * 通用用户id获取角色列表及选中状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getRoleByUserId",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<HashMap<String,Object>> getRoleByUserId(HttpServletRequest request,@RequestBody SRole srole ) throws Exception {
		if(StringUtils.isEmpty(srole.getUserid())){
			srole.setUserid("");
		}
		return sysUserRoleService.getUserRoleByUserId(srole);
	}
	
	
	/**
	 * 保存用户对应角色
	 * @param request
	 * @param model
	 * @param srole
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveUserRole",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg saveUserRole(HttpServletRequest request,@RequestBody SRole srole ) throws Exception {
		return sysUserRoleService.saveUserRole(srole);
	}
}
