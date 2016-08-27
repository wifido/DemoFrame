package com.sf.sfpp.web.common.utils;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/18
 */
public class IntroductionUtils {
    public static String getShortIntroduction(String source) {
        return getShortIntroduction(source, 500);
    }

    public static String getShortIntroduction(String source, int number) {
        String trim = source.replaceAll("#", " ").replaceAll("[**]", " ").trim();
        return trim.substring(0, trim.length() > number ? number : trim.length());
    }
}
