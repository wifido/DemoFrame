<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompTitle" %>
<%@ page import="com.sf.sfpp.web.common.PagePathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<p class="bannerlist">

        <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
        PcompTitle title = pcompCacheObject.getPcompTitle();
    %>
    <a href="<%out.print(PathUtils.makePath(PagePathConstants.PCOMP_HOMEPAGE_PATH));%>" <%
        if (title == null) out.print("class=\"active\"");%>>全部主题</a>
        <%
        for(PcompTitle pcompTitle:pcompCacheObject.getPcompTitles()){
    %>
    <a href="<%
            String titleId = pcompTitle.getId();
            out.print(StrUtils.makeString(PathUtils.makePath(PagePathConstants.PCOMP_HOMEPAGE_PATH),
            Constants.PARAMETER_START_SEPARATOR,
            PcompConstants.PCOMP_TITLE,
            Constants.PARAMETER_EQUALS,titleId));
        %>"
            <%
                if (title != null && titleId.equals(title.getId())) out.print("class='active'");
            %>><%
        out.print(pcompTitle.getName());
    %></a>
        <%
        }
    %>

<p>