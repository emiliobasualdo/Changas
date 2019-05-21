<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <style><%@include file="/WEB-INF/css/formError.css"%></style>
    <style><%@include file="/WEB-INF/css/loginBody.css"%></style>
</head>
<body>
<div class="login-form">
    <c:url value="/signup/resend-email-verification" var="resendEmailVerificationUrl" />
    <form:form action="${resendEmailVerificationUrl}" method="post">
        <input type="hidden" name="token" value="<c:out value="${token}"/>">
        <input type="hidden" name="uri" value="<c:out value="${uri}"/>">
        <h2 class="text-center"><spring:message code="resendEmailVerificationBody.header"/></h2>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-lg btn-block"><spring:message code="resendEmailVerificationBody.reset.btn"/></button>
        </div>
    </form:form>
</div>
</body>
</html>

