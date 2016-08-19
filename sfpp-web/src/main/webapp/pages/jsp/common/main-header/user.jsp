<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.user.dao.domain.User" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
    User user = (User) webCache.getUser();
%>
<li class="dropdown user user-menu">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
        <img src="/sfpp-web/assets/img/avatar.png" class="user-image" alt="User Image">
        <span class="hidden-xs"><%=user.getUserNo()%></span>
    </a>
    <ul class="dropdown-menu">
        <!-- User image -->
        <li class="user-header">
            <img src="/sfpp-web/assets/img/avatar.png" class="img-circle" alt="User Image">

            <p>
                <%=user.getUserNo()%>
                <small>加入时间：<%=user.getCreatedTime()%></small>
            </p>
        </li>
        <!-- Menu Body -->
        <!-- Menu Footer-->
        <li class="user-footer">
            <div class="pull-left">
                <a href="#" class="btn btn-primary btn-flat" disabled="true">个人主页</a>
            </div>
            <div class="pull-right">
                <a href="<%=PathUtils.makePath(PathConstants.LOGOUT_PATH)%>" class="btn btn-primary btn-flat">Sign out</a>
            </div>
        </li>
    </ul>
</li>
