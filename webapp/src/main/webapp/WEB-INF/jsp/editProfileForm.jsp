<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Changas</title>
    <meta charset="utf-32">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <style><%@ include file="/WEB-INF/css/signUpBody.css" %></style>
    <style><%@include file="/WEB-INF/css/formError.css"%></style>
</head>
<body>
<div class="signup-form">
    <c:url value="/edit-profile" var="editProfileUrl" />
    <form:form modelAttribute="userForm" action="${editProfileUrl}" method="post">
        <h2><spring:message code="editProfile.header"/></h2>
        <table style="width: 100%">
            <tr>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                            <form:label path="name"><spring:message code="UserRegisterForm.name"/></form:label>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <form:input class="form-control" path="name" />
                        </div>
                        <form:errors cssClass="form-error" path="name" element="p"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-group">
                        <div class="input-group">
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
                        <div class="input-group">
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
        </table>

        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block btn-lg"><spring:message code="editProfile.btn"/></button>
        </div>
    </form:form>
</div>
</body>
</html>

