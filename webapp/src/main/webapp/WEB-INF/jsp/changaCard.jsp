<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
    <style><%@include file="/WEB-INF/css/changaCard.css"%></style>
</head>

<body>
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
</body>

</html>
