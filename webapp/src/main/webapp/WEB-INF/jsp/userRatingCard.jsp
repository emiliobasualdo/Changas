<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <script>


        $(document).ready(function(){
            var id = '${requestScope.userId}';
            var ctx = '${pageContext.request.contextPath}';
            $('.forms').submit(function(e) {
                e.preventDefault();
                var data = $(this).serialize();
                $.post(ctx+'/rate?'+data, function(data2){
                });
            });
        });

        <%--$(document).ready(function(){--%>
            <%--var id = '${requestScope.userId}';--%>
            <%--$("button[type='submit']").click(function(e){--%>
                <%--e.preventDefault();--%>
                <%--var nameR =  'rating' + id;--%>
                <%--var radioValue = $("input[name='rating' ]:checked").val();--%>
                <%--if(radioValue){--%>
                    <%--$.post('/rate?id='+id+'&rating='+radioValue,--%>
                        <%--function(data, status){--%>
                            <%--alert("Data: " + data + "\nStatus: " + status);--%>
                        <%--});--%>
                <%--}--%>
            <%--});--%>

        <%--});--%>

        <%--$(document).ready(function(){--%>
            <%--var id = '${requestScope.userId}';--%>
            <%--jQuery('button[id=set' + id + '"]').click(function(e){--%>
                <%--e.preventDefault();--%>
                <%--var radioValue =  jQuery('input[name=rating"' + id + '"]:checked').val();--%>
                <%--if(radioValue){--%>
                    <%--$.post('/rate?id='+id+'&rating='+radioValue,--%>
                        <%--function(data, status){--%>
                            <%--alert("Data: " + data + "\nStatus: " + status);--%>
                        <%--});--%>
                <%--}--%>
            <%--});--%>

        <%--});--%>
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
                    <form id="rating-form-<c:out value="${requestScope.userId}"/>" class="forms">
                        <input type="hidden" name="userId" value="<c:out value="${requestScope.userId}"/>">
                        <%--<input type="hidden" name="changaId" value="<c:out value="${requestScope.changaId}"/>">--%>
                        <fieldset class="rating">
                            <input type="radio" id="star5<c:out value="${requestScope.userId}"/>" name="rating" value=5 /><label class = "full" for="star5<c:out value="${requestScope.userId}"/>" title="<spring:message code="rating.5star"/>"></label>
                            <input type="radio" id="star4half<c:out value="${requestScope.userId}"/>" name="rating" value=4.5 /><label class="half" for="star4half<c:out value="${requestScope.userId}"/>" title="<spring:message code="rating.4star"/>"></label>
                            <input type="radio" id="star4<c:out value="${requestScope.userId}"/>" name="rating" value=4 /><label class = "full" for="star4<c:out value="${requestScope.userId}"/>" title="<spring:message code="rating.4star"/>"></label>
                            <input type="radio" id="star3half<c:out value="${requestScope.userId}"/>" name="rating" value=3.5 /><label class="half" for="star3half<c:out value="${requestScope.userId}"/>" title="<spring:message code="rating.3star"/>"></label>
                            <input type="radio" id="star3<c:out value="${requestScope.userId}"/>" name="rating" value=3 /><label class = "full" for="star3<c:out value="${requestScope.userId}"/>" title="<spring:message code="rating.3star"/>"></label>
                            <input type="radio" id="star2half<c:out value="${requestScope.userId}"/>" name="rating" value=2.5 /><label class="half" for="star2half<c:out value="${requestScope.userId}"/>" title="<spring:message code="rating.2star"/>"></label>
                            <input type="radio" id="star2<c:out value="${requestScope.userId}"/>" name="rating" value=2 /><label class = "full" for="star2<c:out value="${requestScope.userId}"/>" title="<spring:message code="rating.2star"/>"></label>
                            <input type="radio" id="star1half<c:out value="${requestScope.userId}"/>" name="rating" value=1.5 /><label class="half" for="star1half<c:out value="${requestScope.userId}"/>" title="<spring:message code="rating.1star"/>"></label>
                            <input type="radio" id="star1<c:out value="${requestScope.userId}"/>" name="rating" value=1 /><label class = "full" for="star1<c:out value="${requestScope.userId}"/>" title="<spring:message code="rating.1star"/>"></label>
                            <input type="radio" id="starhalf<c:out value="${requestScope.userId}"/>" name="rating" value="0.5" /><label class="half" for="starhalf<c:out value="${requestScope.userId}"/>" title="<spring:message code="rating.0star"/>"></label>
                        </fieldset>
                        <input type="submit" class="btn btn-primary btn-block btn-lg"/>
                    </form>
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
