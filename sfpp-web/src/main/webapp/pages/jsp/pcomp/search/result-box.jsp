<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.search.domain.SearchResult" %>
<%@ page import="java.util.List" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    Object o = request.getAttribute(PathConstants.PCOMP_SEARCH_RESULT);
    if (o == null || ((List) o).size() == 0) {
%>
<div class="box box-default">
    <div class="box-header with-border" id="<%=PcompConstants.INTRODUCTION%>_box_header">
        <h3 class="box-title"><strong><a>无结果</a></strong>
        </h3>

        <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
        </div>

    </div>
    <div class="box-body" id="<%=PcompConstants.INTRODUCTION%>_box_body">
        <p>换个关键词试试</p>
    </div>
    <div class="box-footer" id="<%=PcompConstants.INTRODUCTION%>_box_footer">
    </div>
</div>
<%
} else {
    List<SearchResult> searchResults = (List<SearchResult>) o;
    for (SearchResult searchResult : searchResults) {
%>
<div class="box box-default">
    <div class="box-header with-border" id="<%=PcompConstants.INTRODUCTION%>_box_header">
        <h3 class="box-title"><strong><a href="<%=searchResult.getLink()%>"><%=searchResult.getTitle()%>
        </a></strong>
        </h3>

        <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
            </button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
        </div>

    </div>
    <div class="box-body" id="<%=PcompConstants.INTRODUCTION%>_box_body">
        <p><%=searchResult.getIntroduction()%>
        </p>
    </div>
    <div class="box-footer" id="<%=PcompConstants.INTRODUCTION%>_box_footer">
    </div>
</div>
<%
        }
    }
%>