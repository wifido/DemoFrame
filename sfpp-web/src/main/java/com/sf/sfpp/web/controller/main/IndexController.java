package com.sf.sfpp.web.controller.main;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.WebCache;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/12
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public ModelAndView mainPage(HttpServletRequest request, ModelMap model) {
        WebCache webCache = new WebCache();
        webCache.setTitle("SFPP");
        model.addAttribute(Constants.WEB_CACHE_KEY, webCache);
        return new ModelAndView("index");
    }
}
