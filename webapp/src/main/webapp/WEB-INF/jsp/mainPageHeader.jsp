<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%--https://bootsnipp.com/snippets/WMlMa--%>
<html>
    <head>
        <style>
            <%@ include file="/WEB-INF/css/mainPageHeader.css" %>
        </style>
    </head>
    <body>
        <%@ include file="/WEB-INF/jsp/navigationBar.jsp" %>

        <section>
            <div id="carouselExampleFade" class="carousel slide carousel-fade" data-ride="carousel">
                <div class="carousel-inner" role="listbox">
                    <div class="carousel-item active">
                        <div class="view">
                            <img class="d-block h-75 w-100" src="https://cdn.makespace.com/blog/wp-content/uploads/2016/05/16125035/makespace-full-service-storage-pickup-delivery-1600x715.jpg" alt="First slide" class="d-block w-100">
                            <div class="mask rgba-black-light"></div>
                        </div>
                        <div class="carousel-caption">
                            <h1 class="h1-responsive" style="color: #26B3BA">C H A N G A S</h1>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <div class="view">
                            <img class="d-block h-75 w-100" src="https://d1ix0byejyn2u7.cloudfront.net/drive/images/uploads/authors/interior.jpg" alt="Second slide" class="d-block w-100">
                            <div class="mask rgba-black-light"></div>
                        </div>
                        <div class="carousel-caption">
                            <h1 class="h1-responsive" style="color: #26B3BA">C H A N G A S</h1>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <div class="view">
                            <img class="d-block h-75 w-100" src="http://massrealestatelawblog.com/wp-content/uploads/sites/9/2015/02/snow-shovel.jpg" alt="Third slide" class="d-block w-100">
                            <div class="mask rgba-black-light"></div>
                        </div>
                        <div class="carousel-caption">
                            <h1 class="h1-responsive" style="color: #26B3BA">C H A N G A S</h1>
                        </div>
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleFade" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only"></span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleFade" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only"></span>
                </a>
            </div>
        </section>
        <section class="search-sec">
            <div class="container">
                <c:url value="/" var="searchUrl" />
                <form:form action="${searchUrl}" modelAttribute="searchForm" method="post">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="row">
                                <div class="col-lg-6 col-md-3 col-sm-12 p-0">
                                    <form:label path="search"><spring:message code="mainPageHeader.placeholder"/></form:label>
                                    <form:input type="text" class="form-control search-slt"/>
                                    <form:errors cssClass="form-error" path="name" element="p"/>        <%--todo--%>
                                </div>
                                <%--<div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                    <input type="text" class="form-control search-slt" placeholder="Enter Drop City">
                                </div>--%>
                                <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                    <form:label path="search"><spring:message code="mainPageHeader.placeholder"/></form:label>
                                    <select class="form-control search-slt" id="exampleFormControlSelect1">
                                        <option><spring:message code="mainPageHeader.categories"/></option>
                                        <option><spring:message code="mainPageHeader.categories1"/></option>
                                        <option><spring:message code="mainPageHeader.categories2"/></option>
                                        <option><spring:message code="mainPageHeader.categories3"/></option>
                                        <option><spring:message code="mainPageHeader.categories4"/></option>
                                        <option><spring:message code="mainPageHeader.categories5"/></option>
                                    </select>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-12 p-0">
                                    <button type="button" class="btn wrn-btn" style="background-color: #26B3BA"><spring:message code="mainPageHeader.searchBtn"/></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </section>
        <%--<div id="carousel-example-2" class="carousel slide carousel-fade z-depth-1-half" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carousel-example-2" data-slide-to="0" class="active"></li>
                <li data-target="#carousel-example-2" data-slide-to="1"></li>
                <li data-target="#carousel-example-2" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner" role="listbox">
                <div class="carousel-item active">
                    <div class="view">
                        <img class="d-block h-75 w-100" src="https://cdn.makespace.com/blog/wp-content/uploads/2016/05/16125035/makespace-full-service-storage-pickup-delivery-1600x715.jpg" alt="First slide">
                        <div class="mask rgba-black-light"></div>
                    </div>
                    <div class="carousel-caption">
                        <h1 class="h1-responsive" style="color: #26B3BA">C H A N G A S</h1>
                    </div>
                </div>
                <div class="carousel-item">
                    <div class="view">
                        <img class="d-block h-75 w-100" src="https://d1ix0byejyn2u7.cloudfront.net/drive/images/uploads/authors/interior.jpg" alt="Second slide">
                        <div class="mask rgba-black-light"></div>
                    </div>
                    <div class="carousel-caption">
                        <h1 class="h1-responsive" style="color: #26B3BA">C H A N G A S</h1>
                    </div>
                </div>
                <div class="carousel-item">
                    <div class="view">
                        <img class="d-block h-75 w-100" src="http://massrealestatelawblog.com/wp-content/uploads/sites/9/2015/02/snow-shovel.jpg" alt="Third slide">
                        <div class="mask rgba-black-light"></div>
                    </div>
                    <div class="carousel-caption">
                        <h1 class="h1-responsive" style="color: #26B3BA">C H A N G A S</h1>
                    </div>
                </div>
            </div>
            <a class="carousel-control-prev" href="#carousel-example-2" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only"></span>
            </a>
            <a class="carousel-control-next" href="#carousel-example-2" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only"></span>
            </a>
        </div>--%>
    </body>

</html>