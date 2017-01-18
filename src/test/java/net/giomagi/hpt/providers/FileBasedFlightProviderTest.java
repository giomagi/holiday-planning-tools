package net.giomagi.hpt.providers;

import com.google.common.collect.ImmutableList;
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
    public void retrievesFliesForAGivenRouteAndDate() {

        ImmutableList<String> flightFromFile = ImmutableList.of("LHR,LIN,AZ,2017-06-01,0955,1840,10",
                                                                "LHR,LIN,AZ,2017-06-02,1455,1840,20",
                                                                "LHR,LIN,BA,2017-06-02,1000,1300,30",
                                                                "LHR,MXP,AZ,2017-06-02,1655,1840,40",
                                                                "LHR,LIN,AZ,2017-06-03,1955,2140,50");

        FileBasedFlightProvider provider = new FileBasedFlightProvider(flightFromFile);

        assertThat(provider.flights("LHR", "LIN", LocalDate.of(2017, 6, 2)),
                   containsInAnyOrder(new Flight("LHR", "LIN", "AZ",
                                                 LocalDate.of(2017, 6, 2), LocalTime.of(14, 55), LocalTime.of(18, 40),
                                                 Price.of(20, "GBP")),
                                      new Flight("LHR", "LIN", "BA",
                                                 LocalDate.of(2017, 6, 2), LocalTime.of(10, 0), LocalTime.of(13, 0),
                                                 Price.of(30, "GBP"))));
    }
}