<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="local" />

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>
<c:choose>
    <c:when test="${sessionScope.user != null}">
        <jsp:include page="customer/fragments/header.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include  page="fragments/mainHeader.jsp"/>
    </c:otherwise>

</c:choose>
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
                            <h6 class="my-0"><fmt:message key="th.total"/></h6>
                        </div>
                        <b class="text-danger">${requestScope.cartTotalPrice.longValue()} <fmt:message
                                key="span.currency.tenge"/></b>
                    </li>
                </ul>
                <c:forEach items="${requestScope.cartItems}" var="cartItem" >
                    <ul class="list-group mb-3">
                        <li class="list-group-item d-flex justify-content-between lh-condensed">
                            <div>
                                <a><fmt:message key="head.vol"/>${cartItem.book.title}</a>
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
                                    <i class="fas fa-envelope"></i>
                                        ${sessionScope.user.email}
                                </li>
                                <li>
                                    <i class="fas fa-map-marker-alt pr-1"></i>
                                        ${sessionScope.user.address}, ${sessionScope.user.postalCode}
                                </li>
                                <li>
                                    <i class="fas fa-phone"></i>
                                        ${requestScope.user.phone}
                                </li>
                            </ul>
                        </div>
                        <button type="submit" class="btn btn-secondary">
                            <a href="edit_profile.jsp"><fmt:message key="button.edit"/></a>
                        </button>
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

