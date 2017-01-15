package net.giomagi.hpt.helpers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.giomagi.hpt.model.NotFoundException;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class IataCodes {

    private static final IataCodes IATA_CODES = new IataCodes(RawData.fromFile("data/airports.dat"));
    private Map<String, String> codesToAirports = new HashMap<>(10000);

    IataCodes(List<String> airportStrings) {

        Set<String> inconsistentKeys = new HashSet<>();
        for (String airportString : airportStrings) {
            String[] chunks = airportString.split(",");
            String airportCode = sanitise(chunks[4]);

            if (codesToAirports.containsKey(airportCode)) {
                inconsistentKeys.add(airportCode);
            }
            codesToAirports.put(airportCode, sanitise(chunks[2]) + " " + sanitise(chunks[1]));
        }

        for (String key : inconsistentKeys) {
            codesToAirports.remove(key);
        }
    }

    private String sanitise(String chunk) {
        return chunk.replaceAll("\"", "");
    }

    public String airportNameFor(String airportCode) {
        if (!codesToAirports.containsKey(airportCode)) {
            throw new NotFoundException(airportCode);
        }

        return codesToAirports.get(airportCode);
    }

    public static IataCodes translator() {
        return IATA_CODES;
    }
}
