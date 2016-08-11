/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.service.permission;

import java.util.List;

import com.sf.sfpp.user.dao.domain.permission.Role;
import com.sf.sfpp.user.dao.dto.permission.UserRole;

/**
 * 
 * @date     2016年7月27日
 * @author   591791
 */
public interface RoleService {
    
    /** 通过工号获取用户信息*/
    Role getRoleByRoleId(String userNo);
	
	List<Role> getAllRole();
	
	int addRole(Role role);
	
	int updateRole(Role role);
	
	List<UserRole> getUserRoleList(String userNo);

    int changeUserRole(String roleId, Boolean state, String userNo);
}
