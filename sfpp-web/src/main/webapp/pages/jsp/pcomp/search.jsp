<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../common/commonHead.jsp"></jsp:include>
    <jsp:include page="./common/CSS.jsp"></jsp:include>
</head>

<body class="skin-blue">

<div class="">
    <jsp:include page="../common/main-header.jsp"></jsp:include>
    <jsp:include page="./common/content-header.jsp"></jsp:include>
    <section class="content">
        <jsp:include page="./search/title-box.jsp"></jsp:include>
        <jsp:include page="./search/result-box.jsp"></jsp:include>
    </section>
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>
<jsp:include page="../common/commonJS.jsp"></jsp:include>
<jsp:include page="./common/JS.jsp"></jsp:include>
</body>

</html>