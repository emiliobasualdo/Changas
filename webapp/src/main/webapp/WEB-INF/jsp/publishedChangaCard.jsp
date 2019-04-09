<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
</head>

<div class="changaCard-option">
    <h1>
        <c:out value="${requestScope.title}" />
    </h1>
    <%--<span class="">
            <i class="fas fa-map-marker-alt fas-2x"></i>
            <small>
                <c:out value="${requestScope.neighborhood}" />, <c:out value="${requestScope.street}" /> <c:out value="${requestScope.number}" />
            </small><br>
            </span>
    <hr />
    <p>
        <c:out value="${requestScope.description}" />
    </p>
    <hr />--%>
    <div class="price">
        <%--<div class="front">
                <span class="price">
                    <c:out value="${requestScope.price}" />$<b>$</b>
                </span>
        </div>--%>
        <div class="back">
            <a href="admin-changa?id=<c:out value="${requestScope.changa_id}"/>" class="button">Administrar Changa</a>
        </div>
    </div>
</div>

</html>
