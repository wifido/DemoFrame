/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.manager.impl;

import com.sf.sfpp.user.dao.domain.Role;
import com.sf.sfpp.user.dao.dto.UserRole;
import com.sf.sfpp.user.dao.mapper.RoleMapper;
import com.sf.sfpp.user.manager.RoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



/**
 * 
 * @date     2016年7月27日
 * @author   591791
 */
@Component
public class RoleManagerImpl implements RoleManager {
	
    private Logger log = LoggerFactory.getLogger(this.getClass());

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
        if (roleName != null && !roleName.equals("")) {
        	Role dbRole = roleMapper.selectByPrimaryRoleName(roleName);
            if(dbRole != null  
            		&& !dbRole.getRoleId().equals(role.getRoleId())){
            	log.error("角色名称:" + roleName + "已经存在");
            	return -1;
            }
        }
        
        return roleMapper.updateByPrimaryKeySelective(role);

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

    @Override
    public Role getRoleByName(String roleName) {
        return roleMapper.selectByPrimaryRoleName(roleName);
    }

}
