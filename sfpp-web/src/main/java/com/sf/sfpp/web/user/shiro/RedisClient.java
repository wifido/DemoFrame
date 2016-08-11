/*
 * Copyright 2015-2020 SF-Express Tech Company.
 */
package com.sf.sfpp.web.user.shiro;

import java.util.concurrent.TimeUnit;

/**
 * Redis客户端，用于操作redis
 * 
 * @date 2016年2月26日
 * @author 591791
 */
public interface RedisClient {

    public boolean deleteObject(String key);

    public void setObject(String key, Object object, long paramLong, TimeUnit paramTimeUnit);

    public void setObject(String key, Object object);

    public void setObjectIgnoreError(String key, Object object, long paramLong, TimeUnit paramTimeUnit);

    public <T> T getObject(String key, Class<T> clazz);

    public <T> T getObjectIgnoreError(String key, Class<T> clazz);

    void publish(String channel, String message);
}
