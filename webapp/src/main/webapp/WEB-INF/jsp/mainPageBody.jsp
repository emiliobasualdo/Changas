<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <style><%@include file="/WEB-INF/css/mainPageBody.css"%></style>
    <title>Changas</title>
</head>
<body>

    <section class="search-sec">
        <div class="container">
            <c:url value="/filter" var="filterUrl" />
            <c:choose>
                <c:when test="${isFiltered}">
                    <form action="${filterUrl}" method="get">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="row">
                                    <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                        <input name="tfilter" class="form-control search-slt" placeholder="Search"/>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                        <select name="cfilter" class="form-control search-slt" id="exampleFormControlSelect1">
                                            <c:forEach items="${categories}" var="category">
                                                <option value="${category.key}">${category.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                        <button type="submit" class="btn wrn-btn" style="background-color: #26B3BA"><spring:message code="mainPage.button.filter"/></button>
                                    </div>
                                    <c:url value="/" var="rootUrl" />
                                    <form:form action="${rootUrl}" method="get">
                                        <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                            <button type="submit" class="btn wrn-btn" style="background-color: #26B3BA"><spring:message code="mainPage.button.de-filter"/></button>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="${filterUrl}" method="get">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="row">
                                    <div class="col-lg-6 col-md-3 col-sm-12 p-0">
                                        <input name="tfilter" class="form-control search-slt" placeholder="Search"/>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                        <select name="cfilter" class="form-control search-slt" id="exampleFormControlSelect1">
                                            <c:forEach items="${categories}" var="category">
                                                <option value="${category.key}">${category.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                        <button type="submit" class="btn wrn-btn" style="background-color: #26B3BA"><spring:message code="mainPage.button.filter"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </c:otherwise>
        </c:choose>
        </div>
    </section>



    <div class="auto-table">
        <c:choose>
            <c:when test="${isUserLogged}">
                <c:forEach items="${changaList}" var="entry">
                    <c:set var="title" value="${entry.key.title}" scope="request"/>
                    <c:set var="str" value="${entry.key.description}" />
                    <c:set var="descriptionLength" value="${fn:length(str)}"/>
                    <c:choose>
                        <c:when test="${descriptionLength >= 100}">
                            <c:set var="description" value="${fn:substring(str,0,97)}..." scope="request"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="description" value="${str}" scope="request"/>
                        </c:otherwise>
                    </c:choose>
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
                    <c:set var="str" value="${changa.description}" />
                    <c:set var="descriptionLength" value="${fn:length(str)}"/>
                    <c:choose>
                        <c:when test="${descriptionLength >= 100}">
                            <c:set var="description" value="${fn:substring(str,0,97)}..." scope="request"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="description" value="${str}" scope="request"/>
                        </c:otherwise>
                    </c:choose>
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
