package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_USER_FIRST_BREAKFAST_ID = START_SEQ + 2;
    public static final int MEAL_USER_FIRST_LUNCH_ID = START_SEQ + 3;
    public static final int MEAL_USER_FIRST_DINNER_ID = START_SEQ + 4;
    public static final int MEAL_USER_SECOND_BREAKFAST_ID = START_SEQ + 5;
    public static final int MEAL_USER_SECOND_LUNCH_ID = START_SEQ + 6;
    public static final int MEAL_USER_SECOND_DINNER_ID = START_SEQ + 7;
    public static final int MEAL_ADMIN_LUNCH_ID = START_SEQ + 8;
    public static final int MEAL_ADMIN_DINNER_ID = START_SEQ + 9;

    public static final Meal MEAL_USER_FIRST_BREAKFAST = new Meal(MEAL_USER_FIRST_BREAKFAST_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_USER_FIRST_LUNCH = new Meal(MEAL_USER_FIRST_LUNCH_ID, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_USER_FIRST_DINNER = new Meal(MEAL_USER_FIRST_DINNER_ID, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_USER_SECOND_BREAKFAST = new Meal(MEAL_USER_SECOND_BREAKFAST_ID, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_USER_SECOND_LUNCH = new Meal(MEAL_USER_SECOND_LUNCH_ID, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_USER_SECOND_DINNER = new Meal(MEAL_USER_SECOND_DINNER_ID, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Meal MEAL_ADMIN_LUNCH = new Meal(MEAL_ADMIN_LUNCH_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal MEAL_ADMIN_DINNER = new Meal(MEAL_ADMIN_DINNER_ID, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
}

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
