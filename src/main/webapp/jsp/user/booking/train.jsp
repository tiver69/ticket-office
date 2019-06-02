<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8"> <title>Booking Page</title>
</head>
<script type="text/javascript">
  function loadRequest()
  {
    var currentCoach = ${pageContext.request.getParameter("currentCoach")};
    if (currentCoach == 0) {
        window.location.href = "trainDetail?currentCoach=1&selectedTrainId=${pageContext.request.getParameter("selectedTrainId")}&departureStationHidden=${pageContext.request.getParameter("departureStationHidden")}&destinationStationHidden=${pageContext.request.getParameter("destinationStationHidden")}&departureDateHidden=${pageContext.request.getParameter("departureDateHidden")}";
    }
  }
</script>
<body onload="loadRequest()">
    <h1><fmt:message key="booking.train.message" /></h1>

    <button type="button" onclick="window.location.href='findTrain?departureStation=${pageContext.request.getParameter("departureStationHidden")}&destinationStation=${pageContext.request.getParameter("destinationStationHidden")}&departureDate=${pageContext.request.getParameter("departureDateHidden")}'" >
        <fmt:message key="booking.ticket.button.return"/>
    </button>

    <table>
             <tr>
                 <th><fmt:message key="booking.button"/></th>
                 <th><fmt:message key="booking.th2"/></th>
                 <th> </th>
                 <th><fmt:message key="booking.th4"/></th>
                 <th><fmt:message key="booking.th5"/></th>
                 <th><fmt:message key="booking.th6"/></th>
             </tr>

              <tr>
                <td>
                <c:out value="${trainInformation.getTrain().getId()}"/>
                </td>

                <td><c:out value="${trainInformation.getFirstRootStation().getName()}"/></td>
                <td><c:out value="${trainInformation.getLastRootStation().getName()}"/></td>

                <td><c:out value="${trainInformation.getDepartureTime()}"/></td>
                <td><c:out value="${trainInformation.getArrivalTime()}"/></td>

                <td><c:out value="${trainInformation.getDuration()}"/></td>
              </tr>
         </table>

         <c:forEach begin="1" end="6" var="i">
             <c:choose>
                 <c:when test="${currentCoach eq i}">
                     <li>
                        <a>${i} <span>(current)</span></a>
                     </li>
                 </c:when>
                 <c:otherwise>
                     <li>
                     <a href="trainDetail?currentCoach=${i}&${pageContext.request.getQueryString()}">${i}</a>
                     </li>
                 </c:otherwise>
             </c:choose>
         </c:forEach>

         <table>
            <c:forEach items="${trainInformation.getTrainCoachPlacesInfoDtoList()}" var="trainCoach">
            <tr>
                <td><c:out value="${trainCoach.getTrainCoach().getNumber()}"/></td>
                <td><c:out value="${trainCoach.getTrainCoach().getCoachType().getName()}"/></td>
                <td>
                <c:out value="${trainCoach.getBookedPlaceList().size()}"/>
                /
                <c:out value="${trainCoach.getTrainCoach().getCoachType().getPlaces()}"/>
                </td>
            </tr>
            <tr>
            <td>
            <c:forEach begin="1" end="${trainCoach.getTrainCoach().getCoachType().getPlaces()}" var="i">
            <c:choose>
                 <c:when test="${trainCoach.getBookedPlaceList().contains(i)}">
                     <div>
                        <c:out value="${i}"/>(nope)
                     </div>
                 </c:when>
                 <c:otherwise>
                    <div>
                        <a href="ticketDetail?${pageContext.request.getQueryString()}&selectedPlace=${i}&selectedCoach=${trainCoach.getTrainCoach().getId()}"><c:out value="${i}"/></a>
                    </div>
                 </c:otherwise>
             </c:choose>
            </c:forEach>
            </td>
            </tr>
            </c:forEach>
         </table>
</body>
</html>