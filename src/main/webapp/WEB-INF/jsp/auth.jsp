<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Вход в систему</title>
    <%@include file="../include/links.html" %>
</head>
<body>
<%@include file="../include/navbar.jsp" %>
<div class="container">
    <h1>Вход в систему</h1>
    <form action="${pageContext.request.contextPath}/auth" method="post">
        <div class="form-group">
            <label for="username">Имя пользователя</label>
            <input id="username" class="form-control ${error != null ? "is-invalid" : ""}"
                   type="text" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input id="password" class="form-control ${error != null ? "is-invalid" : ""}"
                   type="password" name="password" required>
            <div class="invalid-feedback">
                Неправильное имя пользователя или пароль
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Войти</button>
        <a href="${pageContext.request.contextPath}/registration"><button class="btn btn-outline-primary" type="button">Регистрация</button></a>
    </form>
</div>
</body>
</html>
