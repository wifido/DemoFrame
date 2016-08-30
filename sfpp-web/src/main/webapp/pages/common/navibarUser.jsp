<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.user.dao.domain.User" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
%>

<style>
    div > ul > li > a > img {
        -webkit-font-smoothing: antialiased;
        border-bottom-color: rgb(255, 255, 255);
        border-bottom-left-radius: 50%;
        border-bottom-right-radius: 50%;
        border-bottom-style: none;
        border-bottom-width: 0px;
        border-image-outset: 0px;
        border-image-repeat: stretch;
        border-image-slice: 100%;
        border-image-source: none;
        border-image-width: 1;
        border-left-color: rgb(255, 255, 255);
        border-left-style: none;
        border-left-width: 0px;
        border-right-color: rgb(255, 255, 255);
        border-right-style: none;
        border-right-width: 0px;
        border-top-color: rgb(255, 255, 255);
        border-top-left-radius: 50%;
        border-top-right-radius: 50%;
        border-top-style: none;
        border-top-width: 0px;
        box-sizing: border-box;
        color: rgb(255, 255, 255);
        cursor: auto;
        display: block;
        float: left;
        font-family: 'Microsoft Yahei';
        font-size: 13px;
        font-weight: normal;
        height: 25px;
        line-height: 18px;
        list-style-image: none;
        list-style-position: outside;
        list-style-type: none;
        margin-right: 10px;
        margin-top: -2px;
        max-width: none;
        text-align: left;
        vertical-align: middle;
        width: 25px;
    }
</style>

<div class="navbar-right ">
    <ul class="nav navbar-nav m-n hidden-xs nav-user user">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle bg clear" data-toggle="dropdown">
                <img src="/sfpp-web/homeTheme/images/avatar.png" style="">
                <%=((User) webCache.getUser()).getUserNo()%> <b class="caret"></b>
            </a>
            <ul class="dropdown-menu animated fadeInRight">

                <li>
                    <a href="<%=PathUtils.makePath(PathConstants.LOGOUT_PATH)%>" data-toggle="ajaxModal">Logout</a>
                </li>
            </ul>
        </li>
    </ul>
</div>