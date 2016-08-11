/*
 * Copyright 2015-2020 SF-Express Tech Company. 
 */

package com.sf.sfpp.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.manager.ResourceManager;
import com.sf.sfpp.user.manager.UserManager;
import com.sf.sfpp.user.service.UserService;


/**
 * 用户dubbo服务对外接口实现类
 * @date     2016年8月10日
 * @author   lingjie.wu
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

	/***
	 * 通过用户名获取权限资源
	 * 
	 * @param username
	 * @return
	 */
	public List<String> getPermissionsByUserName(String username) throws Exception {
		User user = getUserByUserNo(username);
		if (user == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();

		List<Resource> resource = resourceManager.getResourceByUserNo(username);
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

}
