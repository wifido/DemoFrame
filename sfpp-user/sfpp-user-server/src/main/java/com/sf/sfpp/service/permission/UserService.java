/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.service.permission;

import java.util.List;

import com.sf.sfpp.user.dao.domain.permission.User;



/**
 * 
 * @date     2016年5月17日
 * @author   591791
 */
public interface UserService {
    
    /** 通过工号获取用户信息*/
    User getUserByUserNo(String userNo);
    /***
	 * 通过用户名获取权限资源
	 * 
	 * @param username
	 * @return
	 */
	List<String> getPermissionsByUserName(String username);
	
	List<User> getAllUsers();
	
	int addUser(User user);
	
	int updateUser(User user);
}
