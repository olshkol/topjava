package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getAll();

    void delete(long mealId);

    void add(Meal meal);

    Meal getMealById(long mealId);

    void update(long mealId, Meal meal);
}
