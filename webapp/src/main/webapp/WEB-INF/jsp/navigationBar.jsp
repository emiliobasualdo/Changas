<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
https://mdbootstrap.com/docs/jquery/navigation/navbar/
--%>
<html>
    <head>
        <style><%@include file="/WEB-INF/css/navigationBar.css"%></style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light shadow fixed-top navbar-custom">
            <img src="https://svgsilh.com/svg/1901457.svg" style="width: 1cm; height: 1cm" \>
            <a class="navbar-brand" href="/" style="margin-left: 3px">C H A N G A S</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
                    aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
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
                            <li class="nav-item">
                                <a class="nav-link" href="${logInUrl}"><spring:message code="navigationBar.btn.profile"/></a>
                                <br />
                            </li>
                            <li class="nav-item" style="alignment: right">
                                <c:url value="/create-changa" var="createUrl" />
                                <a class="nav-link" href="${createUrl}"><spring:message code="navigationBar.btn.createChanga"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <c:url value="/profile" var="profileUrl" />
                                <a class="nav-link" href="${profileUrl}"><spring:message code="navigationBar.btn.profile"/></a>
                                <br />
                            </li>
                            <li class="nav-item">
                                <c:url value="/logout" var="logoutUrl" />
                                <a class="nav-link" href="${logoutUrl}"><spring:message code="navigationBar.btn.logOut"/></a>
                            </li>
                            <li class="nav-item" style="alignment: right">
                                <c:url value="/create-changa" var="createUrl" />
                                <a class="nav-link" href="${createUrl}"><spring:message code="navigationBar.btn.createChanga"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <c:choose>
                    <c:when test="${isUserLogged}">
                        <span class="navbar-text white-text"><spring:message code="navigationBar.salute"/><c:out value="${sessionScope.getLoggedUser.name}"/></span>
                    </c:when>
                </c:choose>
            </div>
        </nav>
        <%--<nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
            <div class="container">
                <img src="https://svgsilh.com/svg/1901457.svg" style="width: 1cm; height: 1cm" \>
                <a class="navbar-brand" href="/" style="margin-left: 3px">C H A N G A S</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
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
                                <li class="nav-item">
                                    <a class="nav-link" href="${logInUrl}"><spring:message code="navigationBar.btn.profile"/></a>
                                    <br />
                                </li>
                                <li class="nav-item" style="alignment: right">
                                    <c:url value="/create-changa" var="createUrl" />
                                    <a class="nav-link" href="${createUrl}"><spring:message code="navigationBar.btn.createChanga"/></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item" style="margin-top: 0.4cm">
                                    &lt;%&ndash;<c:url value="/profile" var="profileUrl" />
                                    <a class="nav-link" href="${profileUrl}">Hola, <c:out value="${sessionScope.getLoggedUser.name}"/></a>
                                    &ndash;%&gt;
                                    Hola, <c:out value="${sessionScope.getLoggedUser.name}"/>
                                    <br />
                                </li>
                                <li class="nav-item">
                                    <c:url value="/profile" var="profileUrl" />
                                    <a class="nav-link" href="${profileUrl}"><spring:message code="navigationBar.btn.profile"/></a>
                                    <br />
                                </li>
                                <li class="nav-item">
                                    <c:url value="/logout" var="logoutUrl" />
                                    <a class="nav-link" href="${logoutUrl}"><spring:message code="navigationBar.btn.logOut"/></a>
                                </li>
                                <li class="nav-item" style="alignment: right">
                                    <c:url value="/create-changa" var="createUrl" />
                                    <a class="nav-link" href="${createUrl}"><spring:message code="navigationBar.btn.createChanga"/></a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>--%>
    </body>
</html>
