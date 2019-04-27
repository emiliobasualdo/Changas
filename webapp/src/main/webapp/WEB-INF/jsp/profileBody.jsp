<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- https://bootsnipp.com/snippets/K0ZmK -->
<html>
    <head>
        <style><%@ include file="/WEB-INF/css/profileBody.css" %></style>
        <style><%@ include file="/WEB-INF/css/publishedChangas.css" %></style>
    </head>
    <body>
        <div class="container emp-profile">
            <%--TODO: cabiar form a a un spring form para que por beans se mapen las varibales y poder cambiar el contendido de las mismas--%>
            <%--<form method="post">--%>
                <div class="row">
                    <%-- todo
                    <div class="col-md-4">
                        <div class="profile-img">
                            <img src="https://i.imgur.com/dGo8DOk.jpg" alt=""/>
                            <div class="file btn btn-lg btn-primary">
                                <spring:message code="label.changePhoto"/>
                                <input type="file" name="file"/>
                            </div>
                        </div>
                    </div>--%>
                    <%--<div class="col-md-8">--%>
                        <div class="profile-head">

                            <%--DATOS DEL USUARIO--%>
                            <h5><c:out value="${profile.name}"/> <c:out value="${profile.surname}"/></h5>
                            <h6><c:out value="${profile.email}"/></h6>
                            <p><c:out value="${profile.tel}"/></p>

                            <%--TABS--%>
                            <ul class="nav nav-tabs" id="myTab" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active" id="published-changas-tab" data-toggle="tab" href="#published" role="tab" aria-controls="published" aria-selected="true"><spring:message code="profileBody.nav.publish"/></a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" id="pending-changas-tab" data-toggle="tab" href="#pending" role="tab" aria-controls="pending" aria-selected="false"><spring:message code="profileBody.nav.inscribed"/></a>
                                </li>
                            </ul>
                        </div>
                    <%--</div>--%>
                    <div class="col-md-2">
                        <!--<input type="submit" class="profile-edit-btn" name="btnAddMore" value="Editar perfil"/> todo: para mas adelante estaria bueno-->
                    </div>
                </div>
                <div class="row">
                    <%--<div class="col-md-4">
                        <div class="profile-head" style="text-align: center">
                            <div class="row">
                                <div class="col-md-6">
                                    <label><spring:message code="label.name"/></label>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${profile.name}"/> <c:out value="${profile.surname}"/></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label><spring:message code="label.mail"/></label>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${profile.email}"/></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label><spring:message code="label.telephone"/></label>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${profile.tel}"/></p>
                                </div>
                            </div>
                        </div>
                    </div>--%>
                    <%--<div class="col-md-8">--%>
                        <div class="tab-content profile-tab" id="myTabContent">
                            <div class="tab-pane fade show active" id="published" role="tabpanel" aria-labelledby="published-changas-tab">

                                <%@ include file="/WEB-INF/jsp/publishedChangas.jsp"%>

                            </div>
                            <div class="tab-pane fade" id="pending" role="tabpanel" aria-labelledby="pending-changas-tab">

                                <%@ include file="/WEB-INF/jsp/pendingChangas.jsp" %>

                            </div>
                        </div>
                    <%--</div>--%>
                </div>
            <%--</form>--%>
        </div>
    </body>
</html>