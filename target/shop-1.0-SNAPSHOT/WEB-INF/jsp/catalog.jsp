<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="fragments/mainHeader.jsp" %>

<%--<form action="/main/filterGenre" method="get">--%>
<%--    <c:forEach items="${requestScope.genres}" var="genre">--%>
<%--        <div class="form-check form-check-inline">--%>
<%--            <p>${genre.name}</p>--%>
<%--                            <input name="genreId" type="checkbox" value="${genre.id}" class="form-check-input"--%>
<%--                                   <c:if test="${fn:containsIgnoreCase(requestScope.checkedGenreIds, genre.id)}">checked</c:if>>--%>
<%--                            <label class="form-check-label">${genre.name}</label>--%>
<%--        </div>--%>
<%--    </c:forEach>--%>
<%--    <hr/>--%>
<%--    <button class="btn btn-primary justify-content-center" type="submit" style="width: 100%">--%>
<%--        <fmt:message key="button.search"/>--%>
<%--    </button>--%>
<%--</form>--%>
<form action="/main/catalog" method="get">
    <c:forEach items="${requestScope.books}" var="book">
        <div class="col_1_of_3 span_1_of_3">
            <a href="${b.id}">
                <div class="inner_content clearfix">
                    <div class="product_image">
                        <img src="data:image/jpg;base64,${book.bookImage}" alt=""/>
                    </div>
                    <div class="sale-box"><span class="on_sale title_shop">New</span></div>
                    <div class="price">
                        <div class="cart-left">
                            <p class="title">${book.title}</p>

                        </div>
                        <div class="cart-right"></div>
                        <div class="clear"></div>
                    </div>
                </div>
            </a>
        </div>
    </c:forEach>

</form>