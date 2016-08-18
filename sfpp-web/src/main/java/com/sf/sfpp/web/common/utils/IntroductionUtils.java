package com.sf.sfpp.web.common.utils;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/18
 */
public class IntroductionUtils {
    public static String getShortIntroduction(String source) {
        String trim = source.replaceAll("#", " ").trim();
        return trim.substring(0, trim.length() > 500 ? 500:trim.length());
    }
}
