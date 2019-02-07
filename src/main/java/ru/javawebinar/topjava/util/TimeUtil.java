package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static LocalDate toLocalDate(LocalDateTime time){
        return time.toLocalDate();
    }

    public static LocalTime toLocalTime(LocalDateTime time){
        return time.toLocalTime();
    }
}
