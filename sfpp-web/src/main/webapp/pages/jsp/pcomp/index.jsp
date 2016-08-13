<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../common/commonHead.jsp"></jsp:include>
    <jsp:include page="./common/CSS.jsp"></jsp:include>
</head>

<body class="skin-black-light">

<div class="wrapper">
    <jsp:include page="../common/main-header.jsp"></jsp:include>
    <div class="jumbotron hero">
        <div class="container">
            <div class="row">
                <div class="col-md-1 col-md-push-7 phone-preview">

                </div>
                <div class="col-md-7 col-md-offset-0 col-md-pull-3 get-it">
                    <h1>公共组件平台 </h1>
                    <p>标准开源软件，共享SDK发布平台 </p>
                    <p></p>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="./common/content-header.jsp"></jsp:include>
    <section class="content">
        <jsp:include page="./common/content/titleList.jsp"></jsp:include>
    </section>
</div>


<footer class="site-footer">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <p class="text-center">SFPP © 2016</p></div>
        </div>
    </div>
</footer>
<jsp:include page="../common/commonJS.jsp"></jsp:include>
</body>

</html>