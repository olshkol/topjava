package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    List<Meal> getAll();

    void delete(long mealId);

    void add(Meal meal);

    Meal getMealById(long mealId);

    void update(long mealId, Meal meal);
}
