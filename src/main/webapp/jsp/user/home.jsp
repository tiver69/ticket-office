<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
  <title>Home Page</title>
  <c:import url="/resources/components/links.html"/>
  <script type="text/javascript">
      function confirmReturn(form) {
          return confirm("<fmt:message key='user.home.message.confirm'/>");
      }
      function onLoadHomePage(){
        loadMenuColor();
        document.getElementById("destinationStationSelect").value = 1;
        onDestinationChange();
        document.getElementById("departureStationSelect").value = 2;
        onDepartureChange();
        
        document.getElementById("dateInput").min = getCurrentDate();
      }

      function onDestinationChange()
      {       
        var departureOptions = document.getElementById('departureStationSelect').options;
        for (var i=0, iLen=departureOptions.length; i<iLen; i++) {
            var opt = departureOptions[i];
            if (opt.value == document.getElementById('destinationStationSelect').value) {
                opt.hidden = true;
            }
            else {
                opt.hidden = false;
            }
        }
      }

      function onDepartureChange()
      {
        var destinationOptions = document.getElementById('destinationStationSelect').options;
        for (var i=0, iLen=destinationOptions.length; i<iLen; i++) {
            var opt = destinationOptions[i];
            if (opt.value == document.getElementById('departureStationSelect').value) {
                opt.hidden = true;
            }
            else {
                opt.hidden = false;
            }
        }
      }

      function swapStation(){
        var destinationStationId = document.getElementById('destinationStationSelect').value;
        var departureStationId = document.getElementById('departureStationSelect').value;

        document.getElementById('departureStationSelect').value = destinationStationId;
        onDepartureChange(document.getElementById('departureStationSelect'));

        document.getElementById('destinationStationSelect').value = departureStationId;
        onDestinationChange(document.getElementById('destinationStationSelect'));
      }

      function loadMenuColor() {
        var el = document.getElementsByClassName("menu-home");
          for (var i=0, iLen=el.length; i<iLen; i++) {
            el[i].classList.add("active");
        }        
      }

      function getCurrentDate() {
        var today = new Date();
        var month = today.getMonth() + 1;
        if (month < 10) month = "0" + month.toString();
        var day = today.getDate();
        if (day < 10) day = "0" + day.toString();

        return today.getFullYear() + "-" + month + "-"+ day;
      }
  </script>
</head>

<body onload="onLoadHomePage()">
<div class="site-wrap">

    <c:import url="/resources/components/header.html"/>

<!-- header -->

    <div class="site-blocks-cover inner-page-cover overlay" style="background-image: url(/ticket-office/resources/images/backgroung.png);" data-stellar-background-ratio="0.5">
      <div class="container">
        <div class="row align-items-center justify-content-center text-center">

          <div class="col index-home-message user">            
            <div class="row justify-content-center">
              <div class="col text-center">
                <h1>
                <fmt:message key="home.greeting" />,
                 <c:out value="${user.getPassenger().getFirstName()}"/> <c:out value="${user.getPassenger().getLastName()}"/>
               </h1>
              </div>
            </div>            
          </div>
        </div>
      </div>
    </div>  

<!-- main section -->

    <div class="site-section">
      <div class="container">
        <div class="row">

          <!-- ticket list section -->

          <div class="col-lg-8">
            <h2 class="mb-5 text-primary">
              <fmt:message key="home.message.ticket" />
            </h2>


            <c:choose>
                  <c:when test="${displayType==0}">
                    <ul class="ticket-menu">
                      <li>
                        <div class="text-center border-primary">
                          <a href="home?displayType=0" class="color-black-opacity-5"><fmt:message key="user.home.active"/></a>
                        </div>
                      </li>

                      <li>
                        <div class="text-center">
                           <a href="home?displayType=1" class="color-black-opacity-5"><fmt:message key="user.home.history"/></a>
                        </div>
                      </li>
                    </ul>
                  </c:when>
                  <c:otherwise>
                    <ul class="ticket-menu">
                      <li>
                        <div class="text-center">
                          <a href="home?displayType=0" class="color-black-opacity-5"><fmt:message key="user.home.active"/></a>
                        </div>
                      </li>

                      <li>
                        <div class="text-center border-primary">
                           <a href="home?displayType=1" class="color-black-opacity-5"><fmt:message key="user.home.history"/></a>
                        </div>
                      </li>
                    </ul>
                  </c:otherwise>
             </c:choose>

            <c:forEach items="${ticketList}" var="ticket">
            <!-- ticket item start -->
            <div class="d-block d-md-flex listing-horizontal">
             <c:choose>
                  <c:when test="${displayType==0}">
                    <div class="img d-block" style="background-image: url('/ticket-office/resources/images/active-background.png')">
                      <span class="category">
                            <fmt:message key="ticket.coach"/> - <c:out value="${ticket.getTicket().getTrainCoach().getNumber()}"/><br/>
                            <fmt:message key="ticket.place"/> - <c:out value="${ticket.getTicket().getPlace()}"/><br/>
                            <fmt:message key="ticket.price"/> - <c:out value="${ticket.getTicket().getPrice()}"/>₴<br/>
                      </span>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <div class="img d-block" style="background-image: url('/ticket-office/resources/images/history-background.png')">
                      <span class="category">
                            <fmt:message key="ticket.coach"/> - <c:out value="${ticket.getTicket().getTrainCoach().getNumber()}"/><br/>
                            <fmt:message key="ticket.place"/> - <c:out value="${ticket.getTicket().getPlace()}"/><br/>
                            <fmt:message key="ticket.price"/> - <c:out value="${ticket.getTicket().getPrice()}"/>₴<br/>
                      </span>
                    </div>
                  </c:otherwise>
             </c:choose>

              <div class="lh-content">
                <c:choose>
                <c:when test="${displayType==0}">
                  <a href="returnTicket?ticketId=${ticket.getTicket().getId()}" class="bookmark" 
                    onclick="return confirmReturn(this);" method="delete" >
                      <span class="icon-hand-spock-o">
                        <fmt:message key="user.home.return"/>
                      </span>
                  </a>
                </c:when>
                </c:choose>

                <h3>
                    <c:out value="${ticket.getTicket().getDepartureStation().getName()}"/>
                     - 
                    <c:out value="${ticket.getTicket().getDestinationStation().getName()}"/> 
                </h3>
                
                <p>
                  <span class="icon-train"></span>                  
                  <fmt:message key="ticket.train"/> #<c:out value="${ticket.getTicket().getTrainCoach().getTrain().getId()}"/><br/>
                </p>
                
                <p>
                  <span class="icon-clock-o"></span>                   
                  <fmt:message key="booking.th4"/> - <c:out value="${ticket.getDepartureDateTime()}"/>
                </p>
                
                <p>
                  <span class="icon-clock-o"></span>
                  <fmt:message key="booking.th5"/> - <c:out value="${ticket.getArrivalDateTime()}"/><br/><br/>
                </p>                

              </div>
            </div>
            <!-- ticket item end -->
            </c:forEach>

            <!-- pages -->
            <div class="col-12 mt-5 text-center">
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
                                <a href="home?displayType=${displayType}&currentPage=${i}" class="color-black-opacity-5">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
              </ul>
            </div>
          </div>

          <!-- search form section -->

          <div class="col-lg-3 ml-auto">
            <div class="mb-5">
              <h3 class="h5 text-black mb-3">
                <fmt:message key="home.booking.message"/>
                <a href="javascript:void(0);" onclick="swapStation()" style="float: right;">
                  <span class="icon-exchange"></span>
                </a>
              </h3>


              <form action="findTrain" method="get">
                <span class="icon-room"></span>
                <fmt:message key="home.booking.departure"/>
                <div class="form-group">
                  <div class="select-wrap">
                      <span class="icon"><span class="icon-keyboard_arrow_down"></span></span>
                      <select class="form-control" name="departureStation" id="departureStationSelect" onchange="onDepartureChange()">
                        <c:forEach items="${stations}" var="station">
                          <option value="${station.getId()}"><c:out value="${station.getName()}" /></option>
                        </c:forEach>
                      </select>
                    </div>
                </div>

                <span class="icon-room"></span>
                <fmt:message key="home.booking.destination"/>
                <div class="form-group">
                  <div class="select-wrap">
                      <span class="icon"><span class="icon-keyboard_arrow_down"></span></span>
                      <select class="form-control" name="destinationStation" id="destinationStationSelect" onchange="onDestinationChange()">
                        <c:forEach items="${stations}" var="station">
                          <option value="${station.getId()}"><c:out value="${station.getName()}" /></option>
                        </c:forEach>
                      </select>
                    </div>
                </div>

                <div class="form-group">
                  <fmt:message key="home.booking.date"/>: <input type="date" name="departureDate" required="required" class="form-control" id="dateInput" />
                </div>

                <div class="form-group">
                  <input type="submit" value='<fmt:message key="home.booking"/>' class="btn btn-primary"/>
                </div>
              </form>
            </div>
          </div>


        </div>
      </div>
    </div>
</div>
</body>
</html>
