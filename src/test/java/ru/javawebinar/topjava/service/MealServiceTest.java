package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_USER_FIRST_BREAKFAST_ID, USER_ID);
        assertMatch(meal, MEAL_USER_FIRST_BREAKFAST);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotYourOwn() throws Exception {
        service.get(MEAL_ADMIN_LUNCH_ID, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_USER_FIRST_BREAKFAST_ID, USER_ID);
        MealTestData.assertMatch(service.getAll(USER_ID), MEAL_USER_SECOND_DINNER, MEAL_USER_SECOND_LUNCH,  MEAL_USER_SECOND_BREAKFAST, MEAL_USER_FIRST_DINNER, MEAL_USER_FIRST_LUNCH);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotYourOwn() throws Exception {
        service.delete(MEAL_ADMIN_LUNCH_ID, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> mealsBetween = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID);
        assertMatch(mealsBetween, MEAL_USER_FIRST_DINNER, MEAL_USER_FIRST_LUNCH, MEAL_USER_FIRST_BREAKFAST);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL_USER_SECOND_DINNER, MEAL_USER_SECOND_LUNCH,  MEAL_USER_SECOND_BREAKFAST, MEAL_USER_FIRST_DINNER, MEAL_USER_FIRST_LUNCH, MEAL_USER_FIRST_BREAKFAST);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_USER_FIRST_BREAKFAST);
        updated.setDateTime(LocalDateTime.of(2015, Month.MAY, 30, 12, 0));
        updated.setDescription("Полдник");
        updated.setCalories(330);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_USER_FIRST_BREAKFAST_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotYourOwn() throws Exception {
        Meal updated = new Meal(MEAL_ADMIN_LUNCH);
        updated.setCalories(330);
        service.update(updated, USER_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2019, Month.OCTOBER, 20, 16, 0), "new lunch", 1500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL_USER_SECOND_DINNER, MEAL_USER_SECOND_LUNCH,  MEAL_USER_SECOND_BREAKFAST, MEAL_USER_FIRST_DINNER, MEAL_USER_FIRST_LUNCH, MEAL_USER_FIRST_BREAKFAST);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateCreate() throws Exception {
        service.create(new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Duplicate", 1000), USER_ID);
    }

    @Test
    public void duplicateDateForDifUsers() {
        Meal newMealForUser = new Meal(null, LocalDateTime.of(2019, Month.OCTOBER, 20, 17, 0), "new dinner for user", 1500);
        Meal newMealForAdmin = new Meal(null, LocalDateTime.of(2019, Month.OCTOBER, 20, 17, 0), "new dinner for admin", 1500);

        Meal createdUser = service.create(newMealForUser, USER_ID);
        Meal createdAdmin = service.create(newMealForAdmin, ADMIN_ID);
        newMealForUser.setId(createdUser.getId());
        newMealForAdmin.setId(createdAdmin.getId());
        assertMatch(service.getAll(USER_ID), newMealForUser, MEAL_USER_SECOND_DINNER, MEAL_USER_SECOND_LUNCH,  MEAL_USER_SECOND_BREAKFAST, MEAL_USER_FIRST_DINNER, MEAL_USER_FIRST_LUNCH, MEAL_USER_FIRST_BREAKFAST);
        assertMatch(service.getAll(ADMIN_ID), newMealForAdmin, MEAL_ADMIN_DINNER, MEAL_ADMIN_LUNCH);
    }
}