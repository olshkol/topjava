package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestDate {

    public static final Meal ADMIN_MEAL_1 = new Meal(START_SEQ + 2, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Admin dinner", 510);
    public static final Meal ADMIN_MEAL_2 = new Meal(START_SEQ + 3, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Admin supper", 1500);

    public static final Meal USER_MEAL_1 = new Meal(START_SEQ + 4, LocalDateTime.of(2015, Month.JUNE, 2, 7, 0), "User breakfast", 200);
    public static final Meal USER_MEAL_2 = new Meal(START_SEQ + 5, LocalDateTime.of(2015, Month.JUNE, 2, 14, 0), "User dinner", 1500);
    public static final Meal USER_MEAL_3 = new Meal(START_SEQ + 6, LocalDateTime.of(2015, Month.JUNE, 2, 21, 0), "User supper", 300);
    public static final Meal USER_MEAL_4 = new Meal(START_SEQ + 7, LocalDateTime.of(2015, Month.JUNE, 3, 21, 0), "User supper", 2500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    private static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }
}
