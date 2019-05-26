<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8"> <title>Home Page</title>
</head>
<body>
     <h1><fmt:message key="home.greeting" /></h1>
     <button type="button" onclick="location.href='${pageContext.request.contextPath}/page/registration'" >
         <fmt:message key="home.registration"/>
     </button>
</body>
</html>