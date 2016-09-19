package com.sf.sfpp.pcomp.common.model;

import java.io.Serializable;
import java.util.Date;

public class PcompVersion implements Serializable {
    private String id;

    private String pcompSoftwareId;

    private String versionNumber;

    private Boolean isDeleted;

    private Date createdTime;

    private Integer createdBy;

    private Date modifiedTime;

    private Integer modifiedBy;

    private String introduction;

    private String quickStart;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPcompSoftwareId() {
        return pcompSoftwareId;
    }

    public void setPcompSoftwareId(String pcompSoftwareId) {
        this.pcompSoftwareId = pcompSoftwareId == null ? null : pcompSoftwareId.trim();
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber == null ? null : versionNumber.trim();
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getQuickStart() {
        return quickStart;
    }

    public void setQuickStart(String quickStart) {
        this.quickStart = quickStart == null ? null : quickStart.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pcompSoftwareId=").append(pcompSoftwareId);
        sb.append(", versionNumber=").append(versionNumber);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", modifiedBy=").append(modifiedBy);
        sb.append(", introduction=").append(introduction);
        sb.append(", quickStart=").append(quickStart);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}