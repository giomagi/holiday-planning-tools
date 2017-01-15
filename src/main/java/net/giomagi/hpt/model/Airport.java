package net.giomagi.hpt.model;

import net.giomagi.hpt.helpers.IataCodes;

public class Airport extends ValueType {
    public final String airportName;

    public Airport(String airportName) {
        this.airportName = airportName;
    }

    public static Airport of(String airportName) {
        return new Airport(airportName);
    }

}
