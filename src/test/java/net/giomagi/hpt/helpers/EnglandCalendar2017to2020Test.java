package net.giomagi.hpt.helpers;

import org.junit.Test;

import java.time.LocalDate;

import static net.giomagi.hpt.helpers.EnglandCalendar2017to2020.addWorkingDays;
import static net.giomagi.hpt.helpers.EnglandCalendar2017to2020.isABankHoliday;
import static net.giomagi.hpt.helpers.EnglandCalendar2017to2020.workDaysBetween;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class EnglandCalendar2017to2020Test {

    @Test
    public void addsTheRequiredNumberOfWorkingDays() {

        assertThat(addWorkingDays(LocalDate.of(2017, 1, 24), 3),
                   equalTo(LocalDate.of(2017, 1, 27)));

        assertThat(addWorkingDays(LocalDate.of(2017, 1, 24), 4),
                   equalTo(LocalDate.of(2017, 1, 30)));

        assertThat(addWorkingDays(LocalDate.of(2017, 5, 26), 1),
                   equalTo(LocalDate.of(2017, 5, 30)));
    }

    @Test
    public void countsWorkingDays() {

        assertThat(workDaysBetween(LocalDate.of(2017, 1, 24), LocalDate.of(2017, 1, 27)), equalTo(4));
        assertThat(workDaysBetween(LocalDate.of(2017, 1, 24), LocalDate.of(2017, 1, 30)), equalTo(5));
        assertThat(workDaysBetween(LocalDate.of(2017, 5, 26), LocalDate.of(2017, 5, 30)), equalTo(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void workDaysCalculationRequiresOrderedDates() {

        workDaysBetween(LocalDate.now().plusDays(1), LocalDate.now());
    }

    @Test
    public void understandsBankHolidays() {

        // new years day
        assertThat(isABankHoliday(LocalDate.of(2016, 1, 1)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2016, 1, 2)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2016, 1, 3)), equalTo(false));

        assertThat(isABankHoliday(LocalDate.of(2017, 1, 1)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2017, 1, 2)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2017, 1, 3)), equalTo(false));

        assertThat(isABankHoliday(LocalDate.of(2022, 1, 1)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2022, 1, 2)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2022, 1, 3)), equalTo(true));

        // not testing good friday and easter monday, i will just hold a hardcoded list

        // may day bank holiday
        assertThat(isABankHoliday(LocalDate.of(2017, 5, 1)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2017, 5, 8)), equalTo(false));

        assertThat(isABankHoliday(LocalDate.of(2018, 5, 1)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2018, 5, 7)), equalTo(true));

        // spring bank holiday
        assertThat(isABankHoliday(LocalDate.of(2017, 5, 29)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2018, 5, 28)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2020, 5, 25)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2021, 5, 31)), equalTo(true));

        // late summer bank holiday
        assertThat(isABankHoliday(LocalDate.of(2017, 8, 28)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2018, 8, 27)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2020, 8, 31)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2025, 8, 25)), equalTo(true));

        // christmas + boxing day
        assertThat(isABankHoliday(LocalDate.of(2014, 12, 25)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2014, 12, 26)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2014, 12, 27)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2014, 12, 28)), equalTo(false));

        assertThat(isABankHoliday(LocalDate.of(2015, 12, 25)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2015, 12, 26)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2015, 12, 27)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2015, 12, 28)), equalTo(true));

        assertThat(isABankHoliday(LocalDate.of(2016, 12, 25)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2016, 12, 26)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2016, 12, 27)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2016, 12, 28)), equalTo(false));

        assertThat(isABankHoliday(LocalDate.of(2021, 12, 25)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2021, 12, 26)), equalTo(false));
        assertThat(isABankHoliday(LocalDate.of(2021, 12, 27)), equalTo(true));
        assertThat(isABankHoliday(LocalDate.of(2021, 12, 28)), equalTo(true));
    }
}