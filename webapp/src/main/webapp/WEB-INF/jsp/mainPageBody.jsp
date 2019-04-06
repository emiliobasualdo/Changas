<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<body>
    <div class="container">
        <div class="col">
                <c:forEach items="${changaList}" var="changa">
                    <c:set var="title" value="${changa.title}" scope="request"/>
                    <c:set var="description" value="${changa.description}" scope="request"/>
                    <c:set var="ownerName" value="${changa.user_id}" scope="request"/>
                    <c:set var="price" value="${changa.price}" scope="request"/>
                    <c:set var="neighborhood" value="${changa.neighborhood}" scope="request"/>
                    <div class="row" style="margin-top: 20px">
                        <c:import url="/WEB-INF/jsp/changaCard.jsp"/>
                    </div>
                </c:forEach>
        </div>
    </div>
</body>
</html>
