<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 2019/11/8
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
</head>
<body style="align-content: center">
<form action="${path}/user/login" method="post">
    用户：<input type="text" name="name"/><br>
    密码：<input type="password" name="password"><br>
          <input type="submit" value="登录">
</form>

</body>
</html>
