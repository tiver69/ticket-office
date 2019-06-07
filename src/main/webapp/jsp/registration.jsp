<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <title>Registration</title>
    <c:import url="/resources/components/links.html"/>
</head>
<body>
<div class="site-wrap">

    <c:import url="/resources/components/header.html"/>

    <div class="site-blocks-cover overlay" style="background-image: url(/ticket-office/resources/images/backgroung.png);" data-stellar-background-ratio="0.5">
      <div class="container">
        <div class="row align-items-center justify-content-center text-center">
            <div class="col-md-5">
            <div class="form-search-wrap p-2">

                <form action="signup" method="post">
                <div class="row align-items-center">

                <div class="w-50 p-3 no-sm-border border-right"> 
                    <div class="wrap-icon"> 
                      <span class="icon icon-info"></span>
                      <input type="text" class="form-control" placeholder='<fmt:message key="registration.first.name"/>'
                            name="firstName" required="required"></input>
                    </div>
                    </div>
                    
                    <div class="w-50 p-3 no-sm-borde"> 
                    <div class="wrap-icon">
                      <span class="icon icon-info"></span>
                      <input type="text" class="form-control" placeholder='<fmt:message key="registration.last.name"/>'
                            name="lastName" required="required"></input>
                    </div>
                    </div>

                  <div class="w-50 p-3 no-sm-border border-right">     
                  <div class="wrap-icon">               
                    <span class="icon icon-user"></span>
                    <input placeholder='<fmt:message key="registration.username"/>' type="text"  class="form-control"
                        name="login" required="required" />
                    </div>
                  </div>

                  <div class="w-50 p-3 no-sm-border">
                    <div class="wrap-icon">
                      <span class="icon icon-lock"></span>
                      <input type="password" name="password" required="required" class="form-control" placeholder='<fmt:message key="registration.password"/>' />
                    </div>                    
                  </div>

                  <div class="w-100 p-3 ml-auto text-center">
                    <input type="submit" class="btn btn-primary" value='<fmt:message key="registration.button"/>'>
                  </div>
                  
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