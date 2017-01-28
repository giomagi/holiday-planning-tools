package net.giomagi.hpt.helpers;

import org.junit.Test;

import java.time.LocalDate;

import static net.giomagi.hpt.helpers.UkCalendar.addWorkingDays;
import static net.giomagi.hpt.helpers.UkCalendar.workDaysBetween;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class UkCalendarTest {

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
}