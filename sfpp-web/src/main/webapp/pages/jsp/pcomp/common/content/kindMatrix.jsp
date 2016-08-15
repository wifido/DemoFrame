<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.common.utils.ImageUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompKind" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@ page import="java.util.List" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<div class="kn_column_content">
    <div class="allist clearfix" id="base_list">
        <ul>
            <%
                WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
                PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
                List<PcompKind> pcompKinds = pcompCacheObject.getPcompKinds();
                for (PcompKind pcompKind : pcompKinds) {
                    String link = PathUtils.makeKindPath(pcompKind.getId());
            %>
            <li class="whitebk">
                <div class="divtop pos">
                    <div class="bannerimg">
                        <img src="<%=pcompKind.getBannerImage()%>" alt="" width="<%=ImageUtils.BANNER_IMAGE_WIDTH%>"
                             height="<%=ImageUtils.BANNER_IMAGE_HEIGHT%>">
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
            <%
                }
            %>
        </ul>
    </div>
</div>