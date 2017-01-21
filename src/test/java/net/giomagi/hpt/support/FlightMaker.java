package net.giomagi.hpt.support;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Price;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class FlightMaker {
    public static final Property<Flight, String> origin = Property.newProperty();
    public static final Property<Flight, String> destination = Property.newProperty();
    public static final Property<Flight, LocalDate> date = Property.newProperty();
    public static final Property<Flight, Integer> price = Property.newProperty();

    public static final Instantiator<Flight> flight =
            lookup -> new Flight(lookup.valueOf(origin, randomAlphabetic(3)),
                                 lookup.valueOf(destination, randomAlphabetic(3)),
                                 randomAlphabetic(2),
                                 lookup.valueOf(date, LocalDate.now()),
                                 LocalTime.now(),
                                 LocalTime.now(),
                                 Price.of(lookup.valueOf(price, 123), "XYZ"));
}
