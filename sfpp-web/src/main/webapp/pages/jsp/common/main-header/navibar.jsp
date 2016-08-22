<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@ page import="java.util.Map" %>
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

    <li <%=arg1%> role="presentation">
        <a href="<%=PathUtils.makePath(PathConstants.INDEX_PATH)%>"><i
                class="fa fa-home"></i> 主页</a>
    </li>
    <li <%=arg2%> role="presentation">
        <a href="<%=PathUtils.makePath(PathConstants.KNNET_HOMEPAGE_PATH)%>"><i class="fa fa-globe"></i>
            <%=Constants.KNOWLEDGE_NETWORK_SYSTEM%></a>
    </li>
    <li <%=arg3%> role="presentation">
        <a href="<%=PathUtils.makePath(PathConstants.COMMUNITY_HOMEPAGE_PATH)%>"> <i
                class="fa fa-group"></i> <%=Constants.COMMUNITY_SYSTEM%></a>
    </li>
    <li <%=arg4%> role="presentation">
        <a href="<%=PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH)%>"> <i class="fa fa-gears"></i>
            <%=Constants.PUBLIC_COMPONENT_SYSTEM%></a>
    </li>
    <li <%=arg5%> role="presentation">
        <a href="<%=PathUtils.makePath(PathConstants.ACADEMY_HOMEPAGE_PATH)%>"> <i
                class="fa fa-building"></i> <%=Constants.ACADEMY_SYSTEM%></a>
    </li>
</ul>
