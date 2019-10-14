package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;

    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SecurityUtil.authUserId() == 0)
            response.sendRedirect("/");

        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                authUserId());

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            controller.create(meal);
        } else {
            controller.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SecurityUtil.authUserId() == 0) {
            response.sendRedirect("users");
        } else {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, authUserId()) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                String dateAfterAsStr = request.getParameter("dateAfter");
                String dateBeforeAsStr = request.getParameter("dateBefore");
                String timeAfterAsStr = request.getParameter("timeAfter");
                String timeBeforeAsStr = request.getParameter("timeBefore");

                LocalDate dateAfter = dateAfterAsStr == null || dateAfterAsStr.isEmpty()
                        ? LocalDate.MIN : LocalDate.parse(dateAfterAsStr);
                LocalDate dateBefore = dateBeforeAsStr == null || dateBeforeAsStr.isEmpty()
                        ? LocalDate.MAX : LocalDate.parse(dateBeforeAsStr);
                LocalTime timeAfter = timeAfterAsStr == null || timeAfterAsStr.isEmpty()
                        ? LocalTime.MIN : LocalTime.parse(timeAfterAsStr);
                LocalTime timeBefore = timeBeforeAsStr == null || timeBeforeAsStr.isEmpty()
                        ? LocalTime.MAX : LocalTime.parse(timeBeforeAsStr);

                List<MealTo> mealsTo = controller.getAll().stream()
                        .filter(mealTo -> mealTo.getDateTime().toLocalDate().compareTo(dateAfter) >= 0
                                && mealTo.getDateTime().toLocalDate().compareTo(dateBefore) <= 0
                                && mealTo.getDateTime().toLocalTime().compareTo(timeAfter) >= 0
                                && mealTo.getDateTime().toLocalTime().compareTo(timeBefore) <= 0)
                        .collect(Collectors.toList());

                request.setAttribute("meals", mealsTo);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            }
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }
}
