<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>

    <script src="http://code.jquery.com/jquery-latest.js"> </script>

    <script>
        var page = 1;
        $(document).ready(function(){
            $('#myAnchor').click(function(e){
                e.preventDefault();
                $.get('/page?page='+page, function(data) {
                    $('#container').append(data);
                    page++;
                });
            });
        });
    </script>
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
                                        <input name="tfilter" class="form-control search-slt" placeholder="<spring:message code="mainPage.search"/>"/>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                        <select name="cfilter" class="form-control search-slt" id="exampleFormControlSelect1">
                                            <%--<option selected="selected" hidden="hidden"><c:out value="${cFilter}"/></option> todo--%>
                                            <c:forEach items="${categories}" var="category">
                                                <option value="${category}"> ${category}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                        <button type="submit" class="btn wrn-btn" style="background-color: #26B3BA; margin-left: 5px;"><spring:message code="mainPage.button.filter"/></button>
                                    </div>
                                    <c:url value="/" var="rootUrl" />
                                    <form:form action="${rootUrl}" method="get">
                                        <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                            <button type="submit" class="btn wrn-btn" style="background-color: #26B3BA; margin-left: 10px;"><spring:message code="mainPage.button.de-filter"/></button>
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
                                        <input name="tfilter" class="form-control search-slt" placeholder="<spring:message code="mainPage.search"/>"/>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                        <select name="cfilter" class="form-control search-slt">
                                            <option selected="selected" disabled="disabled" hidden="hidden"><spring:message code="mainPage.select"/></option>
                                            <c:forEach items="${categories}" var="category">
                                                <option value="${category}"> ${category} </option>
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
    <c:if test="${!changaList.isEmpty()}">
        <div id="container" class="auto-table">
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
        <div style=" margin: auto; display: block; margin-top: 25px">
            <button id="myAnchor" type="button" class="btn wrn-btn" style="background-color: #26B3BA"> <spring:message code="mainPage.showMore.btn"/> </button>
        </div>
    </c:if>

</body>
</html>
