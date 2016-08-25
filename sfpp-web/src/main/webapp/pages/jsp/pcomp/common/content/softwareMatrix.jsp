<%@ page import="com.github.pagehelper.PageInfo" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompKind" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompSoftware" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@ page import="com.sf.sfpp.web.common.utils.PermissionUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
    PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
    PcompKind pcompKind = pcompCacheObject.getPcompKind();
    PageInfo<PcompSoftware> pcompSoftwares = pcompCacheObject.getPcompSoftwares();
    for (PcompSoftware pcompSoftware : pcompSoftwares.getList()) {
%>
<div class="row">
    <div class="col-lg-2 col-md-3  col-sm-4 col-xs-12">
        <div class="box box-default ">
            <div class="box-header with-border">
                <h3 class="box-title"><a
                        href="<%=PathUtils.makeSoftwarePath(pcompSoftware.getId())%>"><b><%=pcompSoftware.getName()%>
                </b></a></h3>

                <div class="box-tools pull-right">
                    <%
                        if (pcompSoftware != null
                                && PermissionUtils.isCurrentUser(pcompSoftware.getCreatedBy())) {
                    %>
                    <button type="button" class="btn btn-box-tool"><i class="fa fa-pencil"></i>
                    </button>
                    <button type="button" class="btn btn-box-tool"><i class="fa fa-trash"></i>
                    </button>
                    <%}%>
                    <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                    </button>
                </div>
            </div>
            <div class="box-body">
                <%=pcompSoftware.getIntroductionShort()%>
            </div>
        </div>
    </div>

    <%
        }
    %>
</div>
<div class="row">
    <p class="text-center">
        <%
            PageInfo pageInfo = (PageInfo) pcompSoftwares;

        %>
        <a href="<%=pcompKind!=null?PathUtils.makeKindPath(pcompKind.getId()):PathUtils.makeKindPath(null)%>">
            <button class="btn <%if(pageInfo.getPageNum()==1){%>btn-primary<%}else{%>btn-warn<%}%>">
                首页
            </button>
        </a>
        <a href="<%if(pageInfo.getPageNum()!=1){%><%=pcompKind!=null?PathUtils.makeKindPath(pcompKind.getId(),pageInfo.getPageNum()-1):PathUtils.makeKindPath(null,pageInfo.getPageNum()-1)%><%}%>">
            <button class="btn btn-warn" <%if(pageInfo.getPageNum()==1){%>disabled<%}%>>上一页
            </button>
        </a>
        <%
            int start = 0, end = 0;
            if (pageInfo.getPages() < 10) {
                start = 1;
                end = pageInfo.getPages();
            } else if (pageInfo.getPageNum() <= pageInfo.getPages() - 10) {
                start = pageInfo.getPageNum();
                end = pageInfo.getPageNum() + 9;
            } else {
                start = pageInfo.getPages() - 10;
                end = pageInfo.getPages();
            }
        %>
        <%

            for (int i = start; i <= end; i++) {
        %>
        <a href="<%=pcompKind!=null?PathUtils.makeKindPath(pcompKind.getId(),i):PathUtils.makeKindPath(null,i)%>">
            <button class="btn <%if(pageInfo.getPageNum()==i){%>btn-primary<%}else{%>btn-warn<%}%>"><%=i%>
            </button>
        </a>
        <%
            }
        %>

        <a href="<%if(pageInfo.getPageNum()!=pageInfo.getPages()){%><%=pcompKind!=null?PathUtils.makeKindPath(pcompKind.getId(),pageInfo.getPageNum()+1):PathUtils.makeKindPath(null,pageInfo.getPageNum()+1)%><%}%>">
            <button class="btn btn-warn" <%if(pageInfo.getPageNum()==pageInfo.getPages()){%>disabled<%}%>>下一页
            </button>
        </a>


        <a href="<%=pcompKind!=null?PathUtils.makeKindPath(pcompKind.getId(),pageInfo.getPages()):PathUtils.makeKindPath(null,pageInfo.getPages())%>">
            <button class="btn <%if(pageInfo.getPageNum()==pageInfo.getPages()){%>btn-primary<%}else{%>btn-warn<%}%>">
                末页
            </button>
        </a>

    </p>
</div>