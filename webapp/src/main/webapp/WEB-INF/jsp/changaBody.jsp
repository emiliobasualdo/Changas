<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <style><%@include file="/WEB-INF/css/changaBody.css"%></style>
</head>

<body>

    <div class="jumbotron jumbotron-fluid" style="margin-top: 2cm">
        <div class="container">
            <h2>
                <c:out value="${changa.title}" />
            </h2>
            <h5 style="margin-top: 1cm">
                <c:out value="${changa.description}" />
            </h5>
            <h5>
                <c:out value="${changa.price}" /> $
            </h5>
        </div>
    </div>

    <div class="container">
        <table class="table">
            <thead>
            <tr>
                <th><spring:message code="changaBody.Table.name" /></th>
                <th><spring:message code="changaBody.Table.telephone" /></th>
                <th><spring:message code="changaBody.Table.location" /></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><c:out value="${changaOwner.name}"/> <c:out value="${changaOwner.surname}"/></td>
                <td><c:out value="${changaOwner.tel}" /></td>
                <td><c:out value="${changa.neighborhood}" /></td>
            </tr>
            </tbody>
        </table>
        <!--<a href="#" class="btn btn-primary" style="margin-top: 1cm; margin-bottom: 1cm;">Aceptar</a>-->
    </div>
    <div class="container" style="margin-top: 80px">
        <c:choose>
            <c:when test="${userAlreadyInscribedInChanga == false}">
                <c:choose>
                    <c:when test="${userOwnsChanga == true}">
                        <div class="alert alert-info" role="alert">
                            <spring:message code="changaBody.alertOwner"/><strong><a href="/profile" class="alert-link"><spring:message code="changaBody.alert.btn"/></a></strong>.
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
                    <c:when test="${userOwnsChanga == true}">
                        <div class="alert alert-info" role="alert">
                            <spring:message code="changaBody.alertOwner"/><strong><a href="/profile" class="alert-link"><spring:message code="changaBody.alert.btn"/></a></strong>.
                        </div>
                        <br />
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info" role="alert">
                            <strong><spring:message code="changaBody.alert.bold"/></strong><spring:message code="changaBody.alert"/><strong><a href="/profile" class="alert-link"><spring:message code="changaBody.alert.btn"/></a></strong>.
                        </div>
                        <br />
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
