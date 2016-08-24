<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend" %>
<%@ page import="com.sf.sfpp.web.common.utils.PermissionUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
    PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
    PcompSoftwareExtend pcompSoftware = (PcompSoftwareExtend) pcompCacheObject.getPcompSoftware();
%>


<div class="box direct-chat direct-chat-primary">
    <div class="box-header with-border" id="<%=PcompConstants.INTRODUCTION%>_box_header">
        <h3 class="box-title"><%=pcompSoftware.getName()%>
        </h3>

        <div class="box-tools pull-right">
           <% 
           if (pcompSoftware != null 
               && PermissionUtils.isCurrentUser(pcompSoftware.getCreatedBy())) { 
           %>
            <button type="button" class="btn btn-box-tool" data-toggle="tooltip" title="" data-widget="chat-pane-toggle"
                    data-original-title="编辑/关闭编辑" onclick="edit<%=PcompConstants.INTRODUCTION%>()">
                <i class="fa fa-pencil"></i></button>
            <%
            } 
            %>
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
        </div>

    </div>
    <div class="box-body" id="<%=PcompConstants.INTRODUCTION%>_box_body">
        <div id="<%=PcompConstants.INTRODUCTION%>">
            <textarea style="display:none;"><%=pcompSoftware.getIntroduction()%></textarea>
        </div>
    </div>
    <div class="box-footer" id="<%=PcompConstants.INTRODUCTION%>_box_footer">
    </div>
</div>
