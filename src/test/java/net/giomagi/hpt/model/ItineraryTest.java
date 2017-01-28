package net.giomagi.hpt.model;

import com.natpryce.makeiteasy.MakeItEasy;
import net.giomagi.hpt.support.FlightMaker;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

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
                   equalTo("STN>AOI - LIN>LHR : 80GBP : 11 (8)"));
    }

    @Test
    public void printsAllDetailsAsCsv() {

        assertThat(Itinerary.of(new Flight("STN", "AOI", "FR",
                                           LocalDate.of(2017, 1, 21), LocalTime.of(12, 25), LocalTime.of(13, 35),
                                           Price.of(22, "GBP")),
                                new Flight("LIN", "LHR", "AZ",
                                           LocalDate.of(2017, 1, 24), LocalTime.of(14, 25), LocalTime.of(17, 20),
                                           Price.of(67, "GBP"))).asCsv(),
                   equalTo("STN,AOI,FR,2017-01-21,12:25,13:35,22GBP,LIN,LHR,AZ,2017-01-24,14:25,17:20,67GBP,89GBP,4,2"));
    }
}