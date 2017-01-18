package net.giomagi.hpt.providers;

import com.google.common.collect.*;
import net.giomagi.hpt.helpers.RawData;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Price;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class FileBasedFlightProvider {

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

    public Set<Flight> flights(String departure, String arrival, LocalDate flightDate) {
        return ImmutableSet.copyOf(flights.get(departure + "-" + arrival + "-" + flightDate.toString()));
    }

    public static FileBasedFlightProvider fromFile(String fileName) {
        return new FileBasedFlightProvider(RawData.fromFile(fileName));
    }
}
