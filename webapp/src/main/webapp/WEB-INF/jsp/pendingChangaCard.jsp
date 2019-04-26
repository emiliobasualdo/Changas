<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
</head>

<div class="changaCard-option">
    <h1>
        <c:out value="${requestScope.title}" />
    </h1>
    <span class="">
            <i class="fas fa-map-marker-alt fas-2x"></i>
            <small>
                <c:out value="${requestScope.neighborhood}" />, <c:out value="${requestScope.street}" /> <c:out value="${requestScope.number}" />
            </small><br>
            </span>
    <hr />
    <%--<p>
        <c:out value="${requestScope.state}" />
    </p>
    <hr />--%>
    <div class="price">
        <div class="front">
                <span class="price">
                    <c:out value="${requestScope.price}" />$<b>$</b>
                </span>
        </div>
        <div class="back">
            <c:url value="/unjoin-changa" var="unJoinUrl" />
            <form action="${unJoinUrl}" method="post">
                <input type="hidden" name="changaId" value="<c:out value="${requestScope.changa_id}"/>">
                <input type="submit" name="unjoinBtn" class="btn btn-success btn-block" value="Desanotame" />
            </form>
        </div>
    </div>
</div>

</html>
