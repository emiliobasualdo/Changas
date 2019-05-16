<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <style><%@include file="/WEB-INF/css/navigationBar.css"%></style>
    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-light bg-dark shadow fixed-top navbar-custom">
            <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
                <ul class="navbar-nav mr-auto">
                    <c:choose>
                        <c:when test="${isUserLogged == false}">
                            <li class="nav-item">
                                <c:url value="/signup" var="signUpUrl" />
                                <a class="nav-link" href="${signUpUrl}"><spring:message code="navigationBar.btn.signUp"/></a>
                            </li>
                            <li class="nav-item">
                                <c:url value="/login" var="logInUrl" />
                                <a class="nav-link" href="${logInUrl}"><spring:message code="navigationBar.btn.logIn"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <c:url value="/profile" var="profileUrl" />
                                <a class="nav-link" href="${profileUrl}"><spring:message code="navigationBar.btn.profile"/></a>
                            </li>
                            <li class="nav-item">
                                <c:url value="/logout" var="logoutUrl" />
                                <a class="nav-link" href="${logoutUrl}"><spring:message code="navigationBar.btn.logOut"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
            <img src="https://svgsilh.com/svg/1901457.svg" style="width: 1cm; height: 1cm" \>
            <c:url value="/" var="homeUrl" />
            <a class="navbar-brand" href="${homeUrl}" style="margin-left: 3px"><spring:message code="changas.title"/></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
                    aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <c:url value="/create-changa" var="createUrl" />
                        <a class="nav-link" href="${createUrl}"><spring:message code="navigationBar.btn.createChanga"/></a>
                    </li>
                </ul>
            </div>
        </nav>
    </body>
</html>