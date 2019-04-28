<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <style><%@include file="/WEB-INF/css/adminChangaBody.css"%></style>
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
            <h5><c:out value="${changa.neighborhood}" />, <c:out value="${changa.street}" /> <c:out value="${changa.number}" /></h5>
        </div>
    </div>

    <div class="container">
        <c:choose>
            <c:when test="${notInscribedUsers == true}">
                <div class="btn-group">
                    <a href="/edit-changa?id=<c:out value="${changa.changa_id}"/>" class="btn btn-info center-pill"><spring:message code="adminchangaBody.btn.edit"/></a>
                    <br />

                    <div class="container">
                        <c:url value="/delete-changa" var="deleteUrl" />
                        <form action="${deleteUrl}" method="post">
                            <input type="hidden" name="changaId" value="<c:out value="${changa.changa_id}"/>">
                            <input type="submit"  class="btn btn-danger center-pill" value="Eliminar" />
                        </form>
                        <br />
                    </div>

                    <div class="container">
                        <c:url value="/close-changa" var="closeUrl" />
                        <form action="${closeUrl}" method="post">
                            <input type="hidden" name="changaId" value="<c:out value="${changa.changa_id}"/>">
                            <input type="submit"  class="btn btn-success center-pill" value="Finalizar" />
                        </form>
                        <br />
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="container" style="margin-top: 40px">
                    <h2 style="margin-bottom: 20px"><spring:message code="adminchangaBody.h2"/></h2>
                    <div class="auto-table">
                        <c:forEach items="${inscribedUsers}" var="user">
                            <c:set var="name" value="${user.name}" scope="request"/>
                            <c:set var="surname" value="${user.surname}" scope="request"/>
                            <c:set var="tel" value="${user.tel}" scope="request"/>
                            <c:set var="email" value="${user.email}" scope="request"/>
                            <c:set var="changaId" value="${changa.changa_id}" scope="request"/>
                            <c:set var="userId" value="${user.user_id}" scope="request"/>
                            <c:import url="inscribedUserCard.jsp"/>
                        </c:forEach>
                    </div>
                </div>
                <br />
                <div class="btn-group">
                    <div class="container">
                        <c:url value="/delete-changa" var="deleteUrl" />
                        <form action="${deleteUrl}" method="post">
                            <input type="hidden" name="changaId" value="<c:out value="${changa.changa_id}"/>">
                            <input type="submit"  class="btn btn-danger center-pill" value="Eliminar" />
                        </form>
                        <br />
                    </div>

                    <div class="container">
                        <c:url value="/close-changa" var="closeUrl" />
                        <form action="${closeUrl}" method="post">
                            <input type="hidden" name="changaId" value="<c:out value="${changa.changa_id}"/>">
                            <input type="submit"  class="btn btn-success center-pill" value="Finalizar" />
                        </form>
                        <br />
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>

</html>
