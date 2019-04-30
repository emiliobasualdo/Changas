<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <%--TODO: INTERNACIONALIZAR--%>

    <head>
        <style><%@include file="/WEB-INF/css/inscribedUserCard.css"%></style>
    </head>
    <body>
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title" style="text-align: center"><c:out value="${requestScope.name}"/> <c:out value="${requestScope.surname}"/></h3>
                    <p class="card-text" style="text-align: center"><strong>Teléfono</strong>: <c:out value="${requestScope.tel}"/></p>
                    <p class="card-text" style="text-align: center"><strong>Email</strong>: <c:out value="${requestScope.email}"/></p>
                    <c:choose>
                        <c:when test="${requestScope.state == 'accepted'}">
                            <p class="card-text" style="text-align: center; color: green;">Changuero aceptado</p>
                            <c:url value="/reject-user" var="rejectUrl" />
                            <form action="${rejectUrl}" method="post">
                                <input type="hidden" name="changaId" value="<c:out value="${requestScope.changaId}"/>">
                                <input type="hidden" name="userId" value="<c:out value="${requestScope.userId}"/>">
                                <input type="submit"  class="btn btn-danger btn-block" value="Rechazar changuero" />
                            </form>
                            <br />
                        </c:when>
                        <c:when test="${requestScope.state == 'declined'}">
                            <p class="card-text" style="text-align: center; color: red">Changuero rechazado</p>
                            <c:url value="/accept-user" var="acceptUrl" />
                            <form action="${acceptUrl}" method="post">
                                <input type="hidden" name="changaId" value="<c:out value="${requestScope.changaId}"/>">
                                <input type="hidden" name="userId" value="<c:out value="${requestScope.userId}"/>">
                                <input type="submit"  class="btn btn-success btn-block" value="Aceptar changuero" />
                            </form>
                            <br />
                        </c:when>
                        <c:otherwise>
                            <c:url value="/accept-user" var="acceptUrl" />
                            <form action="${acceptUrl}" method="post">
                                <input type="hidden" name="changaId" value="<c:out value="${requestScope.changaId}"/>">
                                <input type="hidden" name="userId" value="<c:out value="${requestScope.userId}"/>">
                                <input type="submit"  class="btn btn-success btn-block" value="Aceptar changuero" />
                            </form>
                            <br />
                            <c:url value="/reject-user" var="rejectUrl" />
                            <form action="${rejectUrl}" method="post">
                                <input type="hidden" name="changaId" value="<c:out value="${requestScope.changaId}"/>">
                                <input type="hidden" name="userId" value="<c:out value="${requestScope.userId}"/>">
                                <input type="submit"  class="btn btn-danger btn-block" value="Rechazar changuero" />
                            </form>
                            <br />
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
    </body>
</html>
