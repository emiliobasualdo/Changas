<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <style><%@ include file="/WEB-INF/css/userRating.css" %></style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<div class="card">
    <div class="card-body">
        <div class="row">
            <div class="column left">
                <div class="container">
                <h4 class="card-title"><c:out value="${requestScope.name}"/> <c:out value="${requestScope.surname}"/></h4>
                <fieldset class="rating">
                    <input type="radio" id="star5" name="rating" value="5" /><label class = "full" for="star5" title="<spring:message code="rating.5star"/>"></label>
                    <input type="radio" id="star4half" name="rating" value="4 and a half" /><label class="half" for="star4half" title="<spring:message code="rating.4star"/>"></label>
                    <input type="radio" id="star4" name="rating" value="4" /><label class = "full" for="star4" title="<spring:message code="rating.4star"/>"></label>
                    <input type="radio" id="star3half" name="rating" value="3 and a half" /><label class="half" for="star3half" title="<spring:message code="rating.3star"/>"></label>
                    <input type="radio" id="star3" name="rating" value="3" /><label class = "full" for="star3" title="<spring:message code="rating.3star"/>"></label>
                    <input type="radio" id="star2half" name="rating" value="2 and a half" /><label class="half" for="star2half" title="<spring:message code="rating.2star"/>"></label>
                    <input type="radio" id="star2" name="rating" value="2" /><label class = "full" for="star2" title="<spring:message code="rating.2star"/>"></label>
                    <input type="radio" id="star1half" name="rating" value="1 and a half" /><label class="half" for="star1half" title="<spring:message code="rating.1star"/>"></label>
                    <input type="radio" id="star1" name="rating" value="1" /><label class = "full" for="star1" title="<spring:message code="rating.1star"/>"></label>
                    <input type="radio" id="starhalf" name="rating" value="half" /><label class="half" for="starhalf" title="<spring:message code="rating.0star"/>"></label>
                </fieldset>
                </div>
            </div>
            <div class="column right">
                <c:url value="/img/nieve1.jpg" var="nieveImage"/>
                <img src="${nieveImage}" class="user">
            </div>
        </div>
        <%--<a href="#" class="btn btn-primary">See Profile</a>--%>

    </div>
</div>
</html>