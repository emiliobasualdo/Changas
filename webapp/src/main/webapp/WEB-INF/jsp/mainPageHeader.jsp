<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>

    <%@ include file="/WEB-INF/jsp/navigationBar.jsp" %>

    <div id="carousel-example-2" class="carousel slide carousel-fade z-depth-1-half" data-ride="carousel" style="margin-top: 80px">
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
                    <h1 class="h1-responsive" style="color: #26B3BA"><spring:message code="changas.title"/></h1>
                </div>
            </div>
            <div class="carousel-item">
                <div class="view">
                    <img class="d-block h-75 w-100" src="https://d1ix0byejyn2u7.cloudfront.net/drive/images/uploads/authors/interior.jpg" alt="Second slide">
                    <div class="mask rgba-black-light"></div>
                </div>
                <div class="carousel-caption">
                    <h1 class="h1-responsive" style="color: #26B3BA"><spring:message code="changas.title"/></h1>
                </div>
            </div>
            <div class="carousel-item">
                <div class="view">
                    <img class="d-block h-75 w-100" src="http://massrealestatelawblog.com/wp-content/uploads/sites/9/2015/02/snow-shovel.jpg" alt="Third slide">
                    <div class="mask rgba-black-light"></div>
                </div>
                <div class="carousel-caption">
                    <h1 class="h1-responsive" style="color: #26B3BA"><spring:message code="changas.title"/></h1>
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
    </div>

</html>