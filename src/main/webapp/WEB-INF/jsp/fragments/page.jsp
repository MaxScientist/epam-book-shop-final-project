<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="mens-toolbar">
    <div class="pager">
        <ul class="dc_pagination dc_paginationA dc_paginationA06">
            <li><a href="${pageContext.request.contextPath}${page.url}&num=${page.prePageNum}"><<</a></li>
            <c:forEach begin="1" end="${page.totalPageSize}" var="n">

                <li><a href="${pageContext.request.contextPath}${page.url}&num=${n}">${n}</a></li>
            </c:forEach>
            <li><a href="${pageContext.request.contextPath}${page.url}&num=${page.nextPageNum}">>></a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
</div>