<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<div class="col-md-4 col-sm-6 col-xs-12">
    <a href="<%=request.getParameter("link")%>">
        <div class="project-item">
            <img src="<%=request.getParameter("image")%>" alt="">

            <div class="project-hover">
                <h4><%=request.getParameter("name")%>
                </h4>
            </div>
        </div>
    </a>
</div>