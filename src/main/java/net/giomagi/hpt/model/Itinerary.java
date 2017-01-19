package net.giomagi.hpt.model;

public class Itinerary extends ValueType {
    public final Flight outboundFlight;
    public final Flight returnFlight;

    public Itinerary(Flight outboundFlight, Flight returnFlight) {
        this.outboundFlight = outboundFlight;
        this.returnFlight = returnFlight;
    }

    public static Itinerary of(Flight out, Flight ret) {
        return new Itinerary(out, ret);
    }
}
