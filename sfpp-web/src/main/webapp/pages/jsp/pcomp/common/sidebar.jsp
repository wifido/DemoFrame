<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompVersion" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@ page import="com.sf.sfpp.web.common.utils.PermissionUtils" %>
<%@ page import="java.util.List" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<aside class="main-sidebar">
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
        PcompSoftwareExtend pcompSoftware = (PcompSoftwareExtend) pcompCacheObject.getPcompSoftware();
        List<PcompVersion> pcompVersions = pcompSoftware.getPcompVersions();
        String page_nav = (String) request.getAttribute(PcompConstants.SOFTWARE_PAGE_NAVIGATION);
        String pcomp_version = (String) request.getAttribute(PcompConstants.PCOMP_VERSION);
    %>
    <div class="user-panel">
        <div class="pull-left image">
            <img src="<%=pcompSoftware.getAvatar()%>" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
            <p><%=pcompSoftware.getName()%>
            <%
                if (pcompSoftware != null
                        && PermissionUtils.isCurrentUser(pcompSoftware.getCreatedBy())) {
            %>
            <button type="button" class="btn btn-box-tool"><i class="fa fa-pencil"></i>
            </button>
            <button type="button" class="btn btn-box-tool"><i class="fa fa-trash"></i>
            </button>
            <%}%>
            </p>
        </div>
    </div>

    <section class="sidebar" style="height: auto;">
        <ul class="sidebar-menu">
            <li class="header">目录</li>
            <li class="treeview <%=(PcompConstants.INTRODUCTION.equals(page_nav))||page_nav==null?"active":""%>">
                <a href="<%=PathUtils.makeVersionPath(pcompSoftware.getId(),PcompConstants.INTRODUCTION,"")%>">
                    <i class="fa fa-dashboard "></i> <span><%=PcompConstants.INTRODUCTION_CH%></span>
                </a>
            </li>

            <li class="treeview <%=(PcompConstants.HISTORY.equals(page_nav))?"active":""%>">
                <a href="#">
                    <i class="fa fa-folder"></i> <span><%=PcompConstants.HISTORY_CH%></span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <%
                        for (PcompVersion pcompVersion : pcompVersions) {

                    %>
                    <li class="<%=(PcompConstants.HISTORY.equals(page_nav))&&(pcompVersion.getId().equals(pcomp_version))?"active":""%>">

                        <a href="<%=PathUtils.makeVersionPath(pcompSoftware.getId(),PcompConstants.HISTORY,pcompVersion.getId())%>"><i
                                class="fa <%=(PcompConstants.HISTORY.equals(page_nav))&&(pcompVersion.getId().equals(pcomp_version))?"fa-check-circle-o":"fa-circle-o"%>"></i> <%=pcompVersion.getVersionNumber()%>
                        </a>
                        </li>
                    <%
                        }
                    %>
                </ul>
            </li>
            <li class="treeview <%=(PcompConstants.DOWNLOAD.equals(page_nav))?"active":""%>">
                <a href="#">
                    <i class="fa fa-share"></i> <span><%=PcompConstants.DOWNLOAD_CH%></span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <%
                        for (PcompVersion pcompVersion : pcompVersions) {

                    %>
                    <li class="<%=(PcompConstants.DOWNLOAD.equals(page_nav))&&(pcompVersion.getId().equals(pcomp_version))?"active":""%>">
                        <a href="<%=PathUtils.makeVersionPath(pcompSoftware.getId(),PcompConstants.DOWNLOAD,pcompVersion.getId())%>"><i
                                class="fa <%=(PcompConstants.DOWNLOAD.equals(page_nav))&&(pcompVersion.getId().equals(pcomp_version))?"fa-check-circle-o":"fa-circle-o"%>"></i> <%=pcompVersion.getVersionNumber()%>
                        </a></li>
                    <%
                        }
                    %>
                </ul>
            </li>
            <li class="tree-view <%=(PcompConstants.QUICKSTART.equals(page_nav))?"active":""%>">
                <a href="#">
                    <i class="fa fa fa-adn"></i> <span><%=PcompConstants.QUICKSTART_CH%></span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <%
                        for (PcompVersion pcompVersion : pcompVersions) {

                    %>
                    <li class="<%=(PcompConstants.QUICKSTART.equals(page_nav))&&(pcompVersion.getId().equals(pcomp_version))?"active":""%>">
                        <a href="<%=PathUtils.makeVersionPath(pcompSoftware.getId(),PcompConstants.QUICKSTART,pcompVersion.getId())%>"><i
                                class="fa <%=(PcompConstants.QUICKSTART.equals(page_nav))&&(pcompVersion.getId().equals(pcomp_version))?"fa-check-circle-o":"fa-circle-o"%>"></i> <%=pcompVersion.getVersionNumber()%>
                        </a></li>
                    <%
                        }
                    %>
                </ul>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>