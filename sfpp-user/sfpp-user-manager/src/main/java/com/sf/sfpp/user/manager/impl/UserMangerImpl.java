/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.manager.impl;

import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.domain.Role;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.dao.mapper.UserMapper;
import com.sf.sfpp.user.manager.ResourceManager;
import com.sf.sfpp.user.manager.RoleManager;
import com.sf.sfpp.user.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 591791
 * @date 2016年5月17日
 */
@Component
public class UserMangerImpl implements UserManager {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleManager roleManager;

    @Autowired
    ResourceManager resourceManager;

    @Override
    public User getUserByUserNo(String userNo) {
        User user = userMapper.getUserByUserNo(userNo);
        return user;
    }

    @Override
    public User getUserByUserId(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUser();
    }

    @Override
    public int addUser(User user) {
        String userNo = user.getUserNo();
        int count = userMapper.countUsersByUserNo(userNo);
        if (count > 0) {
            return -1;
        }
        count = userMapper.insertSelective(user);
        user = userMapper.getUserByUserNo(userNo);
        Role role = new Role();
        role.setRoleName(userNo);
        role.setRemark(StrUtils.makeString(user.getUserNo(), "的个人私有角色"));
        roleManager.addRole(role);
        role = roleManager.getRoleByName(userNo);
        roleManager.changeUserRole(StrUtils.makeString(role.getRoleId()), true, userNo);
        return count;
    }

    @Override
    public int updateUser(User user) {
        String userNo = user.getUserNo();
        int count = userMapper.countUsersByUserNo(userNo);
        if (count == 1) {
            userMapper.updateByUserNoSelective(user);
            return count;
        }
        return count;
    }

    @Override
    public void addUserRelationToResource(String userId, String resourceUrl, String kind, String id, String right) {
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(userId));
        Role role = roleManager.getRoleByName(user.getUserNo());
        Resource resource = resourceManager.selectResourceByUrl(resourceUrl);
        if (resource == null) {
            resource = new Resource();
            resource.setResourceUrl(resourceUrl);
            resource.setResourceType(kind);
            resource.setResourceName(id);
            resource.setRemark(right);
            resourceManager.addResource(resource);
            resource = resourceManager.selectResourceByUrl(resourceUrl);
        }
        List<Integer> resourceIds = new LinkedList<>();
        resourceIds.add(resource.getResourceId());
        resourceManager.updateRoleResource(StrUtils.makeString(role.getRoleId()), resourceIds);
    }

}
