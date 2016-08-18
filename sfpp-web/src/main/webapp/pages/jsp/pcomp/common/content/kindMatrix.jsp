<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.common.utils.ImageUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompKind" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@ page import="java.util.List" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<ul>

    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
        List<PcompKind> pcompKinds = pcompCacheObject.getPcompKinds();
        for (PcompKind pcompKind : pcompKinds) {
            String link = PathUtils.makeKindPath(pcompKind.getId());
    %>
    <div class="col-lg-3 col-sm-12">
        <li class="whitebk">
            <div class="divtop pos">
                <div>
                    <img src="<%=pcompKind.getBannerImage()%>" class="bannerimg">
                </div>
                <a href="<%=link%>" target="_blank" class="topphoto" target="_self">
                    <img src="<%=pcompKind.getTopPhoto()%>" alt="" width="<%=ImageUtils.TOP_PHOTO_WIDTH%>"
                         height="<%=ImageUtils.TOP_PHOTO_HEIGHT%>">
                </a>
            </div>
            <div class="divbottoms">
                <a href="<%=link%>" class="title" target="_self"><%=pcompKind.getName()%>
                </a>

                <p class="pext">
                    <%=pcompKind.getIntroduction()%>
                </p>

                <p class="pabtn">
                    <a href="<%=link%>" target="_blank" target="_self">立即进入</a>
                </p>
            </div>
        </li>
    </div>
    <%
        }
    %>

</ul>