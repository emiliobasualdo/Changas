<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- https://bootsnipp.com/snippets/K0ZmK -->
<html>
<head>
    <style><%@ include file="/WEB-INF/css/profileBody.css" %></style>
</head>
<body>
<div class="container emp-profile">
    <div class="row">
        <div class="profile-head">

            <%--DATOS DEL USUARIO--%>
            <h5>Administrando</h5>

            <%--TABS--%>
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="published-changas-tab" data-toggle="tab" href="#published" role="tab" aria-controls="published" aria-selected="true">Changas reportadas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="pending-changas-tab" data-toggle="tab" href="#pending" role="tab" aria-controls="pending" aria-selected="false">Estadísticas</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="tab-content profile-tab" id="myTabContent">
            <div class="tab-pane fade show active" id="published" role="tabpanel" aria-labelledby="published-changas-tab">

                <%@ include file="/WEB-INF/jsp/reportedChangas.jsp"%>

            </div>
            <div class="tab-pane fade" id="pending" role="tabpanel" aria-labelledby="pending-changas-tab">



            </div>
        </div>
    </div>
</div>
</body>
</html>