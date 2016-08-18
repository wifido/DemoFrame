<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>按钮权限测试</title>

	<link rel="shortcut icon" href="/sfpp-web/favicon.ico" />

</head>

<body>
             <form>
					<p>这是权限测试</p>
				    <shiro:hasPermission name="/document/createButton">
                        <input type="button" value="创建主题"/>
                    </shiro:hasPermission>
					<shiro:hasPermission name="/document/publishButton">
                        <input type="button" value="发布主题"/>
                    </shiro:hasPermission>
			</form>

</body>

</html>
