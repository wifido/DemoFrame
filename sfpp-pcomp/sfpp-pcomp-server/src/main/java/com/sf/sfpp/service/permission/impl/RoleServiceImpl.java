/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.service.permission.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.sfpp.dao.domain.permission.Role;
import com.sf.sfpp.dao.dto.permission.UserRole;
import com.sf.sfpp.manager.permission.RoleManager;
import com.sf.sfpp.service.permission.RoleService;

/**
 * 
 * @date     2016年7月27日
 * @author   591791
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleManager roleManager;
    
    @Override
    public Role getRoleByRoleId(String userNo) {
        return roleManager.getRoleById(userNo);
    }

    @Override
    public List<Role> getAllRole() {
        return roleManager.getAllRole();
    }

    @Override
    public int addRole(Role role) {
        return roleManager.addRole(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleManager.updateRole(role);
    }

    @Override
    public List<UserRole> getUserRoleList(String userNo) {
        return roleManager.getUserRoleList(userNo);
    }

    @Override
    public int changeUserRole(String roleId, Boolean state, String userNo) {
        return roleManager.changeUserRole(roleId, state, userNo);
    }

}
