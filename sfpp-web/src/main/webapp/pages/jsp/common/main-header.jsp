<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<header class="main-header">
    <!-- Logo -->
    <a href="/sfpp-web/index.do" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>SFPP</b></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>顺丰开放平台</b></span>
    </a>
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
    %>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <jsp:include page="./main-header/navibar.jsp"></jsp:include>
                <% if (webCache.getUser() == null) {%>
                <jsp:include page="./main-header/login.jsp"></jsp:include>
                <%} else {%>
                <jsp:include page="./main-header/user.jsp"></jsp:include>
                <%}%>
            </ul>
        </div>
    </nav>
</header>