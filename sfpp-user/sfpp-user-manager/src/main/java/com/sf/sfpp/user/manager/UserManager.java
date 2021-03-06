/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.manager;

import com.sf.sfpp.user.dao.domain.User;

import java.util.List;


/**
 * 
 * @date     2016年5月17日
 * @author   591791
 */
public interface UserManager {
    
    /** 通过工号获取用户信息  */
    User getUserByUserNo(String userNo);

    /** 通过ID获取用户信息  */
    User getUserByUserId(int userId);
    
    /** 获取所有用户列表 */
    List<User> getAllUsers();

    /**
     * 新增用户
     * 返回-1表示失败，其他表示成功
     */
    int addUser(User user);
    
    /**
     * 修改用户
     * 返回1表示成功，其它表示失败 
     */
    int updateUser(User user);

    void addUserRelationToResource(String userId, String resourceUrl, String kind, String id, String right);
}
