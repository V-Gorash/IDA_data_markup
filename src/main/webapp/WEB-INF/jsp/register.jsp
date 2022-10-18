<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Регистрация</title>
    <%@include file="../include/links.html" %>
</head>
<body>
<%@include file="../include/navbar.jsp" %>
<div class="container">
    <h1>Регистрация</h1>
    <form action="${pageContext.request.contextPath}/registration" method="post">
        <div class="form-group">
            <label for="username">Имя пользователя</label>
            <input id="username" class="form-control ${loginError != null ? "is-invalid" : ""}"
                   type="text" name="username" required>
            <div class="invalid-feedback">
                Пользователь с таким именем уже существует
            </div>
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input id="password" class="form-control" type="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="password-repeat">Повторите пароль</label>
            <input id="password-repeat" class="form-control ${passwordError != null ? "is-invalid" : ""}"
                   type="password" name="repeatPassword" required>
            <div class="invalid-feedback">
                Пароли не совпадают
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Зарегистрироваться</button>
    </form>
</div>
</body>
</html>
