package com.sf.sfpp.common.domain;

import java.io.Serializable;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/10
 */
public class ImageObject implements Serializable {
    private String imageID;
    private String imageContent;

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }
}
