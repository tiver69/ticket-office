<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8"> <title>Home Page</title>
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
</script>
</head>
<body>
     <h1><fmt:message key="admin.userlist.message" />
     <button type="button" onclick="window.location.href='/ticket-office/page/user/home'" >
         <fmt:message key="booking.ticket.button.return"/>
     </button>
     </h1>

    <c:forEach items="${passengerList}" var="passenger">
    <div>
        <div id="currentUpdateInfo${passenger.getPassenger().getId()}" style="display: inherit;">
            <fmt:message key="registration.first.name"/> - <c:out value="${passenger.getPassenger().getFirstName()}"/><br/>
            <fmt:message key="registration.last.name"/> - <c:out value="${passenger.getPassenger().getLastName()}"/><br/>
            <fmt:message key="registration.username"/> - <c:out value="${passenger.getPassenger().getLogin()}"/><br/>
            <fmt:message key="admin.userlist.totalticket"/> - <c:out value="${passenger.getTotalTicket()}"/><br/>
            <fmt:message key="admin.userlist.lastactive"/> - <c:out value="${passenger.getLastActive()}"/><br/>
            <fmt:message key="admin.userlist.lastactive"/> - <c:out value="${passenger.getRoles()}"/><br/>
            <button type="button" onclick="setCurrentUpdate(${passenger.getPassenger().getId()})" >
                <fmt:message key="booking.ticket.edit.button"/>
            </button>
        </div>

        <div id="currentUpdate${passenger.getPassenger().getId()}" style="display: none;">
            <form action="updatePassenger" method="put" onsubmit="return confirmUpdate(this);">
                <input type="hidden" name="passengerId" value="${passenger.getPassenger().getId()}" />
                <fmt:message key="registration.first.name"/> - <input type="text" name="passengerFirstName" value="${passenger.getPassenger().getFirstName()}" /><br/>
                <fmt:message key="registration.last.name"/> - <input type="text" name="passengerLastName" value="${passenger.getPassenger().getLastName()}" /><br/>
                <fmt:message key="registration.username"/> -  <input type="text" name="passengerLogin" value="${passenger.getPassenger().getLogin()}" /><br/>
                <fmt:message key="admin.userlist.totalticket"/> - <c:out value="${passenger.getTotalTicket()}"/><br/>
                <fmt:message key="admin.userlist.lastactive"/> - <c:out value="${passenger.getLastActive()}"/><br/>
                <fmt:message key="admin.userlist.lastactive"/> - <c:out value="${passenger.getRoles()}"/><br/>
                <input type="submit" value=<fmt:message key="admin.userlist.update.button"/> />
                <button type="button" onclick="unsetCurrentUpdate(${passenger.getPassenger().getId()})" >
                    <fmt:message key="admin.userlist.cancel.button"/>
                </button>
            </form>
        </div>

         <c:choose>
              <c:when test="${!passenger.getRoles().contains('ADMIN')}">
                 <form action="removePassenger" method="delete" onsubmit="return confirmDelete(this);">
                     <input type="hidden" name="passengerId" value="${passenger.getPassenger().getId()}" />
                     <input type="submit" value=<fmt:message key="admin.userlist.remove.button"/> />
                 </form>
                 <form action="promoteToAdmin" method="post" onsubmit="return confirmPromote(this);">
                     <input type="hidden" name="passengerId" value="${passenger.getPassenger().getId()}" />
                     <input type="submit" value=<fmt:message key="admin.userlist.promote.button"/> />
                 </form>
              </c:when>
         </c:choose><br/><br/>
     </div>
     </c:forEach>
</body>
</html>