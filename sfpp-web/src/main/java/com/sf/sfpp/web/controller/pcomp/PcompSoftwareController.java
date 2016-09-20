package com.sf.sfpp.web.controller.pcomp;

import com.github.pagehelper.PageInfo;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.dto.JsonResult;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.ImageKind;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend;
import com.sf.sfpp.pcomp.service.PcompKindService;
import com.sf.sfpp.pcomp.service.PcompSoftwareService;
import com.sf.sfpp.pcomp.service.PcompTitleService;
import com.sf.sfpp.user.dao.domain.User;
import com.sf.sfpp.web.common.PathConstants;
import com.sf.sfpp.web.controller.common.AbstractCachedController;
import com.sf.sfpp.web.controller.user.UserController;
import com.sf.sfpp.web.controller.user.UserRightController;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/16
 */
@Controller
public class PcompSoftwareController extends AbstractCachedController {
    private final static Logger log = LoggerFactory.getLogger(PcompSoftwareController.class);

    @Autowired
    private UserRightController userRightController;
    @Autowired
    private UserController userController;
    @Autowired
    private PcompTitleService pcompTitleService;

    @Autowired
    private PcompKindService pcompKindService;

    @Autowired
    private PcompSoftwareService pcompSoftwareService;

    @Autowired
    private ImageController imageController;

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_VALIDATE_PATH,
            method = RequestMethod.GET)
    public JsonResult<Boolean> validateSoftwareName(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String kindId = request.getParameter(PcompConstants.PCOMP_KIND);
        String softwareName = request.getParameter(PathConstants.PCOMP_SOFTWARE_NAME);
        try {
            result.setData(pcompSoftwareService.existsSoftware(kindId, softwareName));
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        result.setData(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_FETCH_PATH,
            method = RequestMethod.GET)
    public JsonResult<List<PcompSoftware>> getSoftwareList(HttpServletRequest request) {
        JsonResult<List<PcompSoftware>> result = new JsonResult<>();
        List<PcompSoftware> pcompSoftwares = new LinkedList<>();
        String titleName = request.getParameter(PathConstants.PCOMP_TITLE_NAME);
        String kindName = request.getParameter(PathConstants.PCOMP_KIND_NAME);
        try {
            PcompKind pcompKind = pcompKindService.fetchKind(titleName, kindName);
            List<PcompSoftware> list = pcompSoftwareService
                    .fetchAllSoftwaresSeparatelyByKind(pcompKind.getId(), Constants.ALL_PAGE_NUMBER).getList();
            for (PcompSoftware pcompSoftware : list) {
                if (!userRightController.getHasViewPcompSoftwareRight(pcompSoftware.getId()).getData()) {
                    list.remove(pcompSoftware);
                }
            }
            result.setData(list);
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            log.warn(ExceptionUtils.getStackTrace(e));
        }
        result.setData(pcompSoftwares);
        return result;
    }

    //todo 各种推荐实现
    @ResponseBody
    @RequestMapping(value = "pcomp/software/recommended",
            method = RequestMethod.GET)
    public JsonResult<List<PcompSoftware>> getRecommendedSoftware(HttpServletRequest request) {
        JsonResult<List<PcompSoftware>> result = new JsonResult<>();
        try {
            String pcompKindId = request.getParameter("pcompKindId");
            List<PcompSoftware> list;
            if (pcompKindId == null) {
                list = pcompSoftwareService.fetchRecommendedSoftwares().getList();
            } else {
                list = pcompSoftwareService.fetchAllSoftwaresSeparatelyByKind(pcompKindId, 1).getList();
            }
            for (PcompSoftware pcompSoftware : list) {
                if (!userRightController.getHasViewPcompSoftwareRight(pcompSoftware.getId()).getData()) {
                    list.remove(pcompSoftware);
                }
            }
            result.setData(list);
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTrace(e));
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "pcomp/software/internal/history",
            method = RequestMethod.GET)
    public JsonResult<PageInfo<PcompSoftware>> getInternalSoftwareHistory(HttpServletRequest request) {
        JsonResult<PageInfo<PcompSoftware>> result = new JsonResult<>();
        try {
            String pageNumber = request.getParameter("pageNumber");
            PageInfo<PcompSoftware> list = pcompSoftwareService
                    .fetchAllInternalSoftwaresOrderByCreatedTime(Integer.parseInt(pageNumber));
            List<PcompSoftware> pcompSoftwareList = new ArrayList<>();
            for (PcompSoftware pcompSoftware : list.getList()) {
                if (userRightController.getHasViewPcompSoftwareRight(pcompSoftware.getId()).getData()) {
                    pcompSoftwareList.add(new PcompSoftwareHistoryItem(pcompSoftware, userController));
                }
            }
            list.setList(pcompSoftwareList);
            result.setData(list);
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTrace(e));
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "pcomp/software/open/history",
            method = RequestMethod.GET)
    public JsonResult<PageInfo<PcompSoftware>> getOpenSoftwareHistory(HttpServletRequest request) {
        JsonResult<PageInfo<PcompSoftware>> result = new JsonResult<>();
        try {
            String pageNumber = request.getParameter("pageNumber");
            PageInfo<PcompSoftware> list = pcompSoftwareService
                    .fetchAllOpenSoftwaresOrderByCreatedTime(Integer.parseInt(pageNumber));
            for (PcompSoftware pcompSoftware : list.getList()) {
                if (userRightController.getHasViewPcompSoftwareRight(pcompSoftware.getId()).getData()) {
                    list.getList().add(new PcompSoftwareHistoryItem(pcompSoftware, userController));
                }
                list.getList().remove(pcompSoftware);
            }
            result.setData(list);
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTrace(e));
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "pcomp/software/getAllByKind",
            method = RequestMethod.GET)
    public JsonResult<PageInfo<PcompSoftware>> getAllSoftwareByKind(HttpServletRequest request) {
        JsonResult<PageInfo<PcompSoftware>> result = new JsonResult<>();
        try {
            String pcompKindId = request.getParameter("pcompKindId");
            String pageNumber = request.getParameter("pageNumber");
            PageInfo<PcompSoftware> pcompSoftwarePageInfo = pcompSoftwareService
                    .fetchAllSoftwaresSeparatelyByKind(pcompKindId, Integer.parseInt(pageNumber));
            for (PcompSoftware pcompSoftware : pcompSoftwarePageInfo.getList()) {
                if (!userRightController.getHasViewPcompSoftwareRight(pcompSoftware.getId()).getData()) {
                    pcompSoftwarePageInfo.getList().remove(pcompSoftware);
                }
            }
            result.setData(pcompSoftwarePageInfo);
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTrace(e));
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "pcomp/software/getById",
            method = RequestMethod.GET)
    public JsonResult<PcompSoftware> getSoftwareById(HttpServletRequest request) {
        JsonResult<PcompSoftware> result = new JsonResult<>();
        try {
            String pcompSoftwareId = request.getParameter("pcompSoftwareId");
            if (userRightController.getHasViewPcompSoftwareRight(pcompSoftwareId).getData()) {
                result.setData(pcompSoftwareService.fetchSoftware(pcompSoftwareId));
            } else {
                result.setData(null);
                result.setMessage("Permission Denied");
            }
        } catch (Exception e) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            log.warn(stackTrace);
            result.setMessage(stackTrace);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_CREATE_PATH,
            method = RequestMethod.POST)
    public JsonResult<Boolean> createSoftware(@RequestParam(PathConstants.PCOMP_SOFTWARE_AVATAR) MultipartFile avatar,
            HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String kindId = request.getParameter(PcompConstants.PCOMP_KIND);
        String pcomp_software_name = request.getParameter(PathConstants.PCOMP_SOFTWARE_NAME);
        String pcomp_software_short_introduction = request
                .getParameter(PathConstants.PCOMP_SOFTWARE_SHORT_INTRODUCTION);
        String pcomp_software_introduction = request.getParameter(PathConstants.PCOMP_SOFTWARE_INTRODUCTION);
        PcompSoftware pcompSoftware = null;
        try {
            JsonResult<Boolean> hasRight = userRightController.getHasAddPcompSoftwareRight(kindId);
            if (hasRight.getData()) {
                pcompSoftware = new PcompSoftware();
                if (pcompSoftwareService.existsSoftware(kindId, pcomp_software_name)) {
                    result.setData(false);
                    result.setMessage("Software exists!");
                }
                pcompSoftware.setId(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM));
                pcompSoftware.setName(pcomp_software_name);
                pcompSoftware.setPcompKindId(kindId);
                pcompSoftware.setIntroduction(pcomp_software_introduction);
                pcompSoftware.setIntroductionShort(pcomp_software_short_introduction);
                pcompSoftware
                        .setAvatar(avatar.getSize() > 0 ? imageController.uploadImage(avatar, ImageKind.AVATAR) : "");
                pcompSoftware.setCreatedBy(((User) SecurityUtils.getSubject().getPrincipal()).getId());
                pcompSoftware.setModifiedBy(((User) SecurityUtils.getSubject().getPrincipal()).getId());
                pcompSoftwareService.addSoftware(pcompSoftware);
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
    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_MODIFICATION_PATH,
            method = RequestMethod.POST)
    public JsonResult<Boolean> updateSoftware(@RequestParam(value = PathConstants.PCOMP_SOFTWARE_AVATAR,
            required = false) MultipartFile avatar, HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        boolean modified = false;
        String pcomp_software_id = request.getParameter(PathConstants.PCOMP_SOFTWARE_ID);
        String pcomp_software_name = request.getParameter(PathConstants.PCOMP_SOFTWARE_NAME);
        String pcomp_software_introduction = request.getParameter(PathConstants.PCOMP_SOFTWARE_INTRODUCTION);
        try {
            JsonResult<Boolean> hasRight = userRightController.getHasModifyPcompSoftwareRight(pcomp_software_id);
            if (hasRight.getData()) {
                PcompSoftwareExtend pcompSoftware = (PcompSoftwareExtend) pcompSoftwareService
                        .fetchSoftware(pcomp_software_id);
                if (pcompSoftware == null) {
                    throw new PcompException(
                            new StringBuilder().append("Software:").append(pcomp_software_id).append("不存在").toString(),
                            new Exception());
                }

                if (avatar != null && avatar.getSize() != 0) {
                    pcompSoftware.setAvatar(imageController.uploadImage(avatar, ImageKind.AVATAR));
                    modified = true;
                }

                if (!StrUtils.isNull(pcomp_software_name)) {
                    pcompSoftware.setName(pcomp_software_name);
                    modified = true;
                }
                if (!StrUtils.isNull(pcomp_software_introduction)) {
                    pcompSoftware.setIntroduction(pcomp_software_introduction);
                    modified = true;
                }
                if (modified) {
                    pcompSoftware.setModifiedBy(((User) SecurityUtils.getSubject().getPrincipal()).getId());
                }
                pcompSoftwareService.updateSoftware(pcompSoftware);
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
    @RequestMapping(value = PathConstants.PCOMP_SOFTWARE_REMOVE_PATH,
            method = RequestMethod.GET)
    public JsonResult<Boolean> removeSoftware(HttpServletRequest request) {
        JsonResult<Boolean> result = new JsonResult<>();
        String softwareID = request.getParameter(PcompConstants.PCOMP_SOFTWARE);
        try {
            JsonResult<Boolean> hasRight = userRightController.getHasModifyPcompSoftwareRight(softwareID);
            if (hasRight.getData()) {
                User user = (User) SecurityUtils.getSubject().getPrincipal();
                pcompSoftwareService.removeSoftware(softwareID, user.getId());
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

class PcompSoftwareHistoryItem extends PcompSoftware {
    private String id;
    private String name;
    private String title;
    private String log;
    private String createTime;
    private String avatar;

    public PcompSoftwareHistoryItem(PcompSoftware pcompSoftware, UserController userController) {
        setId(pcompSoftware.getId());
        setName(pcompSoftware.getName());
        setTitle(pcompSoftware.getCreatedTime());
        User user = userController.getUserInfo(StrUtils.makeString(pcompSoftware.getCreatedBy())).getData();
        setLog(user.getUserNo(),user.getUserName());
        setCreateTime(pcompSoftware.getCreatedTime());
        setAvatar(pcompSoftware.getAvatar());
    }

    private static ThreadLocal<DateFormat> threadLocal1 = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd/MM/yyyy");
        }
    };

    private static ThreadLocal<DateFormat> threadLocal2 = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("MM月dd日");
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String userNo, String userName) {
        this.log = StrUtils.makeString(userName, "(", userNo, ")", " 发布了 ", this.name);
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createdtime) {
        this.createTime = threadLocal1.get().format(createdtime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(Date createdTime) {
        this.title = threadLocal2.get().format(createdTime);
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
