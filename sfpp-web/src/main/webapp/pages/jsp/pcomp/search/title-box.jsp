<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.elasticsearch.pcomp.SortRule" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<div class="box ">
    <div class="box-header with-border">
        <h3 class="box-title"><strong>搜索选项</strong></h3>
    </div>
    <div class="box-body search-box-body">
        <form action="<%=PathUtils.makePath(PathConstants.PCOMP_SEARCH_PATH)%>" method="get">
            <div class="input-group">
                <input type="text" class="form-control" name="<%=PathConstants.PCOMP_SEARCH_KEYWORD%>"
                        value="<%=(String)request.getAttribute(PathConstants.PCOMP_SEARCH_KEYWORD)%>">
                        <span class="input-group-btn">
                            <button type="submit" name="search" class="btn btn-primary"><i class="fa fa-search"></i>
                            </button>
                        </span>
            </div>
            <div class="input-group">
                <%String category = (String) request.getAttribute(PathConstants.PCOMP_SEARCH_CATEGORY_PARA);%>
                <label class="form-element"><strong>类别：</strong></label>
                <label class="form-element">
                    <input type="radio" value="all"
                           name="<%=PathConstants.PCOMP_SEARCH_CATEGORY_PARA%>"<%if(StrUtils.isNull(category)||category.equals("all")){%>
                           checked<%}%>>全部
                </label>
                <label class="form-element">
                    <input type="radio" value="<%=PcompConstants.PCOMP_SOFTWARE%>"
                           name="<%=PathConstants.PCOMP_SEARCH_CATEGORY_PARA%>"
                           <% if(!StrUtils.isNull(category)&&category.equals(PcompConstants.PCOMP_SOFTWARE)){%>checked<%}%> >软件
                </label>
                <label class="form-element">
                    <input type="radio" value="<%=PcompConstants.PCOMP_VERSION%>"
                           name="<%=PathConstants.PCOMP_SEARCH_CATEGORY_PARA%>"
                           <% if(!StrUtils.isNull(category)&&category.equals(PcompConstants.PCOMP_VERSION)){%>checked<%}%>>版本
                </label>
            </div>
            <div class="input-group">
                <%String sorted_by = (String) request.getAttribute(PathConstants.PCOMP_SEARCH_SORTED_BY_PARA);%>
                <label class="form-element"><strong>排序：</strong></label>
                <label class="form-element">
                    <input type="radio" value="<%=SortRule.BY_CORRELATION%>"
                           name="<%=PathConstants.PCOMP_SEARCH_SORTED_BY_PARA%>"
                           <% if(StrUtils.isNull(category)||sorted_by.equals(SortRule.BY_CORRELATION)){%>checked<%}%>>相关性正序
                </label>
                <label class="form-element">
                    <input type="radio" value="<%=SortRule.BY_CORRELATION_DESC%>"
                           name="<%=PathConstants.PCOMP_SEARCH_SORTED_BY_PARA%>"
                           <% if(!StrUtils.isNull(category)&&sorted_by.equals(SortRule.BY_CORRELATION_DESC)){%>checked<%}%>>相关性倒序
                </label>
                <label class="form-element">
                    <input type="radio" value="<%=SortRule.BY_MODIFIED_TIME%>"
                           name="<%=PathConstants.PCOMP_SEARCH_SORTED_BY_PARA%>"
                           <% if(!StrUtils.isNull(category)&&sorted_by.equals(SortRule.BY_MODIFIED_TIME)){%>checked<%}%>>修改时间正序
                </label>
                <label class="form-element">
                    <input type="radio" value="<%=SortRule.BY_MODIFIED_TIME_DESC%>"
                           name="<%=PathConstants.PCOMP_SEARCH_SORTED_BY_PARA%>"
                           <% if(!StrUtils.isNull(category)&&sorted_by.equals(SortRule.BY_MODIFIED_TIME_DESC)){%>checked<%}%>>修改时间倒序
                </label>
            </div>
        </form>
    </div>

</div>