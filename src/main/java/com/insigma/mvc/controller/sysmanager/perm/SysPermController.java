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
 * 权限管理
 * @author wengsh
 *
 */
@RestController
@RequestMapping("/sys/perm")
public class SysPermController extends MvcHelper<SPermission> {
	
	
	@Resource
	private SysPermService sysPermService;
	
	
	/**
	 * 权限树数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/treedata",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<List<SPermission>> treedata(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return sysPermService.getPermTreeList();
	}
	
	
	/**
	 * 通过权限编号获取权限数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getPermData/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<SPermission> getPermDataByid(HttpServletRequest request, HttpServletResponse response,@PathVariable String id) throws Exception {
		return  sysPermService.getPermDataById(id);
	}
	
	
	/**
	 * 更新或保存权限
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/saveorupdate",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> saveorupdate(HttpServletRequest request, @Valid SPermission spermission,BindingResult result) throws Exception {
		//检验输入
		if (result.hasErrors()){
			return validate(result);
		}
		return sysPermService.saveOrUpdatePermData(spermission);
		
	}
	
	/**
	 * 删除权限相关数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deletePermDataById/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> deletePermDataById(HttpServletRequest request,@PathVariable String id) throws Exception {
		return   sysPermService.deletePermDataById(id);
	}
	
	/**
	 * 权限树移动排序
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/moveNode/{id}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<String> moveNode(HttpServletRequest request,@PathVariable String id) throws Exception {
		return   sysPermService.moveNode(id);
	}
}
