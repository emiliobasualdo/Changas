<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <script>

        $(document).ready(function(){
            var id = '${requestScope.userId}';
            $("button[type='submit']").click(function(e){
                e.preventDefault();
                var nameR =  'rating' + id;
                var radioValue = $("input[name='rating' ]:checked").val();
                if(radioValue){
                    $.post('/rate?id='+id+'&rating='+radioValue,
                        function(data, status){
                            alert("Data: " + data + "\nStatus: " + status);
                        });
                }
            });

        });
    </script>
    <style><%@ include file="/WEB-INF/css/userRating.css" %></style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<div class="card">
    <div class="card-body">
        <div class="row">
            <div class="column left">
                <div class="container">
                    <h4 class="card-title"><c:out value="${requestScope.name}"/> <c:out value="${requestScope.surname}"/></h4>
                    <c:url value="/rate" var="urlRate"/>
                    <%--<form action="${urlRate}" method="post">--%>
                        <input type="hidden" name="id<c:out value="${requestScope.userId}"/>" value="<c:out value="${requestScope.userId}"/>">
                        <fieldset class="rating">
                            <input type="radio" id="star5" name="rating<c:out value="${requestScope.userId}"/>" value="5" /><label class = "full" for="star5" title="<spring:message code="rating.5star"/>"></label>
                            <input type="radio" id="star4half" name="rating<c:out value="${requestScope.userId}"/>" value="4" /><label class="half" for="star4half" title="<spring:message code="rating.4star"/>"></label>
                            <input type="radio" id="star4" name="rating<c:out value="${requestScope.userId}"/>" value="4" /><label class = "full" for="star4" title="<spring:message code="rating.4star"/>"></label>
                            <input type="radio" id="star3half" name="rating<c:out value="${requestScope.userId}"/>" value="3" /><label class="half" for="star3half" title="<spring:message code="rating.3star"/>"></label>
                            <input type="radio" id="star3" name="rating<c:out value="${requestScope.userId}"/>" value="3" /><label class = "full" for="star3" title="<spring:message code="rating.3star"/>"></label>
                            <input type="radio" id="star2half" name="rating<c:out value="${requestScope.userId}"/>" value="2" /><label class="half" for="star2half" title="<spring:message code="rating.2star"/>"></label>
                            <input type="radio" id="star2" name="rating<c:out value="${requestScope.userId}"/>" value="2" /><label class = "full" for="star2" title="<spring:message code="rating.2star"/>"></label>
                            <input type="radio" id="star1half" name="rating<c:out value="${requestScope.userId}"/>" value="1" /><label class="half" for="star1half" title="<spring:message code="rating.1star"/>"></label>
                            <input type="radio" id="star1" name="rating<c:out value="${requestScope.userId}"/>" value="1" /><label class = "full" for="star1" title="<spring:message code="rating.1star"/>"></label>
                            <input type="radio" id="starhalf" name="rating<c:out value="${requestScope.userId}"/>" value="half" /><label class="half" for="starhalf" title="<spring:message code="rating.0star"/>"></label>
                        </fieldset>
                        <button type="submit" id="set<c:out value="${requestScope.userId}"/>" class="btn btn-primary btn-block btn-lg">Set</button>
                    <%--</form>--%>
                </div>
            </div>
            <div class="column right">
                <c:url value="/img/nieve1.jpg" var="nieveImage"/>
                <img src="${nieveImage}" class="user">
            </div>
        </div>
    </div>
</div>
</html>
