<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="java.util.Map" %>
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
        Map<String, String> pathTree = webCache.getPathTree();
    %>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <%if (pathTree.containsKey(Constants.PUBLIC_COMPONENT_SYSTEM) && pathTree.size() >= 3) { %>
        <a href="#" class="sidebar-toggle"
           data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </a>
        <%}%>
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