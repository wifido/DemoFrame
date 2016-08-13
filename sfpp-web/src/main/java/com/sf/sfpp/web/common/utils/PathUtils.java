package com.sf.sfpp.web.common.utils;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.utils.StrUtils;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/13
 */
public class PathUtils {
    public static String makePath(String controllerPath) {
        return StrUtils.makeString(Constants.FOLDER_PATH_SEPARATOR, Constants.MAIN_SYSTEM_SHORT.toLowerCase(), "-web", controllerPath, ".do");
    }
}
