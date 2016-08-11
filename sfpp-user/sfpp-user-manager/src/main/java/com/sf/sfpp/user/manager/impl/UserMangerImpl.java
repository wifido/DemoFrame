/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.dao.mapper.UserMapper;
import com.sf.sfpp.user.manager.UserManager;


/**
 * 
 * @date     2016年5月17日
 * @author   591791
 */
@Component
public class UserMangerImpl implements UserManager {

    @Autowired
    UserMapper userMapper;
    
    @Override
    public User getUserByUserNo(String userNo) {
        User user = userMapper.getUserByUserNo(userNo);
        return user ;
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

}
