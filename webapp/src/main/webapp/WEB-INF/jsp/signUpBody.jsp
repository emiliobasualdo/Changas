<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%--<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
    <title>Bootstrap Elegant Sign Up Form with Icons</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>
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
    <!-- action="" -> aca ponemos que pasa cuando el usuario apreta en ingresar -->
    <form:form action="/createUser" modelAttribute="signUpForm" method="post">
        <h2>Create una cuenta</h2>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <form:label path="name">Nombre</form:label>
                <form:input class="form-control" path="name" placeholder="Nombre" required="required"/>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <form:label path="surname">Apellido</form:label>
                <form:input class="form-control" path="surname" placeholder="Apellido" required="required"/>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon">
                    <i class="fa fa-user"></i>
                    <form:label path="username">Nombre de usuario</form:label>
                </span>
                <form:input class="form-control" path="username" placeholder="Usuario" required="required"/>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-phone"></i></span>
                <form:label path="telephone">Telefono</form:label>
                <form:input class="form-control" path="telephone" placeholder="Telefono" required="required"/>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-paper-plane"></i></span>
                <form:label path="email">Email</form:label>
                <form:input class="form-control" path="email" placeholder="Email"/>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                <form:label path="password">Contrasena</form:label>
                <form:input class="form-control" path="password" placeholder="Contrasena" required="required"/>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
				<span class="input-group-addon">
					<i class="fa fa-lock"></i>
					<i class="fa fa-check"></i>
				</span>
                <form:label path="repeatPassword"></form:label>
                <form:input class="form-control" path="repeatPassword" placeholder="Confirmar contrasena" required="required"/>
            </div>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block btn-lg">Registrarse</button>
        </div>
        <p class="small text-center">Clickeando en el boton de registracion, estas de acuerdo con nuestros Terminos de Condiciones y Politica de Privacidad <%--<br><a href="#">Terms &amp; Conditions</a>, and <a href="#">Privacy Policy</a>--%>.</p>
    </form:form>
    <div class="text-center">Ya tenes una cuenta? <a href="login">Ingresa aca</a>.</div>
</div>
</body>
</html>

