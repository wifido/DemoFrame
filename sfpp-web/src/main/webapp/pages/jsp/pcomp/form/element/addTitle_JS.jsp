<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.FormUtils" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script>
    function validate<%=PathConstants.PCOMP_TITLE_NAME%>() {
        <%
            String formId = FormUtils.mkFormId(PathConstants.PCOMP_TITLE_NAME);
            String formGroupId = FormUtils.mkFormGroupId(PathConstants.PCOMP_TITLE_NAME);
            String textInputId = FormUtils.mkTextInputId(PathConstants.PCOMP_TITLE_NAME);
            String helpBlockId = FormUtils.mkHelpBlockId(PathConstants.PCOMP_TITLE_NAME);
        %>
        var a = $("#<%=textInputId%>");
        if (isNull(a[0].value)) {
            $("#<%=helpBlockId%>").html("不能为空!");
            $("#<%=formGroupId%>").attr("class", "form-group has-warning");
            return false;
        } else if (a[0].value.length > 32) {
            $("#<%=helpBlockId%>").html("长度不能大于32!");
            $("#<%=formGroupId%>").attr("class", "form-group has-warning");
            return false;
        }
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open("GET", "<%=PathUtils.makePath(PathConstants.PCOMP_TITLE_VALIDATE_PATH)%>?<%=PathConstants.PCOMP_TITLE_NAME%>=" + a[0].value.trim(), false);
        xmlHttp.send();
        var resp = JSON.parse(xmlHttp.responseText);
        var bool = resp.data;
        var message = resp.message;
        if (!isNull(message)) {
            $("#<%=helpBlockId%>").html(message);
            $("#<%=formGroupId%>").attr("class", "form-group has-error");
            return false;
        }
        else if (bool) {
            $("#<%=helpBlockId%>").html("已存在!");
            $("#<%=formGroupId%>").attr("class", "form-group has-warning");
            return false;
        } else {
            $("#<%=helpBlockId%>").html("可以使用!");
            $("#<%=formGroupId%>").attr("class", "form-group has-success");
            return true;
        }
    }

    function validate<%=PcompConstants.PCOMP_TITLE%>(input) {
        if (validate<%=PathConstants.PCOMP_TITLE_NAME%>()) {
            return true;
        } else {
            return false;
        }
    }

</script>
<jsp:include page="../../../common/form/util.jsp"></jsp:include>