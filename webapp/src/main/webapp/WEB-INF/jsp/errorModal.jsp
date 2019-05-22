<html>
<%--https://www.tutorialrepublic.com/codelab.php?topic=bootstrap&file=simple-error-confirmation-popup--%>
<head>
    <style><%@include file="/WEB-INF/css/errorModal.css"%></style>
</head>
<body>
<div id="errorModal" class="modal fade">
    <div class="modal-dialog modal-confirm">
        <div class="modal-content">
            <div class="modal-header">
                <div class="icon-box">
                    <i class="material-icons">&#xE5CD;</i>
                </div>
                <h4 class="modal-title"><c:out value="${requestScope.titleError}" /></h4>
            </div>
            <div class="modal-body">
                <p class="text-center"><c:out value="${requestScope.messageError}" /></p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-danger btn-block" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>