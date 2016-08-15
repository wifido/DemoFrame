<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompKind" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
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
                    <h1><%=Constants.PUBLIC_COMPONENT_SYSTEM%> </h1>
                    <p><%=Constants.PUBLIC_COMPONENT_DESCRIPTION%>  </p>
                    <p></p>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="./common/content-header.jsp"></jsp:include>
    <section class="content">
        <jsp:include page="./common/content/titleList.jsp"></jsp:include>
        <jsp:include page="./common/content/kindMatrix.jsp"></jsp:include>
    </section>
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>
<jsp:include page="../common/commonJS.jsp"></jsp:include>
</body>

</html>