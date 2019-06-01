<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; utf-8">
    <title>Login</title>
</head>
<body>
    <div align="center">
    <form action="signin" method="get">
    <fmt:message key="registration.username"/>: <input type="text" name="login" required="required"></input>
    <fmt:message key="registration.password"/>: <input type="password" name="password" required="required"></input>
    <br/>
    <input type="submit" value=<fmt:message key="login.button"/> />
    </form>
    </div>
</body>
</html>