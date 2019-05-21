<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- https://bootsnipp.com/snippets/K0ZmK -->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <style><%@ include file="/WEB-INF/css/profileBody.css" %></style>
    </head>
    <body>
        <div class="container emp-profile" style="margin-top: 60px">
                <div class="row">
                    <div class="col-xs-6 col-sm-6 col-md-8" id="div1">
                        <div class="profile-head">
                            <div class="container custom-container">
                                <div class="profile-img">
                                    <c:url value="${urlImage}" var="mudanzaImage"/>
                                    <img src="${mudanzaImage}" alt="Changa Image">
                                    <div class="file btn btn-lg btn-primary">
                                        <form method="POST" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data">
                                            <input type="file" name="file" /><br/>
                                            <button type="submit"><spring:message code="profileBody.nav.photo"/></button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="container">
                                <h5><c:out value="${getLoggedUser.name}"/> <c:out value="${getLoggedUser.surname}"/></h5>
                                <h6><c:out value="${getLoggedUser.email}"/></h6>
                                <p><c:out value="${getLoggedUser.tel}"/></p>
                            </div>

                            <ul class="nav nav-tabs" id="myTab" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active" id="published-changas-tab" data-toggle="tab" href="#published" role="tab" aria-controls="published" aria-selected="true"><spring:message code="profileBody.nav.publish"/></a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" id="pending-changas-tab" data-toggle="tab" href="#pending" role="tab" aria-controls="pending" aria-selected="false"><spring:message code="profileBody.nav.inscribed"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-xs-6 col-md-4" style="float: right" id="div2">
                        <p>
                            <c:url value="/edit-profile" var="editProfileUrl" />
                            <a href="${editProfileUrl}" class="profile-edit-btn"><spring:message code="profileBody.nav.edit"/></a>
                        </p>
                        <p>
                            <c:url value="/edit-password" var="resetPasswordUrl" />
                            <a href="${resetPasswordUrl}" class="profile-edit-btn"><spring:message code="profileBody.nav.reset"/></a>
                        </p>
                    </div>
                </div>
                <div class="row">
                        <div class="tab-content profile-tab" id="myTabContent">
                            <div class="tab-pane fade show active" id="published" role="tabpanel" aria-labelledby="published-changas-tab">

                                <%@ include file="/WEB-INF/jsp/publishedChangas.jsp"%>

                            </div>
                            <div class="tab-pane fade" id="pending" role="tabpanel" aria-labelledby="pending-changas-tab">

                                <%@ include file="/WEB-INF/jsp/pendingChangas.jsp" %>

                            </div>
                        </div>
                </div>
        </div>
    </body>
</html>