package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjava.model.Meal;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE Meal m Set m.description=:description, m.calories=:calories, m.dateTime=:dateTime " +
            "WHERE m.id=:id AND m.user.id=:userId")
    int update(@Param("description")String description, @Param("calories")int calories,
               @Param("dateTime")LocalDateTime dateTime, @Param("id")int id, @Param("userId")int userId);

    @Transactional
    @Modifying
    int deleteByIdAndUserId(int id, int userId);

    Optional<Meal> findByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    List<Meal> findAllByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserIdOrderByDateTimeDesc(LocalDateTime dateAfter, LocalDateTime dateBefore, int userId);

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.id=:id AND m.user.id=:userId")
    Meal getWithUser(@Param("id" )int id, @Param("userId") int userId);
}
