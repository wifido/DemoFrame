/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.manager;

import java.util.List;

import com.sf.sfpp.user.dao.domain.Role;
import com.sf.sfpp.user.dao.dto.UserRole;



/**
 * 
 * @date     2016年5月17日
 * @author   591791
 */
public interface RoleManager {
    
    /** 通过id获取角色  */
    Role getRoleById(String roleId);
    
    /** 获取所有用户列表 */
    List<Role> getAllRole();

    /**
     * 新增用户
     * 返回-1表示失败，其他表示成功
     */
    int addRole(Role role);
    
    /**
     * 修改用户
     * 返回1表示成功，其它表示失败 
     */
    int updateRole(Role role);
    
    /**
     * 获取用户与角色绑定列表
     * @author 591791
     * @param userNo
     * @return
     */
    List<UserRole> getUserRoleList(String userNo);

    int changeUserRole(String roleId, Boolean state, String userNo);
    
}
