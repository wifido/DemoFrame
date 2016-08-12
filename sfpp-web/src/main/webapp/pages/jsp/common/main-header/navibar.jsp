<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<ul class="nav navbar-nav navbar-left">
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        List<String> pathTree = webCache.getPathTree();
        String arg1 = "", arg2 = "", arg3 = "", arg4 = "", arg5 = "";
        if (pathTree.size() == 0)
            arg1 = "class=\"active\"";
        else if (Constants.KNOWLEDGE_NETWORK_SYSTEM.equals(pathTree.get(1)))
            arg2 = "class=\"active\"";
        else if (Constants.COMMUNITY_SYSTEM.equals(pathTree.get(1)))
            arg3 = "class=\"active\"";
        else if (Constants.PUBLIC_COMPONENT_SYSTEM.equals(pathTree.get(1)))
            arg4 = "class=\"active\"";
        else if (Constants.ACADEMY_SYSTEM.equals(pathTree.get(1)))
            arg5 = "class=\"active\"";
        else
            arg1 = "class=\"active\"";
    %>

    <li <%out.print(arg1);%> role="presentation">
        <a href="#"><i class="glyphicon glyphicon-home"></i> 主页</a>
    </li>
    <li <%out.print(arg2);%> role="presentation">
        <a href="/sfpp-web/knlib.do"><i class="fa fa-globe"></i> 知识网络</a>
    </li>
    <li <%out.print(arg3);%> role="presentation">
        <a href="/sfpp-web/commonity.do"> <i class="fa fa-group"></i> 开放社区</a>
    </li>
    <li <%out.print(arg4);%> role="presentation">
        <a href="/sfpp-web/pcomp.do"> <i class="fa fa-gears"></i> 公共组件</a>
    </li>
    <li <%out.print(arg5);%> role="presentation">
        <a href="/sfpp-web/academy.do"> <i class="fa fa-building"></i> 技术学院</a>
    </li>
</ul>
