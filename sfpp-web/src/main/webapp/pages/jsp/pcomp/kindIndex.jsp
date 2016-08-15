<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompKind" %>
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

    <div class="jumbotron hero_kind">
        <div class="container">
            <div class="row">
                <div class="col-md-1 col-md-push-7 phone-preview">
                    <%
                        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
                        PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
                        PcompKind pcompKind = pcompCacheObject.getPcompKind();
                    %>
                </div>
                <div class="col-md-0 col-md-offset-0 col-md-pull-0">
                    <h1><%=pcompKind!=null?pcompKind.getName():null%> </h1>
                    <p><%=pcompKind!=null?pcompKind.getIntroduction():null%>  </p>
                    <p></p>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="./common/content-header.jsp"></jsp:include>
    <section class="content">
        <jsp:include page="./common/content/softwareMatrix.jsp"></jsp:include>
    </section>
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>
<jsp:include page="../common/commonJS.jsp"></jsp:include>
<jsp:include page="./common/JS.jsp"></jsp:include>
</body>

</html>