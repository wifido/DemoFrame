<%@ page import="com.sf.sfpp.pcomp.common.domain.PcompCacheObject" %>
<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.domain.WebCache" %>
<%@ page import="com.sf.sfpp.pcomp.common.model.PcompSoftware" %>
<%@ page import="java.util.List" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    WebCache webCache = (WebCache) request.getAttribute(Constants.WEB_CACHE_KEY);
    PcompCacheObject pcompCacheObject = (PcompCacheObject) webCache.getCacheObject();
    List<PcompSoftware> pcompSoftwares = pcompCacheObject.getPcompSoftwares();
    for (PcompSoftware pcompSoftware : pcompSoftwares) {
%>

<div class="col-md-3">
    <div class="box box-default ">
        <div class="box-header with-border">
            <h3 class="box-title"><a href="#"><b><%=pcompSoftware.getName()%></b></a></h3>

            <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i>
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