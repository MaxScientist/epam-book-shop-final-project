<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>
<jsp:include page="fragments/mainHeader.jsp"/>
<div class="main">
    <div class="wrap">
        <div class="section group">
            <h2 class="head"><fmt:message key="featured.books"/></h2>
            <form action="/main/sortBooks" method="get">
                <select name="sortType" class="custom-select" onchange="this.form.submit();">
                    <c:forEach items="${sessionScope.sortTypes}" var="sortType">
                        <option value="${sortType}"
                                <c:if test="${sessionScope.selectedSortType eq sortType}">
                                    selected
                                </c:if>>
                            <fmt:message key="${sortType.key}"/>
                        </option>
                    </c:forEach>
                </select>
            </form>
            <div class="cont">
                <div id="content_left" class="cell left">
                    <div id="main-catalog-filter-layer">
                        <div class="filter-left">
                            <div class="ct slides_container" style="width: 50%; margin: 0; text-align: left">

                                <p class="uppercase"><fmt:message key="label.genres"/></p>

                                <form action="/main/filterGenre" method="get">
                                    <c:forEach items="${sessionScope.genres}" var="genre">
                                        <div class="form-check form-check-inline">
                                            <input name="genreId" type="checkbox" value="${genre.id}"
                                                   class="form-check-input"
                                                   <c:if test="${requestScope.checkedGenreIds.contains(genre.id)}">checked</c:if>>
                                            <label class="form-check-label"
                                                   style="font-size: small">${genre.name}</label>
                                        </div>
                                    </c:forEach>
                                    <hr style="width: 30%;"/>
                                    <button class="btn btn-primary justify-content-center" type="submit"
                                            style="width: fit-content">
                                        <fmt:message key="label.search"/>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="main-container">
                    <table>
                        <tr>
                            <c:forEach items="${sessionScope.books}" var="book" varStatus="loop">

                            <c:if test="${not loop.first and loop.index % 3 == 0}">
                        </tr>
                        <tr>
                            </c:if>
                            <td class="td-custom">
                                <img src="data:image/jpeg;base64,${book.bookImage}" alt="cover"
                                     style="width: 200px; height: 300px"
                                />
                                <div class="card-custom">
                                    <label class="label-custom truncate" style="font-size: 14px">
                                            ${book.title}
                                    </label>
                                    <div>
                                        <label style="font-size: 14px">
                                                ${book.bookPrice}
                                        </label>
                                        <fmt:message key="span.currency.tenge"/>
                                    </div>
                                    <div class="row">
                                        <div>
                                            <c:choose>
                                                <c:when test="${sessionScope.user.roleId eq Constants.roleAdminId}">
                                                    <form action="/main/editBook" method="get">
                                                        <input type="hidden" name="bookId" value="${book.id}" required>
                                                        <button style="width: 100%" type="submit"
                                                                class="btn btn-outline-primary">
                                                            <fmt:message key="button.edit"/>
                                                        </button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="/main/bookDetails" method="post">
                                                        <input type="hidden" name="bookId" value="${book.id}">
                                                        <button type="submit"
                                                                class="btn btn-outline-primary">
                                                            <fmt:message key="button.moreDetails"/>
                                                        </button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <hr style="width: 10%" align="left"/>
                            </td>
                            </c:forEach>
                        </tr>
                    </table>

                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<%@ include file="fragments/footer.jsp" %>