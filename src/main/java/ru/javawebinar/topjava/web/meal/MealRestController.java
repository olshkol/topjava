package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;


    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(SecurityUtil.authUserId(), mealId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(SecurityUtil.authUserId(), mealId);
    }

    public void update(Meal meal, int userId) {
        log.info("update {} with userId={}", meal, userId);
        assureIdConsistent(meal, userId);
        service.update(SecurityUtil.authUserId(), meal);
    }
}