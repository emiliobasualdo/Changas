<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
                <c:choose>
                    <c:when test="${currentUser == null}">
                        <li class="nav-item">
                            <a class="nav-link" href="/signUp"><spring:message code="navigationBar.btn.signUp"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/logIn"><spring:message code="navigationBar.btn.logIn"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/logIn"><spring:message code="navigationBar.btn.profile"/></a>
                            <br />
                        </li>
                        <li class="nav-item" style="margin-left: 15cm">
                            <a class="nav-link" href="/createChanga"><spring:message code="navigationBar.btn.createChanga"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <%-- TODO: saludo al usuario --%>
                        <%--<li>
                            <label style="margin-top: 0.5cm">Hola, <c:out value="${currentUser.name}"/> </label>
                        </li>--%>
                        <li class="nav-item">
                            <a class="nav-link" href="profile?id=<c:out value="${currentUser.user_id}"/>"><spring:message code="navigationBar.btn.profile"/></a>
                            <br />
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/signUp"><spring:message code="navigationBar.btn.logOut"/></a>
                        </li>
                        <li class="nav-item" style="margin-left: 15cm">
                            <a class="nav-link" href="/createChanga"><spring:message code="navigationBar.btn.createChanga"/></a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
</body>

</html>
