<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <style><%@ include file="/WEB-INF/css/signUpBody.css" %></style>
</head>
<body>
<div class="signup-form">
    <form:form modelAttribute="userForm" method="post">
        <h2><spring:message code="editProfile.header"/></h2>
        <table style="width: 100%">
            <tr>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                            <form:label path="name"><spring:message code="UserRegisterForm.name"/></form:label>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <form:input class="form-control" path="name" />
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                            <form:label path="surname"><spring:message code="UserRegisterForm.surname"/></form:label>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <form:input class="form-control" path="surname" />
                        </div>
                    </div>
                </td>
            </tr>
                <%--<tr>
                    <td>
                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                <form:label path="username"><spring:message code="UserRegisterForm.username"/></form:label>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="form-group">
                            <div class="input-group">
                                <form:input class="form-control" path="username" />
                            </div>
                        </div>
                    </td>
                </tr>--%>
            <tr>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-phone"></i></span>
                            <form:label path="telephone"><spring:message code="UserRegisterForm.telephone"/></form:label>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <form:input class="form-control" path="telephone" />
                            <form:errors path="telephone">error wach</form:errors>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-paper-plane"></i></span>
                            <form:label path="email"><spring:message code="UserRegisterForm.email"/></form:label>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <form:input class="form-control" path="email" />
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                            <form:label path="password"><spring:message code="UserRegisterForm.password"/></form:label>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <form:input class="form-control" path="password" />
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                                <span class="input-group-addon">
                                    <i class="fa fa-lock"></i>
                                    <i class="fa fa-check"></i>
                                </span>
                            <form:label path="repeatPassword"><spring:message code="UserRegisterForm.repeatPassword"/></form:label>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <div class="input-group">
                            <form:input class="form-control" path="repeatPassword"/>
                        </div>
                    </div>
                </td>
            </tr>
        </table>

        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block btn-lg"><spring:message code="signUpBody.button"/></button>
        </div>
    </form:form>
</div>
</body>
</html>

