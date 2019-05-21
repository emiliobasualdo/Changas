<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style><%@include file="/WEB-INF/css/formError.css"%></style>
    <style><%@include file="/WEB-INF/css/loginBody.css"%></style>
</head>
<body>
<div class="login-form">
    <c:url value="/login/forgot-password" var="forgotPasswordUrl" />
    <form:form action="${forgotPasswordUrl}" modelAttribute="forgotPasswordForm" method="post">
        <h2 class="text-center"><spring:message code="resetPasswordBody.header"/></h2>
        <p class="small text-center"><spring:message code="forgotPasswordBody.info"/>.</p>
        <div class="form-group">
            <form:label path="mail"><spring:message code="resetPasswordBody.mail"/></form:label>
            <form:input path="mail" class="form-control" />
            <form:errors cssClass="form-error" path="mail" element="p"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-lg btn-block"><spring:message code="resetPasswordBody.reset.btn"/></button>
        </div>
    </form:form>
</div>
</body>
</html>