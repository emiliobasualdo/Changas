
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <style>
            body {
                font: 20px Montserrat, sans-serif;
                line-height: 1.8;
            }
            p {font-size: 16px;}
            .margin {margin-bottom: 45px;}
            .bg-1 {
                background-color: #1abc9c; /* Green */
                color: #ffffff;
            }
            .container-fluid {
                padding-top: 70px;
                padding-bottom: 70px;
            }
            .navbar {
                margin-bottom: 0;
            }
            .img-center {
                display: block;
                margin-left: auto;
                margin-right: auto
            }
            .btn-circle {
                margin-top: 40px;
                width: 70px;
                height: 70px;
                padding: 10px 16px;
                border-radius: 35px;
                font-size: 24px;
                line-height: 1.33;
            }
        </style>
    </head>

    <header>

        <nav class="navbar navbar-light bg-light static-top">
            <div class="container">No tenes una cuenta? &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a class="btn btn-primary" href="#">Sign In</a><a class="btn btn-primary" href="#">Log In</a>
            </div>
        </nav>

        <div class="container-fluid bg-1 text-center">
            <div>
                <h1 class="margin">Changas</h1>
            </div>
            <div>
                <img src="http://ultimominuto.com.mx/wp-content/uploads/2019/01/c670f2b2-michael-jackson-2018-1.jpg" class="img-responsive img-circle img-center"  width="200" height="400" >
            </div>
            <div>
                <button id="emitirButton" type="button" class="btn btn-primary btn-circle btn-xl" data-toggle="modal" data-target="#emitirChangaModal"><i class="fas fa-plus"></i></button>
                <h4>Emitir changa</h4>
            </div>
        </div>

    </header>

    <jsp:include page="/WEB-INF/jsp/createChangaForm.jsp"/>

</html>

