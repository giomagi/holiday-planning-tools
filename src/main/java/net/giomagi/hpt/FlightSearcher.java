package net.giomagi.hpt;

import com.google.common.collect.ImmutableSet;
import net.giomagi.hpt.model.DateRange;
import net.giomagi.hpt.model.Itinerary;
import net.giomagi.hpt.providers.FlightProvider;
import net.giomagi.hpt.providers.ItineraryCreator;

import java.time.LocalDate;
import java.util.Collections;

public class FlightSearcher {

    public static void main(String[] args) {

        ItineraryCreator itineraries = new ItineraryCreator(FlightProvider.fromFile("data/flights.csv"));

        // TODO: rename departure/arrival > origin/destination
        for (Itinerary itinerary : itineraries.generate(ImmutableSet.of("LHR", "LGW"),
                                                        ImmutableSet.of("AGP", "SVQ"),
                                                        DateRange.of(LocalDate.of(2017, 6, 1),
                                                                     LocalDate.of(2017, 6, 8)))) {

            System.out.println(itinerary.summary());
            System.out.println(itinerary.outboundFlight);
            System.out.println(itinerary.returnFlight);
            System.out.println();
        }
    }
}
