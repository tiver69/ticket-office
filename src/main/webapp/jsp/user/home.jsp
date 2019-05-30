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
     <h1><fmt:message key="home.greeting" />, <c:out value="${user.getRoles()}"/>
     <c:out value="${user.getPassenger().getId()}"/></h1>
     <button type="button" onclick="location.href='${pageContext.request.contextPath}/page/user/logout'" >
         <fmt:message key="user.home.logout"/>
     </button>

     <form action="findTrain" method="post">
              <fmt:message key="home.booking.departure"/>
              <select name="departureStation">
                  <c:forEach items="${stations}" var="station">
                      <option value="${station.getId()}"><c:out value="${station.getName()}" /></option>
                  </c:forEach>
              </select>
              ===>
              <fmt:message key="home.booking.destination"/>
              <select name="destinationStation">
                  <c:forEach items="${stations}" var="station">
                      <option value="${station.getId()}"><c:out value="${station.getName()}" /></option>
                  </c:forEach>
              </select>
              <br />
              <fmt:message key="home.booking.date"/>: <input type="date" name="departureDate" required="required"></input>

             <input type="submit" value=<fmt:message key="home.booking"/> />
          </form>
</body>
</html>