
<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 2019/11/8
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="path"/>
<html>
<head>
    <title>Title</title>
</head>
<body>

<shiro:notAuthenticated>
    您好，请
    <a href="${path}/user/login.jsp">登录</a>
    ，登录之后浏览更多精彩内容
</shiro:notAuthenticated>


<shiro:authenticated>
    您好：<shiro:principal/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="${path}/user/logout"> 安全退出</a>
    <ul>
        <shiro:hasRole name="admin">
            <li>用户管理</li>
            <li>书籍管理</li>
            <li>视频管理</li>
            <li>评论管理</li>

           <%--  name为数据库存的值可以写成当个词 :如select 最好用户加权限：如user:select
           <shiro:hasPermission name="select"><a href="#">查</a></shiro:hasPermission>--%>
            <shiro:hasPermission name="user:select"><a href="#">查</a></shiro:hasPermission>
            <shiro:hasPermission name="user:add"><a href="#">增</a></shiro:hasPermission>
            <shiro:hasPermission name="user:delete"><a href="#">删</a></shiro:hasPermission>
            <shiro:hasPermission name="user:update"><a href="#">改</a></shiro:hasPermission>
        </shiro:hasRole>
        <shiro:hasRole name="super">
             <li>管理管理员</li>
        </shiro:hasRole>
    </ul>
</shiro:authenticated>
</body>
</html>
