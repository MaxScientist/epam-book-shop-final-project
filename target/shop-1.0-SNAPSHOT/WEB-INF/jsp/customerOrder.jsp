<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>

<jsp:include page="fragments/mainHeader.jsp"/>
<div class="container mt-5" style="background-color: #f5f5f5; margin-top: 100px;">
    <c:if test="${sessionScope.user.roleId eq Constants.roleUserId and
                    not sessionScope.user.banned and
                    sessionScope.user.statusId ne Constants.accessStatusDeletedId}">
        <div class="row">
            <div class="col-md-12">
                <div class="user-dashboard-info-box table-responsive mb-0 bg-white p-4 shadow-sm">
                    <table class="table manage-candidates-top mb-0">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th><fmt:message key="label.order.products"/></th>
                            <th><fmt:message key="label.total"/></th>
                            <th><fmt:message key="label.status"/></th>
                            <th><fmt:message key="label.date"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty requestScope.userOrders}">
                            <div class="mx-auto" style="text-align: center">
                                <h5 class="card-title"><fmt:message key="head.order.empty"/></h5>
                                <img src="${pageContext.request.contextPath}/static/img/empty.png" style="width: 40%">
                            </div>
                        </c:if>
                        <c:if test="${not empty requestScope.userOrders}">
                            <c:forEach items="${requestScope.userOrders}" var="order">
                                <tr class="candidates-list">
                                    <td class="title">
                                            ${order.id}
                                    </td>
                                    <td class="title">
                                        <c:forEach items="${order.orderItems}" var="orderItem">
                                            <ul class="list-group mb-3">
                                                <li class="list-group-item d-flex justify-content-between lh-condensed">
                                                    <div>
                                                        <h5 class="card-title">
                                                            <span>${orderItem.book.title}
                                                        </h5>
                                                        <div>
                                                            <ul>
                                                                <li>ISBN: <span>${orderItem.book.isbn}</span></li>
                                                                <li><fmt:message key="label.pageCount"/>
                                                                    <span>${orderItem.book.pages}</span>
                                                                </li>
                                                                <li><fmt:message key="label.binding"/>:
                                                                    <span>${orderItem.book.binding}</span></li>
                                                                <li><fmt:message key="label.releaseDate"/>:
                                                                    <span>${orderItem.book.releaseDate}</span>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <b class="text-success">${orderItem.quantity}
                                                            <fmt:message key="label.amount.short"/></b> x
                                                        <b class="text-danger"> ${orderItem.fixedPrice}
                                                            <fmt:message key="span.currency.tenge"/></b>
                                                    </div>
                                                </li>
                                            </ul>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <span><b>${order.totalPrice}</b> <fmt:message key="span.currency.tenge"/></span>
                                    </td>
                                    <td>
                                        <span class="text-primary"><b>${order.status.name}</b></span>
                                    </td>
                                    <td>
                                        <span><i>${order.createdDate}</i></span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </c:if>
</div>
<jsp:include page="fragments/footer.jsp"/>
