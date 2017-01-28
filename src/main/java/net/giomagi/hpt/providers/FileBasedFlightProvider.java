package net.giomagi.hpt.providers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.giomagi.hpt.model.Range;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Price;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class FileBasedFlightProvider implements FlightProvider {

    private Multimap<String, Flight> flights = HashMultimap.create();

    FileBasedFlightProvider(List<String> flightsAsStrings) {

        for (String flightsAsString : flightsAsStrings) {
            String[] chunks = flightsAsString.split(",");
            flights.put(chunks[0] + "-" + chunks[1] + "-" + chunks[3],
                        new Flight(chunks[0],
                                   chunks[1],
                                   chunks[2],
                                   LocalDate.parse(chunks[3]),
                                   LocalTime.parse(chunks[4], DateTimeFormatter.ofPattern("HHmm")),
                                   LocalTime.parse(chunks[5], DateTimeFormatter.ofPattern("HHmm")),
                                   Price.of(Integer.parseInt(chunks[6]), "GBP")));
        }
    }

    @Override
    public Set<Flight> find(String departure, String arrival, Range<LocalDate> dates) {

        Set<Flight> res = newHashSet();
        LocalDate flightDate = dates.lower;

        while (!flightDate.isAfter(dates.upper)) {
            res.addAll(flights.get(departure + "-" + arrival + "-" + flightDate.toString()));
            flightDate = flightDate.plusDays(1);
        }

        return res;
    }
}
