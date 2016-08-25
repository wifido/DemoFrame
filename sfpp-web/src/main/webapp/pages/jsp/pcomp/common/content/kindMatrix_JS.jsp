<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page import="com.github.pagehelper.PageInfo" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.common.utils.ImageUtils" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompKind" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompTitle" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<div class="row">
    <ul>

        <%
            WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
            PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
            PageInfo<PcompKind> pcompKinds = pcompCacheObject.getPcompKinds();
            PcompTitle title = pcompCacheObject.getPcompTitle();
            for (PcompKind pcompKind : pcompKinds.getList()) {
                String link = PathUtils.makeKindPath(pcompKind.getId());
        %>
        <div class=" col-lg-2 col-md-3  col-sm-4 col-xs-12">
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
                        <shiro:hasPermission name="/document/createSubject">
                            <a href="<%=link%>" target="_blank" target="_self">修改</a>
                            <a href="<%=StrUtils.makeString(PathUtils.makeKindPath(PathConstants.PCOMP_KIND_REMOVE_PATH),
                                Constants.PARAMETER_START_SEPARATOR, PcompConstants.PCOMP_KIND,
                                Constants.PARAMETER_EQUALS, pcompKind.getId())%>" target="_blank" target="_self">删除</a>
                        </shiro:hasPermission>
                    </p>
                </div>
            </li>
        </div>
        <%
            }
        %>
    </ul>
</div>
<div class="row">
    <p class="text-center">
        <%
            PageInfo pageInfo = (PageInfo) pcompKinds;

        %>
        <a href="<%=title!=null?PathUtils.makeTitlePath(title.getId()):PathUtils.makeTitlePath(null)%>">
            <button class="btn <%if(pageInfo.getPageNum()==1){%>btn-primary<%}else{%>btn-warn<%}%>">
                首页
            </button>
        </a>
        <a href="<%if(pageInfo.getPageNum()!=1){%><%=title!=null?PathUtils.makeTitlePath(title.getId(),pageInfo.getPageNum()-1):PathUtils.makeTitlePath(null,pageInfo.getPageNum()-1)%><%}%>">
            <button class="btn btn-warn" <%if(pageInfo.getPageNum()==1){%>disabled<%}%>>上一页
            </button>
        </a>
        <%
            int start = 0, end = 0;
            if (pageInfo.getPages() < 10) {
                start = 1;
                end = pageInfo.getPages();
            } else if (pageInfo.getPageNum() <= pageInfo.getPages() - 10) {
                start = pageInfo.getPageNum();
                end = pageInfo.getPageNum() + 9;
            } else {
                start = pageInfo.getPages() - 10;
                end = pageInfo.getPages();
            }
        %>
        <%

            for (int i = start; i <= end; i++) {
        %>
        <a href="<%=title!=null?PathUtils.makeTitlePath(title.getId(),i):PathUtils.makeTitlePath(null,i)%>">
            <button class="btn <%if(pageInfo.getPageNum()==i){%>btn-primary<%}else{%>btn-warn<%}%>"><%=i%>
            </button>
        </a>
        <%
            }
        %>

        <a href="<%if(pageInfo.getPageNum()!=pageInfo.getPages()){%><%=title!=null?PathUtils.makeTitlePath(title.getId(),pageInfo.getPageNum()+1):PathUtils.makeTitlePath(null,pageInfo.getPageNum()+1)%><%}%>">
            <button class="btn btn-warn" <%if(pageInfo.getPageNum()==pageInfo.getPages()){%>disabled<%}%>>下一页
            </button>
        </a>


        <a href="<%=title!=null?PathUtils.makeTitlePath(title.getId(),pageInfo.getPages()):PathUtils.makeTitlePath(null,pageInfo.getPages())%>">
            <button class="btn <%if(pageInfo.getPageNum()==pageInfo.getPages()){%>btn-primary<%}else{%>btn-warn<%}%>">
                末页
            </button>
        </a>

    </p>
</div>