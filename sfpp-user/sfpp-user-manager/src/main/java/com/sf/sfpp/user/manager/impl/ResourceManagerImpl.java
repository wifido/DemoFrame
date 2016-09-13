/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.manager.impl;

import com.sf.sfpp.user.dao.domain.Resource;
import com.sf.sfpp.user.dao.dto.RoleResource;
import com.sf.sfpp.user.dao.mapper.ResourceMapper;
import com.sf.sfpp.user.manager.ResourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 591791
 * @date 2016年6月12日
 */
@Component
public class ResourceManagerImpl implements ResourceManager {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public Boolean existSubResource(String parentResUrl) {
        int count = resourceMapper.getSubResourceCount(parentResUrl);
        return count > 0;
    }

    @Override
    public List<Resource> getSubResource(String parentResUrl) {
        return resourceMapper.getSubResource(parentResUrl);
    }

    @Override
    public List<Resource> getResourceByUserNo(String userNo) {
        return resourceMapper.selectResourceByUserNo(userNo);
    }

    @Override
    public List<Resource> getResourceList() {
        return resourceMapper.getResourceList();

    }

    @Override
    public List<RoleResource> getResourceByRoleId(String roleId) {
        List<RoleResource> result = new LinkedList<RoleResource>();
        //        List<Resource> bindList = resourceMapper.getResourceBindByRoleId(roleId);
        //        for (Resource resource : bindList) {
        //            RoleResource rr = ConvertDomainUtils.convertObject(resource, RoleResource.class);
        //            rr.setBindState(true);
        //            result.add(rr);
        //        }
        //        List<Resource> unBindList = resourceMapper.getResourceUnBindByRoleId(roleId);
        //        for (Resource resource : unBindList) {
        //            RoleResource rr = ConvertDomainUtils.convertObject(resource, RoleResource.class);
        //            rr.setBindState(false);
        //            result.add(rr);
        //        }
        result = resourceMapper.getResourceByRoleId(roleId);
        return result;
    }

    @Override
    public int updateRoleResource(String roleId, List<Integer> resourceIdList) {
        return resourceMapper.insertRoleResourceList(roleId, resourceIdList);
    }

    @Override
    public int deleteResource(String id) {
        Integer resourceId = Integer.parseInt(id);
        Resource resource = new Resource();
        resource.setIsDeleted(true);
        resource.setResourceId(resourceId);
        int count = resourceMapper.updateByPrimaryKeySelective(resource);
        return count;
    }

    @Override
    public int updateResource(Resource resource) {
        int count = resourceMapper.updateByPrimaryKeySelective(resource);
        return count;
    }

    @Override
    public int addResource(Resource resource) {
        return resourceMapper.insertSelective(resource);
    }

    @Override
    public Resource selectResourceByUrl(String resourceUrl) {
        return resourceMapper.selectResourceByUrl(resourceUrl);
    }

}
