<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>

    <head>
        <title>Changas</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        <style><%@include file="/WEB-INF/css/signUpBody.css"%></style>
        <style><%@include file="/WEB-INF/css/formError.css"%></style>
    </head>
    <body>
        <div class="signup-form">
            <c:url value="/create-changa" var="createUrl" />
            <form:form method="post" modelAttribute="changaForm" action="${createUrl}">
                <h2><spring:message code="issueChangaForm.header"/></h2>
                <table style="width: 100%;">
                    <tr>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:label path="title"><spring:message code="ChangaForm.title"/></form:label>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:input class="form-control" path="title" maxlength="50"/>
                                </div>
                                <form:errors cssClass="form-error" path="title" element="p"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:label path="description"><spring:message code="ChangaForm.description"/></form:label>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:textarea rows="3" class="form-control" path="description"/>
                                </div>
                                <form:errors cssClass="form-error" path="description" element="p"/>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:label path="description"><spring:message code="ChangaForm.category"/></form:label>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:select  class="form-control" path="category">
                                        <c:forEach items="${categories}" var="category">
                                            <option value="${category}"><spring:message code="${category}"/></option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                                <form:errors cssClass="form-error" path="category" element="p"/>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:label path="neighborhood"><spring:message code="ChangaForm.neighborhood"/></form:label>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:select class="form-control" path="neighborhood">
                                        <c:forEach items="${neighborhoods}" var="neighborhood">
                                            <option value="${neighborhood}">${neighborhood}</option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                                <form:errors cssClass="form-error" path="neighborhood" element="p"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:label path="street"><spring:message code="ChangaForm.street"/></form:label>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:input class="form-control" path="street"/>
                                </div>
                                <form:errors cssClass="form-error" path="street" element="p"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:label path="number"><spring:message code="ChangaForm.number"/></form:label>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:input class="form-control" path="number"/>
                                </div>
                                <form:errors cssClass="form-error" path="number" element="p"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:label path="price"><spring:message code="ChangaForm.price"/></form:label>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <div class="input-group">
                                    <form:input class="form-control" path="price"/>
                                </div>
                                <form:errors cssClass="form-error" path="price" element="p"/>
                            </div>
                        </td>
                    </tr>
                </table>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block btn-lg"><spring:message code="issueChangaForm.btn"/></button>
                </div>
            </form:form>
        </div>
    </body>
</html>

