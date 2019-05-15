<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <style><%@ include file="/WEB-INF/css/signUpBody.css" %></style>
        <style><%@include file="/WEB-INF/css/formError.css"%></style>
    </head>
    <body>
    <div class="signup-form">
        <c:url value="/signup" var="createUrl" />
        <form:form action="${createUrl}" modelAttribute="signUpForm" method="post">
            <h2><spring:message code="signUpBody.header"/></h2>
            <table style="width: 100%">
                <tr>
                    <td>
                        <div class="form-group">
                            <div class="input-group" style="display: inline-block;">
                                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                <form:label path="name"><spring:message code="UserRegisterForm.name"/></form:label>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="form-group">
                            <div class="align-self-md-center">
                                <div class="input-group">
                                    <form:input class="form-control" path="name" />
                                </div>
                                <form:errors cssClass="form-error" path="name" element="p"/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <div class="input-group" style="display: inline-block;">
                                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                <form:label path="surname"><spring:message code="UserRegisterForm.surname"/></form:label>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="form-group">
                            <div class="input-group">
                                <form:input class="form-control" path="surname" />
                            </div>
                            <form:errors cssClass="form-error" path="surname" element="p"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <div class="input-group" style="display: inline-block;">
                                <span class="input-group-addon"><i class="fa fa-phone"></i></span>
                                <form:label path="telephone"><spring:message code="UserRegisterForm.telephone"/></form:label>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="form-group">
                            <div class="input-group">
                                <form:input class="form-control" path="telephone" />
                            </div>
                            <form:errors cssClass="form-error" path="telephone" element="p"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <div class="input-group" style="display: inline-block;">
                                <span class="input-group-addon"><i class="fa fa-paper-plane"></i></span>
                                <form:label path="email"><spring:message code="UserRegisterForm.email"/></form:label>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="form-group">
                            <div class="input-group">
                                <form:input class="form-control" path="email" />
                            </div>
                            <form:errors cssClass="form-error" path="email" element="p"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <div class="input-group" style="display: inline-block;">
                                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                <form:label path="password"><spring:message code="UserRegisterForm.password"/></form:label>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="form-group">
                            <div class="input-group">
                                <form:input type="password" class="form-control" path="password" />
                            </div>
                            <form:errors cssClass="form-error" path="password" element="p"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon">
                                    <i class="fa fa-lock"></i>
                                    <i class="fa fa-check"></i>
                                </span>
                                <form:label path="repeatPassword"><spring:message code="UserRegisterForm.repeatPassword"/></form:label>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="form-group">
                            <div class="input-group">
                                <form:input type="password" class="form-control" path="repeatPassword"/>
                            </div>
                            <form:errors cssClass="form-error" path="repeatPassword" element="p"/>
                        </div>
                    </td>
                </tr>
            </table>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block btn-lg"><spring:message code="signUpBody.button"/></button>
            </div>
            <p class="small text-center"><spring:message code="signUpBody.alert.termsAndPivacyP"/>.</p>
        </form:form>
        <c:url value="/login" var="logInUrl" />
        <div class="text-center" style="color: black;"><spring:message code="signUpBody.alert.logIn"/><a href="${logInUrl}" style="color: black;"><spring:message code="signUpBody.alert.btn.logIn"/></a>.</div>
    </div>
    </body>
</html>

