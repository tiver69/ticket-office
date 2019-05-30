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
     <form action="trainDetail" method="post">
     <table>
         <tr>
             <th><fmt:message key="booking.button"/></th>
             <th><fmt:message key="booking.th2"/></th>
             <th> </th>
             <th><fmt:message key="booking.th4"/></th>
             <th><fmt:message key="booking.th5"/></th>
             <th><fmt:message key="booking.th6"/></th>
             <th><fmt:message key="booking.th7"/></th>
         </tr>
         <c:forEach items="${trainsInformation}" var="trainInformation">
          <tr>
            <td>
            <input type="submit" name="selectedTrainId" value="${trainInformation.getTrain().getId()}">
            </td>

            <td><c:out value="${trainInformation.getFirstRootStation().getName()}"/></td>
            <td><c:out value="${trainInformation.getLastRootStation().getName()}"/></td>

            <td><c:out value="${trainInformation.getDepartureTime()}"/></td>
            <td><c:out value="${trainInformation.getArrivalTime()}"/></td>

            <td><c:out value="${trainInformation.getDuration()}"/></td>
            <td>
                 <table>
                     <c:forEach items="${trainInformation.getCoachPlacesInfo()}" var="coachType">
                     <tr>
                         <td><c:out value="${coachType.getCoachType().getName()}"/></td>
                         <td> (<c:out value="${coachType.getQuantity()}"/>): </td>
                         <td>
                         <c:out value="${coachType.getAvailablePlaces()}"/>
                         /
                         <c:out value="${coachType.getTotalPlaces()}"/>
                         </td>
                     </tr>
                     </c:forEach>
                  </table>
            </td>
          </tr>
         </c:forEach>
     </table>
     </form>
</body>
</html>