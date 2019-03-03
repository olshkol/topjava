package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestDate.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(ADMIN_MEAL_1.getId(), ADMIN_ID);
        assertMatch(meal, ADMIN_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(ADMIN_MEAL_1.getId(), USER_ID);
    }

    @Test
    public void delete() {
        service.delete(ADMIN_MEAL_1.getId(), ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL_2);
        assertMatch(service.getAll(USER_ID), USER_MEAL_4, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(ADMIN_MEAL_1.getId(), USER_ID);
    }

    @Test
    public void getBetweenDates() {
        LocalDate startDate = LocalDate.of(2015, Month.JUNE, 3);
        LocalDate endDate = LocalDate.of(2015, Month.JUNE, 7);
        List<Meal> betweenDates = service.getBetweenDates(startDate, endDate, USER_ID);
        assertMatch(betweenDates, USER_MEAL_4);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.JUNE, 1, 12, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2015, Month.JUNE, 1, 18, 0);
        List<Meal> betweenDateTimes = service.getBetweenDateTimes(startDateTime, endDateTime, ADMIN_ID);
        assertMatch(betweenDateTimes, ADMIN_MEAL_1);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        assertMatch(meals, ADMIN_MEAL_2, ADMIN_MEAL_1);
    }

    @Test
    public void update() {
        Meal meal = new Meal(ADMIN_MEAL_1);
        meal.setCalories(4000);
        meal.setDescription("good meal");
        service.update(meal, ADMIN_ID);
        assertMatch(service.get(meal.getId(), ADMIN_ID),meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = new Meal(ADMIN_MEAL_1);
        meal.setCalories(4000);
        meal.setDescription("good meal");
        service.update(meal, USER_ID);
        service.get(meal.getId(), USER_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2015, 6, 15, 22, 15), "supper", 2000);
        Meal createdMeal = service.create(newMeal, ADMIN_ID);
        newMeal.setId(createdMeal.getId());
        assertMatch(service.getAll(ADMIN_ID), newMeal, ADMIN_MEAL_2, ADMIN_MEAL_1);
    }
}