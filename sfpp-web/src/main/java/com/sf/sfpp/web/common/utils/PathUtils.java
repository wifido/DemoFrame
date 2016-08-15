package com.sf.sfpp.web.common.utils;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.web.common.PagePathConstants;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/13
 */
public class PathUtils {
    public static String makePath(String controllerPath) {
        return StrUtils.makeString(Constants.FOLDER_PATH_SEPARATOR, Constants.MAIN_SYSTEM_SHORT.toLowerCase(), "-web", controllerPath, ".do");
    }

    public static String makeKindPath(String kindId) {
        return StrUtils.makeString(PathUtils.makePath(PagePathConstants.PCOMP_KIND_PATH),
                Constants.PARAMETER_START_SEPARATOR,
                PcompConstants.PCOMP_KIND,
                Constants.PARAMETER_EQUALS,
                kindId);
    }
}
