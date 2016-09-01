/*
* Copyright 2015-2020 SF-Express Tech Company. 
*/

package com.sf.sfpp.user.dao.dto;

import com.sf.sfpp.user.dao.domain.Role;

/**
 * 用户和角色关联关系
 * @date     2016年8月10日
 * @author   lingjie.wu
 */
public class UserRole extends Role {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7978870509101049584L;
	/**
     * 角色-用户是否已绑定，
     */
    private Boolean bindState;
    /**
     * 
     */
    public UserRole() {}
    
    public Boolean getBindState() {
        return bindState;
    }
    public void setBindState(Boolean bindState) {
        this.bindState = bindState;
    }

}
