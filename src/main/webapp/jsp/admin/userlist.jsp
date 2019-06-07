<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <c:import url="/resources/components/links.html"/>
    <title>Home Page</title>
    <script type="text/javascript">
        function confirmPromote(form) {
            return confirm("<fmt:message key='admin.userlist.promote.confirm'/>");
        }
        function confirmUpdate(form) {
            return confirm("<fmt:message key='admin.userlist.update.confirm'/>");
        }
        function setCurrentUpdate(currentUpdatedId) {
            var id =  "currentUpdate"+currentUpdatedId;
            document.getElementById(id).style.display='inherit';
            document.getElementById("currentUpdateInfo"+currentUpdatedId).style.display='none';
        }
        function unsetCurrentUpdate(currentUpdatedId) {
            var id =  "currentUpdate"+currentUpdatedId;
            document.getElementById(id).style.display='none';
            document.getElementById("currentUpdateInfo"+currentUpdatedId).style.display='inherit';
        }
        function confirmDelete(form) {
            return confirm("<fmt:message key='admin.userlist.remove.confirm'/>");
        }
        function loadMenuColor() {
        document.getElementById("menu-admin").classList.add("active");
      }
    </script>
</head>
<body onload="loadMenuColor()">
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
                    <fmt:message key="admin.userlist.message" />
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

          <!-- user list section -->

          <div class="col">

            <c:forEach items="${passengerList}" var="passenger">
            <!-- user item start -->
            <div id="currentUpdateInfo${passenger.getPassenger().getId()}" style="display: inherit;">
            <div class="d-block d-md-flex listing-horizontal">

             <c:choose>
                  <c:when test="${!passenger.getRoles().contains('ADMIN')}">
                    <div class="img d-block" style="background-image: url('/ticket-office/resources/images/user-display-background.png'); background-position: center;">
                      <span class="category">
                        USER
                      </span>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <div class="img d-block" style="background-image: url('/ticket-office/resources/images/user-display-background.png'); background-position: center;">
                      <span class="category" style="background-color: #531aec;">
                        ADMIN
                      </span>
                    </div>
                  </c:otherwise>
             </c:choose>

              <div class="lh-content">
                       <a href="javascript:void(0);" class="bookmark user update" 
                            onclick="setCurrentUpdate(${passenger.getPassenger().getId()})">
                            <span class="icon-cog">
                                <fmt:message key="booking.ticket.edit.button"/>
                            </span>
                        </a>
                 <c:choose>
                      <c:when test="${!passenger.getRoles().contains('ADMIN')}">
                        <a href="promoteToAdmin?passengerId=${passenger.getPassenger().getId()}" class="bookmark user promote" 
                            onclick="return confirmPromote(this);" method="post" >
                            <span class="icon-star">
                                <fmt:message key="admin.userlist.promote.button"/>
                            </span>
                        </a>
                        <a href="removePassenger?passengerId=${passenger.getPassenger().getId()}" class="bookmark user remove" 
                            onclick="return confirmDelete(this);" method="delete" >
                            <span class="icon-remove">
                                <fmt:message key="admin.userlist.remove.button"/>
                            </span>
                        </a>
                      </c:when>
                 </c:choose>

                <h3>
                    <c:out value="${passenger.getPassenger().getFirstName()}"/> 
                    <c:out value="${passenger.getPassenger().getLastName()}"/>
                </h3>
                
                <p>
                    <span class="icon-user"></span>                  
                     <c:out value="${passenger.getPassenger().getLogin()}"/>
                </p>
                
                <p>
                    <span class="icon-clock-o"></span>                   
                    <fmt:message key="admin.userlist.totalticket"/> - <c:out value="${passenger.getTotalTicket()}"/>
                </p>
                
                <p>
                    <span class="icon-clock-o"></span>
                    <fmt:message key="admin.userlist.lastactive"/> - <c:out value="${passenger.getLastActive()}"/>
                </p>                

              </div>
            </div>
            </div>
            <!-- user item end -->

            <!-- user UPDATE item start -->
            <div id="currentUpdate${passenger.getPassenger().getId()}" style="display: none; background: #e6e6e6;">
            <div class="d-block d-md-flex listing-horizontal">

             <c:choose>
                  <c:when test="${!passenger.getRoles().contains('ADMIN')}">
                    <div class="img d-block" style="background-image: url('/ticket-office/resources/images/user-update-background.png'); background-position: center;">
                      <span class="category">
                        USER
                      </span>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <div class="img d-block" style="background-image: url('/ticket-office/resources/images/user-update-background.png'); background-position: center;">
                      <span class="category" style="background-color: #531aec;">
                        ADMIN
                      </span>
                    </div>
                  </c:otherwise>
             </c:choose>

              <div class="lh-content">
                <a href="javascript:void(0);" class="bookmark user" style="width: 150px;"
                            onclick="unsetCurrentUpdate(${passenger.getPassenger().getId()})" method="delete" >
                    <span class="icon-refresh">
                        <fmt:message key="admin.userlist.cancel.button"/>
                    </span>
                </a>

                <form action="updatePassenger" method="put" onsubmit="return confirmUpdate(this);">
                    <input class="btn bookmark update user" type="submit"
                    value='<fmt:message key="admin.userlist.update.button"/>' />
                    <input type="hidden" name="passengerId" value="${passenger.getPassenger().getId()}" />

                <h3>
                    <input type="text" class="user-list update form h3" name="passengerFirstName" value="${passenger.getPassenger().getFirstName()}" />
                    <input type="text" class="user-list update form h3" name="passengerLastName" value="${passenger.getPassenger().getLastName()}" />
                </h3>
                
                <p>
                    <span class="icon-user"></span>                  
                    <input type="text" class="user-list update form" name="passengerLogin" value="${passenger.getPassenger().getLogin()}" />
                </p>
                </form>
                
                <p>
                    <span class="icon-clock-o"></span>                   
                    <fmt:message key="admin.userlist.totalticket"/> - <c:out value="${passenger.getTotalTicket()}"/>
                </p>
                
                <p>
                    <span class="icon-clock-o"></span>
                    <fmt:message key="admin.userlist.lastactive"/> - <c:out value="${passenger.getLastActive()}"/>
                </p>                

              </div>
            </div>
            </div>
            <!-- user UPDATE item end -->


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
                                <a href="users?currentPage=${i}" class="color-black-opacity-5">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
              </ul>
            </div>
          </div>

        </div>
      </div>
    </div> 
</div>
</body>
</html>