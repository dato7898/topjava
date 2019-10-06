package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class CrudMealMemoryImpl implements CrudMeal {
    private static final AtomicLong idCounter = new AtomicLong(0);

    private static final List<Meal> meals = new CopyOnWriteArrayList<>(Arrays.asList(
            new Meal(idCounter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(idCounter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(idCounter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(idCounter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(idCounter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(idCounter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    ));

    private static final CrudMealMemoryImpl INSTANCE = new CrudMealMemoryImpl();

    private CrudMealMemoryImpl() {
    }

    public static CrudMealMemoryImpl getInstance() {
        return INSTANCE;
    }

    public Long getNextId() {
        return idCounter.incrementAndGet();
    }

    public List<Meal> mealList() {
        return meals;
    }

    public Optional<Meal> getMealById(Long id) {
        return meals.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public void update(Meal meal) {
        Meal oldValue = getMealById(meal.getId()).get();
        oldValue.setDateTime(meal.getDateTime());
        oldValue.setDescription(meal.getDescription());
        oldValue.setCalories(meal.getCalories());
    }

    public void create(Meal meal) {
        meals.add(meal);
    }

    public void delete(Long id) {
        meals.removeIf(meal -> meal.getId().equals(id));
    }

}