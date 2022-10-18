<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Система разметки данных</title>
    <%@include file="../include/links.html" %>
</head>
<body>

<%@include file="../include/navbar.jsp" %>

<div id="headerwrap">
    <div class="container centered">
        <div class="row">
            <h1>Страница разработчика</h1>
        </div>
        <div class="row">
            <form action="${pageContext.request.contextPath}/report" method="get" target="_blank">
                <div class="form-group">
                    <label for="username">Число оценок для агрегации</label>
                    <input id="username" class="form-control"
                           type="number" name="numMarks" required>
                </div>
                <button class="btn btn-primary" type="submit">Скачать отчет</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
