<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <style><%@include file="/WEB-INF/css/mainPageBody.css"%></style>
</head>
<body>
    <c:if test="${changaList.isEmpty()}">
        <div class="container">
            <div class="col-sm-12">

                <div class="bs-calltoaction bs-calltoaction-default">
                    <div class="row">
                        <div class="col-md-8 cta-contents">
                            <h1 class="cta-title"><spring:message code="mainPageBody.noChangasTitle"/></h1>
                            <div class="cta-desc">
                                <p><spring:message code="mainPageBody.noChangasP"/></p>
                            </div>
                        </div>
                        <div class="col-md-6 cta-button">
                            <c:url value="/create-changa" var="createChangaUrl"/>
                            <a href="${createChangaUrl}" class="btn btn-lg btn-block btn-default"><spring:message code="mainPageBody.noChangasBtn"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${changaList.isEmpty() == false}">
        <div class="auto-table">
            <c:choose>
                <c:when test="${isUserLogged}">
                    <c:forEach items="${changaList}" var="entry">
                        <c:set var="title" value="${entry.key.title}" scope="request"/>
                        <c:set var="description" value="${entry.key.description}" scope="request"/>
                        <c:set var="user_id" value="${entry.key.user_id}" scope="request"/>
                        <c:set var="price" value="${entry.key.price}" scope="request"/>
                        <c:set var="changa_id" value="${entry.key.changa_id}" scope="request"/>
                        <c:set var="neighborhood" value="${entry.key.neighborhood}" scope="request"/>
                        <c:set var="street" value="${entry.key.street}" scope="request"/>
                        <c:set var="number" value="${entry.key.number}" scope="request"/>
                        <c:set var="inscribed" value="${entry.value}" scope="request"/>
                        <c:import url="/WEB-INF/jsp/changaCard.jsp"/>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${changaList}" var="changa">
                        <c:set var="title" value="${changa.title}" scope="request"/>
                        <c:set var="description" value="${changa.description}" scope="request"/>
                        <c:set var="user_id" value="${changa.user_id}" scope="request"/>
                        <c:set var="price" value="${changa.price}" scope="request"/>
                        <c:set var="changa_id" value="${changa.changa_id}" scope="request"/>
                        <c:set var="neighborhood" value="${changa.neighborhood}" scope="request"/>
                        <c:set var="street" value="${changa.street}" scope="request"/>
                        <c:set var="number" value="${changa.number}" scope="request"/>
                        <c:set var="inscribed" value="${false}" scope="request"/>
                        <c:import url="/WEB-INF/jsp/changaCard.jsp"/>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</body>
</html>
