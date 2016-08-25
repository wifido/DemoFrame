<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <jsp:include page="../../common/commonHead.jsp"></jsp:include>
    <jsp:include page="./common/CSS.jsp"></jsp:include>
    <jsp:include page="../common/CSS.jsp"></jsp:include>
</head>
<body background="/sfpp-web/assets/img/backgrounds/1.jpg" style=" background-repeat:no-repeat ;
        background-size:cover;
        background-attachment: fixed;">

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg" style="padding: 0 0 0 0;">
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
                <div class="col-sm-10 col-xs-10 col-md-10 col-lg-10 col-sm-offset-1 col-xs-offset-1 col-lg-offset-1 col-md-offset-1 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <ul id="mytab" class="nav nav-tabs">
                                <h3 class="page-header"><span class="glyphicon glyphicon-bell"></span>新建版本</h3>
                            </ul>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-list-alt"></i>
                        </div>
                    </div>

                    <div class="tab-content">
                        <div class="form-bottom tab-pane fade in active" id="xw1">
                            <jsp:include page="./element/addVersion.jsp"></jsp:include>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../common/commonJS.jsp"></jsp:include>
<jsp:include page="./common/JS.jsp"></jsp:include>
<jsp:include page="../common/JS.jsp"></jsp:include>
<jsp:include page="./element/addVersion_JS.jsp"></jsp:include>
</body>

</html>