<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://kit.fontawesome.com/3ceb327b1f.js" crossorigin="anonymous"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/static/css/style.css">

    <title>BookShop &ndash; <fmt:message key="button.signIn"/></title>
    <style>
        body {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>

<div class="container mx-auto mt-2 mb-2" style="width: 500px;">
    <div class="row" style="text-align: center;">
<%--        <img src="img/logo.png" style="width: 200px" alt="logo">--%>
        <h3 class="mt-3"><fmt:message key="head.signIn"/></h3>
    </div>
    <form action="/main/customer" method="post" class="mt-2">
        <div class="form-group">
            <label><fmt:message key="label.email"/></label>
            <input type="email" name="userEmail" class="form-control" required>
            <small name="emailHelp" class="form-text text-danger"></small>
        </div>
        <div class="form-group">
            <label><fmt:message key="label.password"/></label>
            <input type="password" name="userPassword" class="form-control" required>
            <small class="form-text text-danger">
                ${requestScope.emailPasswordError}
            </small>
            <small class="form-text text-danger">
                ${requestScope.userNotExistsError}
            </small>
        </div>
        <button name="submit" class="btn btn-primary text-uppercase" style="width: 475px;">
            <fmt:message key="button.signIn"/>
        </button>
        <a class="d-block text-center mt-2 small" href="/main/signup"><fmt:message key="button.register"/></a>
        <a class="d-block text-center mt-2 small" href="/"><fmt:message key="button.main"/></a>
    </form>
    <br/>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
</body>
</html>