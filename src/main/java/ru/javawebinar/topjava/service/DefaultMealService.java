package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

public class DefaultMealService implements MealService {
    private MealRepository mealRepository = new InMemoryMealRepository();

    @Override
    public List<Meal> getAll() {
        return mealRepository.getAll();
    }
}
