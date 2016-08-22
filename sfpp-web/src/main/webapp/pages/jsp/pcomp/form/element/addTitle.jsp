<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.FormUtils" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<div class="form-bottom tab-pane fade in active" id="xw1">

    <form id="<%=FormUtils.mkFormId(PathConstants.PCOMP_TITLE_NAME)%>" action="<%=PathUtils.makePath(PathConstants.PCOMP_TITLE_CREATE_PATH)%>" method="get" onsubmit="return validate<%=PcompConstants.PCOMP_TITLE%>(this)">
        <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_TITLE_NAME)%>">
            <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_TITLE_NAME)%>">主题名称</label>
            <input type="text" name="<%=PathConstants.PCOMP_TITLE_NAME%>" placeholder="请输入主题名称（不超过32个字）" class="form-control" id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_TITLE_NAME)%>" onblur="validate<%=PathConstants.PCOMP_TITLE_NAME%>(this)" >
            <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_TITLE_NAME)%>"></span>
        </div>
        <div class="form-group" >
        <button class="btn btn-primary" type="submit" >提交</button>
        </div>
    </form>
</div>