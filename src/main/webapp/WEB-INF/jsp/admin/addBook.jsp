<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>

<jsp:include page="../fragments/mainHeader.jsp"/>
<div class="container" style="background-color: #f5f5f5; margin-top: 80px;">
    <c:if test="${(sessionScope.user.roleId eq Constants.roleAdminId) and
                    not sessionScope.user.banned and
                    sessionScope.user.statusId ne Constants.accessStatusDeletedId}">
    <div class="row">

        <form action="/main/addNewBook" method="post" enctype='multipart/form-data' class="col-md-9 mt-5">
            <c:if test="${not empty requestScope.emptyFieldError}">
                <small class="form-text text-danger"><fmt:message key="small.error.empty.field"/></small>
            </c:if>
            <div class="row">
                <div class="form-group col-md-8">
                    <label><fmt:message key="label.title"/></label>
                    <input name="bookTitle" type="text" class="form-control" required>
                </div>
                <c:if test="${not empty requestScope.suchBookExistsError}">
                    <small><fmt:message key="small.error.invalid.book.title" /></small>
                </c:if>
                <div class="form-group col-md-4">
                    <label><fmt:message key="label.book.language"/></label>
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
                    <label><fmt:message key="label.releaseDate"/></label>
                    <input name="bookReleaseDate" type="date" value="yyyy-mm-dd"
                           placeholder="${requestScope.bookInfo.releaseDate}"
                           class="form-control" required>
                </div>
                <div class="form-group col-md-6">
                    <label><fmt:message key="label.publisher"/></label>
                    <input name="publisherHouse" type="text" class="form-control" placeholder="PublisherHouse" required>
                </div>

                <div class="form-group col-md-6" style="height: 56px;">
                    <label><fmt:message key="label.genre"/></label>

                    <select name="genreId" class="form-control">
                        <c:forEach items="${requestScope.genres}" var="genre"><label type="hidden"></label>
                            <option name="${genre.id}" value="${genre.id}">${genre.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-6">
                    <label><fmt:message key="label.authorFirstName"/></label>
                    <input name="authorFirstName" type="text" placeholder="FirstName"
                           class="form-control" required>
                    <label><fmt:message key="label.authorLastName"/></label>
                    <input name="authorLastName" type="text" placeholder="LastName"
                           class="form-control" required>
                </div>

                <div class="form-group col-md-6">
                    <label><fmt:message key="label.price"/>
                    </label><label>(<fmt:message key="span.currency.tenge"/>)</label>
                    <input name="bookPrice" type="number" class="form-control" required>
                </div>
                <div class="form-group col-md-6">
                    <label><fmt:message key="label.isbn"/></label>
                    <input name="bookISBN" type="text" placeholder="ISBN" class="form-control" required>
                </div>
                <c:if test="${not empty requestScope.isbnError}">
                <small class="form-text text-danger"><fmt:message key="small.error.invalid.isbn"/></small>
                </c:if>
                <div class="form-group col-md-6">
                    <label><fmt:message key="label.pages"/></label>
                    <input name="bookPages" type="number" class="form-control" required>
                </div>
                <div class="form-group col-md-6">
                    <label><fmt:message key="label.binding"/></label>
                    <input name="bookBinding" type="text" class="form-control" required>
                </div>
                <hr>

                <div class="form-group">
                    <label><fmt:message key="label.book.image"/></label>
                    <input name="bookImage" type='file'  accept="image/*" class="form-control-file">
                </div>
                <c:if test="${not empty requestScope.imageError}">
                <small class="form-text text-danger"><fmt:message key="small.error.invalid.image"/></small>
                </c:if>
                <button type="submit" class="btn btn-primary btn-lg" style="float: right;"><fmt:message
                        key="button.add"/></button>
        </form>
        </c:if>

    </div>
    <c:if test="${sessionScope.user.roleId eq Constants.roleUserId}">
        <img src="img/404.png" class="mx-auto" width="100%">
    </c:if>
</div>
<%--<jsp:include page="footer.jsp"/>--%>