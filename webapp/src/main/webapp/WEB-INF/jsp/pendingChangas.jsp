<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
    <body>
        <div class="auto-table">
            <c:forEach var="entry" items="${pendingChangas}" >
                <c:set var="title" value="${entry.key.title}" scope="request"/>
                <c:set var="price" value="${entry.key.price}" scope="request"/>
                <c:set var="neighborhood" value="${entry.key.neighborhood}" scope="request"/>
                <c:set var="street" value="${entry.key.street}" scope="request"/>
                <c:set var="number" value="${entry.key.number}" scope="request"/>
                <c:set var="changa_id" value="${entry.key.changa_id}" scope="request"/>
                <c:set var="state" value="${entry.value.state}" scope="request"/>
                <c:import url="pendingChangaCard.jsp"/>
            </c:forEach>
        </div>
    </body>
</html>
