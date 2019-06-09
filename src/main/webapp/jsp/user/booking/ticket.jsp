<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <c:import url="/resources/components/links.html"/>
    <title>Ticket</title>
</head>
<body>
<div class="site-wrap">

    <c:import url="/resources/components/header.html"/>
    <hr/>

    <!-- header -->

    <div class="site-blocks-cover overlay ticket" style="background-image: url());" data-stellar-background-ratio="0.5">
    <div class="container">
    <div class="row align-items-center justify-content-center text-center ticket">
    <div class="col-ticket">
        <div class="listing-horizontal d-block d-md-flex form-search-wrap ticket">

            <div class="img d-block" style="background-image: url('/ticket-office/resources/images/active-background.png')">
              <span class="category">
                    <fmt:message key="ticket.coach"/> - <c:out value="${ticket.getTicket().getTrainCoach().getNumber()}"/><br/>
                    <fmt:message key="ticket.place"/> - <c:out value="${ticket.getTicket().getPlace()}"/><br/>
                    <fmt:message key="ticket.price"/> - <c:out value="${ticket.getTicket().getPrice()}"/>₴<br/>
              </span>
            </div>

            <div class="lh-content" style="text-align: left;">
            <h3>
                <c:out value="${ticket.getTicket().getDepartureStation().getName()}"/>
                 -
                <c:out value="${ticket.getTicket().getDestinationStation().getName()}"/>
            </h3>
            
            <p>
              <span class="icon-train"></span>
              <fmt:message key="ticket.train"/> #<c:out value="${ticket.getTicket().getTrainCoach().getTrain().getId()}"/>
            </p>

            <p>
              <span class="icon-clock-o"></span>
              <fmt:message key="booking.departure"/> - <c:out value="${ticket.getDepartureDateTime()}"/>
            </p>

            <p>
              <span class="icon-clock-o"></span>
              <fmt:message key="booking.arrival"/> - <c:out value="${ticket.getArrivalDateTime()}"/>
            </p>

            <h3>
                <c:out value="${ticket.getTicket().getPassenger().getLastName()}"/>
                <c:out value="${ticket.getTicket().getPassenger().getFirstName()}"/>
            </h3>

            <button type="button" class="btn btn-primary" onclick="history.go(-1)" >
                <fmt:message key="booking.ticket.return.button"/>
            </button>

            <button type="button" class="btn btn-primary" method="post" onclick="window.location.href='saveTicket?${pageContext.request.getQueryString()}&price=${ticket.getTicket().getPrice()}'" >
                <fmt:message key="booking.ticket.confirm.button"/>
            </button>
        </div>
    </div>
    </div>
    </div>
    </div>
    </div>

<hr/>
</div>
</body>
</html>
