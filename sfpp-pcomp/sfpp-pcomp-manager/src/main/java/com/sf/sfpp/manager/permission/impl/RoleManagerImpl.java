/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.manager.permission.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sf.sfpp.dao.domain.permission.Role;
import com.sf.sfpp.dao.dto.permission.UserRole;
import com.sf.sfpp.dao.mapper.permission.RoleMapper;
import com.sf.sfpp.manager.permission.RoleManager;



/**
 * 
 * @date     2016年7月27日
 * @author   591791
 */
@Component
public class RoleManagerImpl implements RoleManager {

    @Autowired
    private RoleMapper roleMapper;
    
    @Override
    public Role getRoleById(String id) {
        Integer roleId = Integer.parseInt(id);
        return roleMapper.selectByPrimaryKey(roleId );
    }

    @Override
    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }

    @Override
    public int addRole(Role role) {
        String roleName = role.getRoleName();
        int count = roleMapper.countRoleByName(roleName);
        if (count > 0) {
            return -1;
        }
        count = roleMapper.insertSelective(role);
        return count;
    }

    @Override
    public int updateRole(Role role) {
        String roleName = role.getRoleName();
        if (roleName == null) {
            roleMapper.updateByPrimaryKeySelective(role);
            return 1;
        }
        int count = roleMapper.countRoleByName(roleName);
        if (count == 1) {
            roleMapper.updateByPrimaryKeySelective(role);
            return count;
        }
        return count;
    }

    @Override
    public List<UserRole> getUserRoleList(String userNo) {
        List<UserRole> resultList = new ArrayList<UserRole>();
       /* List<Role> bindList = roleMapper.getRoleBindByUserNo(userNo);
        for (Role role : bindList) {
            UserRole userRole = ConvertDomainUtils.convertObject(role, UserRole.class);
            userRole.setBindState(true);
            resultList.add(userRole);
        }
        List<Role> unBindList = roleMapper.getRoleUnBindByUserNo(userNo);
        for (Role role : unBindList) {
            UserRole userRole = ConvertDomainUtils.convertObject(role, UserRole.class);
            userRole.setBindState(false);
            resultList.add(userRole);
        }*/
        resultList = roleMapper.getUserRoleByUserNo(userNo);
        return resultList;
    }

    
    @Override
    public int changeUserRole(String roleId, Boolean state, String userNo) {
        int result = 0;
        if (state) {    // 绑定
            roleMapper.unBindUserRole(roleId, userNo);
            result = roleMapper.bindUserRole(roleId, userNo);
        } else {        // 解除绑定
            result = roleMapper.unBindUserRole(roleId, userNo);
        }
        return result;
    }

}
