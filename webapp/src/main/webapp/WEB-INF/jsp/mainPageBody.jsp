<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<style>
    .auto-table {
        margin-top: 20px;
        /*display: table;*/
        display: grid;
        grid-gap: 20px;
        grid-template-columns: repeat(3, 1fr);
        grid-auto-rows: minmax(100px, auto);
        /*width: 100%;*/
    }
</style>
<body>
    <div class="auto-table">
                <c:forEach items="${changaList}" var="changa">
                    <c:set var="title" value="${changa.title}" scope="request"/>
                    <c:set var="description" value="${changa.description}" scope="request"/>
                    <c:set var="user_id" value="${changa.user_id}" scope="request"/>
                    <c:set var="price" value="${changa.price}" scope="request"/>
                    <c:set var="changa_id" value="${changa.changa_id}" scope="request"/>
                    <c:set var="neighborhood" value="${changa.neighborhood}" scope="request"/>
                    <c:set var="street" value="${changa.street}" scope="request"/>
                    <c:set var="number" value="${changa.number}" scope="request"/>
                    <c:import url="/WEB-INF/jsp/changaCard.jsp"/>
                </c:forEach>
    </div>
</body>
</html>
