package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public interface CrudMeal {
    List<Meal> mealList();

    Optional<Meal> getMealById(Long id);

    void update(Meal meal);

    void create(Meal meal);

    void delete(Long id);
}
