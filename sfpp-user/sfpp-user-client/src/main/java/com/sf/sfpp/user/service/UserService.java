/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.service;

import com.sf.sfpp.user.dao.domain.User;

import java.util.List;


/**
 * 用户dubbo服务对外接口
 * @date     2016年8月10日
 * @author   lingjie.wu
 */
public interface UserService {
    
    /** 通过工号获取用户信息*/
	User getUserByUserNo(String userNo) throws Exception;
	/** 通过id获取用户信息*/
	User getUserByUserId(int userId) throws Exception;
    /***
	 * 通过用户名获取权限资源
	 * 
	 * @param username
	 * @return
	 */
	List<String> getPermissionsByUserName(String username) throws Exception;
	
	List<User> getAllUsers() throws Exception;
	
	int addUser(User user) throws Exception;
	
	int updateUser(User user) throws Exception;
}
