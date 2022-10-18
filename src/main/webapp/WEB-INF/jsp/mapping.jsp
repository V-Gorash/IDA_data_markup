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
    <div class="container centered align-content-center">
        <div class="row">
            <div class="col">
                <h1>Является ли данное изображение КТ-снимком легких?</h1>
            </div>
        </div>
        <div class="row>">
            <div class="col">
                <img src="data:image/png;base64,${base64Image}" alt="image"/>
            </div>
        </div>
        <div class="row">
            <div class="col d-grid gap-2">
                <a href="/mapping/${imageId}?result=true"><button type="button" class="btn btn-success btn-lg">Да</button> </a>
            </div>
            <div class="col d-grid gap-2">
                <a href="/mapping/${imageId}?result=false"><button type="button" class="btn btn-danger btn-lg">Нет</button> </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>