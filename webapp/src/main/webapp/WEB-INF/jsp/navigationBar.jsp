<%--
  Created by IntelliJ IDEA.
  User: jimenalozano
  Date: 3/4/19
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<html>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
    <div class="container" style="background-color: #26B3BA">
        <a class="navbar-brand" href="#">CHANGAS</a>
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
                    <a class="nav-link" href="#">Registrate</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Ingresar</a>
                </li>
                <li class="nav-item" style="margin-left: 15cm">
                    <a class="nav-link" href="#" data-toggle="modal" data-target="#issueChangaModal">Emitir changa</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<jsp:include page="issueChangaForm.jsp"/>

</html>
