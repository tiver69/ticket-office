<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8"> <title>Booking Page</title>
</head>
<body>
     <h1><fmt:message key="booking.message" /></h1>
     <table>
            <c:forEach items="${trainsInformation}" var="trainInformation">
             <tr>
               <td><c:out value="${trainInformation.getTrain().getId()}"/></td>

               <td><c:out value="${trainInformation.getFirstRootStation().getName()}"/></td>
               <td><c:out value="${trainInformation.getLastRootStation().getName()}"/></td>

               <td><c:out value="${trainInformation.getDepartureTime()}"/></td>
               <td><c:out value="${trainInformation.getArrivalTime()}"/></td>

               <td><c:out value="${trainInformation.getDuration()}"/></td>
             </tr>
            </c:forEach>
     </table>
</body>
</html>