package com.sf.sfpp.pcomp.common.exception;

/**
 * 只是为了包装DubboRPC造成的异常在客户端可以检测到
 * 理论上每种微服务都有一个对应的类来区分不同的微服务
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/11
 */
public class PcompException extends Exception {
    public PcompException(String message) {
        super(message);
    }

    public PcompException(Throwable cause) {
        super(cause);
    }

    public PcompException(String message, Throwable cause) {
        super(message, cause);
    }
}
