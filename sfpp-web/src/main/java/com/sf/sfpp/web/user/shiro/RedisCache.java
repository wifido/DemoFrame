package com.sf.sfpp.web.user.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;


/**
 * Created by Michael on 5/13/16.
 */
public class RedisCache<K, V> implements Cache<K, V>{


    private static final Logger logger = Logger.getLogger(RedisCache.class);

    private RedisManager redisManager;

    private String prefixKey = "redis_session_cacheName:";

    @Override
    public V get(K key) throws CacheException {

        Object o = null;

        String keys = prefixKey+key;
        o = redisManager.getObject(keys,Object.class);

        return (V)o;


    }

    @Override
    public V put(K k, V value) throws CacheException {

        String key = prefixKey + k;
        redisManager.setObject(key,value);
        return value;
    }

    @Override
    public V remove(K k) throws CacheException {
        String key = prefixKey + k;
        Object o = redisManager.getObject(key,Object.class);
        redisManager.deleteObject(key);
        return (V)o;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public String getPrefixKey() {
        return prefixKey;
    }

    public void setPrefixKey(String prefixKey) {
        this.prefixKey = prefixKey;
    }
}
