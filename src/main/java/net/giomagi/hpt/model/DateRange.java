package net.giomagi.hpt.model;

import java.time.LocalDate;

public class DateRange extends ValueType {
    public final LocalDate lower;
    public final LocalDate upper;

    public DateRange(LocalDate lower, LocalDate upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public static DateRange of(LocalDate lower, LocalDate upper) {
        return new DateRange(lower, upper);
    }
}
