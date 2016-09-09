/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.service.impl;

import com.sf.sfpp.user.dao.domain.Role;
import com.sf.sfpp.user.dao.dto.UserRole;
import com.sf.sfpp.user.manager.RoleManager;
import com.sf.sfpp.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色dubbo服务对外接口实现类
 * @date     2016年8月10日
 * @author   lingjie.wu
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleManager roleManager;
    
    @Override
    public Role getRoleByRoleId(String userNo) throws Exception{
        return roleManager.getRoleById(userNo);
    }

    @Override
    public Role getRoleByRoleName(String roleName) throws Exception {
        return roleManager.getRoleByName(roleName);
    }

    @Override
    public List<Role> getAllRole() throws Exception {
        return roleManager.getAllRole();
    }

    @Override
    public int addRole(Role role) throws Exception {
        return roleManager.addRole(role);
    }

    @Override
    public int updateRole(Role role) throws Exception {
        return roleManager.updateRole(role);
    }

    @Override
    public List<UserRole> getUserRoleList(String userNo) throws Exception {
        return roleManager.getUserRoleList(userNo);
    }

    @Override
    public int changeUserRole(String roleId, Boolean state, String userNo) throws Exception {
        return roleManager.changeUserRole(roleId, state, userNo);
    }

}
