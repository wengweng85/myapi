package com.insigma.mvc.serviceimp.login;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.login.LoginMapper;
import com.insigma.mvc.model.LoginInf;
import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SUser;
import com.insigma.mvc.service.login.LoginService;


/**
 * µÇÂ¼service½Ó¿Ú
 * @author wengsh
 *
 */

@Service
public class LoginServiceImpl   extends MvcHelper  implements LoginService {

	//µÇÂ¼dao
	@Resource
	private LoginMapper loginmapper;
	
	@Override
	public AjaxReturnMsg<SUser> getUserAndGroupInfo(String loginname) {
		try {
			return this.success(loginmapper.getUserAndGroupInfo(loginname));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
