<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <head>
        <style>
            p {font-size: 16px;}
        </style>
    </head>
    <div class="card w-100">
        <div class="card-body" style="border-color: #26B3BA">
            <a href="changa?id=1">
            <h2 class="card-title" style="color: #26B3BA">
                <c:out value="${requestScope.title}" />
            </h2></a>
            <p class="card-text">
                Ubicacion: <c:out value="${requestScope.neighborhood}" />
            </p>
            <p class="card-text">
                Precio: <c:out value="${requestScope.price}" />$
            </p>
        </div>
    </div>
</html>