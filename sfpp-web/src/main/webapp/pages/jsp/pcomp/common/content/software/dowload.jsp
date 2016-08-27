<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompVersion" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompVersionDoucumentDownload" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompVersionPlatformDownload" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend" %>
<%@ page import="com.sf.sfpp.web.common.utils.PermissionUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%
    WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
    PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
    PcompSoftwareExtend pcompSoftware = (PcompSoftwareExtend) pcompCacheObject.getPcompSoftware();
    String pcomp_version = (String) request.getAttribute(PcompConstants.PCOMP_VERSION);
    for (PcompVersion pcompVersion : pcompSoftware.getPcompVersions()) {
        if (pcompVersion.getId().equals(pcomp_version)) {
            PcompVersionExtend pcompVersionExtend = (PcompVersionExtend) pcompVersion;
%>

<div class="box direct-chat direct-chat-primary">
    <div class="box-header with-border" id="<%=PcompConstants.HISTORY_DOWNLOAD%>_box_header">
        <h3 class="box-title"><%=PcompConstants.HISTORY_DOWNLOAD_CH%>
        </h3>

        <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
        </div>

    </div>
    <div class="box-body" id="<%=PcompConstants.HISTORY_DOWNLOAD%>_box_body">
        <div class="markdown-body editormd-html-preview">
            <h2>软件下载：
                <%
                    if ((pcompSoftware != null
                            && PermissionUtils.isCurrentUser(pcompSoftware.getCreatedBy())) || (PermissionUtils.isCurrentUser(pcompVersionExtend.getCreatedBy()))) {
                %>
                <button class="btn btn-primary" onclick="edit<%=PcompConstants.PCOMP_VERSION%>('', '', 3)">增加</button>
                <%
                    }
                %>
            </h2>
            <table>
                <tbody>
                <%
                    for (PcompVersionPlatformDownload pcompVersionPlatformDownload : pcompVersionExtend.getPcompVersionPlatformDownloads()) {
                %>
                <tr>
                    <td>
                        <%=pcompVersionPlatformDownload.getPlatform()%>
                    </td>
                    <td>
                        <a href="<%=pcompVersionPlatformDownload.getDownload()%>">点我下载</a>
                    </td>
                    <%
                        if ((pcompSoftware != null
                                && PermissionUtils.isCurrentUser(pcompSoftware.getCreatedBy())) || (PermissionUtils.isCurrentUser(pcompVersionExtend.getCreatedBy()))) {
                    %>
                    <td>

                        <button class="btn btn-success"
                                onclick="edit<%=PcompConstants.PCOMP_VERSION%>('<%=pcompVersionPlatformDownload.getId()%>', '<%=pcompVersionPlatformDownload.getPlatform()%>', 1)">
                            修改
                        </button>
                        <button class="btn btn-danger"
                                onclick="remove<%=PcompConstants.PCOMP_VERSION%>Extend('<%=pcompVersion.getId()%>', '<%=pcompVersionPlatformDownload.getId()%>', '<%=pcompVersionPlatformDownload.getPlatform()%>', 1) ">
                            删除
                        </button>

                    </td>
                    <%
                        }
                    %>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
            <h2>文档下载：
                <%
                    if ((pcompSoftware != null
                            && PermissionUtils.isCurrentUser(pcompSoftware.getCreatedBy())) || (PermissionUtils.isCurrentUser(pcompVersionExtend.getCreatedBy()))) {
                %>
                <button class="btn btn-primary" onclick="edit<%=PcompConstants.PCOMP_VERSION%>('', '', 4)">增加</button>
                <%
                    }
                %>
            </h2>
            <table>
                <tbody>
                <%
                    for (PcompVersionDoucumentDownload pcompVersionDoucumentDownload : pcompVersionExtend.getPcompVersionDoucumentDownloads()) {
                %>
                <tr>
                    <td>
                        <%=pcompVersionDoucumentDownload.getDescription()%>
                    </td>
                    <td>
                        <a href="<%=pcompVersionDoucumentDownload.getDownload()%>">点我下载</a>
                    </td>
                    <%
                        if ((pcompSoftware != null
                                && PermissionUtils.isCurrentUser(pcompSoftware.getCreatedBy())) || (PermissionUtils.isCurrentUser(pcompVersionExtend.getCreatedBy()))) {
                    %>
                    <td>

                        <button class="btn btn-success"
                                onclick="edit<%=PcompConstants.PCOMP_VERSION%>('<%=pcompVersionDoucumentDownload.getId()%>', '<%=pcompVersionDoucumentDownload.getDescription()%>', 2)">
                            修改
                        </button>
                        <button class="btn btn-danger"
                                onclick="remove<%=PcompConstants.PCOMP_VERSION%>Extend('<%=pcompVersion.getId()%>', '<%=pcompVersionDoucumentDownload.getId()%>', '<%=pcompVersionDoucumentDownload.getDescription()%>', 2) ">
                            删除
                        </button>

                    </td>
                    <%
                        }
                    %>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
    <div class="box-footer" id="<%=PcompConstants.HISTORY_DOWNLOAD%>_box_footer">
    </div>
</div>
<div id="downloadModificationDialog" title="修改(由于缓存，修改可能会有10秒的延迟)">
    <form id="downloadModificationForm" role="form"
          action=""
          enctype="multipart/form-data"
          method="post">
        <input id="id-input" type="text" value="" name="" hidden>
        <input id="id-input2" type="text" value="<%=pcompVersion.getId()%>" name="<%=PcompConstants.PCOMP_VERSION%>"
               hidden>

        <div class="form-group">
            <label for="text-input">新的下载描述</label>
            <input type="text" value="" class="form-control"
                   name=""
                   id="text-input"
                   required>
            <span class="help-block"></span>
        </div>

        <div class="form-group">
            <label for="file-input">新的下载文件</label>
            <input id="file-input" class="file"
                   type="file"
                   name="">
            <span class="help-block"></span>
        </div>
    </form>
</div>


<%
            break;
        }
    }
%>
