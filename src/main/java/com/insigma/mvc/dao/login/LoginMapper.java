package com.insigma.mvc.dao.login;


import java.util.List;

import com.insigma.mvc.model.LoginInf;
import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SUser;
import com.insigma.resolver.AppException;


/**
 * ��¼service�ӿ�
 * @author wengsh
 *
 */
public interface LoginMapper {
	/**
	 * ͨ���û�����ȡ��Ա����Ϣ
	 * @param username
	 * @return �û���
	 * @throws Exception 
	 */
	public SUser getUserAndGroupInfo(String loginname) throws Exception ;
	
	/**
	 * ͨ���û�id��ȡ�û���ɫ����
	 * @param userid
	 * @return ��ɫ����
	 * @throws Exception 
	 */
	public List<SRole> findRolesStr(String loginname) throws AppException, Exception;
	
	
	/**
	 * ͨ���û�id��ȡ�û�Ȩ�޼���
	 * @param userid
	 * @return Ȩ�޼���
	 * @throws Exception 
	 */
	public List<SPermission> findPermissionStr(String loginname) throws AppException, Exception;
	
	
	/**
	 * ����hashinfo
	 * @param hashinfo
	 */
	public void saveLoginHashInfo(LoginInf inf);
	
	
	public List<LoginInf> findLoginInfoByhashcode(String loginhash);

}