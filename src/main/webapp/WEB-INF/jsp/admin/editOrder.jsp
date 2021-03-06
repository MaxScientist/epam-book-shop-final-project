<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>

<jsp:include page="../fragments/mainHeader.jsp"/>
<div class="container mt-5" style="background-color: #f5f5f5; margin-top: 100px;">
    <c:if test="${sessionScope.user.roleId eq Constants.roleAdminId and
                    not sessionScope.user.banned and
                    sessionScope.user.statusId ne Constants.accessStatusDeletedId}">
        <div class="row">
            <div class="col-md-12">
                <div class="user-dashboard-info-box table-responsive mb-0 bg-white p-4 shadow-sm">
                    <table class="table manage-candidates-top mb-0">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th><fmt:message key="label.user"/></th>
                            <th><fmt:message key="label.order.products"/></th>
                            <th><fmt:message key="label.total"/></th>
                            <th><fmt:message key="th.status"/></th>
                            <th><fmt:message key="label.date"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.orders}" var="order">
                            <tr class="candidates-list">
                                <td class="title">
                                        ${order.id}
                                </td>
                                <td class="title">
                                    <div class="candidate-list-details">
                                        <div class="candidate-list-info">
                                            <div class="candidate-list-title">
                                                <h5 class="mb-0">${order.user.userLogin}</h5>
                                            </div>
                                            <div>
                                                <ul>
                                                    <li><i>${order.user.email}</i></li>
                                                    <li><i>  ${order.user.address}, ${order.user.postalCode}</i></li>
                                                    <li><i>${order.user.phoneNumber}</i></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td class="title">
                                    <c:forEach items="${order.orderItems}" var="orderItem">
                                        <ul class="list-group mb-3">
                                            <li class="list nav-item list-group-item d-flex justify-content-between lh-condensed">
                                                <div class="clear">
                                                    <form action="${pageContext.request.contextPath}/main/editBook" method="get">
                                                        <input type="hidden" name="bookId" value="${orderItem.book.id}">
                                                        <input
                                                                type="submit"
                                                                class="btn-link"
                                                                value="${orderItem.book.title}"
                                                                style="white-space: nowrap; overflow: hidden;text-overflow: ellipsis; width: 250px">
                                                    </form>
                                                </div>
                                                <div class="clear">
                                                    <b class="text-success">${orderItem.quantity}
                                                        <fmt:message
                                                            key="label.amount.short"/></b> x <b
                                                        class="text-danger"> ${orderItem.fixedPrice} <fmt:message
                                                        key="span.currency.tenge"/></b>
                                                </div>
                                            </li>
                                        </ul>
                                    </c:forEach>
                                </td>
                                <td>
                                    <span><b>${order.totalPrice}</b>  <fmt:message key="span.currency.tenge"/></span>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/main/editOrderStatus" method="post">
                                        <input type="hidden" name="orderId" value="${order.id}">
                                        <select name="orderStatusId" class="form-control"
                                                onchange="this.form.submit();">
                                            <c:forEach items="${requestScope.orderStatuses}" var="status">
                                                <option class="text-primary" value="${status.id}"
                                                        <c:if test="${order.orderStatusId eq status.id}">
                                                            selected
                                                        </c:if>>
                                                        ${status.name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </form>
                                </td>
                                <td>
                                    <span><i>${order.createdDate}</i></span>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${sessionScope.user.roleId eq Constants.roleUserId}">
        <img src="${pageContext.request.contextPath}/static/images/empty.png" style="width: 40%" alt="">
    </c:if>
</div>
<jsp:include page="../fragments/footer.jsp"/>
