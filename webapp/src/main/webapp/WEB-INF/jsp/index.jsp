<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <div class="jumbotron text-center">
        <h1>My First Bootstrap Page</h1>
        <p>Resize this responsive page to see the effect!</p>
    </div>

    <div class="container">
        <div id="changasList">
            <ul>
                <c:forEach items="${changaList}" var="changa">
                    <li>
                        <c:out value="${changa.title}" />
                        <c:out value="${changa.description}"/>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</html>
