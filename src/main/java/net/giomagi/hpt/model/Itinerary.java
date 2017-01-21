package net.giomagi.hpt.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Itinerary extends ValueType {
    public final Flight outboundFlight;
    public final Flight returnFlight;

    public Itinerary(Flight outboundFlight, Flight returnFlight) {
        this.outboundFlight = outboundFlight;
        this.returnFlight = returnFlight;
    }

    public static Itinerary of(Flight out, Flight ret) {
        return new Itinerary(out, ret);
    }

    public String summary() {
        return String.format("%s>%s - %s>%s : %dGBP : %s (%d)",
                             outboundFlight.departure,
                             outboundFlight.arrival,
                             returnFlight.departure,
                             returnFlight.arrival,
                             outboundFlight.price.value + returnFlight.price.value,
                             outboundFlight.flightDate.until(returnFlight.flightDate, ChronoUnit.DAYS) + 1,
                             workDaysBetween(outboundFlight.flightDate, returnFlight.flightDate));
    }

    // TODO: move it into proper calendar
    private int workDaysBetween(LocalDate d1, LocalDate d2) {
        int c = 0;

        for (LocalDate curr = d1; !curr.isAfter(d2); curr = curr.plusDays(1)) {
            if (curr.getDayOfWeek() != DayOfWeek.SATURDAY
                    && curr.getDayOfWeek() != DayOfWeek.SUNDAY
                    && !isABankHoliday(curr)) {

                c++;
            }
        }

        return c;
    }

    private boolean isABankHoliday(LocalDate date) {
        return false;
    }
}
