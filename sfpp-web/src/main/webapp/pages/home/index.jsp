<%@ page import="com.github.pagehelper.PageInfo" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompKind" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompSoftware" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
    <%
        WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
        PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
    %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><%=webCache.getTitle()%>
    </title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/sfpp-web/homeTheme/css/normalize.css">
    <link rel="stylesheet" href="/sfpp-web/homeTheme/css/font-awesome.css">
    <link rel="stylesheet" href="/sfpp-web/homeTheme/css/bootstrap.min.css">
    <link rel="stylesheet" href="/sfpp-web/homeTheme/css/templatemo-style.css">
    <script src="/sfpp-web/homeTheme/js/vendor/modernizr-2.6.2.min.js"></script>
</head>
<body>
<!--[if lt IE 7]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->

<div class="site-bg"></div>
<div class="site-bg-overlay"></div>

<!-- TOP HEADER -->
<div class="top-header">
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-sm-6 col-xs-12" style="height: 50px; line-height: 50px;font-size: large">
                <p class="phone-info"><%=Constants.MAIN_SYSTEM%>-<%=Constants.MAIN_SYSTEM_SHORT%>
                </p>
            </div>
            <jsp:include page="../common/navibarUser.jsp"></jsp:include>
        </div>
    </div>
</div> <!-- .top-header -->


<div class="container" id="page-content">
    <div class="row">


        <div class="col-md-9 col-sm-12 content-holder">
            <!-- CONTENT -->
            <div id="menu-container">

                <div class="logo-holder logo-top-margin">
                    <a href="" class="site-brand"><img src="/sfpp-web/homeTheme/images/logo.png" width="100px"
                                                       height="120px" alt=""></a>
                </div>


                <div id="menu-1" class="homepage home-section text-center">
                    <div class="welcome-text">
                        <h2>你好, 欢迎光临 <strong><%=Constants.MAIN_SYSTEM%>
                        </strong></h2>

                        <p>顺丰人自己的技术交流，研发，共享平台，期待你的加入 </p>

                        <form action="<%=PathUtils.makePath(PathConstants.PCOMP_SEARCH_PATH)%>" method="get"
                              class="subscribe-form">
                            <div class="row">
                                <fieldset class="col-md-offset-2 col-md-6">
                                    <input name="<%=PathConstants.PCOMP_SEARCH_KEYWORD%>" class="email"
                                           id="subscribe-email"
                                           placeholder="输入想要搜索的内容">
                                </fieldset>
                                <fieldset class="col-md-4 button-holder">
                                    <input name="submit" type="submit" class="button default" id="submit"
                                           value="Submit">
                                </fieldset>
                            </div>
                            <p class="subscribe-text">汇聚知识，积聚力量!</p>
                        </form>
                    </div>
                </div>

                <div id="menu-2" class="content gallery-section">


                    <div class="box-content">
                        <h3 class="widget-title">最新组件</h3>

                        <div class="row">
                            <%
                                PageInfo<PcompSoftware> pcompSoftwares = pcompCacheObject.getPcompSoftwares();
                                int count = 0;
                                for (PcompSoftware pcompSoftware : pcompSoftwares.getList()) {
                            %>
                            <jsp:include page="element/hoverTitleBox.jsp">
                                <jsp:param name="link"
                                           value="<%=PathUtils.makeSoftwarePath(pcompSoftware.getId())%>"></jsp:param>
                                <jsp:param name="name" value="<%=pcompSoftware.getName()%>"></jsp:param>
                                <jsp:param name="image" value="<%=pcompSoftware.getAvatar()%>"></jsp:param>
                            </jsp:include>
                            <%
                                    count++;
                                    if(count == 6){
                                        break;
                                    }
                                }
                            %>
                        </div>
                        <div class="col-md-12 col-sm-12" style="text-align: right;">
                            <a href="<%=PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH)%>" style="background: #ffffff;">立即进入>></a>
                        </div>
                    </div>

                    <div class="box-content">
                        <h3 class="widget-title">分类推荐</h3>

                        <div class="row">
                            <%
                                PageInfo<PcompKind> pcompKinds = pcompCacheObject.getPcompKinds();
                                count = 0;
                                for (PcompKind pcompKind : pcompKinds.getList()) {
                            %>
                            <jsp:include page="element/hoverTitleBox.jsp">
                                <jsp:param name="link"
                                           value="<%=PathUtils.makeKindPath(pcompKind.getId())%>"></jsp:param>
                                <jsp:param name="name" value="<%=pcompKind.getName()%>"></jsp:param>
                                <jsp:param name="image" value="<%=pcompKind.getBannerImage()%>"></jsp:param>
                            </jsp:include>
                            <%
                                    count++;
                                    if(count == 6){
                                        break;
                                    }
                                }
                            %>
                        </div>
                        <div class="col-md-12 col-sm-12" style="text-align: right;">
                            <a href="<%=PathUtils.makePath(PathConstants.PCOMP_HOMEPAGE_PATH)%>" style="background: #ffffff;">立即进入>></a>
                        </div>
                    </div>
                </div>

                <div id="menu-3" class="content about-section">
                    <div class="row">
                        <div class="col-md-8 col-sm-8">
                            <div class="box-content profile">
                                <h3 class="widget-title">本周明星</h3>

                                <div class="profile-thumb">
                                    <img src="/sfpp-web/homeTheme/images/example/academy_lecturer.PNG" alt="">
                                </div>
                                <div class="profile-content">
                                    <h5 class="profile-name">刘小青</h5>
                                    <span class="profile-role">后端开发工程师</span>

                                    <p class="profile-name"> 立体化服务监控平台架构设计</p>
                                    <br>
                                    <b>讲师分享集锦</b>
                                    <ul>
                                        <li>立体化监控定义</li>
                                        <li>立体化监控的关注点</li>
                                        <li>立体化监控的架构特点</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-4">
                            <div class="box-content">
                                <h3 class="widget-title">背景介绍</h3>

                                <p>Vestibulum pellentesque ante sit amet tristique hendrerit. Sed consequat, nunc
                                    lobortis faucibus pretium, enim nibh blandit est, nec sollicitudin est quam a neque.
                                    Aenean eget malesuada justo.</p>

                                <div class="about-social">
                                    <ul>
                                        <li><a href="#" class="fa fa-facebook"></a></li>
                                        <li><a href="#" class="fa fa-twitter"></a></li>
                                        <li><a href="#" class="fa fa-linkedin"></a></li>
                                        <li><a href="#" class="fa fa-dribbble"></a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-5 col-sm-5">
                            <div class="box-content">
                                <h3 class="widget-title">来自于</h3>

                                <div class="project-item">
                                    <img src="/sfpp-web/homeTheme/images/7.jpg" alt="">

                                    <div class="project-hover">
                                        <h4>互联网业务研发中心</h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-7 col-sm-7">
                            <div class="box-content">
                                <h3 class="widget-title">综合评价</h3>
                                <ul class="progess-bars">
                                    <li>
                                        <span>课程 4.52</span>

                                        <div class="progress">
                                            <div class="progress-bar" role="progressbar" aria-valuenow="80"
                                                 aria-valuemin="0" aria-valuemax="100" style="width: 90%;"></div>
                                        </div>
                                    </li>
                                    <li>
                                        <span>讲师 4.51</span>

                                        <div class="progress">
                                            <div class="progress-bar" role="progressbar" aria-valuenow="95"
                                                 aria-valuemin="0" aria-valuemax="100" style="width: 89%;"></div>
                                        </div>
                                    </li>
                                    <li>
                                        <span>组织 4.77</span>

                                        <div class="progress">
                                            <div class="progress-bar" role="progressbar" aria-valuenow="70"
                                                 aria-valuemin="0" aria-valuemax="100" style="width: 95%;"></div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-md-12 col-sm-12" style="text-align: right;">
                            <a href="#" style="background: #ffffff;">立即进入</a>
                        </div>
                    </div>

                </div>

                <div id="menu-4" class="content contact-section">
                    <div class="row">

                    </div>
                </div>


            </div>
        </div>


        <div class="col-md-3 hidden-sm">

            <nav id="nav" class="main-navigation hidden-xs hidden-sm">
                <ul class="main-menu">
                    <li>
                        <a class="show-1 active homebutton" href="#"><i class="fa fa-home"></i>主页</a>
                    </li>
                    <li>
                        <a class="show-2 aboutbutton" href="#"><i
                                class="fa fa-gears"></i><%=Constants.PUBLIC_COMPONENT_SYSTEM%>
                        </a>
                    </li>
                    <li>
                        <a class="show-3 projectbutton" href="#"><i
                                class="fa fa-building"></i><%=Constants.ACADEMY_SYSTEM%>
                        </a>
                    </li>
                    <li>
                        <a class="show-4 contactbutton" href="#"><i
                                class="fa fa-users"></i><%=Constants.COMMUNITY_SYSTEM%>
                        </a>
                    </li>
                    <li>
                        <a class="show-5 fifthbutton" href="#"><i
                                class="fa fa-globe"></i><%=Constants.KNOWLEDGE_NETWORK_SYSTEM%>
                        </a>
                    </li>
                </ul>
            </nav>

        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"></jsp:include>

<script src="/sfpp-web/homeTheme/js/vendor/jquery-1.10.2.min.js"></script>
<script src="/sfpp-web/homeTheme/js/plugins.js"></script>
<script src="/sfpp-web/homeTheme/js/main.js"></script>
<script src="/sfpp-web/homeTheme/js/bootstrap.min.js"></script>
<!-- templatemo 439 rectangle -->
</body>
</html>