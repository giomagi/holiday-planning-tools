package net.giomagi.hpt.helpers;

import com.google.common.collect.ImmutableSet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static java.time.DayOfWeek.*;

public class UkCalendar {

    private static final ImmutableSet<LocalDate> FUTURE_GOOD_FRIDAYS = ImmutableSet.of(LocalDate.of(2017, 4, 14),
                                                                                       LocalDate.of(2018, 3, 30),
                                                                                       LocalDate.of(2019, 4, 19),
                                                                                       LocalDate.of(2020, 4, 10));

    private static final ImmutableSet<LocalDate> FUTURE_EASTER_MONDAYS = ImmutableSet.of(LocalDate.of(2017, 4, 17),
                                                                                         LocalDate.of(2018, 4, 2),
                                                                                         LocalDate.of(2019, 4, 22),
                                                                                         LocalDate.of(2020, 4, 13));

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

    // TODO: rename to EnglandCalendarYearXtoY + make it non-static
    public static boolean isABankHoliday(LocalDate date) {
        return isNewYearsDay(date)
                || isGoodFriday(date)
                || isEasterMonday(date)
                || isMayDay(date)
                || isSpringBankHoliday(date)
                || isLateSummerBankHoliday(date)
                || isChristmas(date)
                || isBoxingDay(date);
    }

    private static boolean isNewYearsDay(LocalDate date) {
        return date.getMonth() == Month.JANUARY && (
                (date.getDayOfMonth() == 1 && date.getDayOfWeek() != SATURDAY && date.getDayOfWeek() != SUNDAY)
                        || ((date.getDayOfMonth() == 2 || date.getDayOfMonth() == 3) && date.getDayOfWeek() == MONDAY));
    }

    private static boolean isGoodFriday(LocalDate date) {
        return FUTURE_GOOD_FRIDAYS.contains(date);
    }

    private static boolean isEasterMonday(LocalDate date) {
        return FUTURE_EASTER_MONDAYS.contains(date);
    }

    private static boolean isMayDay(LocalDate date) {
        return date.getMonth() == Month.MAY && date.getDayOfWeek() == MONDAY && date.getDayOfMonth() < 8;
    }

    private static boolean isSpringBankHoliday(LocalDate date) {
        return date.getMonth() == Month.MAY && date.getDayOfWeek() == MONDAY && date.getDayOfMonth() > 24;
    }

    private static boolean isLateSummerBankHoliday(LocalDate date) {
        return date.getMonth() == Month.AUGUST && date.getDayOfWeek() == MONDAY && date.getDayOfMonth() > 24;
    }

    private static boolean isChristmas(LocalDate date) {
        return date.getMonth() == Month.DECEMBER && (
                (date.getDayOfMonth() == 25 && date.getDayOfWeek() != SATURDAY && date.getDayOfWeek() != SUNDAY)
                || (date.getDayOfMonth() == 27 && (date.getDayOfWeek() == MONDAY || date.getDayOfWeek() == TUESDAY)));
    }

    private static boolean isBoxingDay(LocalDate date) {
        return date.getMonth() == Month.DECEMBER && (
                (date.getDayOfMonth() == 26 && date.getDayOfWeek() != SATURDAY && date.getDayOfWeek() != SUNDAY)
                || (date.getDayOfMonth() == 28 && (date.getDayOfWeek() == MONDAY || date.getDayOfWeek() == TUESDAY)));
    }
}
