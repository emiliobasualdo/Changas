<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

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
                    <th>Nombre</th>
                    <th>Telefono</th>
                    <th>Ubicacion</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><c:out value="${changa.user_id}"/></td>
                    <!-- todo: mostrar el nombre del USUARIO que emitio la changa junto a su telefono -->
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
