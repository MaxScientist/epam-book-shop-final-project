<%@ page contentType="text/html;charset=UTF-8" import="java.util.*" language="java" pageEncoding="UTF-8"
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
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/images/favicon.png" sizes="16x16"
          type="image/png">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    <style>
        <%@include  file="/static/css/style.css" %>
        <%--        <%@include file="/static/css/style_last.css"%>--%>
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
        <div class="header-top-left">
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
            </form>
            <div class="clear"></div>
        </div>
        <div class="cssmenu">
            <ul>
                <li class="active"><a href="${pageContext.request.contextPath}/main/login">
                    <fmt:message key="button.sample"/></a></li>
                |
                <li><a href="${pageContext.request.contextPath}/main/catalog">Catalog</a></li>
                |
                <li><a href="${pageContext.request.contextPath}/main/cart">Checkout</a></li>
                |
                <li><a href="${pageContext.request.contextPath}/main/login">Log In</a></li>
                |
                <li><a href="${pageContext.request.contextPath}/main/signUp">Sign Up</a></li>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="header-bottom">
    <div class="wrap">
        <div class="header-bottom-left">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/index.jsp">
                    <img src="${pageContext.request.contextPath}/res/shop_assets/images/logo.png" style="height: 41px"
                         alt=""/></a>
            </div>
            <div class="menu">
                <ul class="megamenu skyblue">
                    <%--                    <li class="active grid"><a href="/"><fmt:message key="index.name" /></a></li>--%>
                    <%--                    <li><a class="color4" href="#">Category</a>--%>
                    <%--                        <div class="megapanel">--%>
                    <%--                            <div class="row">--%>
                    <%--                                <div class="col1">--%>
                    <%--                                    <div class="h_nav">--%>
                    <%--                                        <h4>List of Category</h4>--%>
                    <%--                                        <ul>--%>
                    <%--                                        </ul>--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>

                    <%--                            </div>--%>
                    <%--                        </div>--%>
                    <%--                    </li>--%>

                    <li><a href="${pageContext.request.contextPath}/main/orders">Other</a></li>
                    <li><a href="${pageContext.request.contextPath}/main/cart">Purchase</a></li>
                </ul>
            </div>
        </div>
        <form action="/main/search" method="get">
            <div class="header-bottom-right">
                <div class="search">
                    <input type="text" name="bookTitle" class="textbox" value="Search" onfocus="this.value = '';"
                           onblur="if (this.value == '') {this.value = 'Search';}">
                    <input type="submit" value="Subscribe" id="submit" name="submit">
                    <div id="response"></div>
                </div>
            </div>
        </form>
        <div class="clear"></div>
    </div>
</div>


