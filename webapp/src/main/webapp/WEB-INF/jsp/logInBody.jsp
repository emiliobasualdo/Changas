<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <style><%@ include file="/WEB-INF/css/loginBody.css" %></style>
    </head>
    <body>
        <div class="login-form">
            <c:url value="/login" var="loginUrl" />
            <form action="${loginUrl}"  method="post">
                <div class="avatar">
                    <img src="https://i.imgur.com/dGo8DOk.jpg" alt="Avatar">
                </div>
                <h2 class="text-center"><spring:message code="logInBody.header"/></h2>
                <div class="form-group">
                    <label for="username"><spring:message code="UserLoginForm.username"/></label>
                    <input id="username"  name="j_username" class="form-control" />
                </div>
                <div class="form-group">
                    <label for="password"><spring:message code="UserLoginForm.password"/></label>
                    <input id="password" type="password" name="j_password" class="form-control"/>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-lg btn-block"><spring:message code="logInBody.button"/></button>
                </div>
                <div class="clearfix" style="text-align: center">
                    <label class="pull-left checkbox-inline" style="margin-left: 3px"><input type="checkbox" name="j_rememberme"><spring:message code="logInBody.checkbox"/></label>
                </div>
            </form>
            <c:url value="/login/forgot-password" var="forgotPasswordUrl" />
            <div style="text-align: center">
                <a href="${forgotPasswordUrl}" class="pull-right text-center small"><spring:message code="loginBody.alert.forgotPassword"/></a>
                <c:url value="/signup" var="signupUrl" />
                <p class="text-center small"><spring:message code="loginBody.alert.noAccount"/><a href="${signupUrl}"><spring:message code="loginBody.btn.singUp"/></a></p>
            </div>
        </div>
    </body>
</html>