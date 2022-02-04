<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>

<jsp:include page="../fragments/mainHeader.jsp"/>
<div class="container mt-5" style="background-color: #f5f5f5; margin-top: 100px;">
    <c:if test="${(sessionScope.user.roleId eq Constants.roleAdminId) and
                    not sessionScope.user.banned and
                    sessionScope.user.statusId ne Constants.accessStatusDeletedId}">
        <div class="row">
            <div class="col-md-12">
                <div class="user-dashboard-info-box table-responsive mb-0 bg-white p-4 shadow-sm">
                    <table class="table manage-candidates-top mb-0">
                        <thead>
                        <tr>
                            <th><fmt:message key="label.language"/></th>
                            <th><fmt:message key="label.genreName"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <form action="/main/addNewGenre" method="post">

                            <tr>
                                <td class="title">

                                    <input type="text" name="genreLanguageId" class="form-control" required>
                                </td>
                                <td class="title">
                                    <input type="text" name="genreName" class="form-control" required>
                                </td>
                            </tr>
                            <tr>
                                <td class="title">
                                    <input type="text" name="genreLanguageId" class="form-control" required>
                                </td>
                                <td class="title">
                                    <input type="text" name="genreName" class="form-control" required>
                                </td>
                                <td>
                                    <button type="submit" class="btn btn-success"><fmt:message
                                            key="button.add"/></button>
                                </td>
                            </tr>

                        </form>
                        <c:forEach items="${requestScope.genres}" var="genre" varStatus="iter">
                            <tr>
                                <form action="/main/editGenre" method="post">
                                    <td class="title">
                                                <input type="text" name="genreLanguageId"
                                                       value="${genre.languageId}"
                                                       class="form-control" required>
                                    </td>
                                    <td class="title">
                                        <input type="text" name="genreName" value="${genre.name}"
                                               class="form-control" required>
                                    </td>
                                    <td>
                                        <input type="hidden" name="genreId" value="${genre.id}" required>
                                        <c:choose>
                                            <c:when test="${iter.count%2 == 0}">
                                                <button type="submit" class="btn btn-danger">
                                                <fmt:message key="button.save"/>
                                                </button>
                                            </c:when>
                                        </c:choose>
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