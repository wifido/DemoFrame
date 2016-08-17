<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../../common/commonHead.jsp"></jsp:include>
    <jsp:include page="../common/CSS.jsp"></jsp:include>
</head>

<body class="<%=Constants.THEME%>">
<%
    WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
    PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
    PcompSoftwareExtend pcompSoftware = (PcompSoftwareExtend) pcompCacheObject.getPcompSoftware();
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

    var <%=PcompConstants.INTRODUCTION%>Editor;
    var button<%=PcompConstants.INTRODUCTION%> = document.createElement("button");
    button<%=PcompConstants.INTRODUCTION%>.setAttribute('type','button');
    button<%=PcompConstants.INTRODUCTION%>.setAttribute('class','btn btn-default');
    button<%=PcompConstants.INTRODUCTION%>.setAttribute('onclick','post<%=PcompConstants.INTRODUCTION%>()');
    button<%=PcompConstants.INTRODUCTION%>.innerHTML='提交';
    var editing<%=PcompConstants.INTRODUCTION%> = false;
    function edit<%=PcompConstants.INTRODUCTION%>(){
        var <%=PcompConstants.INTRODUCTION%> = document.getElementById('<%=PcompConstants.INTRODUCTION%>');
        var <%=PcompConstants.INTRODUCTION%>_box_body = document.getElementById('<%=PcompConstants.INTRODUCTION%>_box_body');
        var <%=PcompConstants.INTRODUCTION%>_box_header = document.getElementById('<%=PcompConstants.INTRODUCTION%>_box_header');
        var <%=PcompConstants.INTRODUCTION%>_box_footer = document.getElementById('<%=PcompConstants.INTRODUCTION%>_box_footer');
        <%=PcompConstants.INTRODUCTION%>_box_body.removeChild(<%=PcompConstants.INTRODUCTION%>);
        var div = document.createElement("div");
        div.setAttribute('id','<%=PcompConstants.INTRODUCTION%>');
        var textarea = document.createElement("textarea");
        textarea.setAttribute('style','display:none;');
        textarea.innerHTML = <%=PcompConstants.INTRODUCTION%>Editor.getMarkdown();
        <%=PcompConstants.INTRODUCTION%>_box_body.appendChild(div);
        div.appendChild(textarea);
        if(!editing<%=PcompConstants.INTRODUCTION%>){
            <%=PcompConstants.INTRODUCTION%>Editor = editormd("<%=PcompConstants.INTRODUCTION%>", {
                width   : "100%",
                emoji: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
                height  : 640,
                syncScrolling : "single",
                imageUpload : true,
                imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                imageUploadURL : "<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_CONTENT_IMAGE_UPLOAD_PATH)%>",
                path    : "/sfpp-web/assets/lib/"
            });
            <%=PcompConstants.INTRODUCTION%>_box_footer.appendChild(button<%=PcompConstants.INTRODUCTION%>);
            editing<%=PcompConstants.INTRODUCTION%> = true;
        } else{
            <%=PcompConstants.INTRODUCTION%>Editor = editormd.markdownToHTML("<%=PcompConstants.INTRODUCTION%>", {
                htmlDecode: "style,script,iframe",  // you can filter tags decode
                emoji: true,
                taskList: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
            });
            <%=PcompConstants.INTRODUCTION%>_box_footer.removeChild(button<%=PcompConstants.INTRODUCTION%>);
            editing<%=PcompConstants.INTRODUCTION%> = false;
        }
    };


    $(function () {
        <%=PcompConstants.INTRODUCTION%>Editor = editormd.markdownToHTML("<%=PcompConstants.INTRODUCTION%>", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
        });

    });

    function post<%=PcompConstants.INTRODUCTION%>() {
        var temp = document.createElement("form");
        temp.action ='<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_MODIFICATION_PATH)%>';
        temp.method = "post";
        temp.style.display = "none";
        var opt = document.createElement("textarea");
        opt.name ='<%=PathConstants.PCOMP_SOFTWARE_INTRODUCTION%>';
        opt.value =<%=PcompConstants.INTRODUCTION%>Editor.getMarkdown();
        temp.appendChild(opt);
        opt = document.createElement("textarea");
        opt.name ='<%=PathConstants.PCOMP_SOFTWARE_ID%>';
        opt.value ='<%=pcompSoftware.getId()%>';
        temp.appendChild(opt);
        document.body.appendChild(temp);
        temp.submit();
        return temp;
    }
</script>
<%
} else if (PcompConstants.HISTORY.equals(page_nav)) {
%>
<script type="text/javascript">
    var <%=PcompConstants.HISTORY_INTRODUCTION%>Editor;
    var button<%=PcompConstants.HISTORY_INTRODUCTION%> = document.createElement("button");
    button<%=PcompConstants.HISTORY_INTRODUCTION%>.setAttribute('type','button');
    button<%=PcompConstants.HISTORY_INTRODUCTION%>.setAttribute('class','btn btn-default');
    button<%=PcompConstants.HISTORY_INTRODUCTION%>.setAttribute('onclick','post<%=PcompConstants.HISTORY_INTRODUCTION%>()');
    button<%=PcompConstants.HISTORY_INTRODUCTION%>.innerHTML='提交';
    var editing<%=PcompConstants.HISTORY_INTRODUCTION%> = false;
    function edit<%=PcompConstants.HISTORY_INTRODUCTION%>(){
        var <%=PcompConstants.HISTORY_INTRODUCTION%> = document.getElementById('<%=PcompConstants.HISTORY_INTRODUCTION%>');
        var <%=PcompConstants.HISTORY_INTRODUCTION%>_box_body = document.getElementById('<%=PcompConstants.HISTORY_INTRODUCTION%>_box_body');
        var <%=PcompConstants.HISTORY_INTRODUCTION%>_box_header = document.getElementById('<%=PcompConstants.HISTORY_INTRODUCTION%>_box_header');
        var <%=PcompConstants.HISTORY_INTRODUCTION%>_box_footer = document.getElementById('<%=PcompConstants.HISTORY_INTRODUCTION%>_box_footer');
        <%=PcompConstants.HISTORY_INTRODUCTION%>_box_body.removeChild(<%=PcompConstants.HISTORY_INTRODUCTION%>);
        var div = document.createElement("div");
        div.setAttribute('id','<%=PcompConstants.HISTORY_INTRODUCTION%>');
        var textarea = document.createElement("textarea");
        textarea.setAttribute('style','display:none;');
        textarea.innerHTML = <%=PcompConstants.HISTORY_INTRODUCTION%>Editor.getMarkdown();
        <%=PcompConstants.HISTORY_INTRODUCTION%>_box_body.appendChild(div);
        div.appendChild(textarea);
        if(!editing<%=PcompConstants.HISTORY_INTRODUCTION%>){
            <%=PcompConstants.HISTORY_INTRODUCTION%>Editor = editormd("<%=PcompConstants.HISTORY_INTRODUCTION%>", {
                width   : "100%",
                emoji: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
                height  : 640,
                syncScrolling : "single",
                imageUpload : true,
                imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                imageUploadURL : "<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_CONTENT_IMAGE_UPLOAD_PATH)%>",
                path    : "/sfpp-web/assets/lib/"
            });
            <%=PcompConstants.HISTORY_INTRODUCTION%>_box_footer.appendChild(button<%=PcompConstants.HISTORY_INTRODUCTION%>);
            editing<%=PcompConstants.HISTORY_INTRODUCTION%> = true;
        } else{
            <%=PcompConstants.HISTORY_INTRODUCTION%>Editor = editormd.markdownToHTML("<%=PcompConstants.HISTORY_INTRODUCTION%>", {
                htmlDecode: "style,script,iframe",  // you can filter tags decode
                emoji: true,
                taskList: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
            });
            <%=PcompConstants.HISTORY_INTRODUCTION%>_box_footer.removeChild(button<%=PcompConstants.HISTORY_INTRODUCTION%>);
            editing<%=PcompConstants.HISTORY_INTRODUCTION%> = false;
        }
    };


    $(function () {
        <%=PcompConstants.HISTORY_INTRODUCTION%>Editor = editormd.markdownToHTML("<%=PcompConstants.HISTORY_INTRODUCTION%>", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
        });

    });

    function post<%=PcompConstants.HISTORY_INTRODUCTION%>() {
        var temp = document.createElement("form");
        temp.action ='<%=PathUtils.makePath(PathConstants.PCOMP_VERSION_MODIFICATION_PATH)%>';
        temp.method = "post";
        temp.style.display = "none";
        var opt = document.createElement("textarea");
        opt.name ='<%=PathConstants.PCOMP_VERSION_INTRODUCTION%>';
        opt.value =<%=PcompConstants.HISTORY_INTRODUCTION%>Editor.getMarkdown();
        temp.appendChild(opt);
        opt = document.createElement("textarea");
        opt.name ='<%=PathConstants.PCOMP_SOFTWARE_ID%>';
        opt.value ='<%=pcompSoftware.getId()%>';
        temp.appendChild(opt);
        opt = document.createElement("textarea");
        opt.name ='<%=PathConstants.PCOMP_VERSION_ID%>';
        opt.value ='<%=pcomp_version%>';
        temp.appendChild(opt);
        opt = document.createElement("textarea");
        opt.name ='<%=PcompConstants.SOFTWARE_PAGE_NAVIGATION%>';
        opt.value ='<%=page_nav%>';
        temp.appendChild(opt);
        document.body.appendChild(temp);
        temp.submit();
        return temp;
    }


    var <%=PcompConstants.HISTORY_QUICKSTART%>Editor;
    var button<%=PcompConstants.HISTORY_QUICKSTART%> = document.createElement("button");
    button<%=PcompConstants.HISTORY_QUICKSTART%>.setAttribute('type','button');
    button<%=PcompConstants.HISTORY_QUICKSTART%>.setAttribute('class','btn btn-default');
    button<%=PcompConstants.HISTORY_QUICKSTART%>.setAttribute('onclick','post<%=PcompConstants.HISTORY_QUICKSTART%>()');
    button<%=PcompConstants.HISTORY_QUICKSTART%>.innerHTML='提交';
    var editing<%=PcompConstants.HISTORY_QUICKSTART%> = false;
    function edit<%=PcompConstants.HISTORY_QUICKSTART%>(){
        var <%=PcompConstants.HISTORY_QUICKSTART%> = document.getElementById('<%=PcompConstants.HISTORY_QUICKSTART%>');
        var <%=PcompConstants.HISTORY_QUICKSTART%>_box_body = document.getElementById('<%=PcompConstants.HISTORY_QUICKSTART%>_box_body');
        var <%=PcompConstants.HISTORY_QUICKSTART%>_box_header = document.getElementById('<%=PcompConstants.HISTORY_QUICKSTART%>_box_header');
        var <%=PcompConstants.HISTORY_QUICKSTART%>_box_footer = document.getElementById('<%=PcompConstants.HISTORY_QUICKSTART%>_box_footer');
        <%=PcompConstants.HISTORY_QUICKSTART%>_box_body.removeChild(<%=PcompConstants.HISTORY_QUICKSTART%>);
        var div = document.createElement("div");
        div.setAttribute('id','<%=PcompConstants.HISTORY_QUICKSTART%>');
        var textarea = document.createElement("textarea");
        textarea.setAttribute('style','display:none;');
        textarea.innerHTML = <%=PcompConstants.HISTORY_QUICKSTART%>Editor.getMarkdown();
        <%=PcompConstants.HISTORY_QUICKSTART%>_box_body.appendChild(div);
        div.appendChild(textarea);
        if(!editing<%=PcompConstants.HISTORY_QUICKSTART%>){
            <%=PcompConstants.HISTORY_QUICKSTART%>Editor = editormd("<%=PcompConstants.HISTORY_QUICKSTART%>", {
                width   : "100%",
                emoji: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
                height  : 640,
                syncScrolling : "single",
                imageUpload : true,
                imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                imageUploadURL : "<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_CONTENT_IMAGE_UPLOAD_PATH)%>",
                path    : "/sfpp-web/assets/lib/"
            });
            <%=PcompConstants.HISTORY_QUICKSTART%>_box_footer.appendChild(button<%=PcompConstants.HISTORY_QUICKSTART%>);
            editing<%=PcompConstants.HISTORY_QUICKSTART%> = true;
        } else{
            <%=PcompConstants.HISTORY_QUICKSTART%>Editor = editormd.markdownToHTML("<%=PcompConstants.HISTORY_QUICKSTART%>", {
                htmlDecode: "style,script,iframe",  // you can filter tags decode
                emoji: true,
                taskList: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
            });
            <%=PcompConstants.HISTORY_QUICKSTART%>_box_footer.removeChild(button<%=PcompConstants.HISTORY_QUICKSTART%>);
            editing<%=PcompConstants.HISTORY_QUICKSTART%> = false;
        }
    };


    $(function () {
        <%=PcompConstants.HISTORY_QUICKSTART%>Editor = editormd.markdownToHTML("<%=PcompConstants.HISTORY_QUICKSTART%>", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析
        });

    });

    function post<%=PcompConstants.HISTORY_QUICKSTART%>() {
        var temp = document.createElement("form");
        temp.action ='<%=PathUtils.makePath(PathConstants.PCOMP_VERSION_MODIFICATION_PATH)%>';
        temp.method = "post";
        temp.style.display = "none";
        var opt = document.createElement("textarea");
        opt.name ='<%=PathConstants.PCOMP_VERSION_QUICKSTART%>';
        opt.value =<%=PcompConstants.HISTORY_QUICKSTART%>Editor.getMarkdown();
        temp.appendChild(opt);
        opt = document.createElement("textarea");
        opt.name ='<%=PathConstants.PCOMP_SOFTWARE_ID%>';
        opt.value ='<%=pcompSoftware.getId()%>';
        temp.appendChild(opt);
        opt = document.createElement("textarea");
        opt.name ='<%=PathConstants.PCOMP_VERSION_ID%>';
        opt.value ='<%=pcomp_version%>';
        temp.appendChild(opt);
        opt = document.createElement("textarea");
        opt.name ='<%=PcompConstants.SOFTWARE_PAGE_NAVIGATION%>';
        opt.value ='<%=page_nav%>';
        temp.appendChild(opt);
        document.body.appendChild(temp);
        temp.submit();
        return temp;
    }

    $(function () {
        <%=PcompConstants.HISTORY_DOWNLOAD%>Editor = editormd.markdownToHTML("<%=PcompConstants.HISTORY_DOWNLOAD%>", {
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
        <%=PcompConstants.HISTORY_DOWNLOAD%>Editor = editormd.markdownToHTML("<%=PcompConstants.HISTORY_DOWNLOAD%>", {
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
    var <%=PcompConstants.HISTORY_QUICKSTART%>Editor;
    var button<%=PcompConstants.HISTORY_QUICKSTART%> = document.createElement("button");
    button<%=PcompConstants.HISTORY_QUICKSTART%>.setAttribute('type','button');
    button<%=PcompConstants.HISTORY_QUICKSTART%>.setAttribute('class','btn btn-default');
    button<%=PcompConstants.HISTORY_QUICKSTART%>.innerHTML='提交';
    var editing<%=PcompConstants.HISTORY_QUICKSTART%> = false;
    function edit<%=PcompConstants.HISTORY_QUICKSTART%>(){
        var <%=PcompConstants.HISTORY_QUICKSTART%> = document.getElementById('<%=PcompConstants.HISTORY_QUICKSTART%>');
        var <%=PcompConstants.HISTORY_QUICKSTART%>_box_body = document.getElementById('<%=PcompConstants.HISTORY_QUICKSTART%>_box_body');
        var <%=PcompConstants.HISTORY_QUICKSTART%>_box_header = document.getElementById('<%=PcompConstants.HISTORY_QUICKSTART%>_box_header');
        var <%=PcompConstants.HISTORY_QUICKSTART%>_box_footer = document.getElementById('<%=PcompConstants.HISTORY_QUICKSTART%>_box_footer');
        <%=PcompConstants.HISTORY_QUICKSTART%>_box_body.removeChild(<%=PcompConstants.HISTORY_QUICKSTART%>);
        var div = document.createElement("div");
        div.setAttribute('id','<%=PcompConstants.HISTORY_QUICKSTART%>');
        var textarea = document.createElement("textarea");
        textarea.setAttribute('style','display:none;');
        textarea.innerHTML = <%=PcompConstants.HISTORY_QUICKSTART%>Editor.getMarkdown();
        <%=PcompConstants.HISTORY_QUICKSTART%>_box_body.appendChild(div);
        div.appendChild(textarea);
        if(!editing<%=PcompConstants.HISTORY_QUICKSTART%>){
            <%=PcompConstants.HISTORY_QUICKSTART%>Editor = editormd("<%=PcompConstants.HISTORY_QUICKSTART%>", {
                width   : "100%",
                emoji: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
                height  : 640,
                syncScrolling : "single",
                imageUpload : true,
                imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                imageUploadURL : "<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_CONTENT_IMAGE_UPLOAD_PATH)%>",
                path    : "/sfpp-web/assets/lib/"
            });
            <%=PcompConstants.HISTORY_QUICKSTART%>_box_footer.appendChild(button<%=PcompConstants.HISTORY_QUICKSTART%>);
            editing<%=PcompConstants.HISTORY_QUICKSTART%> = true;
        } else{
            <%=PcompConstants.HISTORY_QUICKSTART%>Editor = editormd.markdownToHTML("<%=PcompConstants.HISTORY_QUICKSTART%>", {
                htmlDecode: "style,script,iframe",  // you can filter tags decode
                emoji: true,
                taskList: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
            });
            <%=PcompConstants.HISTORY_QUICKSTART%>_box_footer.removeChild(button<%=PcompConstants.HISTORY_QUICKSTART%>);
            editing<%=PcompConstants.HISTORY_QUICKSTART%> = false;
        }
    };


    $(function () {
        <%=PcompConstants.HISTORY_QUICKSTART%>Editor = editormd.markdownToHTML("<%=PcompConstants.HISTORY_QUICKSTART%>", {
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
    var <%=PcompConstants.INTRODUCTION%>Editor;
    var button<%=PcompConstants.INTRODUCTION%> = document.createElement("button");
    button<%=PcompConstants.INTRODUCTION%>.setAttribute('type','button');
    button<%=PcompConstants.INTRODUCTION%>.setAttribute('class','btn btn-default');
    button<%=PcompConstants.INTRODUCTION%>.innerHTML='提交';
    var editing<%=PcompConstants.INTRODUCTION%> = false;
    function edit<%=PcompConstants.INTRODUCTION%>(){
        var <%=PcompConstants.INTRODUCTION%> = document.getElementById('<%=PcompConstants.INTRODUCTION%>');
        var <%=PcompConstants.INTRODUCTION%>_box_body = document.getElementById('<%=PcompConstants.INTRODUCTION%>_box_body');
        var <%=PcompConstants.INTRODUCTION%>_box_header = document.getElementById('<%=PcompConstants.INTRODUCTION%>_box_header');
        var <%=PcompConstants.INTRODUCTION%>_box_footer = document.getElementById('<%=PcompConstants.INTRODUCTION%>_box_footer');
        <%=PcompConstants.INTRODUCTION%>_box_body.removeChild(<%=PcompConstants.INTRODUCTION%>);
        var div = document.createElement("div");
        div.setAttribute('id','<%=PcompConstants.INTRODUCTION%>');
        var textarea = document.createElement("textarea");
        textarea.setAttribute('style','display:none;');
        textarea.innerHTML = <%=PcompConstants.INTRODUCTION%>Editor.getMarkdown();
        <%=PcompConstants.INTRODUCTION%>_box_body.appendChild(div);
        div.appendChild(textarea);
        if(!editing<%=PcompConstants.INTRODUCTION%>){
            <%=PcompConstants.INTRODUCTION%>Editor = editormd("<%=PcompConstants.INTRODUCTION%>", {
                width   : "100%",
                emoji: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
                height  : 640,
                syncScrolling : "single",
                imageUpload : true,
                imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                imageUploadURL : "<%=PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_CONTENT_IMAGE_UPLOAD_PATH)%>",
                path    : "/sfpp-web/assets/lib/"
            });
            <%=PcompConstants.INTRODUCTION%>_box_footer.appendChild(button<%=PcompConstants.INTRODUCTION%>);
            editing<%=PcompConstants.INTRODUCTION%> = true;
        } else{
            <%=PcompConstants.INTRODUCTION%>Editor = editormd.markdownToHTML("<%=PcompConstants.INTRODUCTION%>", {
                htmlDecode: "style,script,iframe",  // you can filter tags decode
                emoji: true,
                taskList: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
            });
            <%=PcompConstants.INTRODUCTION%>_box_footer.removeChild(button<%=PcompConstants.INTRODUCTION%>);
            editing<%=PcompConstants.INTRODUCTION%> = false;
        }
    };


    $(function () {
        <%=PcompConstants.INTRODUCTION%>Editor = editormd.markdownToHTML("<%=PcompConstants.INTRODUCTION%>", {
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