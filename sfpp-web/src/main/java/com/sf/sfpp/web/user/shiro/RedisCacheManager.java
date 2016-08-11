package com.sf.sfpp.web.user.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * Created by Michael on 5/13/16.
 */
public class RedisCacheManager implements CacheManager {

    private Cache cache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {

        return this.cache;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
