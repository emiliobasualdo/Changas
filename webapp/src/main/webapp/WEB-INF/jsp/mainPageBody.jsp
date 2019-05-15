<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <style><%@include file="/WEB-INF/css/mainPageBody.css"%></style>
    <title>Changas</title>
</head>
<body>
    <div>
        <tr>
            <td>
                <div class="form-group">
                    <label><spring:message code="ChangaForm.category"/></label>
                    <c:url value="/filter" var="filterUrl" />
                    <form:form action="${filterUrl}" method="get">
                        <label>
                            <select name="cfilter">
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category}"><spring:message code="${category}"/></option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-success btn-block"><spring:message code="mainPage.button.filter"/></button>
                        </label>
                    </form:form>
                    <c:if test="${isFiltered}">
                        <c:url value="/" var="rootUrl" />
                        <form:form action="${rootUrl}" method="get">
                            <button type="submit" class="btn btn-success btn-block"><spring:message code="mainPage.button.de-filter"/></button>
                        </form:form>
                    </c:if>
                </div>
            </td>
        </tr>
    </div>
    <div class="auto-table">
        <c:choose>
            <c:when test="${isUserLogged}">
                <c:forEach items="${changaList}" var="entry">
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
                <c:forEach items="${changaList}" var="changa">
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
    </div>
</body>
</html>
