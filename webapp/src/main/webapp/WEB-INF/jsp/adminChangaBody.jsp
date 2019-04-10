<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    @import url(https://fonts.googleapis.com/css?family=Josefin+Sans:400,300,300italic,400italic,600,700,600italic,700italic);
    body {
        font-family: "Josefin Sans", sans-serif;
        line-height: 1;
        padding: 20px;
        height: 100%;
        background: #eee;
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
            <th>Nombre</th>
            <th>Telefono</th>
            <th>Ubicacion</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><c:out value="${changaOwner.name}"/></td>
            <td><c:out value="${changaOwner.tel}" /></td>
            <td><c:out value="${changa.neighborhood}" /></td>
        </tr>
        </tbody>
    </table>
    <c:choose>
        <c:when test="${alreadyInscribedUsers == false}">
            <a href="#" class="btn btn-primary" style="margin-top: 1cm; margin-bottom: 1cm;">Editar datos</a>
            <br />
        </c:when>
        <c:otherwise>
            <br />
        </c:otherwise>
    </c:choose>
</div>
<div class="container" style="margin-top: 40px">
    <div class="auto-table">
        <h2 style="margin-bottom: 20px">Usuarios anotados</h2>
        <c:forEach items="${inscribedUsers}" var="user">
            <c:set var="name" value="${user.name}" scope="request"/>
            <c:set var="surname" value="${user.surname}" scope="request"/>
            <c:set var="tel" value="${user.tel}" scope="request"/>
            <c:set var="email" value="${user.email}" scope="request"/>
            <c:import url="inscribedUserCard.jsp"/>
        </c:forEach>
    </div>
</div>
</body>
</html>
