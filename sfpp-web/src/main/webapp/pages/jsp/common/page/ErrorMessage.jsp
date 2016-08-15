<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Landing Page</title>
    <link rel="stylesheet" href="/sfpp-web/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/sfpp-web/assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="/sfpp-web/assets/css/common/borderlist.css">
    <link rel="stylesheet" href="/sfpp-web/assets/css/common/Features-Item.css">
    <link rel="stylesheet" href="/sfpp-web/assets/css/common/animate.min.css">
    <link rel="stylesheet" href="/sfpp-web/assets/css/common/login-form.css">
</head>

<body>
<div class="row">
    <div class="col-md-12 col-md-offset-4 col-sm-offset-4">
        <div class="col-md-4 col-sm-6 wow fadeInDown">
            <div class="feature-wrap"><i class="fa fa-bullhorn"></i>
                <h2>呀！出错了： <%=request.getParameter("Message")%></h2>
                <h3><a href="/sfpp-web/index.do">点击这里返回主页</a> </h3></div>
        </div>
    </div>
</div>
<script src="/sfpp-web/assets/js/jquery.min.js"></script>
<script src="/sfpp-web/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="/sfpp-web/assets/js/Features-Item.js"></script>
<script src="/sfpp-web/assets/js/Features-Item1.js"></script>
</body>

</html>