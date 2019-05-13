<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <style><%@include file="/WEB-INF/css/error.css"%></style>
</head>

<div class="container">

    <svg id="Layer_1" xmlns="http://www.w3.org/2000/svg" width="795.9" height="433" viewBox="0 0 795.9 433"/>

    <div class="text">
        <div class="text-inner">
            <h1> <spring:message code="error.title"/> </h1>

            <h2> <c:out value="${message}"/> </h2>

            <c:url value="/" var="homeUrl" />
            <a href="${homeUrl}">
                <div id="home" class="button">
                    <h3> <spring:message code="error.home.btn"/> </h3>
                </div>
            </a>
        </div>

    </div>

</div>
</html>
