<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../../common/commonHead.jsp"></jsp:include>
    <jsp:include page="../common/CSS.jsp"></jsp:include>
</head>

<body class="<%=Constants.THEME%>">
<%
    String page_nav = (String) request.getAttribute(PcompConstants.SOFTWARE_PAGE_NAVIGATION);
    String pcomp_version = (String) request.getAttribute(PcompConstants.PCOMP_VERSION);
%>
<div class="wrapper">
    <jsp:include page="../../common/main-header.jsp"></jsp:include>

    <jsp:include page="../common/sidebar.jsp"></jsp:include>
    <div class="content-wrapper">
        <jsp:include page="../common/content-header.jsp"></jsp:include>
        <section class="content">
            <%
            if (PcompConstants.INTRODUCTION.equals(page_nav) || StrUtils.isNull(page_nav)) {
            %>
                <jsp:include page="../common/content/software/introduction.jsp"></jsp:include>
            <%
            } else if (PcompConstants.HISTORY.equals(page_nav)) {
            %>
                <jsp:include page="../common/content/software/history.jsp"></jsp:include>
            <%
            } else if (PcompConstants.DOWNLOAD.equals(page_nav)) {
            %>
                <jsp:include page="../common/content/software/dowload.jsp"></jsp:include>
            <%
            } else if (PcompConstants.QUICKSTART.equals(page_nav)) {
            %>
                <jsp:include page="../common/content/software/quickstart.jsp"></jsp:include>
            <%
            } else  {
            %>
                <jsp:include page="../common/content/software/introduction.jsp"></jsp:include>
            <%
            }
            %>
        </section>
    </div>
</div>


<jsp:include page="../../common/footer.jsp"></jsp:include>
<jsp:include page="../../common/commonJS.jsp"></jsp:include>
<jsp:include page="../common/JS.jsp"></jsp:include>


<%
    if (PcompConstants.INTRODUCTION.equals(page_nav) || StrUtils.isNull(page_nav)) {
%>
<script type="text/javascript">
    $(function () {
        var testEditor = editormd.markdownToHTML("<%=PcompConstants.HISTORY%>", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
        });
    });
</script>
<%
} else if (PcompConstants.HISTORY.equals(page_nav)) {
%>
<script type="text/javascript">
    $(function () {
        var testEditor = editormd.markdownToHTML("<%=PcompConstants.HISTORY_INTRODUCTION%>", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
        });
        var testEditor2 = editormd.markdownToHTML("<%=PcompConstants.HISTORY_QUICKSTART%>", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
        });
        var testEditor3 = editormd.markdownToHTML("<%=PcompConstants.HISTORY_DOWNLOAD%>", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
        });
    });
</script>
<%
    } else if (PcompConstants.DOWNLOAD.equals(page_nav)) {
%>
<script type="text/javascript">
    $(function () {
        var testEditor3 = editormd.markdownToHTML("<%=PcompConstants.HISTORY_DOWNLOAD%>", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
        });
    });
</script>
<%
} else if (PcompConstants.QUICKSTART.equals(page_nav)) {
%>
<script type="text/javascript">
    $(function () {
        var testEditor3 = editormd.markdownToHTML("<%=PcompConstants.QUICKSTART%>", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
        });
    });
</script>
<%
} else  {
%>
<script type="text/javascript">
    $(function () {
        var testEditor = editormd.markdownToHTML("<%=PcompConstants.HISTORY%>", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
        });
    });
</script>
<%
}
%>


</body>

</html>