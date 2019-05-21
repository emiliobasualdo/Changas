<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<c:choose>
    <c:when test="${isUserLogged}">
        <c:forEach items="${changaPage}" var="entry">
            <c:set var="title" value="${entry.key.title}" scope="request"/>
            <c:set var="description" value="${entry.key.description}" scope="request"/>
            <c:set var="user_id" value="${entry.key.user_id}" scope="request"/>
            <c:set var="price" value="${entry.key.price}" scope="request"/>
            <c:set var="changa_id" value="${entry.key.changa_id}" scope="request"/>
            <c:set var="neighborhood" value="${entry.key.neighborhood}" scope="request"/>
            <c:set var="street" value="${entry.key.street}" scope="request"/>
            <c:set var="number" value="${entry.key.number}" scope="request"/>
            <c:set var="inscribed" value="${entry.value}" scope="request"/>
            <c:import url="/WEB-INF/jsp/changaCard.jsp"/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <c:forEach items="${changaPage}" var="changa">
            <c:set var="title" value="${changa.title}" scope="request"/>
            <c:set var="description" value="${changa.description}" scope="request"/>
            <c:set var="user_id" value="${changa.user_id}" scope="request"/>
            <c:set var="price" value="${changa.price}" scope="request"/>
            <c:set var="changa_id" value="${changa.changa_id}" scope="request"/>
            <c:set var="neighborhood" value="${changa.neighborhood}" scope="request"/>
            <c:set var="street" value="${changa.street}" scope="request"/>
            <c:set var="number" value="${changa.number}" scope="request"/>
            <c:set var="inscribed" value="${false}" scope="request"/>
            <c:import url="/WEB-INF/jsp/changaCard.jsp"/>
        </c:forEach>
    </c:otherwise>
</c:choose>
</html>