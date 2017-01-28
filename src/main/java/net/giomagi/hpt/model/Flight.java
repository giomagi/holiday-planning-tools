package net.giomagi.hpt.model;

import com.google.common.base.Joiner;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flight extends ValueType implements CsvFriendly {

    public final String origin;
    public final String destination;
    public final String carrier;
    public final LocalDate flightDate;
    public final LocalTime takeOffTime;
    public final LocalTime landingTime;
    public final Price price;

    public Flight(String origin, String destination, String carrier,
                  LocalDate flightDate, LocalTime takeOffTime, LocalTime landingTime,
                  Price price) {

        this.origin = origin;
        this.destination = destination;
        this.carrier = carrier;
        this.flightDate = flightDate;
        this.takeOffTime = takeOffTime;
        this.landingTime = landingTime;
        this.price = price;
    }

    @Override
    public String asCsv() {
        return Joiner.on(',').join(new String[] {
                origin,
                destination,
                carrier,
                flightDate.toString(),
                takeOffTime.toString(),
                landingTime.toString(),
                price.asCsv()
        });
    }
}
