package com.sf.sfpp.common.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
public class WebCache {
    private List<String> pathTree = new LinkedList<>();
    private String Message;
    private String title;

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    private Object user;
    private Object cacheObject;

    public List<String> getPathTree() {
        return pathTree;
    }

    public void setPathTree(List<String> pathTree) {
        this.pathTree = pathTree;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Object getCacheObject() {
        return cacheObject;
    }

    public void setCacheObject(Object cacheObject) {
        this.cacheObject = cacheObject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

