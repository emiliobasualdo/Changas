<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
    <style><%@include file="/WEB-INF/css/changaCard.css"%></style>
</head>

<body>
    <div class="changaCard-option">
        <h2>
            <c:out value="${requestScope.title}" />
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
        <p style="height: 50px">
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
                        <c:url value="changa?id=${requestScope.changa_id}" var="changaUrl" />
                        <a href="${changaUrl}" class="button" style="background-color: grey">
                            <spring:message code="changaCard.button" />
                        </a>
                    </c:when>
                    <c:otherwise>
                        <c:url value="changa?id=${requestScope.changa_id}" var="changaUrl" />
                        <a href="${changaUrl}" class="button">
                            <spring:message code="changaCard.button" />
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>

</html>
