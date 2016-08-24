package com.sf.sfpp.user.dao.mapper;

import java.util.List;

import com.sf.sfpp.user.dao.domain.Role;
import com.sf.sfpp.user.dao.dto.UserRole;

import org.apache.ibatis.annotations.Param;

/**
 * 角色dao
 * @date     2016年8月10日
 * @author   lingjie.wu
 */
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);
    
    Role selectByPrimaryRoleName(String roleName);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> getAllRole();
    
    int countRoleByName(String roleName);
    
    /** 通过用户工号查找已绑定的角色 */
    List<Role> getRoleBindByUserNo(String userNo);
    
    /** 通过用户工号查找未绑定的角色 */
    List<Role> getRoleUnBindByUserNo(String userNo);
    
    /** 通过工号查找用户角色的绑定状态 */
    List<UserRole> getUserRoleByUserNo(String userNo);

    int bindUserRole(@Param("roleId")String roleId, @Param("userNo")String userNo);

    int unBindUserRole(@Param("roleId")String roleId, @Param("userNo")String userNo);
}