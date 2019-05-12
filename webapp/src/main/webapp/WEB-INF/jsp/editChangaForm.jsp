<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-32">
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
        <%--TODO: cabiar form a a un spring form para que por beans se mapen las varibales y poder cambiar el contendido de las mismas--%>
        <form:form method="post" modelAttribute="changaForm">
            <h2>Editá tu changa</h2>
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
                                <form:input class="form-control" path="title"/>
                                <form:errors cssClass="form-error" path="title" element="p"/>
                            </div>
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
                                <form:errors cssClass="form-error" path="description" element="p"/>
                            </div>
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
                                <form:input class="form-control" path="neighborhood"/>
                                <form:errors cssClass="form-error" path="neighborhood" element="p"/>
                            </div>
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
                                <form:errors cssClass="form-error" path="street" element="p"/>
                            </div>
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
                                <form:errors cssClass="form-error" path="number" element="p"/>
                            </div>
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
                                <form:errors cssClass="form-error" path="price" element="p"/>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block btn-lg"><spring:message code="editChangaForm.btn"/></button>
            </div>
        </form:form>
    </div>
</body>
</html>

