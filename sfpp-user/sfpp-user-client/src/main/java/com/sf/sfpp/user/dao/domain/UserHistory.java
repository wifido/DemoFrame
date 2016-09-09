package com.sf.sfpp.user.dao.domain;


import java.util.Date;

/**
 * 用户操作历史
 *
 * @author ding.yang 01139954
 * @date 2016/9/7.
 */
public class UserHistory implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1841945501955196692L;

    private String id;

    private Integer userId;

    private String action;

    private String targetId;

    private String description;

    private Date modifiedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
