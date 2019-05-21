<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <script>

            $(window).on('load', function() {
                var pathname = window.location.pathname;
                if (pathname === '/login/error' || pathname === '/login/resend-email-verification') {
                    $('#errorModal').modal('toggle');
                }
            });

        </script>
        <style><%@include file="/WEB-INF/css/formError.css"%></style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <style><%@ include file="/WEB-INF/css/loginBody.css" %></style>
    </head>
    <body>
        <div class="login-form">
            <c:url value="/login" var="loginUrl" />
            <form action="${loginUrl}" method="post">
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
                <div class="form-group clearfix" style="text-align: center">
                    <label class="pull-left checkbox-inline" style="margin-left: 3px"><input type="checkbox" name="j_rememberme"><spring:message code="logInBody.checkbox"/></label>
                </div>
            </form>
            <div style="text-align: center; margin-top: auto;">
                <c:url value="/login/forgot-password" var="forgotPasswordUrl" />
                <a href="${forgotPasswordUrl}" class="pull-right text-center small"><spring:message code="loginBody.alert.forgotPassword"/></a>
                <c:url value="/signup" var="signupUrl" />
                <p class="text-center small"><spring:message code="loginBody.alert.noAccount"/><a href="${signupUrl}"><spring:message code="loginBody.btn.singUp"/></a></p>
            </div>
        </div>
    </body>

    <div class="modal fade" id="errorModal"  tabindex="-1" role="dialog" aria-labelledby="errorModalLabel" aria-hidden="true" >
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="error-form">
                    <c:url value="/login/resend-email-verification" var="resendEmailVerificationUrl" />
                    <form:form class="error-form" action="${resendEmailVerificationUrl}" modelAttribute="emailForm" method="post">
                        <h5 class="text-center"><spring:message code="loginBody.alert.badInput"/></h5>
                        <hr>
                        <h5 class="text-center"><spring:message code="loginBody.alert.accountVerification"/></h5>
                        <div class="form-group">
                            <p class="small text-center"><spring:message code="loginBody.alert.accountVerificationMessage"/></p>
                            <form:input class="form-control" path="mail"/>
                            <form:errors cssClass="form-error" path="mail" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:button type="submit" class="btn btn-primary btn-lg btn-block"><spring:message code="resendEmailVerificationBody.reset.btn"/></form:button>
                            <form:button data-dismiss="modal" type="submit" class="btn btn-primary btn-lg btn-block">OK</form:button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

</html>