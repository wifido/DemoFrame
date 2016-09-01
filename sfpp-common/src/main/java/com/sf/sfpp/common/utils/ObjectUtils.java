package com.sf.sfpp.common.utils;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/25
 */
public class ObjectUtils {
    private final static ClassIntrospector ci = new ClassIntrospector();

    public static long getObjectSize(Object object){
        ObjectInfo res = null;
        try {
            res = ci.introspect(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return res.getDeepSize();
    }
}
