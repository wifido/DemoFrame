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
<form id="NewClassForm" role="form" action="<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_CREATE_PATH)%>" enctype="multipart/form-data"
      method="post"
      onsubmit="return validate<%=PcompConstants.PCOMP_SOFTWARE%>()">
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
        List<PcompTitle> pcompTitles = pcompCacheObject.getPcompTitles();
    %>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_TITLE_NAME)%>">
        <label for="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_TITLE_NAME)%>">所属主题</label>
        <select id="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_TITLE_NAME)%>"
                name="<%=PathConstants.PCOMP_TITLE_NAME%>" class="selectpicker show-tick form-control"
                data-live-search="true"
                onchange="validate<%=PathConstants.PCOMP_TITLE_NAME%>()">
            <%
                for (PcompTitle pcompTitle : pcompTitles) {
            %>
            <option id="<%=pcompTitle.getId()%>"><%=pcompTitle.getName()%>
            </option>
            <%
                }
            %>
        </select>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_TITLE_NAME)%>"></span>
    </div>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_KIND_NAME)%>">
        <label for="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_KIND_NAME)%>">所属分类</label>

        <div id="<%=FormUtils.mkSelectInputDivId(PathConstants.PCOMP_KIND_NAME)%>">
            <select id="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_KIND_NAME)%>"
                    name="<%=PathConstants.PCOMP_KIND_NAME%>" class="selectpicker show-tick form-control"
                    data-live-search="true">
            </select>
        </div>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_KIND_NAME)%>"></span>
    </div>


    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_SOFTWARE_NAME)%>">
        <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_SOFTWARE_NAME)%>">软件名称</label>
        <input type="text" placeholder="请输入软件名称（不超过32个字）" class="form-control"
               name="<%=PathConstants.PCOMP_SOFTWARE_NAME%>"
               id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_SOFTWARE_NAME)%>"
               onblur="validate<%=PathConstants.PCOMP_SOFTWARE_NAME%>(this)" required>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_SOFTWARE_NAME)%>"></span>
    </div>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_SOFTWARE_AVATAR)%>">
        <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_SOFTWARE_AVATAR)%>">小头像</label>
        <input id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_SOFTWARE_AVATAR)%>" class="file" type="file"
               name="<%=PathConstants.PCOMP_SOFTWARE_AVATAR%>">
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_SOFTWARE_AVATAR)%>"></span>
    </div>


    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_SOFTWARE_INTRODUCTION)%>">
        <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_SOFTWARE_INTRODUCTION)%>">详细介绍</label>

        <div id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_SOFTWARE_INTRODUCTION)%>">
            <textarea style="display:none;" placeholder="请输入详细介绍" name="<%=PathConstants.PCOMP_SOFTWARE_INTRODUCTION%>"
                      id="<%=PathConstants.PCOMP_SOFTWARE_INTRODUCTION%>"
                      onblur="validate<%=PathConstants.PCOMP_SOFTWARE_INTRODUCTION%>(this)" required></textarea>
        </div>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_SOFTWARE_INTRODUCTION)%>"></span>
    </div>

    <button class="btn btn-primary" type="submit">提交</button>
</form>