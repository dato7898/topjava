<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 04.10.2019
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link href="styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Id</th>
        <th>Дата</th>
        <th>Описание</th>
        <th>Каллории</th>
        <th>Превышение</th>
        <th>Изменение</th>
        <th>Удаление</th>
    </tr>
    <c:forEach items="${mealsTo}" var="mealTo">
        <tr>
            <c:if test="${requestScope.id == mealTo.getId()}">
                <form method="post" action="meals?action=edit&id=${requestScope.id}">
                    <td>${mealTo.getId()}</td>
                    <td>
                        <label for="dateTime">Дата и время
                            <input size="15" id="dateTime" type="datetime-local" name="dateTime"
                                   value="${mealTo.getDateTime()}" required>
                        </label>
                    </td>
                    <td>
                        <label for="description">Описание
                            <input size="20" id="description" type="text" name="description"
                                   value="${mealTo.getDescription()}" required>
                        </label>
                    </td>
                    <td>
                        <label for="calories">Кол-во каллорий
                            <input size="10" id="calories" type="number" name="calories" value="${mealTo.getCalories()}"
                                   required>
                        </label>
                    </td>
                    <td>
                        <c:if test="${mealTo.isExcess()}">
                            <span style="color: red; ">Превышено</span>
                        </c:if>
                        <c:if test="${!mealTo.isExcess()}">
                            <span style="color: green; ">Не превышено</span>
                        </c:if>
                    </td>
                    <td>
                        <input type="submit" value="Изменить">
                    </td>
                    <td><a href="meals?action=delete&id=${mealTo.getId()}">Удалить</a></td>
                </form>
            </c:if>
            <c:if test="${requestScope.id != mealTo.getId()}">
                <td>${mealTo.getId()}</td>
                <td>${mealTo.getDateTimeAsString()}</td>
                <td>${mealTo.getDescription()}</td>
                <td>${mealTo.getCalories()}</td>
                <td>
                    <c:if test="${mealTo.isExcess()}">
                        <span style="color: red; ">Превышено</span>
                    </c:if>
                    <c:if test="${!mealTo.isExcess()}">
                        <span style="color: green; ">Не превышено</span>
                    </c:if>
                </td>
                <td><a href="meals?action=edit&id=${mealTo.getId()}">Изменить</a></td>
                <td><a href="meals?action=delete&id=${mealTo.getId()}">Удалить</a></td>
            </c:if>
        </tr>
    </c:forEach>
    <tr>
        <form method="post" action="meals?action=create">
            <td>...</td>
            <td>
                <label for="newDateTime">Дата и время
                    <input size="15" id="newDateTime" type="datetime-local" name="newDateTime" required>
                </label>
            </td>
            <td>
                <label for="newDescription">Описание
                    <input size="20" id="newDescription" type="text" name="newDescription" required>
                </label>
            </td>
            <td>
                <label for="newCalories">Кол-во каллорий
                    <input size="10" id="newCalories" type="number" name="newCalories" required>
                </label>
            </td>
            <td>...</td>
            <td>
                <input type="submit" value="Добавить">
            </td>
            <td>...</td>
        </form>
    </tr>
</table>
</body>
</html>
