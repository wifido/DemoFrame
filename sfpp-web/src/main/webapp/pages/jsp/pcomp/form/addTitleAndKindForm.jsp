<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <jsp:include page="../../common/commonHead.jsp"></jsp:include>
    <jsp:include page="./common/CSS.jsp"></jsp:include>
</head>
<body background="/sfpp-web/assets/img/backgrounds/1.jpg" style=" background-repeat:no-repeat ;
        background-size:cover;
        background-attachment: fixed;">

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <!--<div class="row">
        <div class="col-sm-8 col-sm-offset-2 text">
           <h1><strong></strong> </h1>
            <div class="description">
                描述内容
            </div>
        </div>
    </div>-->
            <div class="row">
                <div class="col-md-6 col-xs-10 col-lg-6 col-sm-10 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <ul id="mytab" class="nav nav-tabs">
                                <h3 class="page-header"><span class="glyphicon glyphicon-bell"></span> 请选择需要新建的项目</h3>
                                <li class="active">
                                    <a href='#xw1' data-toggle='tab'>新建主题</a>
                                </li>
                                <li>
                                    <a href='#xw2' data-toggle='tab'>新建分类</a>
                                </li>
                            </ul>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-list-alt"></i>
                        </div>
                    </div>

                    <div class="tab-content">
                        <div class="form-bottom tab-pane fade in active" id="xw1">
                            <jsp:include page="./element/addTitle.jsp"></jsp:include>
                        </div>

                        <div class="form-bottom tab-pane fade" id="xw2">
                            <jsp:include page="./element/addKind.jsp"></jsp:include>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../common/commonJS.jsp"></jsp:include>
<jsp:include page="./common/JS.jsp"></jsp:include>
<jsp:include page="./element/addTitle_JS.jsp"></jsp:include>
<jsp:include page="./element/addKind_JS.jsp"></jsp:include>
</body>

</html>