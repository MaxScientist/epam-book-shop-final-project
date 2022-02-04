<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%@include file="fragments/header.jsp"%>
<div class="main">
    <div class="wrap">
        <div class="section group">
            <div class="cont span_2_of_3">
                <h2 class="head">Featured Products</h2>
                <div id="content_left" class="cell left" style="">
                    <div id="main-catalog-filter-layer">
                        <div id="filter-left">
                            <div class="filter-left">
                                <div class="ct">
                                    <p class="uppercase"><fmt:message key="label.genres"/></p>
                                    <form action="/main/filterGenre" method="get">
                                        <c:forEach items="${requestScope.genres}" var="genre">
                                            <div class="form-check form-check-inline">
                                                <input name="genreId" type="checkbox" value="${genre.id}" class="form-check-input"
                                                       <c:if test="${fn:containsIgnoreCase(requestScope.checkedGenreIds, genre.id)}">checked</c:if>>
                                                <label class="form-check-label">${genre.name}</label>
                                            </div>
                                        </c:forEach>
                                        <hr/>
                                        <button class="btn btn-primary justify-content-center" type="submit" style="width: 100%">
                                            <fmt:message key="label.search"/>
                                        </button>
                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <c:forEach items="${requestScope.books}" var="book">
                    <div class="row g-2 mt-3">
                        <div class="col-md-3">
                            <img src="data:image/jpeg;base64,${book.bookImage}" alt="cover" class="img-fluid"/>
                        </div>
                        <div class="col-md-9">
                            <div class="card-body">
                                <h5 class="card-title">
                                    <fmt:message key="head.vol"/> < ${book.title}
                                </h5>
                                <ul class="list-items">
                                    <c:if test="${sessionScope.user.roleId eq Constants.roleAdminId}">
                                        <li><fmt:message key="th.status"/>:
                                            <span class="text-danger">
                                                    <c:choose>
                                                        <c:when test="${book.accessStatusId eq Constants.accessStatusActiveId}">
                                                            <fmt:message key="select.status.active"/>
                                                        </c:when>
                                                        <c:when test="${book.accessStatusId eq Constants.accessStatusDeletedId}">
                                                            <fmt:message key="select.status.deleted"/>
                                                        </c:when>
                                                    </c:choose>
                                                </span>
                                        </li>
                                    </c:if>
                                        <%--                        <li>ISBN: <span>${book.isbn}</span></li>--%>
                                        <%--                        <li><fmt:message key="li.pageCount"/>: <span>${volume.pageCount}</span></li>--%>
                                        <%--                        <li><fmt:message key="li.format"/>: <span>${volume.format}</span></li>--%>
                                        <%--                        <li><fmt:message key="li.size"/>: <span>${volume.size}</span></li>--%>
                                        <%--                        <li><fmt:message key="li.binding"/>: <span>${volume.binding}</span></li>--%>
                                        <%--                        <li class="text-success fw-bold"><fmt:message key="th.price"/>:--%>
                                        <%--                            <span>${volume.price}</span> <fmt:message key="span.currency.tenge"/>--%>
                                        <%--                        </li>--%>
                                        <%--                        <li><fmt:message key="li.releaseDate"/>: <span>${volume.releaseDate}</span></li>--%>
                                </ul>
                                <div class="row card-footer">
                                    <div class="col">
                                        <c:if test="${sessionScope.user.roleId ne Constants.roleAdminId}">
                                            <form action="/main/addToCart" method="post">
                                                <input type="hidden" name="bookId" value="${book.id}">
                                                <button style="width: 100%" type="submit"
                                                        class="btn btn-outline-primary">
                                                    <fmt:message key="button.add.toCart"/>
                                                </button>
                                            </form>
                                        </c:if>
                                            <%--                            <c:if test="${sessionScope.user.roleID eq Constants.roleAdminId}">--%>
                                            <%--                                <form action="PrepareEditVolumePage" method="get">--%>
                                            <%--                                    <input type="hidden" name="bookId" value="${book.id}" required>--%>
                                            <%--                                    <button style="width: 100%" type="submit"--%>
                                            <%--                                            class="btn btn-outline-primary">--%>
                                            <%--                                        <fmt:message key="button.edit"/>--%>
                                            <%--                                    </button>--%>
                                            <%--                                </form>--%>
                                            <%--                            </c:if>--%>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr/>
                    </div>
                </c:forEach>

                <%--                    <c:forEach items="${page.records}" var="b">--%>


                <%--                        <div class="col_1_of_3 span_1_of_3">--%>
                <%--                            <a href="${pageContext.request.contextPath}/main/ClientServlet?op=buyBook&bookId=${b.id}">--%>
                <%--                                <div class="inner_content clearfix">--%>
                <%--                                    <div class="product_image">--%>
                <%--                                        <img src="static/images/book/${b.filename}" alt=""/>--%>
                <%--                                    </div>--%>
                <%--                                    <div class="sale-box"><span class="on_sale title_shop">New</span></div>--%>
                <%--                                    <div class="price">--%>
                <%--                                        <div class="cart-left">--%>
                <%--                                            <p class="title">${b.name}</p>--%>
                <%--                                            <p class="description">${b.author}</p>--%>
                <%--                                            <div class="price1">--%>
                <%--                                                <span class="actual">${b.price}</span>--%>
                <%--                                            </div>--%>
                <%--                                        </div>--%>
                <%--                                        <div class="cart-right"> </div>--%>
                <%--                                        <div class="clear"></div>--%>
                <%--                                    </div>--%>
                <%--                                </div>--%>
                <%--                            </a>--%>
                <%--                        </div>--%>
                <%--                    </c:forEach>--%>


<%--                <%@ include file="fragments/page.jsp" %>--%>


            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<%@ include file="fragments/footer.jsp" %>
