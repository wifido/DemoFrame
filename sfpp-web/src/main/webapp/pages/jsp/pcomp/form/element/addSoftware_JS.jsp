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
                .html(str)
        c.append(b);
        b.selectpicker({});
        return true;
    }

    function validate<%=PathConstants.PCOMP_KIND_NAME%>() {
        var b = $("#<%=selectId2%>");
        if(isNull(b[0].value)){
            return false;
        } else {
            return true;
        }
    }

    function validate<%=PathConstants.PCOMP_SOFTWARE_NAME%>() {
        <%
            formGroupId = FormUtils.mkFormGroupId(PathConstants.PCOMP_SOFTWARE_NAME);
            String textInputId = FormUtils.mkTextInputId(PathConstants.PCOMP_SOFTWARE_NAME);
            helpBlockId = FormUtils.mkHelpBlockId(PathConstants.PCOMP_SOFTWARE_NAME);
        %>
        var a = $("#<%=formGroupId%>");
        var b = $("#<%=textInputId%>");
        var c = $("#<%=helpBlockId%>");
        if(isNull(b[0].value)){
            c.html("不能为空!");
            a.attr("class", "form-group has-warning");
            return false;
        }
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open("GET", "<%=PathUtils.makePath(PathConstants.PCOMP_KIND_FETCH_PATH)%>?<%=PathConstants.PCOMP_TITLE_NAME%>=" + a[0].value, false);
        xmlHttp.send();
        var resp = JSON.parse(xmlHttp.responseText);
        var list = resp.data;
        var message = resp.message;
    }

    $(document).ready(function () {
        validate<%=PathConstants.PCOMP_TITLE_NAME%>();

        <%=FormUtils.mkTextInputId(PathConstants.PCOMP_SOFTWARE_INTRODUCTION)%>Editor = editormd("<%=FormUtils.mkTextInputId(PathConstants.PCOMP_SOFTWARE_INTRODUCTION)%>", {
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


</script>
<jsp:include page="../../../common/form/util.jsp"></jsp:include>