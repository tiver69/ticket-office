<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8"> <title>Ticket</title>
</head>
<body>
     <h1><fmt:message key="booking.ticket.message" /></h1>
    <fmt:message key="registration.first.name"/> = <c:out value="${ticket.getTicket().getPassenger().getFirstName()}"/><br/>
    <fmt:message key="registration.last.name"/> = <c:out value="${ticket.getTicket().getPassenger().getLastName()}"/><br/>
    <fmt:message key="ticket.coach"/> - ${pageContext.request.getParameter("currentCoach")}<br/>
    <fmt:message key="ticket.place"/> - ${pageContext.request.getParameter("selectedPlace")}<br/>
    <fmt:message key="ticket.price"/> - <c:out value="${ticket.getTicket().getPrice()}"/><br/>
    <fmt:message key="home.booking.departure"/> - <c:out value="${ticket.getTicket().getDepartureStation().getName()}"/><br/>
    <fmt:message key="home.booking.destination"/> - <c:out value="${ticket.getTicket().getDestinationStation().getName()}"/><br/>
    <fmt:message key="ticket.train"/> - <c:out value="${ticket.getTicket().getTrainCoach().getTrain().getId()}"/><br/>
    <fmt:message key="booking.th4"/> - <c:out value="${ticket.getDepartureDateTime()}"/><br/>
    <fmt:message key="booking.th5"/> - <c:out value="${ticket.getArrivalDateTime()}"/><br/>

     <button type="button" onclick="history.go(-1)" >
         <fmt:message key="booking.ticket.button.return"/>
     </button>
    <form action="saveTicket?${pageContext.request.getQueryString()}&price=${ticket.getTicket().getPrice()}" method="post">
        <input type="submit" value=<fmt:message key="booking.ticket.button"/> />
    </form>
</body>
</html>