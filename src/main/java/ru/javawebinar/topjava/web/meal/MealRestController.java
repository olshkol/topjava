package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;


    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExcess(service.getAll(SecurityUtil.getAuthUserId()),
                SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAllFiltered(){
        log.info("getAllFiltered");
        return getAll().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(SecurityUtil.getAuthUserId(), mealId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(SecurityUtil.getAuthUserId(), meal);
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(SecurityUtil.getAuthUserId(), mealId);
    }

    public void update(Meal meal, int mealId) {
        log.info("update {} with mealId={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        service.update(SecurityUtil.getAuthUserId(), meal);
    }
}