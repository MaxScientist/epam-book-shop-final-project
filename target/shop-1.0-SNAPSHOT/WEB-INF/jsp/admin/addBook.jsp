<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>

<jsp:include page="../customer/fragments/header.jsp"/>
<div class="container" style="background-color: #f5f5f5; margin-top: 80px;">
    <c:if test="${(sessionScope.user.roleId eq Constants.roleAdminId) and
                    not sessionScope.user.banned and
                    sessionScope.user.statusId ne Constants.accessStatusDeletedId}">
    <div class="row">
        <div class="col-md-3 mt-5">
            <img src="img/defaultcover.jpg" alt="cover" class="img-fluid"/>
        </div>
        <form action="/main/addNewBook" method="post" enctype="multipart/form-data" class="col-md-9 mt-5">
            <c:if test="${not empty requestScope.emptyFieldError}">
                <small class="form-text text-danger"><fmt:message key="small.error.empty.field"/></small>
            </c:if>
            <div class="row">
                <div class="form-group col-md-8">
                    <label><fmt:message key="label.title"/></label>
                    <input name="bookTitle" type="text" class="form-control" required>
                </div>
                <div class="form-group col-md-4">
                    <select name="languageId" class="form-control">
                        <option value="${Constants.localeRussianId}"
                                <c:if test="${sessionScope.localeId eq Constants.localeRussianId}">selected</c:if>>
                            <fmt:message key="select.option.ru"/>
                        </option>
                        <option value="${Constants.localeEnglishId}"
                                <c:if test="${sessionScope.localeId eq Constants.localeEnglishId}">selected</c:if>>
                            <fmt:message key="select.option.en"/>
                        </option>
                    </select>
                    <c:if test="${not empty requestScope.languageNotExistsError}">
                        <small class="form-text text-danger"><fmt:message
                                key="small.error.not.exists.language"/></small>
                    </c:if>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label><fmt:message key="label.description"/></label>
                    <textarea name="bookDescription" class="form-control" rows="3" required></textarea>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="form-group col-md-6">
                    <label><fmt:message key="li.releaseDate"/></label>
                    <input name="bookReleaseDate" type="date" value="yyyy-mm-dd" placeholder="${requestScope.bookInfo.releaseDate}"
                           class="form-control" required>
                </div>
                <div class="form-group col-md-6">
                    <label><fmt:message key="li.publisher"/></label>
                    <input name="publisherHouse" type="text" class="form-control" placeholder="PublisherHouse" required>
                </div>
                <div class="row">
                    <div class="form-group col-md-6">
                        <label><fmt:message key="li.genre"/></label>

                        <select name="genreId" class="form-control">
                            <c:forEach items="${requestScope.genres}" var="genre"><label type="hidden"></label>
                                <option name="${genre.id}" value="${genre.id}">${genre.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md-6">
                        <label><fmt:message key="li.authorFirstName"/></label>
                        <input name="authorFirstName" type="text" placeholder="FirstName"
                               class="form-control" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label><fmt:message key="li.authorLastName"/></label>
                        <input name="authorLastName" type="text" placeholder="LastName"
                               class="form-control" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label><fmt:message key="li.bookPrice"/></label>
                        <input name="bookPrice" type="number" class="form-control" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label><fmt:message key="li.isbn"/></label>
                        <input name="bookISBN" type="text" placeholder="ISBN" class="form-control" required>
                    </div>

                    <div class="form-group col-md-6">
                        <label><fmt:message key="li.bookPages"/></label>
                        <input name="bookPages" type="number" class="form-control" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label><fmt:message key="li.bookBinding"/></label>
                        <input name="bookBinding" type="text" class="form-control" required>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-6">
                        <label><fmt:message key="label.releasingStatus"/></label>
                        <br>
                            <%--                            <c:forEach items="${requestScope.releasingStatuses}" var="status">--%>
                            <%--                                <div class="form-check form-check-inline">--%>
                            <%--                                    <input name="releasingStatusId" type="radio" value="${status.id}"--%>
                            <%--                                           class="form-check-input">--%>
                            <%--                                    <label class="form-check-label">${status.name}</label>--%>
                            <%--                                </div>--%>
                            <%--                            </c:forEach>--%>
                            <%--                            <c:if test="${not empty requestScope.releasingStatusNotExistsError}">--%>
                        <small class="form-text text-danger"><fmt:message
                                key="small.error.not.exists.releasingStatus"/></small>
                        </c:if>
                    </div>
                </div>
                <hr>
                <c:if test="${not empty requestScope.accessStatusError}">
                <small class="form-text text-danger"><fmt:message key="small.error.invalid.accessStatus"/></small>
                </c:if>
                <div class="form-group">
                    <label><fmt:message key="label.cover.image"/></label>
                    <input name="bookImage" type="file" accept="image/*" class="form-control-file">
                </div>
                <c:if test="${not empty requestScope.coverError}">
                <small class="form-text text-danger"><fmt:message key="small.error.invalid.cover"/></small>
                </c:if>
                <button type="submit" class="btn btn-primary btn-lg" style="float: right;"><fmt:message
                        key="button.add"/></button>
        </form>
    </div>
    <c:if test="${sessionScope.user.roleId eq Constants.roleUserId}">
        <img src="img/404.png" class="mx-auto" width="100%">
    </c:if>
</div>
<%--<jsp:include page="footer.jsp"/>--%>