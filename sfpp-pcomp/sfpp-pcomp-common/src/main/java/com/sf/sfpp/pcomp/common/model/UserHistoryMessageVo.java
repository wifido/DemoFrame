package com.sf.sfpp.pcomp.common.model;

import java.util.Date;

/**
 * @author 01139940 zyt
 * @version 1.0.0
 * @date 2016年9月14日下午2:32:18
 */
public class UserHistoryMessageVo {
    private String id;
    
    private String name;
    
    private Boolean isDeleted;
    
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    private Date createdTime;

    private Integer createdBy;

    private Date modifiedTime;

    private Integer modifiedBy;
    
    private String pcompSoftwareId;
    
    private String versionNumber;

    public String getPcompSoftwareId() {
        return pcompSoftwareId;
    }

    public void setPcompSoftwareId(String pcompSoftwareId) {
        this.pcompSoftwareId = pcompSoftwareId;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
}
