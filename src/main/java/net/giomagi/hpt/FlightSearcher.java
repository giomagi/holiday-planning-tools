package net.giomagi.hpt;

import net.giomagi.hpt.model.DateRange;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Itinerary;
import net.giomagi.hpt.providers.FileBasedFlightProvider;
import net.giomagi.hpt.providers.FlightProvider;
import net.giomagi.hpt.providers.ItineraryCreator;

import java.time.LocalDate;
import java.util.Set;

public class FlightSearcher {

    public static void main(String[] args) {

        ItineraryCreator itineraries = new ItineraryCreator(FlightProvider.fromFile("data/flights.csv"));

        // TODO: rename departure/arrival > origin/destination
        for (Itinerary itinerary : itineraries.generate("LGW", "SVQ",
                                                        DateRange.of(LocalDate.of(2017, 6, 1), LocalDate.of(2017, 6, 8)))) {

            System.out.println(itinerary.outboundFlight);
            System.out.println(itinerary.returnFlight);
            System.out.println();
        }
    }
}
