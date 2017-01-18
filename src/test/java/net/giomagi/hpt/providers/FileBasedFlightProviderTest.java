package net.giomagi.hpt.providers;

import com.google.common.collect.ImmutableList;
import net.giomagi.hpt.model.DateRange;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Price;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class FileBasedFlightProviderTest {

    @Test
    public void retrievesFliesForAGivenRouteAndDateRange() {

        ImmutableList<String> flightFromFile = ImmutableList.of("LHR,LIN,AZ,2017-05-31,0955,1840,10",
                                                                "LHR,LIN,AZ,2017-06-01,0955,1840,20",
                                                                "LHR,LIN,AZ,2017-06-02,1455,1840,30",
                                                                "LHR,LIN,BA,2017-06-02,1000,1300,40",
                                                                "LHR,MXP,AZ,2017-06-02,1655,1840,50",
                                                                "LHR,LIN,AZ,2017-06-03,1955,2140,60",
                                                                "LHR,LIN,AZ,2017-06-04,1955,2140,70");

        FileBasedFlightProvider provider = new FileBasedFlightProvider(flightFromFile);

        assertThat(provider.flights("LHR", "LIN", DateRange.of(LocalDate.of(2017, 6, 1), LocalDate.of(2017, 6, 3))),
                   containsInAnyOrder(new Flight("LHR", "LIN", "AZ",
                                                 LocalDate.of(2017, 6, 1), LocalTime.of(9, 55), LocalTime.of(18, 40),
                                                 Price.of(20, "GBP")),
                                      new Flight("LHR", "LIN", "AZ",
                                                 LocalDate.of(2017, 6, 2), LocalTime.of(14, 55), LocalTime.of(18, 40),
                                                 Price.of(30, "GBP")),
                                      new Flight("LHR", "LIN", "BA",
                                                 LocalDate.of(2017, 6, 2), LocalTime.of(10, 0), LocalTime.of(13, 0),
                                                 Price.of(40, "GBP")),
                                      new Flight("LHR", "LIN", "AZ",
                                                 LocalDate.of(2017, 6, 3), LocalTime.of(19, 55), LocalTime.of(21, 40),
                                                 Price.of(60, "GBP"))));
    }
}