<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>
<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>
<jsp:include page="fragments/mainHeader.jsp"/>

<div class="container mt-5" style="background-color: #f5f5f5; margin-top: 100px;">
    <c:if test="${(sessionScope.user.roleId eq Constants.roleUserId) and
                    not sessionScope.user.banned and
                    sessionScope.user.statusId ne Constants.accessStatusDeletedId}">
        <div class="row">
            <div class="col-md-12">
                <div class="user-dashboard-info-box tables-responsive mb-0 bg-white p-4 shadow-sm">
                    <table class="table manage-candidates-top mb-0">
                        <thead>
                        <tr>
                            <th scope="col"><fmt:message key="label.select"/></th>
                            <th scope="col"><fmt:message key="label.book"/></th>
                            <th scope="col"><fmt:message key="label.price"/>
                                (<span><fmt:message key="span.currency.tenge"/></span>)
                            </th>
                            <th scope="col"><fmt:message key="label.quantity"/></th>
                            <th scope="col"><fmt:message key="label.sum"/>
                                (<span><fmt:message key="span.currency.tenge"/></span>)
                            </th>
                            <th scope="col"><fmt:message key="label.delete"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.cartItems}" var="cartItem">
                            <tr>
                                <td scope="row">
                                    <div class="form-check">
                                        <label>
                                            <input name="cartItemId" value="${cartItem.id}" form="checkOutSelected"
                                                   class="form-check-input" type="checkbox">
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <div>
                                        <div>
                                            <h5 class="card-title">
                                                <fmt:message key="label.title"/>
                                                    ${cartItem.book.title}
                                            </h5>
                                            <div>
                                                <ul class="list-items">
                                                    <li>ISBN: <span>${cartItem.book.isbn}</span></li>
                                                    <li><fmt:message key="label.pageCount"/>:
                                                        <span>${cartItem.book.pages}</span></li>
                                                    <li><fmt:message key="label.binding"/>:
                                                        <span>${cartItem.book.binding}</span></li>
                                                    <li><fmt:message key="label.releaseDate"/>:
                                                        <span>${cartItem.book.releaseDate}</span></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <b class="text-success">${cartItem.book.bookPrice}</b>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/main/setQuantity" method="get" id="SetQuantityForm">
                                        <input type="hidden" name="cartItemId" value="${cartItem.id}">
                                        <input
                                                class="form-control"
                                                type="number"
                                                name="quantity"
                                                min="1"
                                                onchange="this.form.submit();"
                                                value="${cartItem.quantity}">
                                    </form>
                                </td>
                                <td>
                                    <span class="text-danger"><b>${cartItem.book.bookPrice.longValue() * cartItem.quantity}</b><span></span></span>
                                </td>
                                <td class="candidate-list-favourite-time text-center">
                                    <form action="${pageContext.request.contextPath}/main/deleteCartItem" method="post" class="form-check">
                                        <input type="hidden" name="cartItemId" value="${cartItem.id}">
                                        <button type="submit" class="btn btn-danger">
                                            <fmt:message key="label.delete"/>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${not empty requestScope.cartItems}">
                        <div class="row">
                            <form id="checkOutAll" action="${pageContext.request.contextPath}/main/checkOutAll" method="post" class="col">
                                <button type="submit" class="btn btn-primary" style="width: 100%"><fmt:message
                                        key="button.checkout.all"/></button>
                            </form>
                            <form id="checkOutSelected" action="${pageContext.request.contextPath}/main/checkOutSelected" method="post" class="col">
                                <button type="submit" class="btn btn-outline-primary" style="width: 100%"><fmt:message
                                        key="button.checkout.selected"/></button>
                            </form>
                        </div>
                    </c:if>
                    <c:if test="${empty requestScope.cartItems}">
                        <div class="mx-auto" style="text-align: center">
                            <h5 class="card-title"><fmt:message key="head.cart.empty"/></h5>
                            <img src="${pageContext.request.contextPath}/static/images/empty.png" style="display: block; margin-right: auto;"
                                 type="img/png" class="mx-auto" width="400px" height="300px" alt=""/>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${sessionScope.user.roleId eq Constants.roleAdminId}">
        <img src="${pageContext.request.contextPath}/static/images/error.png" style="display: block; margin-right: auto; margin-right: auto;" type="img/png" class="mx-auto" width="70%"/>
    </c:if>
</div>
<jsp:include page="fragments/footer.jsp"/>
