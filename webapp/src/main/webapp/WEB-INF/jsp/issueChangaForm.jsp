<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<div class="modal fade" id="emitirChangaModal" tabindex="-1" role="dialog" aria-labelledby="emitirChangaModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="emitirChangaModalLabel">Crea tu changa</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form:form method="post" modelAttribute="changaForm" action="/create" class="form-group">
                <div class="modal-body">
                    <div class="form-group">
                        <form:label path="title">Titulo</form:label>
                        <form:input class="form-control" path="title"/>
                        <form:errors path="title" element="p"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Descripcion</form:label>
                        <form:textarea rows="3" class="form-control" path="description"/>
                        <form:errors path="description" element="p"/>
                    </div>
                    <div class="form-group">
                        <form:label path="neighborhood">Lugar</form:label>
                        <form:input class="form-control" path="neighborhood"/>
                        <form:errors path="neighborhood" element="p"/>
                    </div>
                    <div class="form-group">
                        <form:label path="price">Precio</form:label>
                        <form:input class="form-control" path="price"/>
                        <form:errors path="price" element="p"/>
                            <%--<input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">--%>
                            <%--<small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>--%>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Emitir</button>
                </div>
            </form:form>
        </div>
    </div>
</div>

</html>