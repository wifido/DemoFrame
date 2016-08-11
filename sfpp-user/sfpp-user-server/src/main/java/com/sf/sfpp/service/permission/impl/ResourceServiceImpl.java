/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.service.permission.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.sfpp.user.dao.domain.permission.Resource;
import com.sf.sfpp.user.dao.dto.permission.ResourceTreeNode;
import com.sf.sfpp.user.dao.dto.permission.RoleResource;
import com.sf.sfpp.user.manager.permission.ResourceManager;
import com.sf.sfpp.service.permission.ResourceService;


/**
 * 
 * @date     2016年6月12日
 * @author   591791
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceManager resourceManager;
    
    @Override
    public Boolean existSubResource(String parentResUrl) {
        return resourceManager.existSubResource(parentResUrl);
    }

    @Override
    public List<Resource> getSubResource(String parentResUrl) {
        return resourceManager.getSubResource(parentResUrl);
    }

    @Override
    public List<ResourceTreeNode> getResourceList() {
        List<ResourceTreeNode> result = new ArrayList<ResourceTreeNode>();
        
        List<Resource> list = resourceManager.getResourceList();
        for (Resource resource : list) {
            ResourceTreeNode rtn = new ResourceTreeNode();
            rtn.setId(resource.getResourceId().toString());
            rtn.setName(resource.getResourceName());
            rtn.setOpen(true);
            rtn.setResourceUrl(resource.getResourceUrl());
            rtn.setResourceType(resource.getResourceType());
            int pId = resource.getParentId() ;
            if (pId == -1) {     //顶级资源节点
                rtn.setIsParent(true);
                result.add(rtn);
            } else {    // 子节点
                List<ResourceTreeNode> children = getChildren(pId, result);
                children.add(rtn);
                setChildren(pId, result, children);
            }
        }
        return result;
    }
    
    private void setChildren(int pId, List<ResourceTreeNode> result, List<ResourceTreeNode> children) {
        String parentId = String.valueOf(pId);
        for (ResourceTreeNode rtn : result) {
            if (rtn.getId().equals(parentId)) {
                rtn.setChildren(children);
                break;
            }
        }
    }

    private List<ResourceTreeNode> getChildren(int pId, List<ResourceTreeNode> origin) {
        List<ResourceTreeNode> result = new ArrayList<ResourceTreeNode>();
        String parentId = String.valueOf(pId);
        for (ResourceTreeNode rtn : origin) {
            if (rtn.getId().equals(parentId)) {
                if(rtn.getChildren() != null){
                    return rtn.getChildren();
                }
                break;
            }
        }
        return result ;
    }


    @Override
    public List<ResourceTreeNode> getResourceByRoleId(String roleId) {
        List<ResourceTreeNode> result = new ArrayList<ResourceTreeNode>();
        List<RoleResource> list = resourceManager.getResourceByRoleId(roleId);
        for (RoleResource resource : list) {
            ResourceTreeNode rtn = new ResourceTreeNode();
            rtn.setId(resource.getResourceId().toString());
            rtn.setName(resource.getResourceName());
            rtn.setOpen(true);
            rtn.setResourceUrl(resource.getResourceUrl());
            rtn.setResourceType(resource.getResourceType());
            rtn.setChecked(resource.getBindState());
            
            int pId = resource.getParentId();
            if (pId == -1) {     //顶级资源节点
                rtn.setIsParent(true);
                result.add(rtn);
            } else {    // 子节点
                List<ResourceTreeNode> children = getChildren(pId, result);
                children.add(rtn);
                setChildren(pId, result, children);
            }
        }
        return result;
    }

    @Override
    public int updateRoleResource(String roleId, List<Integer> resourceIdList) {
        return resourceManager.updateRoleResource(roleId, resourceIdList);
    }

    @Override
    public List<Resource> getMainMenuByUserNo(String userNo) {
        List<Resource> mainMenuList = new ArrayList<Resource>();
        
        List<Resource> allList = resourceManager.getResourceByUserNo(userNo);
        for (Resource resource : allList) {
            if (resource.getResourceType().equalsIgnoreCase("M")) {
                mainMenuList.add(resource);
            }
        }
        return mainMenuList;
    }

    @Override
    public int deleteResource(String id) {
        return resourceManager.deleteResource(id);
    }

    @Override
    public int updateResource(Resource resource) {
        return resourceManager.updateResource(resource);
    }

    @Override
    public int addResource(Resource resource) {
        return resourceManager.addResource(resource);
    }

    @Override
    public List<Resource> getSubResByUserNoAndParent(String userNo, String parentResUrl) {
        List<Resource> subMenuList = new ArrayList<Resource>();
        
        List<Resource> allList = resourceManager.getResourceByUserNo(userNo);
        Set<Integer> allSet = new HashSet<Integer>();
        for (Resource resource : allList) {
            if (resource.getResourceType().equalsIgnoreCase("S")) {
                allSet.add(resource.getResourceId());
            }
        }
        List<Resource> allSubList = resourceManager.getSubResource(parentResUrl);
        for (Resource resource : allSubList) {
            Integer id = resource.getResourceId();
            if (allSet.contains(id)) {
                subMenuList.add(resource);
            }
        }
        return subMenuList;
    }
}
