<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
</head>

<body>

    <c:choose>
        <c:when test="${requestScope.inscribed}">
            <style><%@include file="/WEB-INF/css/changaCardInscribed.css"%></style>
            <div class="changaCard-option">
                <h2>
                    <c:out value="${requestScope.title}" />
                    <c:choose><c:when test="${requestScope.inscribed == true}"><p><i class="fas fa-check"></i></p></c:when></c:choose>
                </h2>
                <span class="">
                    <i class="fas fa-map-marker-alt fas-2x"></i>
                    <small>
                        <c:out value="${requestScope.neighborhood}"/>
                    </small><br>
                </span>
                <hr />
                <p>
                    <c:out value="${requestScope.description}" />
                </p>
                <hr />
                <div class="price inscribed">
                    <div class="front inscribed">
                    <span class="price inscribed">
                        <c:out value="${requestScope.price}" />$
                    </span>
                    </div>
                    <div class="back inscribed">
                        <a href="changa?id=<c:out value="${requestScope.changa_id}"/>" class="button inscribed">
                            <spring:message code="changaCard.button" />
                        </a>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <style><%@include file="/WEB-INF/css/changaCard.css"%></style>
            <div class="changaCard-option">
                <h2>
                    <c:out value="${requestScope.title}" />
                    <c:choose><c:when test="${requestScope.inscribed == true}"><i class="fas fa-check"></i></c:when></c:choose>
                </h2>
                <span class="">
                    <i class="fas fa-map-marker-alt fas-2x"></i>
                    <small>
                        <c:out value="${requestScope.neighborhood}"/>
                    </small><br>
                </span>
                <hr />
                <p>
                    <c:out value="${requestScope.description}" />
                </p>
                <hr />
                <div class="price">
                    <div class="front">
                    <span class="price">
                        <c:out value="${requestScope.price}" />$
                    </span>
                    </div>
                    <div class="back">
                        <a href="changa?id=<c:out value="${requestScope.changa_id}"/>" class="button">
                            <spring:message code="changaCard.button" />
                        </a>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

</body>

</html>
