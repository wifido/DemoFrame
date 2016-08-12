<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.user.dao.domain.User" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
    User user = (User) webCache.getUser();
%>
<!-- User Account: style can be found in dropdown.less -->
<li class="dropdown user user-menu">
    <a href="" class="dropdown-toggle" data-toggle="dropdown">
        <span class="hidden-xs"><%out.print(StrUtils.isNull(user.getUserName()) ? user.getUserNo() : user.getUserName());%></span>
    </a>
</li>
