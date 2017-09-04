package com.insigma.mvc.controller.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SUser;
import com.insigma.mvc.service.login.LoginService;


/**
 * 登录controller
 * @author Administrator
 *
 */
@RestController
public class LoginController extends MvcHelper {
	
	Log log=LogFactory.getLog(LoginController.class);
	
	@Autowired
	private LoginService loginservice;
	
	
	/**
	 * getUserAndGroupInfo
	 * @param request
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserAndGroupInfo/{loginname}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<SUser> getUserAndGroupInfo(HttpServletRequest request,@PathVariable String loginname) throws Exception {
		//转换成json对象并调用远程接口
		return loginservice.getUserAndGroupInfo(loginname);
	}
	
	/**
	 * findPermissionStr
	 * @param request
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findPermissionStr/{username}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<List<SPermission>> findPermissionStr(HttpServletRequest request,@PathVariable String username) throws Exception {
		return loginservice.findPermissionStr(username);
	}
	
	/**
	 * 
	 * @param request
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findRolesStr/{username}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public AjaxReturnMsg<List<SRole>> findRolesStr(HttpServletRequest request,@PathVariable String username) throws Exception {
		return loginservice.findRolesStr(username);
	}

}
