<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompTitle" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.FormUtils" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@ page import="java.util.List" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<form id="NewClassForm" role="form" action="<%=PathUtils.makePath(PathConstants.PCOMP_KIND_CREATE_PATH)%>" method="post" class="login-form" enctype="multipart/form-data"
      onsubmit="return validate<%=PcompConstants.PCOMP_KIND%>(this)">
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
        List<PcompTitle> pcompTitles = pcompCacheObject.getPcompTitles();
    %>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_TITLE_NAME)%>">
        <!--没有下面这一步，下拉选择框可能会右移，视具体情况而定-->
        <label for="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_TITLE_NAME)%>">所属主题</label>
        <select id="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_TITLE_NAME)%>" name="<%=PathConstants.PCOMP_TITLE_NAME%>" class="selectpicker show-tick form-control" data-live-search="true">
            <%
                for (PcompTitle pcompTitle : pcompTitles) {
            %>
                    <option id="<%=pcompTitle.getId()%>"><%=pcompTitle.getName()%></option>
            <%
                }
            %>
        </select>
    </div>


    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_KIND_NAME)%>">
        <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_KIND_NAME)%>">分类名称</label>
        <input type="text" placeholder="请输入分类名称（不超过32个字）" class="form-control" name="<%=PathConstants.PCOMP_KIND_NAME%>"
               id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_KIND_NAME)%>" onblur="validate<%=PathConstants.PCOMP_KIND_NAME%>(this)" required>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_KIND_NAME)%>"></span>
    </div>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_KIND_BANNER_IMAGE)%>">
        <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_KIND_BANNER_IMAGE)%>">背景图</label>
        <input id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_KIND_BANNER_IMAGE)%>" class="file" type="file" name="<%=PathConstants.PCOMP_KIND_BANNER_IMAGE%>">
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_KIND_BANNER_IMAGE)%>"></span>
    </div>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_KIND_TOP_PHOTO)%>">
        <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_KIND_TOP_PHOTO)%>">小头像</label>
        <input id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_KIND_TOP_PHOTO)%>" class="file" type="file"  name="<%=PathConstants.PCOMP_KIND_TOP_PHOTO%>">
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_KIND_TOP_PHOTO)%>"></span>
    </div>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_KIND_INTRODUCTION)%>">
        <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_KIND_INTRODUCTION)%>">简介</label>
        <input type="text" placeholder="请输入简介（不超过32个字）" class="form-control" name="<%=PathConstants.PCOMP_KIND_INTRODUCTION%>"
               id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_KIND_INTRODUCTION)%>" onblur="validate<%=PathConstants.PCOMP_KIND_INTRODUCTION%>(this)" required>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_KIND_INTRODUCTION)%>"></span>
    </div>

    <button class="btn btn-primary" onclick="validate<%=PcompConstants.PCOMP_KIND%>(this)">提交</button>
</form>