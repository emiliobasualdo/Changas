<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
            <c:choose>
                <c:when test="${requestScope.state == 'requested'}">
                    <p><spring:message code="pendingChangaCard.pending"/></p>
                    <hr />
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
                </c:when>
                <c:when test="${requestScope.state == 'accepted'}">
                    <p style="color: green"><spring:message code="pendingChangaCard.acepted"/></p>
                    <hr />
                    <div class="price">
                        <div class="front">
                        <span class="price">
                            <c:out value="${requestScope.price}" />$</b>
                        </span>
                        </div>
                        <div class="back">
                            <%--<c:url value="/unjoin-changa" var="unJoinUrl" />
                            <form method="post" action="${unJoinUrl}">
                                <input type="hidden" name="changaId" value="<c:out value="${requestScope.changa_id}"/>">--%>
                                <input <%--type="submit"--%> class="btn btn-info btn-block" value="Archivar" />
                            <%--</form>--%>
                        </div>
                    </div>
                </c:when>
                <c:when test="${requestScope.state == 'declined'}">
                    <p style="color: red"><spring:message code="pendingChangaCard.rejected"/></p>
                    <hr />
                    <div class="price">
                        <div class="front">
                        <span class="price">
                            <c:out value="${requestScope.price}" />$</b>
                        </span>
                        </div>
                        <div class="back">
                                <%--<c:url value="/unjoin-changa" var="unJoinUrl" />
                                <form method="post" action="${unJoinUrl}">
                                    <input type="hidden" name="changaId" value="<c:out value="${requestScope.changa_id}"/>">--%>
                            <input <%--type="submit"--%> class="btn btn-info btn-block" value="Archivar" />
                                <%--</form>--%>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </body>

</html>