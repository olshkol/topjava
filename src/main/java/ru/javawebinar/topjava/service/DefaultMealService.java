package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.ArrayList;
import java.util.List;

public class DefaultMealService implements MealService {
    private MealRepository mealRepository = new InMemoryMealRepository();

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealRepository.getAll());
    }

    @Override
    public void delete(long mealId) {
        mealRepository.delete(mealId);
    }

    @Override
    public void add(Meal meal) {
        mealRepository.add(meal);
    }

    @Override
    public Meal getMealById(long mealId) {
        return mealRepository.getMealById(mealId);
    }

    @Override
    public void update(long mealId, Meal meal) {
        mealRepository.update(mealId, meal);
    }
}
