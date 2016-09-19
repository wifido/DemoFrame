/*
 * Copyright 2015-2020 SF-Express Tech Company. 
 */

package com.sf.sfpp.user.service.impl;

import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.manager.ResourceManager;
import com.sf.sfpp.user.manager.UserManager;
import com.sf.sfpp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户dubbo服务对外接口实现类
 *
 * @author lingjie.wu && Hash.Zhang
 * @date 2016年8月10日
 * @modify 增加用户与资源操作
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserManager userManager;

    @Autowired
    ResourceManager resourceManager;

    @Override
    public User getUserByUserNo(String userNo) throws Exception {
        User user = userManager.getUserByUserNo(userNo);
        return user;
    }

    @Override
    public User getUserByUserId(int userId) throws Exception {
        return userManager.getUserByUserId(userId);
    }

    /***
     * 通过用户名获取权限资源
     *
     * @param userNo
     * @return
     */
    public List<String> getPermissionsByUserNo(String userNo) throws Exception {
        User user = getUserByUserNo(userNo);
        if (user == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();

        List<Resource> resource = resourceManager.getResourceByUserNo(userNo);
        for (Resource r : resource) {
            list.add(r.getResourceUrl());
        }
        return list;
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return userManager.getAllUsers();
    }

    @Override
    public int addUser(User user) throws Exception {
        return userManager.addUser(user);
    }

    @Override
    public int updateUser(User user) throws Exception {
        return userManager.updateUser(user);
    }

    @Override
    public void bindUserWithResource(String userId, String resourceUrl, String kind, String id, String right) {
        userManager.addUserRelationToResource(userId, resourceUrl, kind, id, right);
    }

}
