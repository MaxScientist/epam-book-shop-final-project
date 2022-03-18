<%@ page contentType="text/html;charset=UTF-8"  isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>

<jsp:include page="fragments/mainHeader.jsp"/>
<div class="container">
    <c:if test="${(sessionScope.user.roleId eq Constants.roleUserId) and
                    not sessionScope.user.banned and
                    sessionScope.user.statusId ne Constants.accessStatusDeletedId}">
        <div class="row" style="background-color: #f5f5f5; margin-top: 80px;">
            <form id="confirmOrder" action="/main/confirmOrderAction" method="post">
                <button class="btn btn-primary btn-lg btn-block mb-4" type="submit">
                    <fmt:message key="button.confirm"/>
                </button>
            </form>
            <div class="col-md-6 order-md-2 mb-4 mt-4">
                <h4 class="d-flex justify-content-between align-items-center mb-3">
                    <span class="text-muted"><fmt:message key="title.cart"/></span>
                </h4>
                <ul class="list-group mb-3">
                    <li class="d-flex justify-content-between lh-condensed">
                        <div>
                            <h6 class="my-0"><fmt:message key="label.total"/></h6>
                        </div>
                        <b class="text-danger">${requestScope.cartTotalPrice.longValue()} <fmt:message
                                key="span.currency.tenge"/></b>
                    </li>
                </ul>
                <c:forEach items="${requestScope.cartItems}" var="cartItem">
                    <ul class="list-group mb-3">
                        <li class="list-group-item d-flex justify-content-between lh-condensed">
                            <div>
                                <a><fmt:message key="label.title"/>: ${cartItem.book.title}</a>
                            </div>
                            <div>
                                <b class="text-success">${cartItem.quantity} pc</b> x <b
                                    class="text-danger"> ${cartItem.book.bookPrice.longValue()} <fmt:message
                                    key="span.currency.tenge"/> </b>
                            </div>
                            <input type="hidden" name="cartItemId" value="${cartItem.id}" form="confirmOrder" required>
                        </li>
                    </ul>
                </c:forEach>
            </div>
            <div class="col-md-6 order-md-1 mt-4">
                <h4 class="mb-3"><fmt:message key="head.address.shipping"/></h4>
                <hr class="mb-4">
                <div class="candidate-list-details">
                    <div class="candidate-list-info">
                        <div class="candidate-list-title">
                            <h5 class="mb-0">${sessionScope.user.userLogin}</h5>
                        </div>
                        <div class="candidate-list-option">
                            <ul class="list-unstyled">
                                <li>
                                    <label for="ccn"><fmt:message key="label.credit.cart"/>:</label>
                                    <input id="ccn" type="tel" inputmode="numeric" pattern="[0-9\s]{13,19}" autocomplete="cc-number" maxlength="19" placeholder="xxxx xxxx xxxx xxxx" required>

                                </li>
                                <li>
                                        ${sessionScope.user.email}
                                </li>
                                <li>
                                        ${sessionScope.user.address}, ${sessionScope.user.postalCode}
                                </li>
                                <li>
                                        ${sessionScope.user.phoneNumber}
                                </li>
                                <li>
                                    <button type="submit" class="btn-warning">
                                        <a href="/main/editProfileAction"><fmt:message key="button.edit.personal.details"/></a>
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <hr class="mb-4">
            </div>
        </div>
    </c:if>
    <c:if test="${sessionScope.user.roleId eq Constants.roleAdminId}">
        <img src="img/404.png" class="mx-auto" width="100%">
    </c:if>
</div>
<jsp:include page="fragments/footer.jsp"/>