<%--
  Created by IntelliJ IDEA.
  User: jimenalozano
  Date: 3/4/19
  Time: 19:01
  To change this template use File | Settings | File Templates.
--%>
<html>
<!-- issueChangaModal -->
<div class="modal fade" id="issueChangaModal" tabindex="-1" role="dialog" aria-labelledby="issueChangaModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="issueChangaModalLabel">Crea tu changa</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="inputName">Nombre</label>
                        <input type="text" class="form-control" id="inputName" placeholder="Ingrese su nombre">
                    </div>
                    <div class="form-group">
                        <label for="inputPhone">Telefono</label>
                        <input type="text" class="form-control" id="inputPhone" placeholder="Ingrese su telefono">
                    </div>
                    <div class="form-group">
                        <label for="inputTitle">Titulo</label>
                        <input type="text" class="form-control" id="inputTitle" placeholder="Ingrese titulo representativo de su changa">
                    </div>
                    <div class="form-group">
                        <label for="inputDescription">Descripcion</label>
                        <textarea class="form-control" id="inputDescription" rows="3"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="inputNeighborhood">Ubicacion</label>
                        <input type="text" class="form-control" id="inputNeighborhood" placeholder="Ingrese ubicacion">
                    </div>
                    <div class="form-group">
                        <label for="inputPrice">Precio</label>
                        <input type="number" class="form-control" id="inputPrice" placeholder="Ingrese precio"/>
                        <%--<input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">--%>
                        <%--<small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>--%>
                    </div>
                </form>
            </div>
            <%--<div class="modal-footer">
                <button type="button" class="btn btn-primary">Emitir</button>
            </div>--%>
        </div>
    </div>
</div>
</html>
