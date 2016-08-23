package com.sf.sfpp.web.common.utils;

import com.sf.sfpp.common.utils.StrUtils;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/22
 */
public class FormUtils {
    public static String mkTextInputId(String name){
        return StrUtils.makeString(name,"_text_input");
    }

    public static String mkSelectInputDivId(String name){
        return StrUtils.makeString(name,"_select_div");
    }

    public static String mkSelectInputId(String name){
        return StrUtils.makeString(name,"_select");
    }

    public static String mkFileInputId(String name){
        return StrUtils.makeString(name,"_file_input");
    }

    public static String mkImageInputId(String name){
        return StrUtils.makeString(name,"_image_input");
    }

    public static String mkHelpBlockId(String name){
        return StrUtils.makeString(name,"_message");
    }

    public static String mkFormId(String name){
        return StrUtils.makeString(name,"_form");
    }

    public static String mkFormGroupId(String name){
        return StrUtils.makeString(name,"_form_group");
    }
}
