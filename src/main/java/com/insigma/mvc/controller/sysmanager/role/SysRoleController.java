package com.insigma.mvc.controller.sysmanager.role;

import java.util.HashMap;
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
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.service.sysmanager.role.SysRoleService;

/**
 * 角色管理及角色角色分配管理
 * @author wengsh
 *
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends MvcHelper<SRole>  {
	
	@Resource
	private SysRoleService sysRoleService;
	
	/**
	 * 角色列表查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/querylist",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<HashMap<String,Object>> querylist(HttpServletRequest request, SRole srole) throws Exception {
		return sysRoleService.getAllRoleList(srole);
	}
	
	
	/**
	 * 通过角色编号获取角色数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getRoleData/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<SRole> getPermDataByid(HttpServletRequest request, HttpServletResponse response,@PathVariable String id) throws Exception {
		return sysRoleService.getRoleDataById(id);
	}
	
	

	/**
	 * 删除角色相关数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteRoleDataById/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> deleteRoleDataById(HttpServletRequest request,@PathVariable String id) throws Exception {
		return  sysRoleService.deleteRoleDataById(id);
	}
	
	/**
	 * 更新或保存角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveorupdate",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> saveorupdate(HttpServletRequest request, @Valid SRole srole,BindingResult result) throws Exception {
		//检验输入
		if (result.hasErrors()){
			return validate(result);
		}
		return sysRoleService.saveOrUpdateRoleData(srole);
	}
	
	/**
	 * 角色-权限树加载
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/treedata/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public  AjaxReturnMsg<List<SRole>> treedata(HttpServletRequest request, HttpServletResponse response,@PathVariable String id) throws Exception {
		return  sysRoleService.getRolePermTreeData(id);
	}
	
	
	/**
	 * 更新或保存权限
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveroleperm",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> saveroleperm(HttpServletRequest request, SRole srole) throws Exception {
		return sysRoleService.saveRolePermData(srole);
	}
}
