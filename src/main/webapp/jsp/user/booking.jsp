<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <title>Booking Page</title>
    <c:import url="/resources/components/links.html"/>
    <script type="text/javascript" src="${contextPath}/ticket-office/resources/js/booking.js"></script>
    <script type="text/javascript" src="${contextPath}/ticket-office/resources/js/current-date.js"></script>
    <script type="text/javascript">
      function loadRequest()
      {
        var departureStationId = ${pageContext.request.getParameter("departureStation")};
        var destinationStationId = ${pageContext.request.getParameter("destinationStation")};
        document.getElementById('departureStationSelectItem').value = departureStationId;
        onDepartureChange(document.getElementById('departureStationSelectItem'));

        document.getElementById('destinationStationSelectItem').value = destinationStationId;
        onDestinationChange(document.getElementById('destinationStationSelectItem'));

        document.getElementById("departureDateItem").min = getCurrentDate(0);
        document.getElementById("departureDateItem").max = getCurrentDate(3);
      }
    </script>
</head>

<body onload="loadRequest()">
<div class="site-wrap">

    <c:import url="/resources/components/header.html"/>

<!-- header -->

    <div class="site-blocks-cover inner-page-cover overlay" style="background-image: url(/ticket-office/resources/images/backgroung.png);" data-stellar-background-ratio="0.5">
      <div class="container">
        <div class="row align-items-center justify-content-center text-center">

          <div class="col index-booking-form">
            <div class="form-search-wrap p-2" style="overflow-x: auto;">
                <form action="findTrain" method="get" style="min-width: 900px;">
                <div class="row align-items-center">

                    <div class="p-3 no-sm-border border-right" style="width: 23%;">     
                        <div class="select-wrap">     
                            <span class="icon-map-marker"></span>          
                            <span class="icon"><span class="icon-angle-down"></span></span>
                            <select class="form-control" onchange="onDepartureChange(this);" id="departureStationSelectItem" name="departureStation">
                                <c:forEach items="${stations}" var="station">
                                    <option value="${station.getId()}"><c:out value="${station.getName()}" /></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="p-3 no-sm-border" style="width: 4%;">
                        <a href="javascript:void(0);" onclick="swapStation()">
                            <span class="icon-exchange"></span>
                        </a>
                    </div> 

                    <div class="p-3 no-sm-border border-left border-right" style="width: 23%;">     
                        <div class="select-wrap">               
                            <span class="icon-map-marker"></span>
                            <span class="icon"><span class="icon-angle-down"></span></span>
                                <select class="form-control" onchange="onDestinationChange(this);" id="destinationStationSelectItem" name="destinationStation">
                                    <c:forEach items="${stations}" var="station">
                                        <option value="${station.getId()}"><c:out value="${station.getName()}" /></option>
                                    </c:forEach>
                                </select>
                        </div>
                    </div>

                    <div class="w-25 p-3 no-sm-border border-right">     
                        <div class="icon-wrap">
                            <span class="icon icon-calendar"></span>                      
                            <input id="departureDateItem" class="form-control" type="date" name="departureDate"
                                required="required" value="${pageContext.request.getParameter('departureDate')}"
                                onchange="onDateChange(this);"/>
                        </div>
                    </div>

                    <div class="w-25 p-3 no-sm-border">     
                        <input type="submit" class="btn btn-primary" value='<fmt:message key="user.home.booking.button"/>' />
                    </div>
                 </div>
                 </form>             
            </div>            
          </div>
        </div>
      </div>
    </div>  

<!-- main section -->

    <div class="site-section">
        <div class="container">
            <div class="row">
                <div class="col">

          <!-- train list section -->

                    <h2 class="mb-5 text-primary">
                      <fmt:message key="booking.message" />
                    </h2>

                    <form action="trainDetail" method="get">

                        <input id="departureHidden" name="departureStationHidden" type="hidden"/>
                        <input id="destinationHidden" name="destinationStationHidden" type="hidden"/>
                        <input id="dateHidden" name="departureDateHidden" type="hidden" value="${pageContext.request.getParameter('departureDate')}"/>

                        <div class="limiter">
                        <div class="wrap-table100">
                        <div class="table100 ver4 m-b-110">
                            <table data-vertable="ver4">
                                <thead>
                                    <tr class="row100 head">
                                        <th class="column100 column1" data-column="column1"><fmt:message key="booking.button"/></th>
                                        <th class="column100 column2" data-column="column2"><fmt:message key="booking.direction"/></th>
                                        <th class="column100 column3" data-column="column3"> </th>
                                        <th class="column100 column4" data-column="column4"><fmt:message key="booking.departure"/></th>
                                        <th class="column100 column5" data-column="column5"><fmt:message key="booking.arrival"/></th>
                                        <th class="column100 column6" data-column="column6"><fmt:message key="booking.duration"/></th>
                                        <th class="column100 column7" data-column="column7"><fmt:message key="booking.seats"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${trainsInformation}" var="trainInformation">
                                    <tr  class="row100">
                                        <td class="column100 column1" data-column="column1">
                                            <input type="submit" class="btn btn-primary" name="selectedTrainId" value="${trainInformation.getTrain().getId()}">
                                        </td>
    
                                        <td class="column100 column2" data-column="column2">
                                            <c:out value="${trainInformation.getFirstRootStation().getName()}"/>
                                        </td>
                                        <td class="column100 column3" data-column="column3">
                                            <c:out value="${trainInformation.getLastRootStation().getName()}"/>
                                        </td>
    
                                        <td class="column100 column4" data-column="column4">
                                            <c:out value="${trainInformation.getDepartureTime()}"/>
                                        </td>
                                        <td class="column100 column5" data-column="column5">
                                            <c:out value="${trainInformation.getArrivalTime()}"/>
                                        </td>
    
                                        <td class="column100 column6" data-column="column6">
                                            <c:out value="${trainInformation.getDuration()}"/>
                                        </td>
                                        <td class="column100 column7" data-column="column7">
                                            <table>
                                                <c:forEach items="${trainInformation.getCoachTypePlacesInfoDtoList()}" var="coachType">
                                                <tr>
                                                    <td><c:out value="${coachType.getCoachType().getName()}"/>
                                                     (<c:out value="${coachType.getQuantity()}"/>): </td>
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
                                </tbody>
                            </table>
                        </div>
                        </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
