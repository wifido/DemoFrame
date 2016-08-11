package com.sf.sfpp.web.user.shiro;


import java.util.concurrent.TimeUnit;

/**
 * Created by Michael on 5/12/16.
 */

public class RedisManager {


    public DefaultRedisClient redisClient ;

    public boolean deleteObject(String key){
        return redisClient.deleteObject(key);
    }

    public void setObject(String key, Object object, long paramLong, TimeUnit paramTimeUnit) {
        redisClient.setObject(key,object,paramLong,paramTimeUnit);
    }

    public void setObject(String key, Object object){
        redisClient.setObject(key,object);
    }

    public void setObjectIgnoreError(String key, Object object, long paramLong, TimeUnit paramTimeUnit){
        redisClient.setObjectIgnoreError(key,object,paramLong,paramTimeUnit);
    }

    public <T> T getObject(String key, Class<T> clazz){
        return redisClient.getObject(key,clazz);
    }

    public <T> T getObjectIgnoreError(String key, Class<T> clazz){
        return redisClient.getObjectIgnoreError(key,clazz);
    }

    void publish(String channel, String message){
        redisClient.publish(channel,message);

    }


    public DefaultRedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(DefaultRedisClient redisClient) {
        this.redisClient = redisClient;
    }
}
