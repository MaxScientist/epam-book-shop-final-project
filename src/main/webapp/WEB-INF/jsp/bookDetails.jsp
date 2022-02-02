<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>
<c:choose>
    <c:when test="${sessionScope.user != null}">
        <jsp:include page="customer/fragments/header.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="fragments/mainHeader.jsp"/>
    </c:otherwise>

</c:choose>
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
                <div class="details col-md-6">
                    <h3 class="product-title">${requestScope.bookInfo.title}</h3>
                    <p class="product-description">${requestScope.bookInfo.description}</p>
                    <h4 class="price"><fmt:message key="th.price"/>: ${requestScope.bookInfo.bookPrice}</h4>
                    <h4 class="publisher"><fmt:message
                            key="publisher"/>: ${requestScope.bookInfo.publisher.publishHouse}</h4>
                    <h4 class="binding"><fmt:message key="li.binding"/>:${requestScope.bookInfo.binding}</h4>
                    <c:forEach items="${requestScope.bookInfo.authors}" var="author">
                        <h4>${author.firstName} ${author.lastName}</h4>
                    </c:forEach>
                    <div class="action">
                        <c:if test="${sessionScope.user.roleId ne Constants.roleAdminId}">
                            <form action="/main/addToCart" method="post">
                                <input type="hidden" name="bookId" value="${requestScope.bookInfo.id}">
                                <button style="width: 30%; font-size: 9px; color: mediumseagreen" type="submit"
                                        class="btn btn-outline-primary">
                                    <fmt:message key="button.add.toCart"/>
                                </button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
