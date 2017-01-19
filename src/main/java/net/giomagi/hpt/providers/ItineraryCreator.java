package net.giomagi.hpt.providers;

import com.google.common.collect.Sets;
import net.giomagi.hpt.model.DateRange;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Itinerary;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class ItineraryCreator {
    private final FlightProvider flights;

    public ItineraryCreator(FlightProvider flightProvider) {
        flights = flightProvider;
    }

    public Set<Itinerary> generate(String departure, String arrival, DateRange dates) {

        Set<Flight> outboundOptions = flights.find(departure, arrival, dates);
        Set<Flight> returnOptions = flights.find(arrival, departure, dates);

        Set<Itinerary> res = newHashSet();
        for (Flight outboundOption : outboundOptions) {
            for (Flight returnOption : returnOptions) {
                if (outboundOption.flightDate.isBefore(returnOption.flightDate)) {
                    res.add(Itinerary.of(outboundOption, returnOption));
                }
            }
        }

        return res;
    }
}
