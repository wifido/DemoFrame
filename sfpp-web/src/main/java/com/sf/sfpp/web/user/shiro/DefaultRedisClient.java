/*
 * Copyright 2015-2020 SF-Express Tech Company.
 */
package com.sf.sfpp.web.user.shiro;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sf.framework.cache.IRedisCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Redis客户端，用于操作redis
 * 
 * @date 2016年2月26日
 * @author 591791
 */
@Component
public class DefaultRedisClient implements RedisClient{

    private Logger logger = LoggerFactory.getLogger(DefaultRedisClient.class);

//    @Autowired
//    @Qualifier("redisCache")
    private IRedisCache<String, String> redisCache;

    @Override
    public boolean deleteObject(String key) {
        try {
            logger.info("delete object from redis. key={}", key);
            redisCache.remove(key);
            logger.info("delete object from redis OK. key={}", key);
            return true;
        } catch (Exception e) {
            logger.error("Error when delete key from redis. error:{}", e.getMessage());
            return false;
        }
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        try {
            logger.info("Getting object from redis. key=" + key);
            String value = redisCache.get(key);
            logger.info("Got object from redis. key=" + key + ",value=" + value);
            if (value == null) {
                return null;
            } else {
                return JSONObject.parseObject(value, clazz);
            }
        } catch (Exception e) {
            logger.error("Error when getObject from redis.key:{} error:{}", key, e.getMessage());
            throw new RedisException(e.getMessage());
        }
    }

    @Override
    public <T> T getObjectIgnoreError(String key, Class<T> clazz) {
        try {
            return this.getObject(key, clazz);
        } catch (Exception e) {
            logger.error("Error when get object from redis.", e);
            return null;
        }
    }

    @Override
    public void setObject(String key, Object object, long paramLong, TimeUnit paramTimeUnit) {
        try {
            String value = JSONObject.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
            logger.info("Set object to redis. key=" + key + ", value=" + value + ", paramLong=" + paramLong
                    + ", paramTimeUnit=" + paramTimeUnit);
            redisCache.add(key, value, paramLong, paramTimeUnit);
        } catch (Exception e) {
            logger.error("Error when setObject to redis. error:{}", e.getMessage());
            throw new RedisException(e.getMessage());
        }
    }

    @Override
    public void setObject(String key, Object object) {
        try {
            String value = JSONObject.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
            logger.info("Set object to redis. key=" + key + ", value=" + value);
            redisCache.add(key, value);
        } catch (Exception e) {
            logger.error("Error when setObject to redis. error:{}", e.getMessage());
            throw new RedisException(e.getMessage());
        }
    }

    @Override
    public void setObjectIgnoreError(String key, Object object, long paramLong, TimeUnit paramTimeUnit) {
        try {
            this.setObject(key, object, paramLong, paramTimeUnit);
        } catch (Exception e) {
            logger.error("Error when set Object IgnoreError to redis. key :" + key, e);
        }
    }

    @Override
    public void publish(String channel, String message) {
        try {
            redisCache.publish(channel, message);
        } catch (Exception e) {
            logger.error("Error when publish message to redis. channel :" + channel, e);
        }
    }


}
