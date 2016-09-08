package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.user.dao.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/9/8
 */
public class UserRightController {
    public Subject getUser() {
        return SecurityUtils.getSubject();
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/title/user/add")
    public JsonResult<Boolean> getHasAddTitleRight() {
        Subject currentUser = getUser();
        if(currentUser.hasRole("系统管理员")||){


        }
    }
}
