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
                <form action="/main/editBookInfo">
                    <div class="details col-md-6">
                        <div class="form-group col-md-8">
                            <label><fmt:message key="label.title"/></label>
                            <input name="bookTitle" type="text" class="form-control"
                                   value="${requestScope.bookInfo.title}" required>
                        </div>

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

                            <button type="submit" class="btn btn-primary btn-lg" style="float: right;"><fmt:message
                                    key="button.add"/></button>

                        </div>
                    </div>
                </form>
                <form action="/main/addAuthorToBook" method="post">
                    <input type="hidden" name="mangaID" value="${requestScope.manga.id}" required>
                    <input type="text" name="authorFirstName" class="form-control"
                           placeholder="<fmt:message key="label.FirstName"/>" required>
                    <input type="text" name="authorLastName" class="form-control"
                           placeholder="<fmt:message key="label.LastName"/>">
                    <br>
                    <c:if test="${not empty requestScope.notUniqueBookAuthorError}">
                        <small class="form-text text-danger">
                            <fmt:message key="small.error.author.exists"/>
                        </small>
                    </c:if>
                    <br>
                    <input type="hidden" name="bookId" value="${requestScope.bookInfo.id}">
                    <button style="width: 100%" type="submit" class="btn btn-outline-primary">
                        <i class="fas fa-plus"></i><fmt:message key="button.add.author"/>
                    </button>
                </form>
                <c:forEach items="${requestScope.bookInfo.authors}" var="author">
                    <h4>${author.firstName} ${author.lastName}</h4>
                </c:forEach>
            </div>
        </div>
    </div>
</div>