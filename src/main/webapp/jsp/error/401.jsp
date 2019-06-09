<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <c:import url="/resources/components/links.html"/>
    <title>Forbidden</title>
</head>
<body>
<div class="site-wrap">

	<div class="site-blocks-cover overlay ticket" style="background-image: url());" data-stellar-background-ratio="0.5">
    <div class="container">
    <div class="row align-items-center justify-content-center text-center">
    <div class="col-ticket">
	    <hr/>
	    <h1>
        	<fmt:message key="401.message" />
	    </h1>
	    <hr/>
        <button type="button" class="btn btn-primary float-right" onclick="history.go(-1)">
            <fmt:message key="booking.ticket.button.return" />
        </button>
        <c:choose>
          <c:when test="${not empty user}">
            <button type="button" class="btn btn-primary float-right" onclick="window.location.href='${pageContext.request.contextPath}/page/user/home'"  style="margin-right: 10px;" >
                <fmt:message key="app.header.home"/>
            </button>
          </c:when>
          <c:otherwise>
            <button type="button" class="btn btn-primary float-right" onclick="window.location.href='${pageContext.request.contextPath}/page/home'"  style="margin-right: 10px;">
                <fmt:message key="app.header.home"/>
            </button>
          </c:otherwise>
        </c:choose>
    </div>
    </div>
    </div>
    </div>

</div>
</body>
</html>
