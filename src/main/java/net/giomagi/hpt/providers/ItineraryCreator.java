package net.giomagi.hpt.providers;

import net.giomagi.hpt.helpers.EnglandCalendar2017to2020;
import net.giomagi.hpt.model.Range;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Itinerary;

import java.time.LocalDate;
import java.util.Set;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Sets.newHashSet;

public class ItineraryCreator {
    private final FlightProvider flights;

    public ItineraryCreator(FlightProvider flightProvider) {
        flights = flightProvider;
    }

    public Set<Itinerary> generate(Set<String> origins, Set<String> destinations,
                                   Range<LocalDate> dates, Range<Integer> minMaxWorkDays) {

        Set<Itinerary> res = newHashSet();
        Set<Flight> outboundOptions = newHashSet();
        Set<Flight> returnOptions = newHashSet();

        for (String origin : origins) {
            for (String destination : destinations) {

                outboundOptions.addAll(flights.find(origin, destination, dates));
                returnOptions.addAll(flights.find(destination, origin, dates));
            }
        }

        for (Flight outboundOption : outboundOptions) {

            LocalDate earliestReturn = EnglandCalendar2017to2020.addWorkingDays(outboundOption.flightDate, minMaxWorkDays.lower - 1);
            LocalDate latestReturn = EnglandCalendar2017to2020.addWorkingDays(outboundOption.flightDate, minMaxWorkDays.upper - 1);

            for (Flight returnOption : returnOptions) {
                if (!returnOption.flightDate.isBefore(earliestReturn)
                        && !returnOption.flightDate.isAfter(latestReturn)) {

                    res.add(Itinerary.of(outboundOption, returnOption));
                }
            }
        }

        return res;
    }
}
