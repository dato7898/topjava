<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%! public static int x = 1; %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <link href="bootstrap.css" rel="stylesheet">
    <link href="style.css" rel="stylesheet">
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<div class="jumbotron pt-4">
    <div class="container">
        <section>
            <h3><a href="index.html">Home</a></h3>
            <hr/>
            <h3 class="text-center">Моя еда</h3>
            <div class="card border-dark">
                <form method="get" action="meals">
                    <div class="card-body pb-0">
                        <div class="row">
                            <div class="offset-1 col-2">
                                <label for="dateAfter">От даты
                                    <input class="form-control" id="dateAfter" type="date" name="dateAfter">
                                </label>
                            </div>
                            <div class="col-2">
                                <label for="dateBefore">До даты
                                    <input class="form-control" id="dateBefore" type="date" name="dateBefore">
                                </label>
                            </div>
                            <div class="offset-2 col-2">
                                <label for="timeAfter">От времени
                                    <input class="form-control" id="timeAfter" type="time" name="timeAfter">
                                </label>
                            </div>
                            <div class="col-2">
                                <label for="timeBefore">До времени
                                    <input class="form-control" id="timeBefore" type="time" name="timeBefore">
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer text-right">
                        <button class="btn btn-danger" onclick="window.history.back()" type="button">Отменить</button>
                        <button class="btn btn-primary" type="submit">Отфильтровать</button>
                    </div>
                </form>
            </div>
            <a class="btn btn-primary" href="meals?action=create">Добавить</a>
            <br><br>
            <div id="datatable_wrapper" class="dataTables_wrapper dt-bootstrap4 no-footer">
                <div class="row">
                    <div class="col-sm-12">
                        <table class="table table-striped dataTable no-footer" id="datatable" role="grid" aria-describedby="datatable_info" style="width: 1110px;">
                            <thead>
                            <tr role="row">
                                <th class="sorting_desc" tabindex="0" aria-controls="datatable" rowspan="1" colspan="1" aria-label="Дата/Время: activate to sort column ascending" aria-sort="descending" style="width: 322px;">Дата/Время</th>
                                <th class="sorting" tabindex="0" aria-controls="datatable" rowspan="1" colspan="1" aria-label="Описание: activate to sort column ascending" style="width: 249px;">Описание</th>
                                <th class="sorting" tabindex="0" aria-controls="datatable" rowspan="1" colspan="1" aria-label="Калории: activate to sort column ascending" style="width: 229px;">Калории</th>
                                <th class="sorting_disabled" rowspan="1" colspan="1" aria-label="" style="width: 69px;"></th>
                                <th class="sorting_disabled" rowspan="1" colspan="1" aria-label="" style="width: 67px;"></th>
                            </tr>
                            </thead>
                            <c:forEach items="${meals}" var="meal">
                                <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
                                <tr class="${meal.excess ? 'excess' : 'normal'} ${(x = x + 1) % 2 == 0 ? 'even' : 'odd'}">
                                    <td class="sorting_1">
                                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                            <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                            ${fn:formatDateTime(meal.dateTime)}
                                    </td>
                                    <td>${meal.description}</td>
                                    <td>${meal.calories}</td>
                                    <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                                    <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
</body>
</html>