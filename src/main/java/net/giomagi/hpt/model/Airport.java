package net.giomagi.hpt.model;

public class Airport extends ValueType {
    public final String airportName;

    public Airport(String airportName) {
        this.airportName = airportName;
    }

    public static Airport of(String airportName) {
        return new Airport(airportName);
    }

    public String iata() {
        return IataCodes.translator().airportCodeFor(airportName);
    }
}
