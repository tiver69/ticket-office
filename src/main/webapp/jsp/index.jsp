<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
  <title>Home Page</title>
  <c:import url="/resources/components/links.html"/>
</head>

<body>
<div class="site-wrap">

    <c:import url="/resources/components/header.html"/>

    <div class="site-blocks-cover overlay" style="background-image: url(/ticket-office/resources/images/backgroung.png);" data-aos="fade" data-stellar-background-ratio="0.5">
      <div class="container">
        <div class="row align-items-center justify-content-center text-center">

          <div class="col index-home-message"> 
            <div class="row justify-content-center mb-4">
              <div class="col-md-8 text-center">
                <h1 data-aos="fade-up">
                  <fmt:message key="home.greeting" />!
                </h1>
                <p>
                  <fmt:message key="home.greeting.message" />
                </p>
              </div>
            </div>

                <button class="btn btn-primary text-white" type="button" onclick="location.href='${pageContext.request.contextPath}/page/login'" >
                  <fmt:message key="home.login"/>
                </button>
                <button class="btn btn-primary text-white" type="button" onclick="location.href='${pageContext.request.contextPath}/page/registration'" >
                  <fmt:message key="home.registration"/>
                </button>
          </div>
        </div>
      </div>
    </div>

</div>
</body>
</html>
