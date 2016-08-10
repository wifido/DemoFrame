/*
 * Copyright 2015-2020 SF-Express Tech Company. 
 */

package com.sf.sfpp.service.permission.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.sfpp.dao.domain.permission.Resource;
import com.sf.sfpp.dao.domain.permission.User;
import com.sf.sfpp.manager.permission.ResourceManager;
import com.sf.sfpp.manager.permission.UserManager;
import com.sf.sfpp.service.permission.UserService;


/**
 * 
 * @date 2016年5月17日
 * @author 591791
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserManager userManager;
	
	@Autowired
	ResourceManager resourceManager;

	@Override
	public User getUserByUserNo(String userNo) {
		User user = userManager.getUserByUserNo(userNo);
		return user;
	}

	/***
	 * 通过用户名获取权限资源
	 * 
	 * @param username
	 * @return
	 */
	public List<String> getPermissionsByUserName(String username) {
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
    public List<User> getAllUsers() {
        return userManager.getAllUsers();
    }

    @Override
    public int addUser(User user) {
        return userManager.addUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userManager.updateUser(user);
    }

}
