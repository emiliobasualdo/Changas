<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <style><%@include file="/WEB-INF/css/mainPageBody.css"%></style>
    <title>Changas</title>
</head>
<body>
<c:if test="${changaList.isEmpty()}">
    <div class="container">
        <div class="col-sm-12">

            <div class="bs-calltoaction bs-calltoaction-default">
                <div class="row">
                    <div class="col-md-8 cta-contents">
                        <h1 class="cta-title"><spring:message code="mainPageBody.noChangasTitle"/></h1>
                        <div class="cta-desc">
                            <p><spring:message code="mainPageBody.noChangasP"/></p>
                        </div>
                    </div>
                    <div class="col-md-6 cta-button">
                        <c:url value="/create-changa" var="createChangaUrl"/>
                        <a href="${createChangaUrl}" class="btn btn-lg btn-block btn-default"><spring:message code="mainPageBody.noChangasBtn"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${changaList.isEmpty() == false}">
    <section class="search-sec">
        <div class="container">
            <c:url value="/filter" var="filterUrl" />
            <form action="${filterUrl}" method="get">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="row">
                            <c:url value="/filter" var="filterUrl" />
                            <form:form action="${filterUrl}" method="get">
                                <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                    <input name="tfilter" value="${tfilter}" class="form-control search-slt" placeholder="<spring:message code="mainPage.search"/>">
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                    <select name="cfilter" class="form-control search-slt" id="exampleFormControlSelect1">
                                        <%-- Opción vacía --%>
                                        <c:choose>
                                            <c:when test="${cfilter == ''}">
                                                <option value="''" selected><spring:message code="categories.all"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="''"><spring:message code="categories.all"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                        <%-- Demás opciones --%>
                                        <c:forEach items="${categories}" var="category">
                                            <c:choose>
                                                <c:when test="${cfilter == category}">
                                                    <option value="${category}" selected><spring:message code="${category}"/></option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${category}"><spring:message code="${category}"/></option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                    <button type="submit" class="btn wrn-btn" style="background-color: #26B3BA"><spring:message code="mainPage.button.filter"/></button>
                                </div>
                            </form:form>
                            <c:if test="${isFiltered}">
                                <c:url value="/" var="rootUrl" />
                                <form:form action="${rootUrl}" method="get">
                                    <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                        <button type="submit" class="btn wrn-btn" style="background-color: #26B3BA"><spring:message code="mainPage.button.de-filter"/></button>
                                    </div>
                                </form:form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </form>
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
</c:if>
</body>
</html>
