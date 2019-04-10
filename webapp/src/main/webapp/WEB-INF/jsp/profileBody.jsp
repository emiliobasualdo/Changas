<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!-- https://bootsnipp.com/snippets/K0ZmK -->
<head>
    <style>
        @import url(https://fonts.googleapis.com/css?family=Josefin+Sans:400,300,300italic,400italic,600,700,600italic,700italic);
        body {
            font-family: "Josefin Sans", sans-serif;
            line-height: 1;
            padding: 20px;
            height: 100%;
            background: white;
        }
        .changaCard-option {
            /*width: 32%;*/
            background: white;
            float: left;
            padding: 2%;
            text-align: center;
            transition: all .3s ease-in-out;
        }
        /*.changaCard-option:nth-child(even) {*/
        /*margin: 0 2%;*/
        /*}*/
        .changaCard-option:hover {
            cursor: pointer;
            box-shadow: 0px 2px 30px rgba(0, 0, 0, 0.3);
            -webkit-transform: scale(1.04);
            transform: scale(1.04);
        }
        .changaCard-option:hover i,  .changaCard-option:hover h1,  .changaCard-option:hover span,  .changaCard-option:hover b {
            color: #26B3BA;
        }
        .changaCard-option:hover .front {
            opacity: 0;
            visibility: hidden;
        }
        .changaCard-option:hover .back {
            opacity: 1 !important;
            visibility: visible !important;
        }
        .changaCard-option:hover .back a.button {
            -webkit-transform: translateY(0px) !important;
            transform: translateY(0px) !important;
        }
        .changaCard-option hr {
            border: none;
            border-bottom: 1px solid #F0F0F0;
        }
        .changaCard-option i {
            font-size: 3rem;
            color: #D8D8D8;
            transition: all .3s ease-in-out;
        }
        .changaCard-option h1 {
            margin: 10px 0;
            color: #212121;
            transition: all .3s ease-in-out;
        }
        .changaCard-option p {
            color: #999;
            padding: 0 10px;
            line-height: 1.3;
        }
        .changaCard-option .price {
            position: relative;
        }
        .changaCard-option .price .front span.price {
            font-size: 2rem;
            text-transform: uppercase;
            margin-top: 20px;
            display: block;
            font-weight: 700;
            position: relative;
        }
        .changaCard-option .price .front span.price b {
            position: absolute;
            font-size: 1rem;
            margin-left: 2px;
            font-weight: 600;
        }
        .changaCard-option .price .back {
            opacity: 0;
            visibility: hidden;
            transition: all .3s ease-in-out;
        }
        .changaCard-option .price .back a.button {
            background: #26B3BA;
            padding: 15px 20px;
            display: inline-block;
            text-decoration: none;
            color: white;
            position: absolute;
            font-size: 13px;
            top: -5px;
            left: 0;
            right: 0;
            width: 150px;
            margin: auto;
            text-transform: uppercase;
            -webkit-transform: translateY(20px);
            transform: translateY(20px);
            transition: all .3s ease-in-out;
        }
        .changaCard-option .price .back a.button:hover {
            background: #f62d3d;
        }

        @media screen and (max-width: 600px) {
            .changaCard-option {
                padding: 5%;
                width: 90%;
            }
            .changaCard-option:nth-child(even) {
                margin: 30px 0 !important;
            }
        }
        .emp-profile{
            padding: 3%;
            margin-top: 3%;
            margin-bottom: 3%;
            border-radius: 0.5rem;
            background: #fff;
        }
        .profile-img{
            font-family: "Josefin Sans", sans-serif;
            font-size: 20px;
            text-align: center;
        }
        .profile-img img{
            width: 40%;
            height: 20%;
        }
        .profile-img .file {
            position: relative;
            overflow: hidden;
            margin-top: -20%;
            width: 70%;
            border: none;
            border-radius: 0;
            font-family: "Josefin Sans", sans-serif;
            font-size: 15px;
            background: #212529b8;
        }
        .profile-img .file input {
            position: absolute;
            opacity: 0;
            right: 0;
            top: 0;
        }
        .profile-head h5{
            color: #333;
        }
        .profile-head h6{
            color: #26B3BA;
        }
        .profile-edit-btn{
            border: none;
            border-radius: 1.5rem;
            width: 70%;
            padding: 2%;
            font-weight: 600;
            font: 15px Montserrat, sans-serif;
            color: #6c757d;
            cursor: pointer;
        }
        .profile-head .nav-tabs{
            margin-bottom:5%;
        }
        .profile-head .nav-tabs .nav-link{
            font-weight:600;
            font-family: "Josefin Sans", sans-serif;
            font-size: 20px;
            border: none;
        }
        .profile-head .nav-tabs .nav-link.active{
            border: none;
            border-bottom:2px solid #26B3BA;
        }
        .profile-tab label{
            font-weight: 600;
            font-family: "Josefin Sans", sans-serif;
            font-size: 20px;
        }
        .profile-tab p{
            font-weight: 600;
            font-family: "Josefin Sans", sans-serif;
            font-size: 20px;
            color: #000000;
        }
    </style>
</head>
<body>
    <div class="container emp-profile">
        <form method="post">
            <div class="row">
                <div class="col-md-4">
                    <div class="profile-img">
                        <img src="https://i.imgur.com/dGo8DOk.jpg" alt=""/>
                        <div class="file btn btn-lg btn-primary">
                            Cambiar foto
                            <input type="file" name="file"/>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="profile-head">
                        <!--<h5>
                            <%--<c:out value="${profile.name}"/> <c:out value="${profile.surname}"/>--%>Jimena Lozano
                        </h5> -->
                        <ul class="nav nav-tabs" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="published-changas-tab" data-toggle="tab" href="#published" role="tab" aria-controls="published" aria-selected="true">Changas publicadas</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="pending-changas-tab" data-toggle="tab" href="#pending" role="tab" aria-controls="pending" aria-selected="false">Changas anotadas</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-2">
                    <!--<input type="submit" class="profile-edit-btn" name="btnAddMore" value="Editar perfil"/> todo: para mas adelante estaria bueno-->
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="profile-head" style="text-align: center">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Nombre</label>
                            </div>
                            <div class="col-md-6">
                                <p><c:out value="${profile.name}"/> <c:out value="${profile.surname}"/></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Email</label>
                            </div>
                            <div class="col-md-6">
                                <p><c:out value="${profile.email}"/></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Telefono</label>
                            </div>
                            <div class="col-md-6">
                                <p><c:out value="${profile.tel}"/></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="tab-content profile-tab" id="myTabContent">
                        <div class="tab-pane fade show active" id="published" role="tabpanel" aria-labelledby="published-changas-tab">

                            <jsp:include page="publishedChangas.jsp"/>

                        </div>
                        <div class="tab-pane fade" id="pending" role="tabpanel" aria-labelledby="pending-changas-tab">

                            <jsp:include page="pendingChangas.jsp"/>

                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</body>
