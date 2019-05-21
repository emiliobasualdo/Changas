<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
    <head>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
        <style><%@include file="/WEB-INF/css/publishedChangaCard.css"%></style>
    </head>
    <body>
        <div class="changaCard-option">
            <h4>
                <c:out value="${requestScope.title}" />
            </h4>
            <span class="">
                    <i class="fas fa-map-marker-alt fas-2x"></i>
                    <small>
                        <c:out value="${requestScope.neighborhood}" />
                    </small><br>
            </span>
            <hr />
            <div class="price">
                <div class="back">
                    <c:url value="admin-changa?id=${requestScope.changa_id}" var="adminChangaUrl" />
                    <a href="${adminChangaUrl}" class="button"><spring:message code="publishedChangaCard.btn.admin"/></a>
                </div>
            </div>
        </div>
    </body>
</html>
