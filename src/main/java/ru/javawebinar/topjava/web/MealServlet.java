package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CrudMeal;
import ru.javawebinar.topjava.dao.CrudMealMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final CrudMeal CRUD_MEAL = CrudMealMemoryImpl.getInstance();

    private static final int CALORIES_PER_DAY = 2000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        String idAsStr = req.getParameter("id");

        if (Objects.equals(req.getParameter("action"), "delete") && idAsStr != null && idAsStr.matches("\\d+")) {
            CRUD_MEAL.delete(Long.parseLong(idAsStr));
        }

        req.setAttribute("mealsTo", MealsUtil.getFiltered(CRUD_MEAL.mealList(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));

        if (idAsStr != null && idAsStr.matches("\\d+") && CRUD_MEAL.getMealById(Long.parseLong(idAsStr)).isPresent()) {
            req.setAttribute("id", idAsStr);
        }

        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("before changing");
        request.setCharacterEncoding("UTF-8");

        String idAsStr = request.getParameter("id");

        Long id;

        if (Objects.equals(request.getParameter("action"), "edit")) {
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
            String description = request.getParameter("description");
            Integer calories = Integer.parseInt(request.getParameter("calories"));
            if (idAsStr != null && !idAsStr.isEmpty() && CRUD_MEAL.getMealById(Long.parseLong(idAsStr)).isPresent()) {
                id = Long.parseLong(idAsStr);
                CRUD_MEAL.update(new Meal(id, dateTime, description, calories));
            }
        } else if (Objects.equals(request.getParameter("action"), "create")) {
            LocalDateTime newDateTime = LocalDateTime.parse(request.getParameter("newDateTime"));
            String newDescription = request.getParameter("newDescription");
            Integer newCalories = Integer.parseInt(request.getParameter("newCalories"));

            id = CrudMealMemoryImpl.getInstance().getNextId();
            CRUD_MEAL.create(new Meal(id, newDateTime, newDescription, newCalories));
        }

        request.setAttribute("mealsTo", MealsUtil.getFiltered(CRUD_MEAL.mealList(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }


}
