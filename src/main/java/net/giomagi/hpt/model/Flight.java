package net.giomagi.hpt.model;

import com.google.common.base.Joiner;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flight extends ValueType implements CsvFriendly {

    public final String departure;
    public final String arrival;
    public final String carrier;
    public final LocalDate flightDate;
    public final LocalTime departureTime;
    public final LocalTime arrivalTime;
    public final Price price;

    public Flight(String departure, String arrival, String carrier,
                  LocalDate flightDate, LocalTime departureTime, LocalTime arrivalTime,
                  Price price) {

        this.departure = departure;
        this.arrival = arrival;
        this.carrier = carrier;
        this.flightDate = flightDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    @Override
    public String asCsv() {
        return Joiner.on(',').join(new String[] {
                departure,
                arrival,
                carrier,
                flightDate.toString(),
                departureTime.toString(),
                arrivalTime.toString(),
                price.asCsv()
        });
    }
}
