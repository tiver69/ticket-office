<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>
<c:set var="saveRequest" value="selectedTrainId=${pageContext.request.getParameter('selectedTrainId')}&departureStationHidden=${pageContext.request.getParameter('departureStationHidden')}&destinationStationHidden=${pageContext.request.getParameter('destinationStationHidden')}&departureDateHidden=${pageContext.request.getParameter('departureDateHidden')}" />

<html>
<head>
    <title>Booking Page</title>
    <c:import url="/resources/components/links.html"/>
</head>

<body">
<div class="site-wrap">

    <c:import url="/resources/components/header.html"/>

<!-- header -->

    <div class="site-blocks-cover inner-page-cover overlay" style="background-image: url(/ticket-office/resources/images/backgroung.png);" data-stellar-background-ratio="0.5">
      <div class="container">
        <div class="row align-items-center justify-content-center text-center">

          <div class="col index-home-message table">
            <div class="row justify-content-center">
              <div class="col text-center">
                <div class="chosen-train-table-wraper">
                <table data-vertable="ver4">
                    <thead>
                        <tr class="row100 head">
                            <th><fmt:message key="booking.train.message" /></th>
                            <th><fmt:message key="booking.th2"/></th>
                            <th> </th>
                            <th><fmt:message key="booking.th4"/></th>
                            <th><fmt:message key="booking.th5"/></th>
                            <th><fmt:message key="booking.th6"/></th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr class="row100">
                            <td>
                            #<c:out value="${trainInformation.getTrain().getId()}"/>
                            </td>

                            <td><c:out value="${trainInformation.getFirstRootStation().getName()}"/></td>
                            <td><c:out value="${trainInformation.getLastRootStation().getName()}"/></td>

                            <td><c:out value="${trainInformation.getDepartureTime()}"/></td>
                            <td><c:out value="${trainInformation.getArrivalTime()}"/></td>

                            <td><c:out value="${trainInformation.getDuration()}"/></td>
                        </tr>
                    </tbody>
                </table> 
                </div>       
                <button type="button" class="btn btn-primary" style="float: right;" onclick="window.location.href='findTrain?departureStation=${pageContext.request.getParameter('departureStationHidden')}&destinationStation=${pageContext.request.getParameter('destinationStationHidden')}&departureDate=${pageContext.request.getParameter('departureDateHidden')}'" >
                    <fmt:message key="booking.ticket.button.return"/>
                </button>
            </div>            
          </div>
          </div>
        </div>
      </div>
    </div>

<!-- main section -->

    <div class="container">
    <div class="row">
    <div class="col">

    <!-- coaches list section -->
        <div class="row">
        <div class="col-lg-7 mt-5 text-center">
            <ul class="custom-pagination">
                <c:forEach begin="1" end="${pages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li>
                                <span>${i}</span>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="trainDetail?currentPage=${i}&${saveRequest}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>
        <div class="col-lg-4 mt-5 text-left" style="font-size: x-large;">
                    <c:set var="trainCoach" value="${trainInformation.getTrainCoachPlacesInfoDtoList().get(0)}"/>
                    <c:out value="${trainCoach.getTrainCoach().getCoachType().getName()}"/>
                    <c:out value="${trainCoach.getBookedPlaceList().size()}"/>
                    /
                    <c:out value="${trainCoach.getTrainCoach().getCoachType().getPlaces()}"/>
        </div>
        </div>

        <hr/>
        <!-- coache places section -->

        <c:set var="placePerLine" value="${trainCoach.getTrainCoach().getCoachType().getPlaces() / 4}"/>
        <div class="wagon-floors">
            <div class="floor floor-1">
                <div class="row">
                    <c:forEach begin="1" end="${placePerLine}" var="i">
                    <c:choose>
                         <c:when test="${trainCoach.getBookedPlaceList().contains(i)}">
                            <div class="col place disable"><c:out value="${i}"/></div>
                         </c:when>
                         <c:otherwise>
                                <a class="col place" href="ticketDetail?${pageContext.request.getQueryString()}&selectedPlace=${i}&selectedCoach=${trainCoach.getTrainCoach().getId()}"><c:out value="${i}"/></a>
                         </c:otherwise>
                     </c:choose>
                    </c:forEach>
                </div>
                <div class="row">
                    <c:forEach begin="${placePerLine+1}" end="${placePerLine*2}" var="i">
                    <c:choose>
                         <c:when test="${trainCoach.getBookedPlaceList().contains(i)}">
                            <div class="col place disable"><c:out value="${i}"/></div>
                         </c:when>
                         <c:otherwise>
                                <a class="col place" href="ticketDetail?${pageContext.request.getQueryString()}&selectedPlace=${i}&selectedCoach=${trainCoach.getTrainCoach().getId()}"><c:out value="${i}"/></a>
                         </c:otherwise>
                     </c:choose>
                    </c:forEach>
                </div>
                <div class="row">                
                    <div class="col">.</div>
                </div>
                <div class="row">
                    <c:forEach begin="${placePerLine*2+1}" end="${placePerLine*3}" var="i">
                    <c:choose>
                         <c:when test="${trainCoach.getBookedPlaceList().contains(i)}">
                            <div class="col place disable"><c:out value="${i}"/></div>
                         </c:when>
                         <c:otherwise>
                                <a class="col place" href="ticketDetail?${pageContext.request.getQueryString()}&selectedPlace=${i}&selectedCoach=${trainCoach.getTrainCoach().getId()}"><c:out value="${i}"/></a>
                         </c:otherwise>
                     </c:choose>
                    </c:forEach>
                </div>
                <div class="row">
                    <c:forEach begin="${placePerLine*3+1}" end="${trainCoach.getTrainCoach().getCoachType().getPlaces()}" var="i">
                    <c:choose>
                         <c:when test="${trainCoach.getBookedPlaceList().contains(i)}">
                            <div class="col place disable"><c:out value="${i}"/></div>
                         </c:when>
                         <c:otherwise>
                                <a class="col place" href="ticketDetail?${pageContext.request.getQueryString()}&selectedPlace=${i}&selectedCoach=${trainCoach.getTrainCoach().getId()}"><c:out value="${i}"/></a>
                         </c:otherwise>
                     </c:choose>
                    </c:forEach>
                </div>
            </div>
        </div>

    </div>
    </div>
    </div>
</div>
</body>
</html>


