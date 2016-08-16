<%@ page import="com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompVersion" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend" %>
<%@ page import="com.sf.sfpp.web.common.utils.EditorMDUtils" %>
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
    <div class="box-header with-border" id="<%=PcompConstants.HISTORY_INTRODUCTION%>_box_header">
        <h3 class="box-title"><%=PcompConstants.HISTORY_INTRODUCTION_CH%>
        </h3>

        <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-toggle="tooltip" title="" data-widget="chat-pane-toggle"
                    data-original-title="编辑/关闭编辑" onclick="edit<%=PcompConstants.HISTORY_INTRODUCTION%>()">
                <i class="fa fa-pencil"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
        </div>

    </div>
    <div class="box-body" id="<%=PcompConstants.HISTORY_INTRODUCTION%>_box_body">
        <div id="<%=PcompConstants.HISTORY_INTRODUCTION%>">
            <textarea style="display:none;"><%=pcompVersionExtend.getIntroduction()%></textarea>
        </div>
    </div>
    <div class="box-footer" id="<%=PcompConstants.HISTORY_INTRODUCTION%>_box_footer">
    </div>
</div>

<div class="box direct-chat direct-chat-primary">
    <div class="box-header with-border" id="<%=PcompConstants.HISTORY_QUICKSTART%>_box_header">
        <h3 class="box-title"><%=PcompConstants.HISTORY_QUICKSTART_CH%>
        </h3>

        <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-toggle="tooltip" title="" data-widget="chat-pane-toggle"
                    data-original-title="编辑/关闭编辑" onclick="edit<%=PcompConstants.HISTORY_QUICKSTART%>()">
                <i class="fa fa-pencil"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
        </div>

    </div>
    <div class="box-body" id="<%=PcompConstants.HISTORY_QUICKSTART%>_box_body">
        <div id="<%=PcompConstants.HISTORY_QUICKSTART%>">
            <textarea style="display:none;"><%=pcompVersionExtend.getQuickStart()%></textarea>
        </div>
    </div>
    <div class="box-footer" id="<%=PcompConstants.HISTORY_QUICKSTART%>_box_footer">
    </div>
</div>

<div class="box direct-chat direct-chat-primary">
    <div class="box-header with-border" id="<%=PcompConstants.HISTORY_DOWNLOAD%>_box_header">
        <h3 class="box-title"><%=PcompConstants.HISTORY_DOWNLOAD_CH%>
        </h3>

        <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-toggle="tooltip" title="" data-widget="chat-pane-toggle"
                    data-original-title="编辑/关闭编辑" onclick="edit<%=PcompConstants.HISTORY_QUICKSTART%>()">
                <i class="fa fa-pencil"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
        </div>

    </div>
    <div class="box-body" id="<%=PcompConstants.HISTORY_DOWNLOAD%>_box_body">
        <div id="<%=PcompConstants.HISTORY_DOWNLOAD%>">
            <textarea style="display:none;"><%=EditorMDUtils.formatDownload(pcompVersionExtend)%></textarea>
        </div>
    </div>
    <div class="box-footer" id="<%=PcompConstants.HISTORY_DOWNLOAD%>_box_footer">
    </div>
</div>
<%
            break;
        }
    }
%>





