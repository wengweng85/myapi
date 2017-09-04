package com.insigma.mvc.serviceimp.login;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.login.LoginMapper;
import com.insigma.mvc.model.LoginInf;
import com.insigma.mvc.model.SGroup;
import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SUser;
import com.insigma.mvc.service.login.LoginService;


/**
 * 登录service接口
 * @author wengsh
 *
 */

@Service
public class LoginServiceImpl   extends MvcHelper  implements LoginService {

	//登录dao
	@Resource
	private LoginMapper loginmapper;
	
	@Override
	public AjaxReturnMsg<SUser> getUserAndGroupInfo(String loginname)  throws Exception{
			SUser suser=loginmapper.getUserAndGroupInfo(loginname);
			List<SGroup> list=loginmapper.getGroupAreaInfo(suser.getGroupid());
			for (SGroup sgroup : list) {
				Object type =sgroup.getType();
				if("1".equals(type)){//当前数据为行政区划
					suser.setAab301(sgroup.getGroupid());
					suser.setAab301name(sgroup.getName());
				    break;
				}
			}
			return this.success(suser);
	}

	@Override
	public AjaxReturnMsg<List<SRole>> findRolesStr(String loginname) throws Exception {
		List<SRole> list= loginmapper.findRolesStr(loginname);
		return this.success(list);
	}

	@Override
	public AjaxReturnMsg<List<SPermission>>  findPermissionStr(String loginname) throws Exception {
		List<SPermission> list=loginmapper.findPermissionStr(loginname);
		return this.success(list);
	}

	@Override
	public void saveLoginHashInfo(LoginInf inf) {
		loginmapper.saveLoginHashInfo(inf);
	}

	@Override
	public List<LoginInf> findLoginInfoByhashcode(String loginhash) {
		return loginmapper.findLoginInfoByhashcode(loginhash);
	}

}
