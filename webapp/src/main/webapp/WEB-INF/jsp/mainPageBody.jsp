<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<div class="table-wrapper-scroll-y my-custom-scrollbar">--%>
    <%--<table class="table table-bordered table-striped mb-0" style="text-align: right">--%>
        <%--<thead>--%>
        <%--<tr>--%>
            <%--<th scope="col">#</th>--%>
            <%--<th scope="col">Changa</th>--%>
            <%--<th scope="col">Localidad</th>--%>
            <%--<th scope="col">Precio ($)</th>--%>
        <%--</tr>--%>
        <%--</thead>--%>
        <%--<tbody>--%>
        <%--<tr>--%>
            <%--<th scope="row">1</th>--%>
            <%--<td>Lavar un auto</td>--%>
            <%--<td>Martínez</td>--%>
            <%--<td>300</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<th scope="row">2</th>--%>
            <%--<td>Babysitting</td>--%>
            <%--<td>Palermo</td>--%>
            <%--<td>600</td>--%>
        <%--</tr>--%>
        <%--</tbody>--%>
    <%--</table>--%>
<%--</div>--%>
<%--</html>--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<body>
    <div class="container">
        <div class="col">
                <c:forEach items="${changaList}" var="changa">
                    <c:set var="title" value="${changa.title}" scope="request"/>
                    <c:set var="description" value="${changa.description}" scope="request"/>
                    <c:set var="ownerName" value="${changa.ownerName}" scope="request"/>
                    <c:set var="ownerPhone" value="${changa.ownerPhone}" scope="request"/>
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
