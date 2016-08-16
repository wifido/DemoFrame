<%@ page import="com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<div id="<%=PcompConstants.HISTORY%>">
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
        PcompSoftwareExtend pcompSoftware = (PcompSoftwareExtend) pcompCacheObject.getPcompSoftware();
    %>
    <textarea style="display:none;"><%=pcompSoftware.getIntroduction()%></textarea>
</div>