<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<jsp:useBean id="Constants" class="com.epam.bookshop.constants.ParameterConstants"/>
<!DOCTYPE HTML>
<html>
<head>
    <title>BookShop</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    <style>
        <%@include  file="/static/css/style.css" %>
        <%@include file="/static/css/form.css" %>
        <%@include file="/static/css/megamenu.css"%>


    </style>

    <script>$(document).ready(function () {
        $(".megamenu").megamenu();
    });</script>

</head>
<body>
<div class="header-top">
    <div class="wrap">
        <div class="header-top-left" style="">
            <form action="/main/changeLanguage" method="get">
                <select name="locale" class="btn rounded-pill" onchange="this.form.submit();">
                    <option value="ru"
                            <c:if test="${sessionScope.localeId eq Constants.localeRussianId}">selected</c:if>>
                        <fmt:message key="select.option.ru"/>
                    </option>
                    <option value="en"
                            <c:if test="${sessionScope.localeId eq Constants.localeEnglishId}">selected</c:if>>
                        <fmt:message key="select.option.en"/>
                    </option>
                </select>
                <input type="hidden" name="pageName" value="${pageContext.request.servletPath}">

            </form>

            <div class="clear"></div>

        </div>
<div style="position: absolute; right: 10%; margin-right: 700px;">
            <h1 align="center" style="color: white;font-family: fontawesome; font-size: 20px; ">BOOK SHOP</h1>
</div>
        <div class="cssmenu">

            <ul>
                <c:if test="${sessionScope.user.roleId eq Constants.roleUserId}">
                    |
                    <li><a href="${pageContext.request.contextPath}/main/displayCart">
                        <fmt:message key="label.checkOut"/>
                    </a></li>
                    |
                    <li><a href="${pageContext.request.contextPath}/main/displayCustomerOrder">
                        <fmt:message key="label.myOrders"/></a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user.roleId eq Constants.roleAdminId}">
                    <li><a href="/main/adminPanel"><fmt:message key="head.adminPanel"/></a></li>
                </c:if>
                <c:if test="${sessionScope.user.roleId eq Constants.roleAdminId or
                sessionScope.user.roleId eq Constants.roleUserId}">
                    |
                    <li><a href="${pageContext.request.contextPath}/main/editProfile"><fmt:message
                            key="head.edit.profile"/></a></li>
                    |
                    <li><a href="${pageContext.request.contextPath}/main/logout"><fmt:message key="label.signOut"/> </a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.user eq null}">
                    <li><a href="${pageContext.request.contextPath}/main/login">
                        <fmt:message key="label.login"/></a>
                    </li>
                    |
                    <li><a href="${pageContext.request.contextPath}/main/signUp">
                        <fmt:message key="label.signUp"/></a>
                    </li>
                    |

                </c:if>

            </ul>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="header-bottom">
    <div class="wrap">
        <div class="header-bottom-left">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/main/home">
                    <img src="${pageContext.request.contextPath}/static/images/logo.png" style="height: 100px"
                         alt=""/></a>
            </div>
            <div class="menu">
                <ul class="megamenu skyblue">
                    <li>
                        <a href="${pageContext.request.contextPath}/main/home">
                            <fmt:message key="label.main"/>
                        </a>
                    </li>
                    <c:if test="${sessionScope.user.roleId eq Constants.roleAdminId}">

                        <li><a href=${pageContext.request.contextPath}"/main/addBook">
                            <fmt:message key="head.add.book"/>
                        </a></li>
                    </c:if>
                </ul>
            </div>
        </div>
        <form action="/main/search" method="get">
            <div class="header-bottom-right">
                <div class="search">

                    <input type="text" name="bookTitle" class="textbox" value="<fmt:message key="label.search"/>"
                           onfocus="this.value = '';"
                           onblur="if (this.value == '') {this.value = 'Search';}">
                    <input type="image" src="/static/images/search.png" style="width: 15px;height: 15px" id="submit" name="submit">
                    <div id="response"></div>
                </div>
            </div>
        </form>
        <div class="clear"></div>
    </div>
</div>


