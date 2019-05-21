<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style><%@include file="/WEB-INF/css/adminChangaBody.css"%></style>
</head>

<body>
    <div class="jumbotron custom-margin">
        <div class="container">
            <h2>
                <c:out value="${changa.title}" />
            </h2>
            <h5>
                <c:out value="${changa.description}" />
            </h5>
            <h5>
                <c:out value="${changa.price}" /> $
            </h5>
            <h5><c:out value="${changa.neighborhood}" />, <c:out value="${changa.street}" /> <c:out value="${changa.number}" /></h5>
        </div>
        <div class="container">
            <c:url value="${urlImage}" var="mudanzaImage"/>
            <img src="${mudanzaImage}" alt="Changa Image">
        </div>
    </div>

    <div class="container">
        <c:choose>
            <c:when test="${notInscribedUsers}">
                <div class="row">
                    <div class="col-lg-4 col-md-4 col-sm-12 p-0">
                        <c:url value="/edit-changa?id=${changa.changa_id}" var="editChangaUrl" />
                        <a id="btnedit" href="${editChangaUrl}" class="btn btn-info center-pill"><spring:message code="adminchangaBody.btn.edit"/></a>
                        <br />
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-12 p-0">
                        <button id="btndelete1" type="button" class="btn btn-danger center-pill" data-toggle="modal" data-target="#deleteModal">
                            <spring:message code="adminchangaBody.btn.delete"/>
                        </button>
                    </div>
                    <br />
                </div>
            </c:when>
            <c:otherwise>
                <div class="container" style="margin-top: 40px">
                    <h2 style="margin-bottom: 20px"><spring:message code="adminchangaBody.h2"/></h2>
                    <div class="auto-table">
                        <c:forEach items="${inscribedUsers}" var="entry">
                            <c:set var="name" value="${entry.key.name}" scope="request"/>
                            <c:set var="surname" value="${entry.key.surname}" scope="request"/>
                            <c:set var="tel" value="${entry.key.tel}" scope="request"/>
                            <c:set var="email" value="${entry.key.email}" scope="request"/>
                            <c:set var="changaId" value="${changa.changa_id}" scope="request"/>
                            <c:set var="userId" value="${entry.key.user_id}" scope="request"/>
                            <c:set var="state" value="${entry.value.state}" scope="request"/>
                            <c:import url="inscribedUserCard.jsp"/>
                        </c:forEach>
                    </div>
                </div>
                <br />
                <div class="row">
                    <div class="col-lg-4 col-md-4 col-sm-12 p-0">
                        <button id="btndelete2" type="button" class="btn btn-danger center-pill" data-toggle="modal" data-target="#deleteModal">
                            <spring:message code="adminchangaBody.btn.delete"/>
                        </button>
                        <br />
                    </div>
                    <c:choose>
                        <c:when test="${changa.state == 'settled'}">
                            <div class="col-lg-4 col-md-4 col-sm-12 p-0">
                                <button id="btnend1" type="button" class="btn btn-success center-pill" data-toggle="modal" data-target="#doneModal">
                                    <spring:message code="adminchangaBody.btn.done"/>
                                </button>
                                <br />
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${hasAcceptedUsers}">
                                    <div class="col-lg-4 col-md-4 col-sm-12 p-0">
                                        <button id="btnsettle1" type="button" class="btn btn-success center-pill" data-toggle="modal" data-target="#settleModal">
                                            <spring:message code="adminchangaBody.btn.settle"/>
                                        </button>
                                        <br />
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="col-lg-4 col-md-4 col-sm-12 p-0">
                                        <spring:message code="adminchangaBody.errorModal.title" var="title"/>
                                        <spring:message code="adminchangaBody.errorModal.message" var="message"/>
                                        <c:set var="titleError" value="${title}" scope="request"/>
                                        <c:set var="messageError" value="${message}" scope="request"/>
                                        <button id="btnerror" type="button" class="btn btn-success center-pill" data-toggle="modal" data-target="#errorModal">
                                            <spring:message code="adminchangaBody.btn.settle"/>
                                        </button>
                                        <br />
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteModalLongTitle"><spring:message code="adminchangaBody.title.delete"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <spring:message code="adminchangaBody.continue"/>
                </div>
                <div class="modal-footer">
                    <div class="row custom-row">
                        <div class="col-lg-6 col-md-6 col-sm-12 p-0">
                            <button <%--id="btncancel1"--%> type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="adminchangaBody.btn.cancel"/></button>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 p-0">
                            <c:url value="/delete-changa" var="deleteUrl" />
                            <form action="${deleteUrl}" method="post">
                                <input type="hidden" name="changaId" value="<c:out value="${changa.changa_id}"/>">
                                <input <%--id="btndelete3"--%> type="submit"  class="btn btn-danger center-pill" value="<spring:message code="adminchangaBody.btn.delete"/>"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="doneModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="doneModalLongTitle"><spring:message code="adminchangaBody.title.done"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <spring:message code="adminchangaBody.continue"/>
                </div>
                <div class="modal-footer">
                    <div class="row custom-row">
                        <div class="col-lg-6 col-md-6 col-sm-12 p-0">
                            <button <%--id="btncancel2"--%> type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="adminchangaBody.btn.cancel"/></button>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 p-0">
                            <c:url value="/close-changa" var="closeUrl" />
                            <form action="${closeUrl}" method="post">
                                <input type="hidden" name="changaId" value="<c:out value="${changa.changa_id}"/>">
                                <input <%--id="btnend2"--%> type="submit"  class="btn btn-success center-pill" value="<spring:message code="adminchangaBody.btn.done"/>" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="settleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="settleModalLongTitle"><spring:message code="adminchangaBody.title.settle"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <spring:message code="adminchangaBody.continue"/>
                </div>
                <div class="modal-footer">
                    <div class="row custom-row">
                        <div class="col-lg-6 col-md-6 col-sm-12 p-0">
                            <button <%--id="btncancel3"--%> type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="adminchangaBody.btn.cancel"/></button>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 p-0">
                            <c:url value="/settle-changa" var="settleUrl" />
                            <form action="${settleUrl}" method="post">
                                <input type="hidden" name="changaId" value="<c:out value="${changa.changa_id}"/>">
                                <input <%--id="btnsettle2"--%> type="submit"  class="btn btn-success center-pill" value="<spring:message code="adminchangaBody.btn.settle"/>"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/jsp/errorModal.jsp"%>
    <%--<%@include file="/WEB-INF/jsp/successModal.jsp"%>--%>
</body>

</html>
