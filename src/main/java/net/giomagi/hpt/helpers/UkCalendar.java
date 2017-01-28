package net.giomagi.hpt.helpers;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class UkCalendar {
    public static LocalDate addWorkingDays(LocalDate date, Integer workDaysCount) {

        for (int i = 0; i < workDaysCount; i++) {
            do {
                date = date.plusDays(1);
            } while (date.getDayOfWeek() == SATURDAY || date.getDayOfWeek() == SUNDAY || isABankHoliday(date));
        }

        return date;
    }

    public static int workDaysBetween(LocalDate d1, LocalDate d2) {

        if (d2.isBefore(d1)) {
            throw new IllegalArgumentException("The first date must be not after the second [ " + d1 + " - " + d2 + " ]");
        }

        int c = 0;

        for (LocalDate curr = d1; !curr.isAfter(d2); curr = curr.plusDays(1)) {
            if (curr.getDayOfWeek() != SATURDAY
                    && curr.getDayOfWeek() != DayOfWeek.SUNDAY
                    && !isABankHoliday(curr)) {

                c++;
            }
        }

        return c;
    }

    private static boolean isABankHoliday(LocalDate date) {
        // TODO: ...
        return date.equals(LocalDate.of(2017, 5, 29));
    }
}
