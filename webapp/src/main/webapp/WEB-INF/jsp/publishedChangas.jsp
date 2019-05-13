<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <body>
        <div class="auto-table">
            <c:forEach items="${publishedChangas}" var="changa">
                <c:set var="title" value="${changa.title}" scope="request"/>
                <c:set var="description" value="${changa.description}" scope="request"/>
                <c:set var="user_id" value="${changa.user_id}" scope="request"/>
                <c:set var="price" value="${changa.price}" scope="request"/>
                <c:set var="changa_id" value="${changa.changa_id}" scope="request"/>
                <c:set var="neighborhood" value="${changa.neighborhood}" scope="request"/>
                <c:set var="street" value="${changa.street}" scope="request"/>
                <c:set var="number" value="${changa.number}" scope="request"/>
                <c:import url="publishedChangaCard.jsp"/>
            </c:forEach>
        </div>
    </body>
</html>
