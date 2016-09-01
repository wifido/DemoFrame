<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../common/commonHead.jsp"></jsp:include>
    <jsp:include page="common/CSS.jsp"></jsp:include>
</head>

<body class="skin-blue">
<div class="wrapper">
    <jsp:include page="../common/main-header.jsp"></jsp:include>


    <div class="content-wrapper">

        <section class="content">
            <div id="<%=PcompConstants.HELP%>">
                <textarea style="display:none;">#SFPP-公共组件模块使用说明文档
**本文会以非常简短的方式介绍如何发布一个软件以及如何发布一个版本**
## 1.概念与权限介绍：
公共组件有四个概念：

1. 主题 - 管理员添加
2. 分类 - 管理员添加
3. 软件（组件，框架，库） - 用户添加
4. 版本 - 用户添加

一个主题对应多个分类，一个分类对应多个软件，一个软件对应多个版本。
每个软件的创建者有修改**本软件以及本软件所有版本**信息的权限，**其他人可以为本软件添加版本**以及**修改自己添加的版本**，但不能修改软件信息和其他非自己创建的版本信息。

##2.概念对应模块展示：
###2.1. 公共组件首页
![](http://10.202.7.85:8080/image/01/000/000015d65f2b4-41df-452c-a614-e7ed08228cc6.png)
###2.2. 分类页
![](http://10.202.7.85:8080/image/01/000/00001048de131-326c-4049-97f5-15c4fac37ee0.png)
###2.3. 软件页
![](http://10.202.7.85:8080/image/01/000/0000102145395-2649-4c3c-8ed1-0086da3bea1e.png)

##3. 发布流程
分为两步：
1. 添加软件
2. 添加版本
之后就是重复第二个步骤添加版本。

##3.1. 添加一个软件
![](http://10.202.7.85:8080/image/01/000/0000114e0d234-7759-4202-8239-bcca46de7016.png)
![](http://10.202.7.85:8080/image/01/000/00001ece2b5e5-ceac-4e11-9615-32089e7bdf67.png)
之后提交即可。

##3.2. 添加一个版本
![](http://10.202.7.85:8080/image/01/000/000014185de62-564c-4d1d-92a8-002a5c11e8b2.png)
![](http://10.202.7.85:8080/image/01/000/00001d3666256-ad06-4013-8a66-695ab956c148.png)
![](http://10.202.7.85:8080/image/01/000/000016e5d1ace-ffd3-4183-8547-66ec5b06b917.png)
![](http://10.202.7.85:8080/image/01/000/0000131ce9ca8-95b4-4e84-8c96-531a172a5f67.png)
之后提交即可。
如果文件很大，可能需要等一段时间上传，并且等一段时间响应（RPC到文件服务器,这个后面会优化，利用Linkin开源的Ambry，欢迎有兴趣的人来交流）。

**日后修改在每个可以修改的地方都有修改标志，请大家注意下即可。**

                </textarea>
            </div>
        </section>
    </div>
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>
<jsp:include page="../common/commonJS.jsp"></jsp:include>
<jsp:include page="common/JS.jsp"></jsp:include>
<jsp:include page="common/sidebar_JS.jsp"></jsp:include>
<script>
    <%=PcompConstants.HELP%>Editor = editormd.markdownToHTML("<%=PcompConstants.HELP%>", {
        htmlDecode: "style,script,iframe",  // you can filter tags decode
        emoji: true,
        taskList: true,
        tex: true,  // 默认不解析
        flowChart: true,  // 默认不解析
        sequenceDiagram: true,  // 默认不解析
    });
</script>