<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style><%@include file="/WEB-INF/css/adminChangaBody.css"%></style>
</head>

<body>

    <div class="jumbotron custom-margin">
        <div class="container">
            <h2>
                <c:out value="${changa.title}" />
            </h2>
            <h5>
                <c:out value="${changa.description}" />
            </h5>
            <h5>
                <c:out value="${changa.price}" /> $
            </h5>
        </div>
        <div class="container">
            <c:url value="${urlImage}" var="mudanzaImage"/>
            <img src="${mudanzaImage}" alt="Changa Image">
        </div>
    </div>

    <div class="container">
        <table class="table">
            <thead>
            <tr>
                <th><spring:message code="changaBody.Table.name" /></th>
                <th><spring:message code="changaBody.Table.location" /></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><c:out value="${changaOwner.name}"/> <c:out value="${changaOwner.surname}"/></td>
                <td><c:out value="${changa.neighborhood}" /></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="container" style="margin-top: 80px">
        <c:url value="/profile" var="profileUrl" />
        <c:choose>
            <c:when test="${userAlreadyInscribedInChanga == false}">
                <c:choose>
                    <c:when test="${userOwnsChanga == true}">
                        <div class="alert alert-info" role="alert">
                            <spring:message code="changaBody.alertOwner"/><strong><a href="${profileUrl}" class="alert-link"><spring:message code="changaBody.alert.btn"/></a></strong>.
                        </div>
                        <br />
                    </c:when>
                    <c:otherwise>
                        <c:url value="/join-changa" var="joinUrl" />
                        <form action="${joinUrl}" method="post">
                            <input type="hidden" name="changaId" value="<c:out value="${changa.changa_id}"/>">
                            <input type="submit"  class="btn btn-success btn-block" value="Anotame en la changa" />
                        </form>
                        <br />
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <%--esto nunca debería pasar--%>
                    <c:when test="${userOwnsChanga == true}">
                        <div class="alert alert-info" role="alert">
                            <spring:message code="changaBody.alertOwner"/><strong><a href="${profileUrl}" class="alert-link"><spring:message code="changaBody.alert.btn"/></a></strong>.
                        </div>
                        <br />
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${inscriptionState == 'requested'}">
                                <div class="alert alert-info" role="alert">
                                    <strong><spring:message code="changaBody.alert.bold"/></strong><spring:message code="changaBody.alert"/><strong><a href="${profileUrl}" class="alert-link"><spring:message code="changaBody.alert.btn"/></a></strong>.
                                </div>
                                <br />
                            </c:when>
                            <c:when test="${inscriptionState == 'declined'}">
                                <div class="alert alert-danger" role="alert">
                                    <strong><spring:message code="changaBody.alertRejection.bold"/></strong><spring:message code="changaBody.alert"/><strong><a href="${profileUrl}" class="alert-link"><spring:message code="changaBody.alert.btn"/></a></strong>.
                                </div>
                                <br />
                            </c:when>
                            <c:when test="${inscriptionState == 'accepted'}">
                                <div class="alert alert-success" role="alert">
                                    <strong><spring:message code="changaBody.alertAccepted.bold"/></strong><spring:message code="changaBody.alert"/><strong><a href="${profileUrl}" class="alert-link"><spring:message code="changaBody.alert.btn"/></a></strong>.
                                </div>
                                <br />
                            </c:when>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
