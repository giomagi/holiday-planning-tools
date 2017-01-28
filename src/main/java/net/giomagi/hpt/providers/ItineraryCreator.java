package net.giomagi.hpt.providers;

import net.giomagi.hpt.helpers.UkCalendar;
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

    public Set<Itinerary> generate(Set<String> departures, Set<String> arrivals,
                                   Range<LocalDate> dates, Range<Integer> minMaxWorkDays) {

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

            LocalDate earliestReturn = UkCalendar.addWorkingDays(outboundOption.flightDate, minMaxWorkDays.lower);
            LocalDate latestReturn = UkCalendar.addWorkingDays(outboundOption.flightDate, minMaxWorkDays.upper);

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
