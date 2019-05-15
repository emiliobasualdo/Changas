<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <style><%@include file="/WEB-INF/css/loginBody.css"%></style>
    <style><%@include file="/WEB-INF/css/formError.css"%></style>
</head>
<body>
<div class="login-form">
    <c:url value="${actionUrl}" var="reserPasswordUrl" />
    <form:form action="${reserPasswordUrl}" modelAttribute="resetPasswordForm" method="post">
        <input type="hidden" name="id" value="<c:out value="${id}"/>">
        <h2 class="text-center"><spring:message code="resetPasswordBody.header"/></h2>
        <div class="form-group">
            <form:label path="newPassword1"><spring:message code="resetPasswordBody.newPassword1"/></form:label>
            <form:password path="newPassword1" class="form-control" />
            <form:errors cssClass="form-error" path="newPassword1" element="p"/>
        </div>
        <div class="form-group">
            <form:label path="newPassword2"><spring:message code="resetPasswordBody.newPassword2"/></form:label>
            <form:password path="newPassword2" class="form-control"/>
            <form:errors cssClass="form-error" path="newPassword2" element="p"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-lg btn-block"><spring:message code="resetPasswordBody.reset.btn"/></button>
        </div>
    </form:form>
</div>
</body>
</html>