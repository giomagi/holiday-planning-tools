package net.giomagi.hpt;

import net.giomagi.hpt.model.DateRange;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.providers.FileBasedFlightProvider;

import java.time.LocalDate;
import java.util.Set;

public class FlightSearcher {

    public static void main(String[] args) {

        FileBasedFlightProvider provider = FileBasedFlightProvider.fromFile("data/flights.csv");

        Set<Flight> flights = provider.flights("LGW", "SVQ",
                                               DateRange.of(LocalDate.of(2017, 6, 1), LocalDate.of(2017, 6, 8)));

        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }
}
