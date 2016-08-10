package com.sf.sfpp.dao.mapper.permission;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.sfpp.dao.domain.permission.Resource;
import com.sf.sfpp.dao.dto.permission.RoleResource;


/**
 * 资源dao
 * @date     2016年8月10日
 * @author   lingjie.wu
 */
public interface ResourceMapper {
    int deleteByPrimaryKey(Integer resourceId);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer resourceId);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
    
    int getSubResourceCount(String parentResUrl);
    
    List<Resource> getSubResource(String parentResUrl);

    List<Resource> selectResourceByUserNo(String username);

    List<Resource> getResourceList();
    
    /** 通过角色id查找资源的绑定状态 */
    List<RoleResource> getResourceByRoleId(String roleId);
    
    int deleteByRoleId(String roleId);

    int insertRoleResourceList(@Param("roleId")String roleId, @Param("resList")List<Integer> resourceIdList);
}