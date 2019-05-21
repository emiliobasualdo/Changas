<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
        <style><%@include file="/WEB-INF/css/changaCard.css"%></style>
    </head>

    <body>
        <div class="changaCard-option">
            <h4>
                <c:out value="${requestScope.title}" />
            </h4>
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
                            <a type="button" class="button" data-toggle="modal" data-target="#unjoinModal">
                                <spring:message code="pendingChangaCard.btn"/>
                            </a>
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

                            <%--<c:url value="/unjoin-changa?changaId=${requestScope.changa_id}" var="unJoinUrl" />--%>
                            <a class="button">
                                Archivar
                            </a>

                            <%--<c:url value="/unjoin-changa" var="unJoinUrl" />
                            <form method="post" action="${unJoinUrl}">
                                <input type="hidden" name="changaId" value="<c:out value="${requestScope.changa_id}"/>">--%>
                                <%--<input &lt;%&ndash;type="submit"&ndash;%&gt; class="btn btn-info btn-block" value="Archivar" />--%>
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

                                <%--<c:url value="/unjoin-changa?changaId=${requestScope.changa_id}" var="unJoinUrl" />--%>
                            <a class="button">
                                Archivar
                            </a>
                                <%--<c:url value="/unjoin-changa" var="unJoinUrl" />
                                <form method="post" action="${unJoinUrl}">
                                    <input type="hidden" name="changaId" value="<c:out value="${requestScope.changa_id}"/>">--%>
                            <%--<input &lt;%&ndash;type="submit"&ndash;%&gt; class="btn btn-info btn-block" value="Archivar" />--%>
                                <%--</form>--%>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </div>
        <div class="modal fade" id="unjoinModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="unjoinModalLongTitle"><spring:message code="pendingChangaCard.popupTitle"/></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <spring:message code="adminchangaBody.continue"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="adminchangaBody.btn.cancel"/></button>
                        <c:url value="/unjoin-changa" var="unJoinUrl" />
                        <form method="post" action="${unJoinUrl}">
                            <input type="hidden" name="changaId" value="<c:out value="${requestScope.changa_id}"/>">
                            <input type="submit" class="btn btn-danger btn-block" value="<spring:message code="pendingChangaCard.btn"/>" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>