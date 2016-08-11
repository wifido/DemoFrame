package com.sf.sfpp.user.dao.mapper.permission;

import java.util.List;

import com.sf.sfpp.user.dao.domain.permission.User;

/**
 * 用户dao
 * @date     2016年8月10日
 * @author   lingjie.wu
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User getUserByUserNo(String userNo);
    
    List<User> getAllUser();
    
    int countUsersByUserNo(String userNo);
    
    int updateByUserNoSelective(User user);
}