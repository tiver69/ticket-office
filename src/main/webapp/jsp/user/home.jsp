<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8"> <title>Home Page</title>
<script type="text/javascript">
    function confirmReturn(form) {
        return confirm("<fmt:message key='user.home.message.confirm'/>");
    }
</script>
</head>
<body>
     <h1><fmt:message key="home.greeting" />,
     <c:out value="${user.getPassenger().getFirstName()}"/> <c:out value="${user.getPassenger().getLastName()}"/>
     <c:out value="${user.getRoles()}"/></h1>
     <button type="button" onclick="location.href='${pageContext.request.contextPath}/page/user/logout'" >
         <fmt:message key="user.home.logout"/>
     </button>

     <form action="findTrain" method="get">
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

    <h2><fmt:message key="home.message.ticket" /></h2>

     <li>
        <a href="home?displayType=0"><fmt:message key="user.home.active"/></a>
     </li>
     <li>
        <a href="home?displayType=1"><fmt:message key="user.home.history"/></a>
     </li>

    <c:forEach items="${ticketList}" var="ticket">
    <div>
        <fmt:message key="ticket.coach"/> - <c:out value="${ticket.getTicket().getTrainCoach().getNumber()}"/><br/>
        <fmt:message key="ticket.place"/> - <c:out value="${ticket.getTicket().getPlace()}"/><br/>
        <fmt:message key="ticket.price"/> - <c:out value="${ticket.getTicket().getPrice()}"/><br/>
        <fmt:message key="home.booking.departure"/> - <c:out value="${ticket.getTicket().getDepartureStation().getName()}"/><br/>
        <fmt:message key="home.booking.destination"/> - <c:out value="${ticket.getTicket().getDestinationStation().getName()}"/><br/>
        <fmt:message key="ticket.train"/> - <c:out value="${ticket.getTicket().getTrainCoach().getTrain().getId()}"/><br/>
        <fmt:message key="booking.th4"/> - <c:out value="${ticket.getDepartureDateTime()}"/><br/>
        <fmt:message key="booking.th5"/> - <c:out value="${ticket.getArrivalDateTime()}"/><br/><br/>

         <c:choose>
              <c:when test="${displayType==0}">
                 <form action="returnTicket" method="delete" onsubmit="return confirmReturn(this);">
                     <input type="hidden" name="ticketId" value="${ticket.getTicket().getId()}" />
                     <input type="submit" value=<fmt:message key="user.home.return"/> />
                 </form>
              </c:when>
         </c:choose><br/><br/>
     </div>
     </c:forEach>
</body>
</html>