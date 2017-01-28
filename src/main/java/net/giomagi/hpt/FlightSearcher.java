package net.giomagi.hpt;

import com.google.common.collect.ImmutableSet;
import net.giomagi.hpt.model.Range;
import net.giomagi.hpt.model.Itinerary;
import net.giomagi.hpt.providers.FlightProvider;
import net.giomagi.hpt.providers.ItineraryCreator;

import java.time.LocalDate;

public class FlightSearcher {

    public static void main(String[] args) {

        ItineraryCreator itineraries = new ItineraryCreator(FlightProvider.fromFile("data/flights.csv"));

        for (Itinerary itinerary : itineraries.generate(ImmutableSet.of("LHR", "LGW", "STN", "LTN"),
                                                        ImmutableSet.of("AGP", "SVQ", "XRY", "LEI"),
                                                        Range.of(LocalDate.of(2017, 1, 1),
                                                                 LocalDate.of(2017, 12, 31)),
                                                        Range.of(11, 11))) {

            System.out.println(itinerary.asCsv());
        }
    }
}
