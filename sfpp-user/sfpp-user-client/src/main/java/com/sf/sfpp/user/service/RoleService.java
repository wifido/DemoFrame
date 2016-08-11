/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.service;

import java.util.List;

import com.sf.sfpp.user.dao.domain.Role;
import com.sf.sfpp.user.dao.dto.UserRole;



/**
 * 角色dubbo服务对外接口
 * @date     2016年8月10日
 * @author   lingjie.wu
 */
public interface RoleService {
    
    /** 通过工号获取用户信息*/
    Role getRoleByRoleId(String userNo) throws Exception;
	
	List<Role> getAllRole() throws Exception;
	
	int addRole(Role role) throws Exception;
	
	int updateRole(Role role) throws Exception;
	
	List<UserRole> getUserRoleList(String userNo) throws Exception;

    int changeUserRole(String roleId, Boolean state, String userNo) throws Exception;
}
