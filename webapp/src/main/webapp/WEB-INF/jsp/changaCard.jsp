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
            <c:choose><c:when test="${requestScope.inscribed}"><p><i class="fas fa-check"></i></p></c:when></c:choose>
        </h2>
        <span class="">
            <c:choose>
                <c:when test="${requestScope.inscribed}">
                    <i class="fas fa-map-marker-alt fas-2x" style="color: grey"></i>
                    <small style="color: grey"><c:out value="${requestScope.neighborhood}"/></small>
                    <br>
                </c:when>
                <c:otherwise>
                    <i class="fas fa-map-marker-alt fas-2x"></i>
                    <small><c:out value="${requestScope.neighborhood}"/></small>
                    <br>
                </c:otherwise>
            </c:choose>
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
                <c:choose>
                    <c:when test="${requestScope.inscribed}">
                        <a href="changa?id=<c:out value="${requestScope.changa_id}"/>" class="button" style="background-color: grey">
                            <spring:message code="changaCard.button" />
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="changa?id=<c:out value="${requestScope.changa_id}"/>" class="button">
                            <spring:message code="changaCard.button" />
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>

</html>
