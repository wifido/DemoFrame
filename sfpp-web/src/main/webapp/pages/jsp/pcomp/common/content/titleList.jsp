<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompTitle" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<p class="bannerlist">

    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
        PcompTitle title = pcompCacheObject.getPcompTitle();
    %>
    <a href="<%=PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH)%>"
            <% if (title == null) {%>
        class="active"<%}%>>全部主题</a>
        <%
        for(PcompTitle pcompTitle:pcompCacheObject.getPcompTitles()){
        String titleId = pcompTitle.getId();
    %>
    <a href="<%=
            StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH),
            Constants.PARAMETER_START_SEPARATOR,
            PcompConstants.PCOMP_TITLE,
            Constants.PARAMETER_EQUALS,titleId)
        %>"
            <%
                if (title != null && titleId.equals(title.getId())) {
            %>
                    class='active'
            <%
                }
            %> target="_self"><%=pcompTitle.getName()%></a>
        <%
        }
    %>

<p>