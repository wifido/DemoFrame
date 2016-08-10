package com.sf.sfpp.web.common.editormd.domain;

import java.io.Serializable;

/**
 * 前端框架editor.js上传图片所需返回，需要序列化成Json
 *
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/9
 */
public class ImageUploadReturn implements Serializable {
    public static int SUCCESS = 1;
    public static int FAIL = 0;

    private int success;
    private String message;
    private String url;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
