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
            .margin {margin-bottom: 25px;}
            .bg-1 {
                background-color: #2db6bb; /* Green */
                color: #ffffff;
            }
            .container-fluid {
                padding-top: 25px;
                padding-bottom: 40px;
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
            <div class="container">No tenes una cuenta? &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a class="btn btn-primary" href="#">Sign In</a><a class="btn btn-primary" href="#">Log In</a>
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
                <button type="button" class="btn btn-primary btn-circle btn-xl" data-toggle="modal" data-target="#emitirChangaModal"><i class="fas fa-plus"></i></button>
                </button>
                <h4>Emitir changa</h4>
            </div>
        </div>

    </header>

    <!-- emitirChangaModal -->
    <div class="modal fade" id="emitirChangaModal" tabindex="-1" role="dialog" aria-labelledby="emitirChangaModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title" id="emitirChangaModalLabel">Crea tu changa</h3>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="inputName">Nombre</label>
                            <input type="text" class="form-control" id="inputName" placeholder="ingrese su nombre">
                        </div>
                        <div class="form-group">
                            <label for="inputPhone">Telefono</label>
                            <input type="text" class="form-control" id="inputPhone" placeholder="ingrese su telefono">
                        </div>
                        <div class="form-group">
                            <label for="inputTitle">Titulo</label>
                            <input type="text" class="form-control" id="inputTitle" placeholder="ingrese titulo representativo de su changa">
                        </div>
                        <div class="form-group">
                            <label for="inputDescription">Descripcion</label>
                            <textarea class="form-control" id="inputDescription" rows="3"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="inputNeighborhood">Lugar</label>
                            <input type="text" class="form-control" id="inputNeighborhood" placeholder="ingrese lugar">
                        </div>
                        <div class="form-group">
                            <label for="inputPrice">Precio</label>
                            <input type="number" class="form-control" id="inputPrice" placeholder="ingrese precio">
                            <%--<input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">--%>
                            <%--<small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>--%>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary">Emitir</button>
                </div>
            </div>
        </div>
    </div>

</html>
