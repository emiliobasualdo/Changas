<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>

    <script src="http://code.jquery.com/jquery-latest.js"> </script>

    <script>
        var page = 1;
        $(document).ready(function(){
            var totalPages = $('#totalPages').val()-1;
            showMoreBtn(page, totalPages);
            $('#myAnchor').click(function(e){
                e.preventDefault();
                $.get('/page?page='+page, function(data) {
                    $('#container').append(data);
                    showMoreBtn(page, totalPages);
                    page++;
                });
            });
        });

        function showMoreBtn(current, total) {
            if (current === total) {
                $('#myAnchor').hide();
            }
        }
    </script>
    <style><%@include file="/WEB-INF/css/mainPageBody.css"%></style>
</head>

<body>
    <section class="search-sec">
        <div class="container">
            <c:url value="/filter" var="filterUrl" />
            <form:form action="${filterUrl}" method="get">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="row">
                            <div class="col-lg-4 col-md-3 col-sm-12 p-0">
                                <input name="tfilter" value="${tfilter}" class="form-control search-slt" placeholder="<spring:message code="mainPage.search"/>">
                            </div>


                            <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                <select name="nfilter" class="form-control search-slt" id="nfilter">

                                    <c:choose>
                                        <c:when test="${nfilter.equals('')}">
                                            <option value="${""}" selected><spring:message code="filters.all"/></option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${""}"><spring:message code="filters.all"/></option>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:forEach items="${neighborhoods}" var="neighborhood">
                                        <c:choose>
                                            <c:when test="${nfilter == neighborhood}">
                                                <option value="${neighborhood}" selected>${neighborhood}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${neighborhood}">${neighborhood}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>


                            <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                <select name="cfilter" class="form-control search-slt" id="cfilter">

                                    <c:choose>
                                        <c:when test="${cfilter.equals('')}">
                                            <option value="${""}" selected><spring:message code="filters.all"/></option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${""}"><spring:message code="filters.all"/></option>
                                        </c:otherwise>
                                    </c:choose>

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

                            <div class="col-lg-2 col-md-3 col-sm-12 p-0">
                                <button type="submit" class="btn wrn-btn" style="background-color: #26B3BA"><spring:message code="mainPage.button.filter"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>

            <c:if test="${isFiltered}">
                <c:url value="/" var="rootUrl" />
                <form:form action="${rootUrl}" method="get">
                    <div class="row justify-content-center">
                        <div class="col-lg-2 col-md-3 col-sm-12 p-0">
                            <button type="submit" class="btn wrn-btn center-pill" style="background-color: #26B3BA; align-self: center"><spring:message code="mainPage.button.de-filter"/></button>
                        </div>
                    </div>
                </form:form>
            </c:if>
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
        <input type="hidden" id="totalPages" value="${totalPages}"/>
        <div style=" margin: auto; display: block; margin-top: 25px">
            <button id="myAnchor" type="button" class="btn wrn-btn" style="background-color: #26B3BA"> <spring:message code="mainPage.showMore.btn"/> </button>
        </div>
    </c:if>

</body>
</html>
