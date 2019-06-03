<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8"> <title>Booking Page</title>
<script type="text/javascript">
  function loadRequest()
  {
    var departureStationId = ${pageContext.request.getParameter("departureStation")};
    var destinationStationId = ${pageContext.request.getParameter("destinationStation")};
    document.getElementById('departureStationSelectItem').value = departureStationId;
        document.getElementById('departureHidden').value = departureStationId;
    document.getElementById('destinationStationSelectItem').value = destinationStationId;
    document.getElementById('destinationHidden').value = destinationStationId;
  }
  function onDestinationChange(sel)
  {
    document.getElementById('destinationHidden').value = sel.value;
  }
  function onDepartureChange(sel)
  {
    document.getElementById('departureHidden').value = sel.value;
  }
  function onDateChange(sel)
  {
    document.getElementById('dateHidden').value = sel.value;
  }
</script>
</head>
<body onload="loadRequest()">
    <form action="findTrain" method="get">
        <fmt:message key="home.booking.departure"/>
        <select onchange="onDepartureChange(this);" id="departureStationSelectItem" name="departureStation">
            <c:forEach items="${stations}" var="station">
                <option value="${station.getId()}"><c:out value="${station.getName()}" /></option>
            </c:forEach>
        </select>
        ===>
        <fmt:message key="home.booking.destination"/>
        <select onchange="onDestinationChange(this);" id="destinationStationSelectItem" name="destinationStation">
            <c:forEach items="${stations}" var="station">
                <option value="${station.getId()}"><c:out value="${station.getName()}" /></option>
            </c:forEach>
        </select>
        <br />
        <fmt:message key="home.booking.date"/>: <input id="departureDateItem" type="date" name="departureDate"
            required="required" value="${pageContext.request.getParameter("departureDate")}"
            onchange="onDateChange(this);"/>

        <input type="submit" value=<fmt:message key="home.booking"/> />
     </form>

     <button type="button" onclick="window.location.href='home'" >
         <fmt:message key="booking.ticket.button.return"/>
     </button>

     <h1><fmt:message key="booking.message" /></h1>
     <form action="trainDetail" method="get">

     <input id="departureHidden" name="departureStationHidden" type="hidden"/>
     <input id="destinationHidden" name="destinationStationHidden" type="hidden"/>
     <input id="dateHidden" name="departureDateHidden" type="hidden"
        value="${pageContext.request.getParameter("departureDate")}"/>
     <input name="currentCoach" type="hidden"
        value="0"/>

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
                     <c:forEach items="${trainInformation.getCoachTypePlacesInfoDtoList()}" var="coachType">
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