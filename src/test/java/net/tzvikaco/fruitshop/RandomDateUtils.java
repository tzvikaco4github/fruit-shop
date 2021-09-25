package net.tzvikaco.fruitshop;

import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class RandomDateUtils {

    public static LocalDate START_LOCAL_DATE = LocalDate.of(1970, Month.JANUARY, 1);

    public static LocalDate randomLocalDate() {
        long days = ChronoUnit.DAYS.between(START_LOCAL_DATE, LocalDate.now());
        LocalDate randomDate = START_LOCAL_DATE.plusDays(RandomUtils.nextLong(1, days + 1));
        return randomDate;
    }

    private RandomDateUtils() {
    }
}
