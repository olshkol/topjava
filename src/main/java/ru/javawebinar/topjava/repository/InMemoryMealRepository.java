package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMealRepository implements MealRepository {

    private volatile static AtomicLong MEAL_ID_SEQUENCE = new AtomicLong(1L);

    private Map<Long, Meal> meals = new ConcurrentHashMap<>();

    public InMemoryMealRepository() {
        this.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        this.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        this.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        this.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        this.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        this.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void delete(long mealId) {
        meals.remove(mealId);
    }

    @Override
    public void add(Meal meal) {
            meal.setId(MEAL_ID_SEQUENCE.get());
            meals.put(MEAL_ID_SEQUENCE.get(), meal);
            MEAL_ID_SEQUENCE.incrementAndGet();
    }

    @Override
    public Meal getMealById(long mealId) {
        return meals.get(mealId);
    }

    @Override
    public void update(long mealId, Meal meal) {
        meals.replace(mealId, meal);
    }
}
