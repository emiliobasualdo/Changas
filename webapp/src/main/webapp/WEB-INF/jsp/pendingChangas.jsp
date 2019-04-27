<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <style><%@ include file="/WEB-INF/css/pendingChangas.css" %></style>
    </head>
    <body>
        <div class="auto-table">
            <c:forEach items="${pendingChangas}" var="changa">
                <c:set var="title" value="${changa.title}" scope="request"/>
                <c:set var="price" value="${changa.price}" scope="request"/>
                <c:set var="neighborhood" value="${changa.neighborhood}" scope="request"/>
                <c:set var="street" value="${changa.street}" scope="request"/>
                <c:set var="number" value="${changa.number}" scope="request"/>
                <c:set var="changa_id" value="${changa.changa_id}" scope="request"/>
                <%--<c:set var="state" value="${entry.getValue.state}" scope="request"/>--%>
                <c:import url="pendingChangaCard.jsp"/>
            </c:forEach>
        </div>
    </body>
</html>
