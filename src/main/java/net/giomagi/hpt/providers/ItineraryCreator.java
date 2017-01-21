package net.giomagi.hpt.providers;

import net.giomagi.hpt.model.DateRange;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Itinerary;

import java.util.Set;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Sets.newHashSet;

public class ItineraryCreator {
    private final FlightProvider flights;

    public ItineraryCreator(FlightProvider flightProvider) {
        flights = flightProvider;
    }

    public Set<Itinerary> generate(Set<String> departures, Set<String> arrivals, DateRange dates) {

        Set<Itinerary> res = newHashSet();
        Set<Flight> outboundOptions = newHashSet();
        Set<Flight> returnOptions = newHashSet();

        for (String departure : departures) {
            for (String arrival : arrivals) {

                outboundOptions.addAll(flights.find(departure, arrival, dates));
                returnOptions.addAll(flights.find(arrival, departure, dates));
            }
        }

        for (Flight outboundOption : outboundOptions) {
            for (Flight returnOption : returnOptions) {
                if (!outboundOption.flightDate.isAfter(returnOption.flightDate)) {
                    res.add(Itinerary.of(outboundOption, returnOption));
                }
            }
        }

        return res;
    }
}
