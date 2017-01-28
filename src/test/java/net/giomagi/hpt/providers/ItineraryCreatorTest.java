package net.giomagi.hpt.providers;

import com.google.common.collect.ImmutableSet;
import net.giomagi.hpt.support.FlightMaker;
import net.giomagi.hpt.model.Range;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Itinerary;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;

import static com.natpryce.makeiteasy.MakeItEasy.*;
import static java.util.Collections.singleton;
import static net.giomagi.hpt.support.FlightMaker.date;
import static net.giomagi.hpt.support.FlightMaker.flight;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItineraryCreatorTest {

    Range<Integer> noWorkingDays = Range.of(0, 0);
    Range<LocalDate> oneWeekFromToday = Range.of(LocalDate.now(), LocalDate.now().plusWeeks(1));

    FlightProvider flights = mock(FlightProvider.class);
    ItineraryCreator itineraries = new ItineraryCreator(flights);

    @Test
    public void returnsAllCombinationOfFlightsBetweenTwoDestinations() {

        Flight flight1 = make(a(flight));
        Flight flight2 = make(a(flight));
        Flight flight3 = make(a(flight));

        when(flights.find("A", "B", oneWeekFromToday)).thenReturn(ImmutableSet.of(flight1, flight2));
        when(flights.find("B", "A", oneWeekFromToday)).thenReturn(ImmutableSet.of(flight3));

        assertThat(itineraries.generate(singleton("A"), singleton("B"), oneWeekFromToday, noWorkingDays),
                   containsInAnyOrder(Itinerary.of(flight1, flight3),
                                      Itinerary.of(flight2, flight3)));
    }

    @Test
    public void returnsCombinationsFromMultipleOriginsAndDestinations() {

        Flight flightAC = make(a(flight));
        Flight flightAD = make(a(flight));
        Flight flightBC = make(a(flight));
        Flight flightBD = make(a(flight));
        Flight flightCA = make(a(flight));
        Flight flightCB = make(a(flight));
        Flight flightDA = make(a(flight));
        Flight flightDB = make(a(flight));

        Range<LocalDate> today = Range.of(LocalDate.now(), LocalDate.now());

        when(flights.find("A", "C", today)).thenReturn(singleton(flightAC));
        when(flights.find("A", "D", today)).thenReturn(singleton(flightAD));
        when(flights.find("B", "C", today)).thenReturn(singleton(flightBC));
        when(flights.find("B", "D", today)).thenReturn(singleton(flightBD));
        when(flights.find("C", "A", today)).thenReturn(singleton(flightCA));
        when(flights.find("C", "B", today)).thenReturn(singleton(flightCB));
        when(flights.find("D", "A", today)).thenReturn(singleton(flightDA));
        when(flights.find("D", "B", today)).thenReturn(singleton(flightDB));

        assertThat(itineraries.generate(ImmutableSet.of("A", "B"), ImmutableSet.of("C", "D"), today, noWorkingDays),
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
    public void returnsOnlyCombinationsThatCoverTheRequiredNumberOfWorkingDays() {

        Range<Integer> minMaxWorkDays = Range.of(2, 4);
        Range<LocalDate> dates = Range.of(LocalDate.of(2017, 1, 21), LocalDate.of(2017, 1, 28));

        Flight outbound = make(a(flight, with(date, LocalDate.of(2017, 1, 21))));
        Flight earlyReturn = make(a(flight, with(date, LocalDate.of(2017, 1, 23))));
        Flight okReturn1 = make(a(flight, with(date, LocalDate.of(2017, 1, 24))));
        Flight okReturn2 = make(a(flight, with(date, LocalDate.of(2017, 1, 26))));
        Flight lateReturn = make(a(flight, with(date, LocalDate.of(2017, 1, 27))));

        when(flights.find("A", "B", dates)).thenReturn(ImmutableSet.of(outbound));
        when(flights.find("B", "A", dates))
                .thenReturn(ImmutableSet.of(earlyReturn, okReturn1, okReturn2, lateReturn));

        assertThat(itineraries.generate(singleton("A"), singleton("B"), dates, minMaxWorkDays),
                   containsInAnyOrder(Itinerary.of(outbound, okReturn1),
                                      Itinerary.of(outbound, okReturn2)));
    }

    @Test
    public void returnsACombinationOnlyIfItHasBothFlights() {

        when(flights.find("A", "B", oneWeekFromToday)).thenReturn(ImmutableSet.of(make(a(flight))));
        when(flights.find("B", "A", oneWeekFromToday)).thenReturn(Collections.emptySet());

        assertThat(itineraries.generate(singleton("A"), singleton("B"), oneWeekFromToday, noWorkingDays), is(empty()));
    }

    @Test
    public void doesNotReturnACombinationIfTheReturnDateIsBeforeTheOutboundDate() {

        when(flights.find("A", "B", oneWeekFromToday))
                .thenReturn(ImmutableSet.of(make(a(flight, with(FlightMaker.date, LocalDate.now())))));

        when(flights.find("B", "A", oneWeekFromToday))
                .thenReturn(ImmutableSet.of(make(a(flight, with(FlightMaker.date, LocalDate.now().plusDays(1))))));

        assertThat(itineraries.generate(singleton("A"), singleton("B"), oneWeekFromToday, Range.of(0, 1)), is(not(empty())));
        assertThat(itineraries.generate(singleton("B"), singleton("A"), oneWeekFromToday, Range.of(0, 1)), is(empty()));
    }
}