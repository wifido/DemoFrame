package com.sf.sfpp.pcomp.common.model;

import java.io.Serializable;
import java.util.Date;

public class PcompKind implements Serializable{
    private String id;

    private String pcompTitleId;

    private String name;

    private String bannerImage;

    private String topPhoto;

    private String introduction;

    private Boolean isDeleted;

    private Date createdTime;

    private Integer createdBy;

    private Date modifiedTime;

    private Integer modifiedBy;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPcompTitleId() {
        return pcompTitleId;
    }

    public void setPcompTitleId(String pcompTitleId) {
        this.pcompTitleId = pcompTitleId == null ? null : pcompTitleId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage == null ? null : bannerImage.trim();
    }

    public String getTopPhoto() {
        return topPhoto;
    }

    public void setTopPhoto(String topPhoto) {
        this.topPhoto = topPhoto == null ? null : topPhoto.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pcompTitleId=").append(pcompTitleId);
        sb.append(", name=").append(name);
        sb.append(", bannerImage=").append(bannerImage);
        sb.append(", topPhoto=").append(topPhoto);
        sb.append(", introduction=").append(introduction);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", modifiedBy=").append(modifiedBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}