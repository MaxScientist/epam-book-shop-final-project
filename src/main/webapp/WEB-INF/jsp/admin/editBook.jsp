<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>

<jsp:include page="../customer/fragments/header.jsp"/>
<div class="container">
    <div class="card">
        <div class="container-fluid">
            <div class="wrapper-fluid">
                <div class="preview col-md-6">
                    <div class="preview-pic tab-content">
                        <div class="tab-pane active" id="pic-1">
                            <img src="data:image/jpg;base64,${requestScope.bookInfo.bookImage}" alt=""/>
                        </div>
                    </div>
                </div>
                <form action="/main/editBookInfo" method="post" enctype="multipart/form-data">
                    <div class="details col-md-6">
                        <div class="form-group col-md-8">
                            <label><fmt:message key="label.title"/></label>
                            <input name="bookTitle" type="text" class="form-control"
                                   value="${requestScope.bookInfo.title}" required>
                        </div>
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

                        <div class="row">
                            <div class="form-group col-md-6">
                                <label><fmt:message key="label.genre"/></label>
                                <select name="genreId" class="form-control">
                                    <c:forEach items="${sessionScope.genres}" var="genre">
                                        <label type="hidden"></label>
                                        <c:choose>
                                            <c:when test="${genre.name.equals(requestScope.bookInfo.genre.name)}">
                                                <option name="${genre.id}" value="${genre.id}"
                                                        selected>${genre.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option name="${genre.id}" value="${genre.id}">${genre.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label><fmt:message key="label.description"/></label>
                                <textarea name="bookDescription" class="form-control" rows="3" required>
                                    ${requestScope.bookInfo.description}</textarea>
                            </div>
                            <div class="form-group col-md-6">
                                <label><fmt:message key="th.price"/></label>
                                <input name="bookPrice" type="number" value="${requestScope.bookInfo.bookPrice}"
                                       class="form-control" required>
                            </div>
                            <div class="form-group col-md-6">
                                <input type="hidden" name="publisherId" value="${requestScope.bookInfo.publisher.id}">
                                <label><fmt:message key="publisher"/></label>
                                <input name="publisherHouse" type="text" class="form-control"
                                       value="${requestScope.bookInfo.publisher.publishHouse}"
                                       placeholder="PublisherHouse"
                                       required>
                            </div>
                            <div class="form-group col-md-6">
                                <label><fmt:message key="label.binding"/></label>
                                <input name="bookBinding" type="text" class="form-control"
                                       value="${requestScope.bookInfo.binding}" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label><fmt:message key="label.pages"/></label>
                                <input name="bookPages" type="number" value="${requestScope.bookInfo.pages}"
                                       class="form-control" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label><fmt:message key="label.isbn"/></label>
                                <input name="bookISBN" type="text" value="${requestScope.bookInfo.isbn}"
                                       placeholder="ISBN"
                                       class="form-control" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label><fmt:message key="li.releaseDate"/></label>
                                <input name="bookReleaseDate" type="date" value="${requestScope.bookInfo.releaseDate}"
                                       placeholder="${requestScope.bookInfo.releaseDate}"
                                       class="form-control" required>
                            </div>
                            <div class="form-group">
                            <select name="accessStatusId" class="form-control">
                                <option value="${Constants.accessStatusActiveId}"
                                        <c:if test="${requestScope.bookInfo.accessStatusId eq Constants.accessStatusActiveId}">
                                            selected
                                        </c:if>
                                >
                                    <fmt:message key="select.status.active"/>
                                </option>
                                <option value="${Constants.accessStatusDeletedId}"
                                        <c:if test="${requestScope.bookInfo.accessStatusId eq Constants.accessStatusDeletedId}">
                                            selected
                                        </c:if>
                                >
                                    <fmt:message key="select.status.deleted"/>
                                </option>
                            </select>
                        </div>
                            <div class="form-group">
                                <label><fmt:message key="label.cover.image"/></label>

                                <input name="bookImage" type="file" value="${requestScope.bookInfo.bookImage}" accept="image/*" class="form-control-file">
                            </div>

                            <input type="hidden" name="bookId" value="${requestScope.bookInfo.id}">
                            <button type="submit" class="btn btn-primary btn-lg" style="float: right;"><fmt:message
                                    key="button.add"/></button>

                        </div>
                    </div>
                </form>
                <div class="row">
                    <div class="col-md-12">
                        <div class="user-dashboard-info-box table-responsive mb-0 bg-white p-4 shadow-sm">
                            <table class="table manage-candidates-top mb-0">
                                <thead>
                                <tr>
                                    <th><fmt:message key="label.FirstName"/></th>
                                    <th><fmt:message key="label.LastName"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <form action="/main/addAuthorToBook" method="post">

                                    <tr>
                                        <input type="hidden" name="bookId" value="${requestScope.bookInfo.id}" required>
                                        <td class="title">
                                            <input type="text" name="authorFirstName" class="form-control"
                                                   placeholder="<fmt:message key="label.FirstName"/>" required>
                                        </td>
                                        <td class="title">
                                            <input type="text" name="authorLastName" class="form-control"
                                                   placeholder="<fmt:message key="label.LastName"/>">
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-warning" style="top: -15px;">
                                                <fmt:message
                                                        key="button.add"/></button>
                                        </td>
                                    </tr>

                                </form>
                                <c:forEach items="${requestScope.bookInfo.authors}" var="author" varStatus="iter">
                                    <tr>
                                        <form action="/main/editAuthor" method="post">
                                            <td class="title">
                                                <input type="text" name="authorFirstName"
                                                       value="${author.firstName}"
                                                       class="form-control" required>
                                            </td>
                                            <td class="title">
                                                <input type="text" name="authorLastName" value="${author.lastName}"
                                                       class="form-control" required>
                                            </td>
                                            <td>
                                                <input type="hidden" name="authorId" value="${author.id}" required>
                                                <button type="submit" class="btn btn-success" style="top: -15px;">
                                                    <fmt:message key="button.save"/>
                                                </button>
                                            </td>
                                        </form>
                                        <td>
                                            <c:if test="${sessionScope.user.roleId eq Constants.roleAdminId}">
                                                <form action="/main/deleteAuthor" method="post"
                                                      style="display: inline-block">
                                                    <input type="hidden" name="authorId" value="${author.id}" required>
                                                    <input type="hidden" name="bookId"
                                                           value="${requestScope.bookInfo.id}" required>
                                                    <button type="submit" class="btn btn-danger">
                                                        DELETE
                                                    </button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <%--                <form action="/main/addAuthorToBook" method="post">--%>
                <%--                    <input type="hidden" name="bookId" value="${requestScope.bookInfo.id}" required>--%>
                <%--                    <input type="text" name="authorFirstName" class="form-control"--%>
                <%--                           placeholder="<fmt:message key="label.FirstName"/>" required>--%>
                <%--                    <input type="text" name="authorLastName" class="form-control"--%>
                <%--                           placeholder="<fmt:message key="label.LastName"/>">--%>
                <%--                    <br>--%>
                <%--                    <c:if test="${not empty requestScope.notUniqueBookAuthorError}">--%>
                <%--                        <small class="form-text text-danger">--%>
                <%--                            <fmt:message key="small.error.author.exists"/>--%>
                <%--                        </small>--%>
                <%--                    </c:if>--%>
                <%--                    <br>--%>
                <%--                    <button style="width: 100%" type="submit" class="btn btn-outline-primary">--%>
                <%--                        <i class="fas fa-plus"></i><fmt:message key="button.add.author"/>--%>
                <%--                    </button>--%>
                <%--                </form>--%>
            </div>
        </div>
    </div>
</div>