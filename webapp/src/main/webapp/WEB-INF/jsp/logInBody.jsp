<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <%--<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>
    <style type="text/css">
        @import url(https://fonts.googleapis.com/css?family=Josefin+Sans:400,300,300italic,400italic,600,700,600italic,700italic);
        body {
            color: #fff;
            background: #26B3BA;
            font-family: "Josefin Sans", sans-serif;
            line-height: 1;
            padding: 20px;
            height: 100%;
        }
        .login-form {
            width: 350px;
            margin: 0 auto;
            padding: 100px 0 30px;
        }
        .login-form form {
            color: #7a7a7a;
            border-radius: 2px;
            margin-bottom: 15px;
            font-size: 13px;
            background: #ececec;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
            position: relative;
        }
        .login-form h2 {
            font-size: 22px;
            margin: 35px 0 25px;
        }
        .login-form .avatar {
            position: absolute;
            margin: 0 auto;
            left: 0;
            right: 0;
            top: -50px;
            width: 95px;
            height: 95px;
            border-radius: 50%;
            z-index: 9;
            background: #70c5c0;
            padding: 15px;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
        }
        .login-form .avatar img {
            width: 100%;
        }
        .login-form input[type="checkbox"] {
            margin-top: 2px;
        }
        .login-form .btn {
            font-size: 16px;
            font-weight: bold;
            background: #70c5c0;
            border: none;
            margin-bottom: 20px;
        }
        .login-form .btn:hover, .login-form .btn:focus {
            background: #50b8b3;
            outline: none !important;
        }
        .login-form a {
            color: #fff;
            text-decoration: underline;
        }
        .login-form a:hover {
            text-decoration: none;
        }
        .login-form form a {
            color: #7a7a7a;
            text-decoration: none;
        }
        .login-form form a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-form">
    <!-- action="" -> aca ponemos que pasa cuando el usuario apreta en ingresar -->
    <form:form action="/logIn" modelAttribute="UserLoginForm" method="post">
        <div class="avatar">
            <img src="https://i.imgur.com/dGo8DOk.jpg" alt="Avatar">
        </div>
        <h2 class="text-center"><spring:message code="logInBody.header"/></h2>
        <div class="form-group">
            <form:label path="username"><spring:message code="UserLoginForm.username"/></form:label>
            <form:input path="username" class="form-control" />
        </div>
        <div class="form-group">
            <form:label path="password"><spring:message code="UserLoginForm.password"/></form:label>
            <form:input path="password" class="form-control"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-lg btn-block"><spring:message code="logInBody.button"/></button>
        </div>
        <div class="clearfix">
            <label class="pull-left checkbox-inline"><input type="checkbox"><spring:message code="logInBody.checkbox"/></label>
            <a href="#" class="pull-right"><spring:message code="loginBody.alert.forgotPassword"/></a>
        </div>
    </form:form>
    <p class="text-center small"><spring:message code="loginBody.alert.noAccount"/><a href="/signUp"><spring:message code="loginBody.btn.singUp"/></a></p>
</div>
</body>
</html>