<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Changas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <style>
        body {
            font: 18px Montserrat, sans-serif;
            line-height: 1.8;
        }
        p {font-size: 16px;}navbar {
                                margin-bottom: 0;
                            }
        .masthead {
            height: 50px;
            min-height: 500px;
            background-image: url('https://www.inmo-santander.com/wp-content/uploads/2019/01/mudanzas.jpg');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
    <div class="container" style="background-color: #26B3BA">
        <img src="https://svgsilh.com/svg/1901457.svg" style="width: 1cm; height: 1cm" \>
        <a class="navbar-brand" href="/">CHANGAS</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <%--<li class="nav-item active">
                    <a class="nav-link" href="#">Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>--%>
                <li class="nav-item">
                    <a class="nav-link" href="/signUp">Registrarse</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/logIn">Ingresar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/profile?id=3">Perfil</a>
                </li>
                <li class="nav-item" style="margin-left: 15cm">
                    <a class="nav-link" href="/createChanga">Emitir Changa</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
</body>

</html>
