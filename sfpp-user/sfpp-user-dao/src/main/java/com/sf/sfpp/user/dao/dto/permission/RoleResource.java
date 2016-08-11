/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.dao.dto.permission;

import com.sf.sfpp.user.dao.domain.permission.Resource;

/**
 * 角色和资源关联关系
 * @date     2016年8月10日
 * @author   lingjie.wu
 */
public class RoleResource extends Resource {

    /**
     * 角色是否已绑定这个资源
     */
    private Boolean bindState;
    /**
     * 
     */
    public RoleResource() {}
    
    public Boolean getBindState() {
        return bindState;
    }
    public void setBindState(Boolean bindState) {
        this.bindState = bindState;
    }
}
