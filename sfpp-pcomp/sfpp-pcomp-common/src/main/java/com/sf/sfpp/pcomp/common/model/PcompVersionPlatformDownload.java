package com.sf.sfpp.pcomp.common.model;

import java.io.Serializable;

public class PcompVersionPlatformDownload implements Serializable {
    private String id;

    private String pcompVersionId;

    private String platform;

    private String download;

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download == null ? null : download.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pcompVersionId=").append(pcompVersionId);
        sb.append(", platform=").append(platform);
        sb.append(", download=").append(download);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}