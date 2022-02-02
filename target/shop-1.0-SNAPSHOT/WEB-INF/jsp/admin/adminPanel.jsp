<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>

<jsp:include page="../customer/fragments/header.jsp"/>

<div class="container" style="background-color: #f5f5f5; margin-top: 100px;">
    <c:if test="${sessionScope.user.roleId eq Constants.roleAdminId and
                    not sessionScope.user.banned and
                    sessionScope.user.statusId ne Constants.accessStatusDeletedId}">
        <div class="mt-4">
            <div class="row row-cols-1 row-cols-md-2 g-4">
                <div class="card col card-manga">
                    <div class="card-body">
                        <h6 class="card-title"><fmt:message key="head.user.settings"/></h6>
                        <form action="/main/displayAllUsers" method="get">
                            <input type="hidden" name="direction" value="users.jsp">
                            <input type="submit" class="btn btn-outline-primary"
                                   value="<fmt:message key="button.edit"/>">
                        </form>
                    </div>
                </div>
                <div class="card col card-manga">
                    <div class="card-body">
                        <h6 class="card-title"><fmt:message key="head.author.settings"/></h6>
                        <form action="DisplayAllAuthors" method="get">
                            <input type="hidden" name="direction" value="authors.jsp">
                            <input type="submit" class="btn btn-outline-primary"
                                   value="<fmt:message key="button.edit"/>">
                        </form>
                    </div>
                </div>
                <div class="card col card-manga">
                    <div class="card-body">
                        <h6 class="card-title"><fmt:message key="head.genres.settings"/></h6>
                        <form action="/main/displayAllGenres" method="get">
<%--                            <input type="hidden" name="direction" value="publishers.jsp">--%>
                            <input type="submit" class="btn btn-outline-primary"
                                   value="<fmt:message key="button.edit"/>">
                        </form>
                    </div>
                </div>
                <div class="card col card-manga">
                    <div class="card-body">
                        <h6 class="card-title"><fmt:message key="head.order.settings"/></h6>
                        <form action="/main/displayAllOrders" method="get">
                            <input type="hidden" name="direction" value="orders.jsp">
                            <input type="submit" class="btn btn-outline-primary"
                                   value="<fmt:message key="button.edit"/>">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${sessionScope.user.roleId eq Constants.roleUserId}">
        <img src="img/404.png" class="mx-auto" width="100%">
    </c:if>
</div>
<%--<jsp:include page="footer.jsp"/>--%>
<%--</body>--%>
<%--</html>--%>