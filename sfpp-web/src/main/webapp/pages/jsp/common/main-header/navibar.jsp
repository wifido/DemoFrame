<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.web.common.PagePathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<ul class="nav navbar-nav navbar-left">
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        Map<String, String> pathTree = webCache.getPathTree();
        String arg1 = "", arg2 = "", arg3 = "", arg4 = "", arg5 = "";
        if (pathTree.size() == 0)
            arg1 = "class=\"active\"";
        else if (pathTree.containsKey(Constants.KNOWLEDGE_NETWORK_SYSTEM))
            arg2 = "class=\"active\"";
        else if (pathTree.containsKey(Constants.COMMUNITY_SYSTEM))
            arg3 = "class=\"active\"";
        else if (pathTree.containsKey(Constants.PUBLIC_COMPONENT_SYSTEM))
            arg4 = "class=\"active\"";
        else if (pathTree.containsKey(Constants.ACADEMY_SYSTEM))
            arg5 = "class=\"active\"";
        else
            arg1 = "class=\"active\"";
    %>

    <li <%out.print(arg1);%> role="presentation">
        <a href="<%out.print(PathUtils.makePath(PagePathConstants.HOMEPAGE_PATH));%>"><i
                class="glyphicon glyphicon-home"></i> 主页</a>
    </li>
    <li <%out.print(arg2);%> role="presentation">
        <a href="<%out.print(PathUtils.makePath(PagePathConstants.KNNET_HOMEPAGE_PATH));%>"><i class="fa fa-globe"></i>
            知识网络</a>
    </li>
    <li <%out.print(arg3);%> role="presentation">
        <a href="<%out.print(PathUtils.makePath(PagePathConstants.COMMUNITY_HOMEPAGE_PATH));%>"> <i
                class="fa fa-group"></i> 开放社区</a>
    </li>
    <li <%out.print(arg4);%> role="presentation">
        <a href="<%out.print(PathUtils.makePath(PagePathConstants.PCOMP_HOMEPAGE_PATH));%>"> <i class="fa fa-gears"></i>
            公共组件</a>
    </li>
    <li <%out.print(arg5);%> role="presentation">
        <a href="<%out.print(PathUtils.makePath(PagePathConstants.ACADEMY_HOMEPAGE_PATH));%>"> <i
                class="fa fa-building"></i> 技术学院</a>
    </li>
</ul>
