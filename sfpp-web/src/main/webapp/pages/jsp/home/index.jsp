<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@ page import="com.sf.sfpp.web.common.PagePathConstants" %>
<%@ page import="java.util.Date" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../common/commonHead.jsp"></jsp:include>
    <jsp:include page="./common/CSS.jsp"></jsp:include>
</head>

<body class="<%=Constants.THEME%>">

<div class="wrapper">
    <jsp:include page="../common/main-header.jsp"></jsp:include>
</div>

<div class="jumbotron hero">
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-push-7 phone-preview">
                <div class="row login-form">
                    <div class="col-md-11 col-md-offset-2">

                    </div>
                </div>
                <div class="iphone-mockup">
                    <div class="screen"></div>
                </div>
            </div>
            <div class=" get-it">
                <h1><%=Constants.MAIN_SYSTEM%> </h1>

                <p>顺丰科技人，交流、共享、研发平台 </p>

                <p><%=new Date()%></p>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <a href="<%=PathUtils.makePath(PagePathConstants.KNNET_HOMEPAGE_PATH)%>" target="_self" class="hero">
            <h2 class="text-center"><%=Constants.KNOWLEDGE_NETWORK_SYSTEM%></h2>
            <h1 class="text-center text-muted text-big-icon"><i class="fa fa-globe"></i></h1>
            <p class="text-center">聚合各技术领域最新知识，提供完整的知识分类和知识地图 </p>
        </a>
    </div>


    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <a href="<%=PathUtils.makePath(PagePathConstants.COMMUNITY_HOMEPAGE_PATH)%>" target="_self" class="hero">
            <h2 class="text-center"><%=Constants.COMMUNITY_SYSTEM%> </h2>
            <h1 class="text-center text-muted text-big-icon"><i class="fa fa-users"></i></h1>
            <p class="text-center">按领域划分的技术相关交流社区，主要话题涉及技术问答、讨论和推荐 </p>
        </a>
    </div>


    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <a href="<%=PathUtils.makePath(PagePathConstants.PCOMP_HOMEPAGE_PATH)%>" target="_self" class="hero">
            <h2 class="text-center"><%=Constants.PUBLIC_COMPONENT_SYSTEM%> </h2>
            <h1 class="text-center text-muted text-big-icon"><i class="fa fa-gears"></i></h1>
            <p class="text-center">标准软件发布，公共SDK共享，开源组件交流与发布 </p>
        </a>
    </div>


    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <a href="<%=PathUtils.makePath(PagePathConstants.ACADEMY_HOMEPAGE_PATH)%>" target="_self" class="hero">
            <h2 class="text-center"><%=Constants.ACADEMY_SYSTEM%> </h2>
            <h1 class="text-center text-muted text-big-icon"><i class="fa fa-building-o"></i></h1>
            <p class="text-center">各领域内部大牛分享，专家推荐精品课程 ，学习路线规划</p>
        </a>
    </div>
</div>
<section class="testimonials">
    <h2 class="text-center"><%=Constants.MAIN_SYSTEM_SHORT%> </h2>
    <blockquote>
        <p>顺丰人自己的技术平台，期待你的加入 </p>
        <footer></footer>
    </blockquote>
</section>
<jsp:include page="../common/footer.jsp"></jsp:include>
<jsp:include page="../common/commonJS.jsp"></jsp:include>
</body>

</html>