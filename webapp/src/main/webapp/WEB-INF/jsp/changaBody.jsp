<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>

    <body>

        <div class="jumbotron jumbotron-fluid">
            <div class="container">
                <h2>
                    <c:out value="${changa.title}" />
                </h2>
                <p style="font-style: italic">
                    <c:out value="${changa.description}" />
                </p>
                <p>
                    <c:out value="${changa.price}" /> $
                </p>
            </div>
        </div>

        <div class="container">
            <table class="table">
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Telefono</th>
                    <th>Ubicacion</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><c:out value="${changa.user_id}"/></td>
                    <td><c:out value="${changa.neighborhood}" /></td>
                </tr>
                </tbody>
            </table>
            <!--<a href="#" class="btn btn-primary" style="margin-top: 1cm; margin-bottom: 1cm;">Aceptar</a>-->
        </div>
        <div class="container" style="margin-top: 80px">
            <form action="/joinChanga" method="post">
                <input type="hidden" name="changaId" value="<c:out value="${changa.user_id}"/>">
                <input type="submit"  class="btn btn-success btn-block" value="Anotame en la changa" />
            </form>
        </div>
    </body>
</html>
