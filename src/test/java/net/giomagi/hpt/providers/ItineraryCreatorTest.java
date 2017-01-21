package net.giomagi.hpt.providers;

import com.google.common.collect.ImmutableSet;
import net.giomagi.hpt.support.FlightMaker;
import net.giomagi.hpt.model.DateRange;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Itinerary;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;

import static com.natpryce.makeiteasy.MakeItEasy.*;
import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItineraryCreatorTest {

    FlightProvider flights = mock(FlightProvider.class);
    ItineraryCreator itineraries = new ItineraryCreator(flights);

    @Test
    public void returnsAllCombinationOfFlightsBetweenTwoDestinations() {

        Flight flight1 = make(a(FlightMaker.flight));
        Flight flight2 = make(a(FlightMaker.flight));
        Flight flight3 = make(a(FlightMaker.flight));

        DateRange dates = DateRange.of(LocalDate.now(), LocalDate.now().plusWeeks(1));

        when(flights.find("A", "B", dates)).thenReturn(ImmutableSet.of(flight1, flight2));
        when(flights.find("B", "A", dates)).thenReturn(ImmutableSet.of(flight3));

        assertThat(itineraries.generate(singleton("A"), singleton("B"), dates),
                   containsInAnyOrder(Itinerary.of(flight1, flight3),
                                      Itinerary.of(flight2, flight3)));
    }

    @Test
    public void returnsCombinationsFromMultipleOriginsAndDestinations() {

        Flight flightAC = make(a(FlightMaker.flight));
        Flight flightAD = make(a(FlightMaker.flight));
        Flight flightBC = make(a(FlightMaker.flight));
        Flight flightBD = make(a(FlightMaker.flight));
        Flight flightCA = make(a(FlightMaker.flight));
        Flight flightCB = make(a(FlightMaker.flight));
        Flight flightDA = make(a(FlightMaker.flight));
        Flight flightDB = make(a(FlightMaker.flight));

        DateRange dates = DateRange.of(LocalDate.now(), LocalDate.now());

        when(flights.find("A", "C", dates)).thenReturn(singleton(flightAC));
        when(flights.find("A", "D", dates)).thenReturn(singleton(flightAD));
        when(flights.find("B", "C", dates)).thenReturn(singleton(flightBC));
        when(flights.find("B", "D", dates)).thenReturn(singleton(flightBD));
        when(flights.find("C", "A", dates)).thenReturn(singleton(flightCA));
        when(flights.find("C", "B", dates)).thenReturn(singleton(flightCB));
        when(flights.find("D", "A", dates)).thenReturn(singleton(flightDA));
        when(flights.find("D", "B", dates)).thenReturn(singleton(flightDB));

        assertThat(itineraries.generate(ImmutableSet.of("A", "B"), ImmutableSet.of("C", "D"), dates),
                   containsInAnyOrder(Itinerary.of(flightAC, flightCA),
                                      Itinerary.of(flightAC, flightCB),
                                      Itinerary.of(flightAC, flightDA),
                                      Itinerary.of(flightAC, flightDB),
                                      Itinerary.of(flightAD, flightCA),
                                      Itinerary.of(flightAD, flightCB),
                                      Itinerary.of(flightAD, flightDA),
                                      Itinerary.of(flightAD, flightDB),
                                      Itinerary.of(flightBC, flightCA),
                                      Itinerary.of(flightBC, flightCB),
                                      Itinerary.of(flightBC, flightDA),
                                      Itinerary.of(flightBC, flightDB),
                                      Itinerary.of(flightBD, flightCA),
                                      Itinerary.of(flightBD, flightCB),
                                      Itinerary.of(flightBD, flightDA),
                                      Itinerary.of(flightBD, flightDB)));
    }

    @Test
    public void returnsACombinationOnlyIfItHasBothFlights() {

        DateRange dates = DateRange.of(LocalDate.now(), LocalDate.now().plusWeeks(1));

        when(flights.find("A", "B", dates)).thenReturn(ImmutableSet.of(make(a(FlightMaker.flight))));
        when(flights.find("B", "A", dates)).thenReturn(Collections.emptySet());

        assertThat(itineraries.generate(singleton("A"), singleton("B"), dates), is(empty()));
    }

    @Test
    public void doesNotReturnACombinationIfTheReturnDateIsBeforeTheOutboundDate() {

        DateRange dates = DateRange.of(LocalDate.now(), LocalDate.now().plusWeeks(1));

        when(flights.find("A", "B", dates))
                .thenReturn(ImmutableSet.of(make(a(FlightMaker.flight, with(FlightMaker.date, LocalDate.now())))));

        when(flights.find("B", "A", dates))
                .thenReturn(ImmutableSet.of(make(a(FlightMaker.flight, with(FlightMaker.date, LocalDate.now().plusDays(1))))));

        assertThat(itineraries.generate(singleton("A"), singleton("B"), dates), is(not(empty())));
        assertThat(itineraries.generate(singleton("B"), singleton("A"), dates), is(empty()));
    }

}