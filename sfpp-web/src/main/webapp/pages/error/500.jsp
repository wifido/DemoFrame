<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>顺丰开发平台</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/sfpp-web/assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/sfpp-web/assets/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/sfpp-web/assets/css/matrix-style.css" />
<link rel="stylesheet" href="/sfpp-web/assets/css/matrix-media.css" />
<link rel="stylesheet" href="/sfpp-web/assets/fonts/font-awesome.min.css" />
</head>
<body>

<div id="content">
  <div id="content-header">
    <h1>Error 500</h1>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-info-sign"></i> </span>
            <h5>Error 500</h5>
          </div>
          <div class="widget-content">
            <div class="error_ex">
              <h1>500</h1>
              <h3>出错啦~你访问的页面拒绝了你的请求!</h3>
              <p>Access to this page is forbidden</p>
              <a class="btn btn-warning btn-big"  href="/pages/main/index.html">回到首页</a> </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<!--Footer-part-->
<div class="row-fluid">
  <div id="footer" class="row-fluid">
    <div class="span12"> 2016 &copy; SF Tech <a href="#" onclick="sftech()">顺丰科技</a></div>
  </div>
</div>

<script type="text/javascript">
  function sftech() {
    window.open("http://www.sf-tech.com.cn");
  }
</script>
</body>
</html>
