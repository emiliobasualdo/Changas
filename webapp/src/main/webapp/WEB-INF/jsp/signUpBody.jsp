<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <style type="text/css">
        @import url(https://fonts.googleapis.com/css?family=Josefin+Sans:400,300,300italic,400italic,600,700,600italic,700italic);
        body {
            color: #999;
            background: #26B3BA;
            font-family: "Josefin Sans", sans-serif;
            line-height: 1;
            padding: 20px;
            height: 100%;
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
    <c:url value="/createUser" var="create" />
    <form:form action="${create}" modelAttribute="signUpForm" method="post">
        <h2><spring:message code="signUpBody.header"/></h2>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <form:label path="name"><spring:message code="UserRegisterForm.name"/></form:label>
                <form:input class="form-control" path="name" />
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <form:label path="surname"><spring:message code="UserRegisterForm.surname"/></form:label>
                <form:input class="form-control" path="surname" />
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <form:label path="username"><spring:message code="UserRegisterForm.username"/></form:label>
                <form:input class="form-control" path="username" />
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-phone"></i></span>
                <form:label path="telephone"><spring:message code="UserRegisterForm.telephone"/></form:label>
                <form:input class="form-control" path="telephone" />
                <form:errors path="telephone">error wach</form:errors>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-paper-plane"></i></span>
                <form:label path="email"><spring:message code="UserRegisterForm.email"/></form:label>
                <form:input class="form-control" path="email" />
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                <form:label path="password"><spring:message code="UserRegisterForm.password"/></form:label>
                <form:input class="form-control" path="password" />
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
				<span class="input-group-addon">
					<i class="fa fa-lock"></i>
					<i class="fa fa-check"></i>
				</span>
                <form:label path="repeatPassword"><spring:message code="UserRegisterForm.repeatPassword"/></form:label>
                <form:input class="form-control" path="repeatPassword"/>
            </div>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block btn-lg"><spring:message code="signUpBody.button"/></button>
        </div>
        <p class="small text-center"><spring:message code="signUpBody.alert.termsAndPivacyP"/><%--<br><a href="#">Terms &amp; Conditions</a>, and <a href="#">Privacy Policy</a>--%>.</p>
    </form:form>
    <div class="text-center" style="color: black;"><spring:message code="signUpBody.alert.logIn"/><a href="/logIn" style="color: black;"><spring:message code="signUpBody.alert.btn.logIn"/></a>.</div>
</div>
</body>
</html>

