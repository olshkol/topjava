package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        System.out.println(filteredWithExceeded);
    }

/*    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> group = mealList.stream().collect(
                Collectors.groupingBy(m -> TimeUtil.toLocalDate(m.getDateTime()), Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExceed> filteredWithExceeded = new ArrayList<>();

        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(TimeUtil.toLocalTime(meal.getDateTime()), startTime, endTime)) {
                filteredWithExceeded.add(new UserMealWithExceed(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        (group.get(TimeUtil.toLocalDate(meal.getDateTime())) > caloriesPerDay)));
            }
        }
        return filteredWithExceeded;
    }*/

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(TimeUtil.toLocalTime(meal.getDateTime()), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        (mealList.stream().collect(
                                Collectors.groupingBy(m -> TimeUtil.toLocalDate(m.getDateTime()),
                                        Collectors.summingInt(UserMeal::getCalories)))
                                .get(TimeUtil.toLocalDate(meal.getDateTime())) > caloriesPerDay)))
                .collect(Collectors.toList());
    }

}
