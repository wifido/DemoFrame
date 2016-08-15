<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        out.print(webCache.getTitle());
    %>
</title>
<%
    String message = webCache.getMessage();
    if(!StrUtils.isNull(message)){
%>
<jsp:forward page="./page/ErrorMessage.jsp">
    <jsp:param name="Message" value="<%=message%>"/>
</jsp:forward>
<%
    }
%>
<link rel="stylesheet" href="/sfpp-web/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/sfpp-web/assets/fonts/font-awesome.min.css">
<link rel="stylesheet" href="/sfpp-web/assets/css/common/borderlist.css">
<link rel="stylesheet" href="/sfpp-web/assets/css/common/Features-Item.css">
<link rel="stylesheet" href="/sfpp-web/assets/css/common/animate.min.css">
<link rel="stylesheet" href="/sfpp-web/assets/css/common/login-form.css">
<link rel="stylesheet" href="/sfpp-web/assets/css/common/AdminLTE.min.css">
<link rel="stylesheet" href="/sfpp-web/assets/css/common/skins/_all-skins.min.css">