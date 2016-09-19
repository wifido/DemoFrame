package com.sf.sfpp.web.controller.user;

import com.github.pagehelper.PageInfo;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.user.dao.domain.UserHistory;
import com.sf.sfpp.user.service.ResourceService;
import com.sf.sfpp.user.service.UserHistoryService;
import com.sf.sfpp.user.service.UserService;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/9/2
 */
@Controller
public class UserController extends AbstractCachedController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserHistoryService userHistoryService;

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * 去除隐私信息（密码等），只保留给前端展示的
     * todo: 如果用户结构有修改，那么一定要修改这里
     *
     * @param source
     * @return
     */
    private User getPureUserInfo(User source) {
        User user = new User();
        user.setId(source.getId());
        user.setCreatedTime(source.getCreatedTime());
        user.setMobile(source.getMobile());
        user.setUserName(source.getUserName());
        user.setUserNo(source.getUserNo());
        return user;
    }

    @ResponseBody
    @RequestMapping(value = "user/get", method = RequestMethod.GET)
    public JsonResult<User> getUser() {
        JsonResult<User> userJsonResult = new JsonResult<>();
        try {
            Subject currentUser = SecurityUtils.getSubject();
            User user = getPureUserInfo((User) currentUser.getPrincipal());
            userJsonResult.setData(user);
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTrace(e));
            userJsonResult.setMessage(e.getMessage());
        }
        return userJsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "user/getUserInfo", method = RequestMethod.GET)
    public JsonResult<User> getUserInfo(@RequestParam("userId") String userid) {
        JsonResult<User> userJsonResult = new JsonResult<>();
        try {
            int userId = Integer.parseInt(userid);
            User user = getPureUserInfo(userService.getUserByUserId(userId));
            userJsonResult.setData(user);
        }catch (NullPointerException we){
            User user = new User();
            userJsonResult.setData(user);
        }  catch (Exception e) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            log.warn(stackTrace);
            userJsonResult.setMessage(stackTrace);
        }
        return userJsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "user/getHistory", method = RequestMethod.GET)
    public JsonResult<PageInfo<UserHistory>> userHistoryPage(HttpServletRequest request) {
        JsonResult<PageInfo<UserHistory>> userHistoryJsonResult = new JsonResult<>();
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int pageNum = Integer.parseInt(request.getParameter("pageNum"));
            PageInfo<UserHistory> userHistory = userHistoryService.getUserHistoryByUserId(userId, pageNum);
            userHistoryJsonResult.setData(userHistory);
        } catch (Exception e) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            log.warn(stackTrace);
            userHistoryJsonResult.setMessage(stackTrace);
        }
        return userHistoryJsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "user/getUserPermissions", method = RequestMethod.GET)
    public JsonResult<List<String>> getUserPermissions(@RequestParam("userId") String userid) {
        JsonResult<List<String>> userJsonResult = new JsonResult<>();
        try {
            int userId = Integer.parseInt(userid);
            userJsonResult.setData(userService.getPermissionsByUserName(userService.getUserByUserId(userId).getUserNo()));
        } catch (Exception e) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            log.warn(stackTrace);
            userJsonResult.setMessage(stackTrace);
        }
        return userJsonResult;
    }
}
