<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>

<jsp:include page="../fragments/mainHeader.jsp"/>
<c:if test="${sessionScope.user.roleId eq Constants.roleAdminId and
                    not sessionScope.user.banned and
                    sessionScope.user.statusId ne Constants.accessStatusDeletedId}">
    <div class="row">
        <div class="col-md-12">
            <div class="user-dashboard-info-box table-responsive mb-0 bg-white p-4 shadow-sm">
                <table class="table manage-candidates-top mb-0">
                    <thead>
                    <tr>
                        <th><fmt:message key="th.user.login"/></th>
                        <th><fmt:message key="th.user.role"/></th>
                        <th><fmt:message key="th.status"/></th>
                        <th><fmt:message key="th.access"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.users}" var="user">
                        <tr class="candidates-list">
                            <td class="title">
                                <div class="candidate-list-details">
                                    <div class="candidate-list-info">
                                        <div class="candidate-list-title">
                                            <h5 class="mb-0">${user.userLogin}</h5>
                                        </div>
                                        <div class="candidate-list-option">
                                            <ul class="list-unstyled">
                                                <li><i class="fas fa-envelope"></i>
                                                        ${user.email}</li>
                                                <li><i class="fas fa-map-marker-alt pr-1"></i>
                                                        ${user.address}, ${user.postalCode}
                                                </li>
                                                <li><i class="fas fa-phone"></i>
                                                        ${user.phoneNumber}
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <span>
                                    <c:choose>
                                        <c:when test="${user.roleId eq Constants.roleUserId}">
                                            <fmt:message key="span.role.user"/>
                                        </c:when>
                                        <c:when test="${user.roleId eq Constants.roleAdminId}">
                                            <fmt:message key="span.role.admin"/>
                                        </c:when>
                                    </c:choose>
                                </span>
                            </td>
                            <form action="/main/editUserCredentials" method="post">
                                <input type="hidden" name="userId" value="${user.id}" required>
                                <td class="candidate-list-favourite-time text-center">
                                    <select name="statusId" class="form-control">
                                        <option value="${Constants.accessStatusDeletedId}"
                                                <c:if test="${user.statusId eq Constants.accessStatusDeletedId}">
                                                    selected
                                                </c:if>>
                                            <fmt:message key="select.status.deleted"/>
                                        </option>
                                        <option value="${Constants.accessStatusActiveId}"
                                                <c:if test="${user.statusId eq Constants.accessStatusActiveId}">
                                                    selected
                                                </c:if>>
                                            <fmt:message key="select.status.active"/>
                                        </option>
                                    </select>
                                </td>
                                <td>
                                    <div class="form-check">
                                        <input class="form-check-input"
                                               type="checkbox"
                                               name="isUserBanned"
                                               value="true"
                                               <c:if test="${user.banned}">checked</c:if>>
                                        <label class="form-check-label">
                                            <fmt:message key="label.ban"/>
                                        </label>
                                    </div>
                                </td>
                                    <%--                                Starting from this point!--%>
                                <td class="candidate-list-favourite-time text-center">
                                    <div class="form-check">
                                        <select name="roleId" class="form-control">
                                            <option value="${Constants.roleUserId}"
                                                    <c:if test="${user.roleId eq Constants.roleUserId}">
                                                        selected
                                                    </c:if>>
                                                <fmt:message key="select.role.user"/>
                                            </option>
                                            <option value="${Constants.roleAdminId}"
                                                    <c:if test="${user.roleId eq Constants.roleAdminId}">
                                                        selected
                                                    </c:if>>
                                                <fmt:message key="select.role.admin"/>
                                            </option>
                                        </select>
                                    </div>
                                </td>
                                <td>
                                    <button type="submit" class="btn btn-danger">
                                        <fmt:message key="button.save"/>
                                    </button>
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${sessionScope.user.roleId eq Constants.roleUserId}">
    <img src="img/404.png" class="mx-auto" width="100%">
</c:if>
</div>
<jsp:include page="../fragments/footer.jsp"/>