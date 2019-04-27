<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <head>
        <meta charset="utf-32">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    </head>
    <style type="text/css">
        body {
            color: #999;
            background: #26B3BA;
        }
        .form-control, .form-control:focus, .input-group-addon {
            border-color: #e1e1e1;
            border-radius: 0;
        }
        .signup-form {
            width: 390px;
            margin: 0 auto;
            padding: 30px 0;
        }
        .signup-form h2 {
            color: #636363;
            margin: 0 0 15px;
            text-align: center;
        }
        .signup-form .lead {
            font-size: 14px;
            margin-bottom: 30px;
            text-align: center;
        }
        .signup-form form {
            border-radius: 1px;
            margin-bottom: 15px;
            background: #fff;
            border: 1px solid #f3f3f3;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }
        .signup-form .form-group {
            margin-bottom: 20px;
        }
        .signup-form label {
            font-weight: normal;
            font-size: 13px;
        }
        .signup-form .form-control {
            min-height: 38px;
            box-shadow: none !important;
            border-width: 0 0 1px 0;
        }
        .signup-form .input-group-addon {
            max-width: 42px;
            text-align: center;
            background: none;
            border-width: 0 0 1px 0;
            padding-left: 5px;
        }
        .signup-form .btn {
            font-size: 16px;
            font-weight: bold;
            background: #26B3BA;
            border-radius: 3px;
            border: none;
            min-width: 140px;
            outline: none !important;
        }
        .signup-form .btn:hover, .signup-form .btn:focus {
            background: #26B3BA;
        }
        .signup-form a {
            color: #26B3BA;
            text-decoration: none;
        }
        .signup-form a:hover {
            text-decoration: underline;
        }
        .signup-form .fa {
            font-size: 21px;
        }
        .signup-form .fa-paper-plane {
            font-size: 17px;
        }
        .signup-form .fa-check {
            color: #fff;
            left: 9px;
            top: 18px;
            font-size: 7px;
            position: absolute;
        }
    </style>
</head>
<body>
<div class="signup-form">
    <c:url value="/create-changa" var="createUrl" />
    <form:form method="post" modelAttribute="changaForm" action="${createUrl}">
        <h2><spring:message code="issueChangaForm.header"/></h2>
        <div class="form-group">
            <div class="input-group">
                <form:label path="title"><spring:message code="ChangaForm.title"/></form:label>
                <form:input class="form-control" path="title"/>
                <form:errors path="title" element="p">Inválido</form:errors>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <form:label path="description"><spring:message code="ChangaForm.description"/></form:label>
                <form:textarea rows="3" class="form-control" path="description"/>
                <form:errors path="description" element="p">Inválido</form:errors>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <form:label path="neighborhood"><spring:message code="ChangaForm.neighborhood"/></form:label>
                <form:input class="form-control" path="neighborhood"/>
                <form:errors path="neighborhood" element="p">Inválido</form:errors>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <form:label path="street"><spring:message code="ChangaForm.street"/></form:label>
                <form:input class="form-control" path="street"/>
                <form:errors path="street" element="p">Inválido</form:errors>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <form:label path="number"><spring:message code="ChangaForm.number"/></form:label>
                <form:input class="form-control" path="number"/>
                <form:errors path="number" element="p">Inválido</form:errors>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <form:label path="price"><spring:message code="ChangaForm.price"/></form:label>
                <form:input class="form-control" path="price"/>
                <form:errors path="price" element="p">Inválido</form:errors>
            </div>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block btn-lg"><spring:message code="issueChangaForm.btn"/></button>
        </div>
    </form:form>
</div>
</body>
</html>

