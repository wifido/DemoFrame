<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="java.util.Map" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<ol class="breadcrumb">
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        Map<String, String> pathTree = webCache.getPathTree();
    %>
    <%
        for (String key : pathTree.keySet()) {
    %>
            <li><a href="<%=pathTree.get(key)%>">
                <%=key%>
            </a></li>
    <%
        }
    %>
</ol>