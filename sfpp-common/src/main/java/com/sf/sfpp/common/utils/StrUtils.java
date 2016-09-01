package com.sf.sfpp.common.utils;

import com.sf.sfpp.common.Constants;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/9
 */
public class StrUtils {
    public static String makeString(Object... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object arg : args) {
            stringBuilder.append(arg);
        }
        return stringBuilder.toString();
    }

    public static String transferNull(String src) {
        if (src == null || src.equals("null")) {
            return "";
        }
        return src;
    }

    public static boolean isNull(String src) {
        if (src == null || src.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static String getKafkaMessageType(String s){
        return s.substring(0,s.indexOf(Constants.KAFKA_TYPE_SEPARATOR));
    }

    public static String getKafkaMessageContent(String s){
        return s.substring(s.indexOf(Constants.KAFKA_TYPE_SEPARATOR) + Constants.KAFKA_TYPE_SEPARATOR.length());
    }
}
