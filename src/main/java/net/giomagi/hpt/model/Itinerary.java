package net.giomagi.hpt.model;

import com.google.common.base.Joiner;
import net.giomagi.hpt.helpers.UkCalendar;

import java.time.temporal.ChronoUnit;

public class Itinerary extends ValueType implements CsvFriendly {
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
        return String.format("%s>%s - %s>%s : %s : %s (%s)",
                             outboundFlight.departure,
                             outboundFlight.arrival,
                             returnFlight.departure,
                             returnFlight.arrival,
                             totalPrice(),
                             totalDays(),
                             workDays());
    }

    @Override
    public String asCsv() {
        return Joiner.on(',').join(new String[] {
                outboundFlight.asCsv(),
                returnFlight.asCsv(),
                totalPrice(),
                totalDays(),
                workDays()
        });
    }

    private String totalPrice() {
        return (outboundFlight.price.value + returnFlight.price.value) + "GBP";
    }

    private String totalDays() {
        return Long.toString(outboundFlight.flightDate.until(returnFlight.flightDate, ChronoUnit.DAYS) + 1);
    }

    public String workDays() {
        return Integer.toString(UkCalendar.workDaysBetween(outboundFlight.flightDate, returnFlight.flightDate));
    }
}
