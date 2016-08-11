/*
 * Copyright 2015-2020 SF-Express Tech Company.
 */
package com.sf.sfpp.web.user.shiro;

/**
 * Redis异常
 * 
 * @date 2016年2月26日
 * @author 591791
 */
public class RedisException extends RuntimeException {
    public RedisException(String msg) {
        super(msg);
    }

    public RedisException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
