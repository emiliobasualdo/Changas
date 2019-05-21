<html>
<%-- https://www.tutorialrepublic.com/codelab.php?topic=bootstrap&file=simple-success-confirmation-popup --%>
<div id="successModal" class="modal fade">
    <div class="modal-dialog modal-confirm">
        <div class="modal-content">
            <div class="modal-header">
                <div class="icon-box">
                    <i class="material-icons">&#xE876;</i>
                </div>
                <h4 class="modal-title"><c:out value="${requestScope.titleSuccess}" /></h4>
            </div>
            <div class="modal-body">
                <p class="text-center"><c:out value="${requestScope.messageSuccess}" /></p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success btn-block" type="submit">OK</button>
            </div>
        </div>
    </div>
</div>
</html>