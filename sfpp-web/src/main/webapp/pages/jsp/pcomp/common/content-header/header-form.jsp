<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@ page import="java.util.Map" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
    Map<String, String> pathTree = webCache.getPathTree();
    if (!pathTree.containsKey(Constants.PUBLIC_SEARCH)) {
%>

<form class="navbar-form navbar-left" role="search" action="<%=PathUtils.makePath(PathConstants.PCOMP_SEARCH_PATH)%> " method="get">
    <div class="form-group">
        <input type="text" class="form-control" name="<%=PathConstants.PCOMP_SEARCH_KEYWORD%>" placeholder="Search">
        <button type="submit" name="search" class="btn btn-primary"><i class="fa fa-search"></i></button>
        <div class="btn-group">
            <button type="button" class="btn btn-primary"
                    onclick=" window.location.href='/sfpp-web/pcomp/software/add.do'">发布软件(或版本)
            </button>
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <span class="caret"></span>
                <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="/sfpp-web/pcomp/title/add.do" target="_blank">创建新主题</a></li>
                <li><a href="/sfpp-web/pcomp/kind/add.do" target="_blank">创建新分类</a></li>
            </ul>
        </div>
    </div>
</form>
<%}%>