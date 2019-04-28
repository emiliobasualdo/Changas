<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>

    <head>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
    </head>

    <body>
        <div class="changaCard-option">
            <h2>
                <c:out value="${requestScope.title}" />
            </h2>
            <span class="">
                    <i class="fas fa-map-marker-alt fas-2x"></i>
                    <small>
                        <c:out value="${requestScope.neighborhood}" />, <c:out value="${requestScope.street}" /> <c:out value="${requestScope.number}" />
                    </small><br>
                    </span>
            <hr />
            <%--<p>
                todo
                <c:out value="${requestScope.state}" />
            </p>
            <hr />--%>
            <div class="price">
                <div class="front">
                        <span class="price">
                            <c:out value="${requestScope.price}" />$</b>
                        </span>
                </div>
                <div class="back">
                    <c:url value="/unjoin-changa" var="unJoinUrl" />
                    <form method="post" action="${unJoinUrl}">
                        <input type="hidden" name="changaId" value="<c:out value="${requestScope.changa_id}"/>">
                        <input type="submit" class="btn btn-danger btn-block" value="Desanotame" />
                    </form>
                </div>
            </div>
        </div>
    </body>

</html>