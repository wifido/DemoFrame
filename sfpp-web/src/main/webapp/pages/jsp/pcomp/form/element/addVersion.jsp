<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompTitle" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.FormUtils" %>
<%@ page import="java.util.List" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<form enctype="multipart/form-data"
      onsubmit="return validate<%=PcompConstants.PCOMP_VERSION%>()">
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
        List<PcompTitle> pcompTitles = pcompCacheObject.getPcompTitles();
    %>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_TITLE_NAME)%>">
        <label for="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_TITLE_NAME)%>">所属主题</label>
        <div id="<%=FormUtils.mkSelectInputDivId(PathConstants.PCOMP_TITLE_NAME)%>">
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
        </div>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_TITLE_NAME)%>"></span>
    </div>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_KIND_NAME)%>">
        <label for="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_KIND_NAME)%>">所属分类</label>

        <div id="<%=FormUtils.mkSelectInputDivId(PathConstants.PCOMP_KIND_NAME)%>">
            <select id="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_KIND_NAME)%>"
                    name="<%=PathConstants.PCOMP_KIND_NAME%>" class="selectpicker show-tick form-control"
                    data-live-search="true" onchange="validate<%=PathConstants.PCOMP_KIND_NAME%>()">
            </select>
        </div>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_KIND_NAME)%>"></span>
    </div>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_SOFTWARE_NAME)%>">
        <label for="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_SOFTWARE_NAME)%>">所属软件</label>

        <div id="<%=FormUtils.mkSelectInputDivId(PathConstants.PCOMP_SOFTWARE_NAME)%>">
            <select id="<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_SOFTWARE_NAME)%>"
                    name="<%=PathConstants.PCOMP_SOFTWARE_NAME%>" class="selectpicker show-tick form-control"
                    data-live-search="true">
            </select>
        </div>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_SOFTWARE_NAME)%>"></span>
    </div>


    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_VERSION_NUMBER)%>">
        <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_NUMBER)%>">版本号</label>
        <input type="text" placeholder="请输入版本号（不超过32个字）" class="form-control"
               name="<%=PathConstants.PCOMP_VERSION_NUMBER%>"
               id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_NUMBER)%>"
        >
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_NUMBER)%>"></span>
    </div>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_VERSION_INTRODUCTION)%>">
        <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_INTRODUCTION)%>">版本介绍</label>

        <div id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_INTRODUCTION)%>">
            <textarea style="display:none;" placeholder="请输入版本介绍" name="<%=PathConstants.PCOMP_VERSION_INTRODUCTION%>"
                      id="<%=PathConstants.PCOMP_VERSION_INTRODUCTION%>"
            ></textarea>
        </div>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_INTRODUCTION)%>"></span>
    </div>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PathConstants.PCOMP_VERSION_QUICKSTART)%>">
        <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_QUICKSTART)%>">快速上手</label>

        <div id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_QUICKSTART)%>">
            <textarea style="display:none;" placeholder="请输入快速上手" name="<%=PathConstants.PCOMP_VERSION_QUICKSTART%>"
                      id="<%=PathConstants.PCOMP_VERSION_QUICKSTART%>"
            ></textarea>
        </div>
        <span class="help-block" id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_QUICKSTART)%>"></span>
    </div>
    <div class="col-lg-12">
        <label for="<%=FormUtils.mkFormGroupId(PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD)%>"><b>软件下载</b></label>
    </div>

    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD)%>">
        <div class="col-lg-12">
        <div class="col-lg-5">
            <div class="form-group">
                <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM)%>">描述</label>
                <input type="text" placeholder="请输入描述（不超过32个字，例如平台）" class="form-control"
                       name="<%=PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM%>"
                       id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM)%>"
                >
            <span class="help-block"
                  id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM)%>"></span>
            </div>
        </div>
        <div class="col-lg-5">
            <div class="form-group">
                <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD)%>">下载文件</label>
                <input id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD)%>"
                       class="file" type="file"
                       name="<%=PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD%>">
            <span class="help-block"
                  id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD)%>"></span>
            </div>
        </div>
        <div class="col-lg-2">
            <div class="form-group">
                <button class="btn btn-info" type="button"
                        onclick="addOne<%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>(this)">增加
                </button>
                <button class="btn btn-danger" type="button"
                        onclick="remove<%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>(this)">删除
                </button>
            </div>
        </div>
        </div>
    </div>

    <div class="col-lg-12">
        <label for="<%=FormUtils.mkFormGroupId(PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT)%>"><b>文档下载</b></label>
    </div>
    <div class="form-group" id="<%=FormUtils.mkFormGroupId(PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT)%>">
        <div class="col-lg-12">
        <div class="col-lg-5">
            <div class="form-group">
                <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION)%>">描述</label>
                <input type="text" placeholder="请输入描述（不超过32个字，例如平台）" class="form-control"
                       name="<%=PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION%>"
                       id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION)%>"
                >
                <span class="help-block"
                      id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION)%>"></span>
            </div>
        </div>
        <div class="col-lg-5">
            <div class="form-group">
                <label for="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD)%>">下载文件</label>
                <input id="<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD)%>"
                       class="file" type="file"
                       name="<%=PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD%>">
                <span class="help-block"
                      id="<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD)%>"></span>
            </div>
        </div>
        <div class="col-lg-2">
            <div class="form-group">
                <button class="btn btn-info" type="button"
                        onclick="addOne<%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>(this)">增加
                </button>
                <button class="btn btn-danger" type="button"
                        onclick="remove<%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>(this)">删除
                </button>
            </div>
        </div>
        </div>
    </div>

    <button class="btn btn-primary" type="submit">提交</button>
</form>