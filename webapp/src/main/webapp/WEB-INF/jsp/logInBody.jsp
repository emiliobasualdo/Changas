<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <style><%@ include file="/WEB-INF/css/loginBody.css" %></style>
    </head>
    <body>
        <div class="login-form">
            <!-- action="" -> aca ponemos que pasa cuando el usuario apreta en ingresar -->
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
                    <input id="password" name="j_password" class="form-control"/>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-lg btn-block"><spring:message code="logInBody.button"/></button>
                </div>
                <div class="clearfix">
                    <label class="pull-left checkbox-inline"><input type="checkbox" name="j_rememberme"><spring:message code="logInBody.checkbox"/></label>
                    <a href="#" class="pull-right"><spring:message code="loginBody.alert.forgotPassword"/></a>
                </div>
            </form>
            <p class="text-center small"><spring:message code="loginBody.alert.noAccount"/><a href="/signup"><spring:message code="loginBody.btn.singUp"/></a></p>
        </div>
    </body>
</html>