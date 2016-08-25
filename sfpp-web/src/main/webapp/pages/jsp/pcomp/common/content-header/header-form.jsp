<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@ page import="java.util.Map" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
    Map<String, String> pathTree = webCache.getPathTree();
    if (!pathTree.containsKey(Constants.PUBLIC_SEARCH)) {
%>

<form class="navbar-form navbar-left" role="search" action="<%=PathUtils.makePath(PathConstants.PCOMP_SEARCH_PATH)%> "
      method="get"
      style="padding-left: 3%">
    <div class="form-group">
        <input type="text" class="form-control" name="<%=PathConstants.PCOMP_SEARCH_KEYWORD%>" placeholder="Search">
        <button type="submit" name="search" class="btn btn-primary"><i class="fa fa-search"></i></button>
        <div class="btn-group">
            <button type="button" class="btn btn-primary"
                    onclick=" window.location.href='<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_ADD_PAGE)%>'">
                发布新软件
            </button>
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <span class="caret"></span>
                <span class="sr-only">Toggle Dropdown</span>
            </button>

            <ul class="dropdown-menu" role="menu">
                <li><a href="<%=PathUtils.makePath(PathConstants.PCOMP_VERSION_ADD_PAGE)%>" target="_self">发布新版本</a>
                </li>
                <shiro:hasPermission name="/document/createSubject">
                    <li><a href="<%=PathUtils.makePath(PathConstants.PCOMP_TITLE_KIND_ADD_PAGE)%>" target="_self">创建新主题或分类</a>
                    </li>
                </shiro:hasPermission>
            </ul>

        </div>
    </div>
</form>
<%}%>