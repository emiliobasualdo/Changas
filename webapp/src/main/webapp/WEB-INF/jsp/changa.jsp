<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <head>
        <style>
            p {font-size: 16px;}
        </style>
        <title>Changas</title>
    </head>
    <div class="card w-100">
        <div class="card-body">
            <h1 class="card-title">
                <c:out value="${requestScope.title}" />
            </h1>
            <p class="card-text">
                <c:out value="${requestScope.description}" />
            </p>
            <p class="card-text" style="line-height: 20%;"><small class="text-muted">
                Ubicacion: <c:out value="${requestScope.neighborhood}" />
            </small></p>
            <p class="card-text" style="line-height: 20%;"><small class="text-muted">
                Precio: <c:out value="${requestScope.price}" />$
            </small></p>
            <p class="card-text" style="line-height: 20%;"><small class="text-muted">
                Contacto: <c:out value="${requestScope.ownerName}"/>  <c:out value="${requestScope.ownerPhone}"/>
            </small></p>
            <a href="#" class="btn btn-primary">Aceptar</a>
        </div>
    </div>
</html>