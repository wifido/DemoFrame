/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.dao.dto.permission;

import java.util.List;

/**
 * 资源树节点
 * @date     2016年8月10日
 * @author   lingjie.wu
 */
public class ResourceTreeNode {

    /**
     * 
     */
    public ResourceTreeNode() {}
    
    /** 资源名称 */
    String name;

    /** 资源id */
    String id;
    
    /** 节点是否打开 */
    Boolean open = true;
    
    /** 是否父节点 */
    Boolean isParent = false;
    
    /** 资源url */
    String resourceUrl;
    
    /** 资源类型 */
    String resourceType;
    
    /** 是否选中 */
    Boolean checked;
    
    /** 子节点 */
    List<ResourceTreeNode> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public List<ResourceTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceTreeNode> children) {
        this.children = children;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
