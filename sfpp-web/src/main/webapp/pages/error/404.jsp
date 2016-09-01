<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>顺丰开发平台</title>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no" />
  <link rel="stylesheet" href="/sfpp-web/assets/bootstrap/css/bootstrap.min.css" />
  <link rel="stylesheet" href="/sfpp-web/assets/css/matrix-style.css" />
  <link rel="stylesheet" href="/sfpp-web/assets/css/matrix-media.css" />
  <link rel="stylesheet" href="/sfpp-web/assets/fonts/font-awesome.min.css" />

</head>
<body>
<div id="content">
  <!--<div id="content-header">
    <h1>Error 404</h1>
  </div>-->
  <div  class=" container-fluid">
    <div id="inner-row" class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-info-sign"></i> </span>
            <h5>Error 404</h5>
          </div>
          <div class="widget-content">
            <div class="error_ex">
              <h1>404</h1>
              <h3>Opps, 页面找不到啦~.</h3>
              <p>We can not find the page you're looking for.</p>
              <a class="btn btn-warning btn-big"  href="<%=PathUtils.makePath(PathConstants.HOMEPAGE_PATH)%>">回到首页</a> </div>
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
