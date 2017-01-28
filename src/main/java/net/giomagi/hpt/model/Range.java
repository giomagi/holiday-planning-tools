package net.giomagi.hpt.model;

import java.time.LocalDate;

public class Range<T> extends ValueType {
    public final T lower;
    public final T upper;

    public Range(T lower, T upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public static <T> Range<T> of(T lower, T upper) {
        return new Range<>(lower, upper);
    }
}
