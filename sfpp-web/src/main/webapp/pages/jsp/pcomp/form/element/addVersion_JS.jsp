<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.FormUtils" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script>
    function validate<%=PathConstants.PCOMP_TITLE_NAME%>() {
        <%
            String formGroupId = FormUtils.mkFormGroupId(PathConstants.PCOMP_TITLE_NAME);
            String helpBlockId = FormUtils.mkHelpBlockId(PathConstants.PCOMP_TITLE_NAME);
            String selectId = FormUtils.mkSelectInputId(PathConstants.PCOMP_TITLE_NAME);
            String selectId2 = FormUtils.mkSelectInputId(PathConstants.PCOMP_KIND_NAME);
            String selectId2Div = FormUtils.mkSelectInputDivId(PathConstants.PCOMP_KIND_NAME);
        %>
        var a = $("#<%=selectId%>");
        var b = $("#<%=selectId2%>");
        var c = $("#<%=selectId2Div%>");
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open("GET", "<%=PathUtils.makePath(PathConstants.PCOMP_KIND_FETCH_PATH)%>?<%=PathConstants.PCOMP_TITLE_NAME%>=" + a[0].value, false);
        xmlHttp.send();
        var resp = JSON.parse(xmlHttp.responseText);
        var list = resp.data;
        var message = resp.message;
        if (!isNull(message)) {
            $("#<%=helpBlockId%>").html(message);
            $("#<%=formGroupId%>").attr("class", "form-group has-error");
            return false;
        }
        var str = "";
        for (var i = 0; i < list.length; i++) {
            str += "<option>";
            str += list[i].name;
            str += "</option>";
        }
        c.empty();
        b = $("<select></select>").attr("id", "<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_KIND_NAME)%>")
                .attr("name", "<%=PathConstants.PCOMP_KIND_NAME%>")
                .attr("class", "selectpicker show-tick form-control")
                .attr("data-live-search", "true")
                .attr("onchange", "validate<%=PathConstants.PCOMP_KIND_NAME%>()")
                .html(str)
        c.append(b);
        b.selectpicker({});
        return validate<%=PathConstants.PCOMP_KIND_NAME%>();
    }

    function validate<%=PathConstants.PCOMP_KIND_NAME%>() {
        <%
            formGroupId = FormUtils.mkFormGroupId(PathConstants.PCOMP_KIND_NAME);
            helpBlockId = FormUtils.mkHelpBlockId(PathConstants.PCOMP_KIND_NAME);
            selectId = FormUtils.mkSelectInputId(PathConstants.PCOMP_TITLE_NAME);
            selectId2 = FormUtils.mkSelectInputId(PathConstants.PCOMP_KIND_NAME);
            String selectId3 = FormUtils.mkSelectInputId(PathConstants.PCOMP_SOFTWARE_NAME);
            String selectId3Div = FormUtils.mkSelectInputDivId(PathConstants.PCOMP_SOFTWARE_NAME);
        %>
        var a = $("#<%=selectId%>");
        var b = $("#<%=selectId2%>");
        var c = $("#<%=selectId3Div%>");
        var d = $("#<%=selectId3%>");
        var e = $("#<%=formGroupId%>");
        var f = $("#<%=helpBlockId%>");
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open("GET", "<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_FETCH_PATH)%>?<%=PathConstants.PCOMP_TITLE_NAME%>=" + a[0].value
                + "&<%=PathConstants.PCOMP_KIND_NAME%>=" + b[0].value, false);
        xmlHttp.send();
        var resp = JSON.parse(xmlHttp.responseText);
        var list = resp.data;
        var message = resp.message;
        if (!isNull(message)) {
            $("#<%=helpBlockId%>").html(message);
            $("#<%=formGroupId%>").attr("class", "form-group has-error");
            return false;
        }
        var str = "";
        for (var i = 0; i < list.length; i++) {
            str += "<option>";
            str += list[i].name;
            str += "</option>";
        }
        c.empty();
        d = $("<select></select>").attr("id", "<%=FormUtils.mkSelectInputId(PathConstants.PCOMP_SOFTWARE_NAME)%>")
                .attr("name", "<%=PathConstants.PCOMP_SOFTWARE_NAME%>")
                .attr("class", "selectpicker show-tick form-control")
                .attr("data-live-search", "true")
                .html(str)
        c.append(d);
        d.selectpicker({});
        return true;
    }


    var <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number = 0;
    function addOne<%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>(button) {
        var formGroup = $(button).parent().parent().parent();
        <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number += 1;
        formGroup.after("<div class=\"form-group\" id=\"<%=FormUtils.mkFormGroupId(PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD)%>" + <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number + "\">\n" +
                "        <div class=\"col-lg-12\">\n" +
                "        <div class=\"col-lg-5\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM)%>" + <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number + "\">描述</label>\n" +
                "            <input type=\"text\" placeholder=\"请输入描述（不超过32个字，例如平台）\" class=\"form-control\"\n" +
                "                   name=\"<%=PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM%>\"\n" +
                "                   id=\"<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM)%>" + <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number + "\"\n" +
                "                   >\n" +
                "            <span class=\"help-block\" id=\"<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM)%>" + <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number + "\"></span>\n" +
                "        </div>\n" +
                "        </div>\n" +
                "        <div class=\"col-lg-5\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD)%>" + <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number + "\">下载文件</label>\n" +
                "            <input id=\"<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD)%>" + <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number + "\" class=\"file\" type=\"file\"\n" +
                "                   name=\"<%=PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD%>\">\n" +
                "            <span class=\"help-block\" id=\"<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD)%>" + <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number + "\"></span>\n" +
                "        </div>\n" +
                "        </div>\n" +
                "        <div class=\"col-lg-2\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <button class=\"btn btn-info\" type=\"button\" onclick=\"addOne<%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>(this)\">增加</button>\n" +
                "            <button class=\"btn btn-danger\" type=\"button\" onclick=\"remove<%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>(this)\">删除</button>\n" +
                "        </div>\n" +
                "        </div>\n" +
                "        </div>\n" +
                "    </div>");
        var input = $("#<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD)%>" + <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number);
        input.fileinput();
    }

    function remove<%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>(button) {
        if (<%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number > 0) {
            var formGroup = $(button).parent().parent();
            formGroup.remove();
            <%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>Number -= 1;
        }
    }

    var <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number = 0;
    function addOne<%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>(button) {
        var formGroup = $(button).parent().parent().parent();
        <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number += 1;
        formGroup.after("<div class=\"form-group\" id=\"<%=FormUtils.mkFormGroupId(PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT)%>" + <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number + "\">\n" +
                "        <div class=\"col-lg-12\">\n" +
                "        <div class=\"col-lg-5\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION)%>" + <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number + "\">描述</label>\n" +
                "            <input type=\"text\" placeholder=\"请输入描述（不超过32个字，例如平台）\" class=\"form-control\"\n" +
                "                   name=\"<%=PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION%>\"\n" +
                "                   id=\"<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION)%>" + <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number + "\"\n" +
                "                    >\n" +
                "            <span class=\"help-block\" id=\"<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION)%>" + <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number + "\"></span>\n" +
                "        </div>\n" +
                "        </div>\n" +
                "        <div class=\"col-lg-5\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD)%>" + <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number + "\">下载文件</label>\n" +
                "            <input id=\"<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD)%>" + <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number + "\" class=\"file\" type=\"file\"\n" +
                "                   name=\"<%=PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD%>\">\n" +
                "            <span class=\"help-block\" id=\"<%=FormUtils.mkHelpBlockId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD)%>" + <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number + "\"></span>\n" +
                "        </div>\n" +
                "        </div>\n" +
                "        <div class=\"col-lg-2\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <button class=\"btn btn-info\" type=\"button\" onclick=\"addOne<%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>(this)\">增加</button>\n" +
                "            <button class=\"btn btn-danger\" type=\"button\" onclick=\"remove<%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>(this)\">删除</button>\n" +
                "        </div>\n" +
                "        </div>\n" +
                "        </div>\n" +
                "    </div>");
        var input = $("#<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD)%>" + <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number);
        input.fileinput();
    }

    function remove<%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>(button) {
        if (<%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number > 0) {
            var formGroup = $(button).parent().parent();
            formGroup.remove();
            <%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>Number -= 1;
        }
    }

    $(document).ready(function () {
        validate<%=PathConstants.PCOMP_TITLE_NAME%>();

        <%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_INTRODUCTION)%>Editor = editormd("<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_INTRODUCTION)%>", {
            width: "100%",
            emoji: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
            height: 640,
            syncScrolling: "single",
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: "<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_CONTENT_IMAGE_UPLOAD_PATH)%>",
            path: "/sfpp-web/assets/lib/"
        });

        <%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_QUICKSTART)%>Editor = editormd("<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_QUICKSTART)%>", {
            width: "100%",
            emoji: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
            height: 640,
            syncScrolling: "single",
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: "<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_CONTENT_IMAGE_UPLOAD_PATH)%>",
            path: "/sfpp-web/assets/lib/"
        });
    });

    function validate<%=PcompConstants.PCOMP_VERSION%>() {
        var a = $("input[type='text'][name]");
        for (var i = 0; i < a.length; i++) {
            if(isNull(a[i].value)){
                $(a[i]).parent().attr("class", "form-group has-warning");
                $(a[i]).next().html("不能为空!");
                return false;
            }else{
                $(a[i]).parent().attr("class", "form-group has-success");
                $(a[i]).next().html("通过");
            }
        }

        a = $("select");
        for (var i = 0; i < a.length; i++) {
            if(isNull(a[i].value)){
                $(a[i]).parent().parent().parent().attr("class", "form-group has-warning");
                $(a[i]).parent().parent().next().html("不能为空!");
                return false;
            }else{
                $(a[i]).parent().parent().parent().attr("class", "form-group has-success");
                $(a[i]).parent().parent().next().html("通过");
            }
        }

        a = $("input[type='file'][name]");
        for (var i = 0; i < a.length; i++) {
            if(isNull(a[i].value)){
                $(a[i]).parent().parent().parent().parent().parent().attr("class", "form-group has-warning");
                $(a[i]).parent().parent().parent().parent().next().html("不能为空!");
                return false;
            }else{
                $(a[i]).parent().parent().parent().parent().parent().attr("class", "form-group has-success");
                $(a[i]).parent().parent().parent().parent().next().html("通过");
            }
        }
        return validate<%=PathConstants.PCOMP_TITLE_NAME%>();
    }
</script>
<jsp:include page="../../../common/form/util.jsp"></jsp:include>