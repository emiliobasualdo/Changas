<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>

    <%@ include file="/WEB-INF/jsp/navigationBar.jsp" %>

    <div id="carousel-example-2" class="carousel slide carousel-fade z-depth-1-half" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carousel-example-2" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-example-2" data-slide-to="1"></li>
            <li data-target="#carousel-example-2" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner" role="listbox">
            <div class="carousel-item active">
                <div class="view custom-img">
                    <c:url value="/img/mudanza1.jpg" var="mudanzaImage"/>
                    <img class="d-block w-100" src="${mudanzaImage}" alt="First slide">
                    <div class="mask rgba-black-light"></div>
                </div>
                <div class="carousel-caption">
                    <h1 class="h1-responsive h1-custom"><spring:message code="changas.title"/></h1>
                </div>
            </div>
            <div class="carousel-item">
                <div class="view custom-img">
                    <c:url value="/img/auto1.jpg" var="autoImage"/>
                    <img class="d-block w-100" src="${autoImage}" alt="Second slide">
                    <div class="mask rgba-black-light"></div>
                </div>
                <div class="carousel-caption">
                    <h1 class="h1-responsive h1-custom"><spring:message code="changas.title"/></h1>
                </div>
            </div>
            <div class="carousel-item">
                <div class="view custom-img">
                    <c:url value="/img/nieve1.jpg" var="nieveImage"/>
                    <img class="d-block w-100" src="${nieveImage}" alt="Third slide">
                    <div class="mask rgba-black-light"></div>
                </div>
                <div class="carousel-caption">
                    <h1 class="h1-responsive h1-custom"><spring:message code="changas.title"/></h1>
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