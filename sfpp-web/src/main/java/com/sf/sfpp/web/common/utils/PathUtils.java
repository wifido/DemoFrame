package com.sf.sfpp.web.common.utils;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.web.common.PathConstants;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/13
 */
public class PathUtils {
    public static String makePath(String controllerPath) {
        return StrUtils.makeString(Constants.FOLDER_PATH_SEPARATOR, Constants.MAIN_SYSTEM_SHORT.toLowerCase(), "-web", controllerPath, "");
    }

    public static String makeTitlePath(String titleId) {
        return makeTitlePath(titleId, 1);
    }

    public static String makeTitlePath(String titleId, int pageNumber) {
        if(StrUtils.isNull(titleId)){
            return StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH),
                    Constants.PARAMETER_START_SEPARATOR,
                    Constants.PAGE_NUMBER,
                    Constants.PARAMETER_EQUALS,
                    pageNumber);
        }
        return StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH),
                Constants.PARAMETER_START_SEPARATOR,
                PcompConstants.PCOMP_TITLE,
                Constants.PARAMETER_EQUALS,
                titleId,
                Constants.PARAMETER_SEPARATOR,
                Constants.PAGE_NUMBER,
                Constants.PARAMETER_EQUALS,
                pageNumber);
    }

    public static String makeKindPath(String kindId) {
        return makeKindPath(kindId, 1);
    }

    public static String makeKindPath(String kindId, int pageNumber) {
        return StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_KIND_PATH),
                Constants.PARAMETER_START_SEPARATOR,
                PcompConstants.PCOMP_KIND,
                Constants.PARAMETER_EQUALS,
                kindId,
                Constants.PARAMETER_SEPARATOR,
                Constants.PAGE_NUMBER,
                Constants.PARAMETER_EQUALS,
                pageNumber);
    }

    public static String makeSoftwarePath(String softwareId) {
        return StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_PATH),
                Constants.PARAMETER_START_SEPARATOR,
                PcompConstants.PCOMP_SOFTWARE,
                Constants.PARAMETER_EQUALS,
                softwareId);
    }

    public static String makeVersionPath(String softwareId, String nav, String versionId) {
        return StrUtils.makeString(makeSoftwarePath(softwareId),
                Constants.PARAMETER_SEPARATOR,
                PcompConstants.SOFTWARE_PAGE_NAVIGATION,
                Constants.PARAMETER_EQUALS,
                nav,
                Constants.PARAMETER_SEPARATOR,
                PcompConstants.PCOMP_VERSION,
                Constants.PARAMETER_EQUALS,
                versionId);
    }
}
