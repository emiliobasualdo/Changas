<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!-- https://bootsnipp.com/snippets/K0ZmK -->
<head>
    <style>
        body{
            background: #26B3BA;
        }
        .emp-profile{
            padding: 3%;
            margin-top: 3%;
            margin-bottom: 3%;
            border-radius: 0.5rem;
            background: #fff;
        }
        .profile-img{
            font: 15px Montserrat, sans-serif;
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
            font: 15px Montserrat, sans-serif;
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
            font: 20px Montserrat, sans-serif;
            border: none;
        }
        .profile-head .nav-tabs .nav-link.active{
            border: none;
            border-bottom:2px solid #0062cc;
        }
        .profile-tab label{
            font-weight: 600;
            font: 15px Montserrat, sans-serif;
        }
        .profile-tab p{
            font-weight: 600;
            font: 15px Montserrat, sans-serif;
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
                                <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Mis datos</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Mis changas</a>
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
                    <div class="profile-head">
                        <h5 style="text-align: center; margin-top: 1cm">
                            <%--<c:out value="${profile.name}"/> <c:out value="${profile.surname}"/>--%>Jimena Lozano
                        </h5>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="tab-content profile-tab" id="myTabContent">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
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
                        <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                            <!-- copiamos lo que hay en mainPageBody -->
                            <c:import url="mainPageBody.jsp"/>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</body>
