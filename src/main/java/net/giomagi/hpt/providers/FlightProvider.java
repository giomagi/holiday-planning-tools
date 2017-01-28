package net.giomagi.hpt.providers;

import net.giomagi.hpt.helpers.RawData;
import net.giomagi.hpt.model.Range;
import net.giomagi.hpt.model.Flight;

import java.time.LocalDate;
import java.util.Set;

public interface FlightProvider {
    Set<Flight> find(String departure, String arrival, Range<LocalDate> dates);

    static FlightProvider fromFile(String fileName) {
        return new FileBasedFlightProvider(RawData.fromFile(fileName));
    }
}
