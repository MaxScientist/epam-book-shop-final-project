<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>
<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Book Shop &ndash; <fmt:message key="head.edit.profile"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
</head>
<body>
<c:if test="${not sessionScope.user.banned and sessionScope.user.statusId ne Constants.accessStatusDeletedId}">
    <section class="vh-100" style="background-color: #eee;">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-lg-12 col-xl-11">
                    <div class="cart text-black" style="border-radius: 25px;">
                        <div class="cart-body p-md-5">
                            <div class="row justify-content-center">
                                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-2 order-lg-1">
                                    <div class="row" style="text-align: center;">
                                        <h3 id="registerHead" class="mt-3"><fmt:message key="head.register"/></h3>
                                    </div>
                                    <form id="registerForm" method="post" action="/main/editProfileAction" class="mx-1 mx-md-4">
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <div class="form-outline flex-fill mb-0">
                                                <label class="form-label" for="registerFirstName"><fmt:message
                                                        key="label.FirstName"/></label>
                                                <input type="text" name="userFirstName" class="form-control"
                                                       value="${sessionScope.user.firstName}"
                                                       id="registerFirstName"
                                                       placeHolder="<fmt:message
                                                        key="label.FirstName"/>" required/>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <div class="form-outline flex-fill mb-0">
                                                <label class="form-label" for="registerLastName"><fmt:message
                                                        key="label.LastName"/></label>
                                                <input type="text" name="userLastName" class="form-control"
                                                       id="registerLastName" value="${sessionScope.user.lastName}"
                                                       placeholder="<fmt:message
                                                        key="label.LastName"/>" required/>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <div class="form-outline flex-fill mb-0">
                                                <label class="form-control" for="registerUsername"><fmt:message
                                                        key="label.login"/></label>
                                                <input type="text" name="userLogin" class="form-control"
                                                       value="${sessionScope.user.userLogin}"
                                                       id="registerUsername"
                                                       placeholder="<fmt:message
                                                        key="label.login"/>" required>
                                                <small id="registerUsernameHelp"
                                                       class="form-text text-danger">${requestScope.loginError}</small>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <div class="form-outline flex-fill mb-0">
                                                <label class="form-control" for="registerPassword">
                                                    <fmt:message key="label.password"/>
                                                </label>
                                                <input type="password" name="userPassword" class="form-control"
                                                       id="registerPassword"
                                                       value="${sessionScope.user.password}"
                                                       placeholder="<fmt:message key="placeholder.password"/>" required>
                                                <small id="registerPasswordHelp" class="form-text text-danger">
                                                        ${requestScope.passwordError}
                                                </small>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <div class="form-outline flex-fill mb-0">
                                                <label class="form-control" for="registerPostalCode"><fmt:message
                                                        key="label.postal"/></label>
                                                <input type="text" name="userPostalCode" class="form-control"
                                                       id="registerPostalCode" value="${sessionScope.user.postalCode}"
                                                       pattern="[0-9]{6}" placeholder="040600" required>
                                                <small id="registerPostalCodedHelp" class="form-text text-muted"></small>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <div class="form-outline flex-fill mb-0">
                                                <label class="form-control" for="registerAddress"><fmt:message
                                                        key="label.address"/></label>
                                                <input type="text" name="userAddress" class="form-control"
                                                       id="registerAddress" value="${sessionScope.user.address}"
                                                       placeholder="Kazakhstan, Nur-Sultan" required>
                                                <small id="registerAddressHelp" class="form-text text-muted"></small>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <div class="form-outline flex-fill mb-0">
                                                <label class="form-control" for="registerPhoneNumber"><fmt:message
                                                        key="label.phone"/></label>
                                                <input type="tel" name="userPhoneNumber" id="registerPhoneNumber"
                                                       class="form-control" value="${sessionScope.user.phoneNumber}"
                                                       pattern="[0-9]{10}"  required>
                                                <small id="registerPhoneHelp" class="form-text text-muted"></small>
                                            </div>
                                        </div>

                                        <button style="align-self: center" type="submit" class="btn btn-primary btn-block text-uppercase">
                                            <fmt:message key="button.save"/>
                                        </button>
                                        <div style="display:flex">
                                            <a class="d-block text-center mt-2 small" href="/main/home">
                                                <fmt:message key="button.main"/>
                                            </a>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</c:if>
</body>
</html>