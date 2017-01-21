package net.giomagi.hpt.model;

import com.natpryce.makeiteasy.MakeItEasy;
import net.giomagi.hpt.support.FlightMaker;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.time.LocalDate;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static net.giomagi.hpt.support.FlightMaker.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class ItineraryTest {

    @Test
    public void theItinerarySummaryConsistOfEndpointsTotalPriceAndDayCounts() {

        assertThat(Itinerary.of(make(a(flight,
                                       with(origin, "STN"),
                                       with(destination, "AOI"),
                                       with(date, LocalDate.of(2017, 5, 22)),
                                       with(price, 25))),
                                make(a(flight,
                                       with(origin, "LIN"),
                                       with(destination, "LHR"),
                                       with(date, LocalDate.of(2017, 6, 1)),
                                       with(price, 55)))).summary(),
                   equalTo("STN>AOI - LIN>LHR : 80GBP : 11 (9)"));
    }
}