<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<form class="navbar-form navbar-left" role="search">
    <div class="form-group">
        <input type="text" class="form-control" id="navbar-search-input" placeholder="Search">
        <button type="submit" name="search"  class="btn btn-default"><i class="fa fa-search"></i></button>
        <div class="btn-group">
            <button type="button" class="btn btn-default" onclick=" window.location.href='/sfpp-web/pcomp/software/add.do'">发布软件(或版本)</button>
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <span class="caret"></span>
                <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="/sfpp-web/pcomp/title/add.do" target="_blank">创建新主题</a></li>
                <li><a href="/sfpp-web/pcomp/kind/add.do" target="_blank">创建新分类</a></li>
            </ul>
        </div>
    </div>
</form>
