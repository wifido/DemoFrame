package com.sf.sfpp.pcomp.common.model;

import java.io.Serializable;

public class PcompVersionDoucumentDownload implements Serializable {
    private String id;

    private String pcompVersionId;

    private String description;

    private String download;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPcompVersionId() {
        return pcompVersionId;
    }

    public void setPcompVersionId(String pcompVersionId) {
        this.pcompVersionId = pcompVersionId == null ? null : pcompVersionId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download == null ? null : download.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pcompVersionId=").append(pcompVersionId);
        sb.append(", description=").append(description);
        sb.append(", download=").append(download);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}