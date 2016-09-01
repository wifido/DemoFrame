<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.sf.sfpp.web.common.utils.PermissionUtils" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>顺丰开发平台</title>

	<link rel="shortcut icon" href="/favicon.ico" />
    <!-- CSS -->
    <link rel="stylesheet" href="/sfpp-web/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/sfpp-web/assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="/sfpp-web/assets/css/form-elements.css">
    <link rel="stylesheet" href="/sfpp-web/assets/css/style.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="/sfpp-web/assets/plugins/html5shiv.min.js"></script>
    <script src="/sfpp-web/assets/plugins/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<%
   if(PermissionUtils.getCurrentUser() != null){
	   request.getRequestDispatcher("/index").forward(request, response);  
   }
%>
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
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>顺丰开发平台</h3>
                            <p>请使用域账号登录!</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form id="loginForm" role="form" action="login" method="post" class="login-form" onsubmit="return $.login.submit()">
                            <div class="form-group">
                                <label class="sr-only" for="form-username">用户名</label>
                                <input type="text" name="username" placeholder="用户名" class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">密码</label>
                                <input type="password" name="password" placeholder="密码" class="form-password form-control" id="form-password">
                            </div>
<!--                             <div class="form-group"> -->
<!--                                 <input type="checkbox" name="rememberMe" id="rememberMe"><span style="padding-left: 15px;">记住</span></input> -->
<!--                             </div> -->
                            
                            <button class="btn">登录</button>
                            <div id="info" type="textarea" style="color:red"></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


<!-- Javascript -->
<script src="/sfpp-web/assets/plugins/jquery/jquery-2.2.0.min.js"></script>
<script src="/sfpp-web/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="/sfpp-web/assets/plugins/jquery/jquery.backstretch.min.js"></script>
<script src="/sfpp-web/assets/js/login.js"></script>
<!-- <script src="/sfpp-web/assets/js/matrix.js"></script> -->

<!--[if lt IE 10]>
<script src="/sfpp-web/assets/plugins/placeholder.js"></script>
<![endif]-->

</body>

</html>
