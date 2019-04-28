<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <%--TODO: INTERNACIONALIZAR--%>

    <head>
        <style><%@include file="/WEB-INF/css/inscribedUserCard.css"%></style>
    </head>
    <body>
        <div class="col-sm-4">
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title" style="text-align: center"><c:out value="${requestScope.name}"/> <c:out value="${requestScope.surname}"/></h3>
                    <p class="card-text" style="text-align: center"><strong>Teléfono</strong>: <c:out value="${requestScope.tel}"/></p>
                    <p class="card-text" style="text-align: center"><strong>Email</strong>: <c:out value="${requestScope.email}"/></p>
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
                </div>
            </div>
        </div>
    </body>
</html>
