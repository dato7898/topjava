package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealTo {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Long id;

    private final LocalDateTime dateTime;

    private final String description;

    private final Integer calories;

    //    private final Supplier<Boolean> excess;
//    private final AtomicBoolean excess;
    private final Boolean excess;

    public MealTo(Long id, LocalDateTime dateTime, String description, Integer calories, Boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDateTimeAsString() {
        return dateTimeFormatter.format(dateTime);
    }

    public String getDescription() {
        return description;
    }

    public Integer getCalories() {
        return calories;
    }

    public Boolean isExcess() {
        return excess;
    }

    //    public Boolean getExcess() {
//        return excess.get();
//    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}