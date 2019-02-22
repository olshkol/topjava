package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.endDate;
import static ru.javawebinar.topjava.util.MealsUtil.startDate;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map <Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());

            Map<Integer, Meal> meals = repository.get(userId);
            if (meals==null)
                meals = new ConcurrentHashMap<>();
            meals.put(meal.getId(), meal);

            repository.put(userId, meals);
            return meal;
        }
        // treat case: update, but absent in storage
        return  repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        return repository.get(userId).remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        return repository.get(userId).get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        if (repository.containsKey(userId)) {
            return sortAndFilter(repository.get(userId));
        }
        return new ArrayList<>();
    }

    private List<Meal> sortAndFilter(Map<Integer, Meal> meals){
        return meals.values()
                .stream()
                .sorted()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}

