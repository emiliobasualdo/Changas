<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    @import url(https://fonts.googleapis.com/css?family=Josefin+Sans:400,300,300italic,400italic,600,700,600italic,700italic);
    body {
        font-family: "Josefin Sans", sans-serif;
        line-height: 1;
        padding: 20px;
        height: 100%;
        background: white;
    }

</style>
<body>

    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h2>
                <c:out value="${changa.title}" />
            </h2>
            <p style="font-style: italic">
                <c:out value="${changa.description}" />
            </p>
            <p>
                <c:out value="${changa.price}" /> $
            </p>
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
                <form action="/joinChanga" method="post">
                    <input type="hidden" name="changaId" value="<c:out value="${changa.user_id}"/>">
                    <input type="submit"  class="btn btn-success btn-block" value="Anotame en la changa" />
                </form>
                <br />
            </c:when>
            <c:otherwise>
                <div class="alert alert-info" role="alert">
                    <strong>Ya estás anotado!</strong> Si querés ver las changas en las que te anotaste dirigite <strong><a href="/profile?id=<c:out value="${currentUser.user_id}"/>" class="alert-link">acá</a></strong>.
                </div>
                <br />
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
