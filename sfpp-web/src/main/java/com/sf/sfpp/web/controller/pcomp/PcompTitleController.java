package com.sf.sfpp.web.controller.pcomp;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import com.sf.sfpp.web.controller.user.UserRightController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/22
 */
@Controller
public class PcompTitleController extends AbstractCachedController {
    private final static Logger log = LoggerFactory.getLogger(PcompSoftwareController.class);
    @Autowired
    private PcompTitleService pcompTitleService;
    @Autowired
    private UserRightController userRightController;

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_TITLE_VALIDATE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> validateTitleName(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        try {
            result.setData(pcompTitleService.existsTitle(titleName));
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        result.setData(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_TITLE_CREATE_PATH, method = RequestMethod.GET)
    public JsonResult<Boolean> createTitle(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        try {
            if (pcompTitleService.existsTitle(titleName)) {
                result.setData(false);
                result.setMessage("Same title exists!");
            }
            Subject currentUser = SecurityUtils.getSubject();
            User user = (User) currentUser.getPrincipal();
            JsonResult<Boolean> hasRight = userRightController.getIsPcompAdmin();
            if (hasRight.getData()) {
                PcompTitle pcompTitle = new PcompTitle();
                pcompTitle.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
                pcompTitle.setName(titleName);
                pcompTitle.setCreatedBy(user.getId());
                pcompTitle.setModifiedBy(user.getId());
                pcompTitleService.addNewTitle(pcompTitle);
                result.setData(true);
            } else {
                result.setData(false);
                result.setMessage("Permission Denied!");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/pcomp_title/get", method = RequestMethod.GET)
    public JsonResult<List<PcompTitle>> getAllTitle() {
        JsonResult<List<PcompTitle>> result = new JsonResult<>();
        try {
            result.setData(pcompTitleService.fetchAllTitles());
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/pcomp_title/fetch", method = RequestMethod.GET)
    public JsonResult<PcompTitle> getTitle(HttpServletRequest request) {
        JsonResult<PcompTitle> result = new JsonResult<>();
        try {
            result.setData(pcompTitleService.fetchTitleByTitleId(request.getParameter(PcompConstants.PCOMP_TITLE)));
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/pcomp_title/delete", method = RequestMethod.GET)
    public JsonResult<Boolean> deleteTitle(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String titleId = request.getParameter(PcompConstants.PCOMP_TITLE);
        try {
            JsonResult<Boolean> hasRight = userRightController.getHasModifyPcompTitleRight(titleId);
            if (hasRight.getData()) {
                Subject currentUser = SecurityUtils.getSubject();
                User user = (User) currentUser.getPrincipal();
                pcompTitleService.removeTitle(titleId, user.getId());
                result.setData(true);
            } else {
                result.setData(false);
                result.setMessage("Permission Denied!");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pcomp/pcomp_title/update", method = RequestMethod.GET)
    public JsonResult<Boolean> updateTitle(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String titleId = request.getParameter(PcompConstants.PCOMP_TITLE);
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        try {
            JsonResult<Boolean> hasRight = userRightController.getHasModifyPcompTitleRight(titleId);
            if (hasRight.getData()) {
                Subject currentUser = SecurityUtils.getSubject();
                User user = (User) currentUser.getPrincipal();
                PcompTitle pcompTitle = pcompTitleService.fetchTitleByTitleId(titleId);
                pcompTitle.setName(titleName);
                pcompTitle.setModifiedBy(user.getId());
                pcompTitleService.modifyTitle(pcompTitle);
                result.setData(true);
            } else {
                result.setData(false);
                result.setMessage("Permission Denied!");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }
}
