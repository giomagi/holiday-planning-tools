package net.giomagi.hpt.providers;

import com.google.common.collect.ImmutableSet;
import net.giomagi.hpt.model.DateRange;
import net.giomagi.hpt.model.Flight;
import net.giomagi.hpt.model.Itinerary;
import net.giomagi.hpt.model.Price;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItineraryCreatorTest {

    Flight flight1 = new Flight("A", "B", "X",
                                LocalDate.now(), LocalTime.now(), LocalTime.now(),
                                Price.of(123, "XXX"));
    Flight flight2 = new Flight("A", "B", "Y",
                                LocalDate.now().plusDays(1), LocalTime.now(), LocalTime.now(),
                                Price.of(123, "XXX"));
    Flight flight3 = new Flight("B", "A", "X",
                                LocalDate.now().plusDays(2), LocalTime.now(), LocalTime.now(),
                                Price.of(123, "XXX"));

    DateRange dates = DateRange.of(LocalDate.now(), LocalDate.now().plusWeeks(1));

    FlightProvider flights = mock(FlightProvider.class);
    ItineraryCreator itineraries = new ItineraryCreator(flights);

    @Test
    public void returnsAllCombinationOfFlights() {

        when(flights.find("A", "B", dates)).thenReturn(ImmutableSet.of(flight1, flight2));
        when(flights.find("B", "A", dates)).thenReturn(ImmutableSet.of(flight3));

        assertThat(itineraries.generate("A", "B", dates),
                   containsInAnyOrder(Itinerary.of(flight1, flight3),
                                      Itinerary.of(flight2, flight3)));
    }

    @Test
    public void returnsACombinationOnlyIfItHasBothFlights() {

        when(flights.find("A", "B", dates)).thenReturn(ImmutableSet.of(flight1));
        when(flights.find("B", "A", dates)).thenReturn(Collections.emptySet());

        assertThat(itineraries.generate("A", "B", dates), is(empty()));
    }

    @Test
    public void doesNotReturnACombinationIfTheReturnDateIsBeforeTheOutboundDate() {

        when(flights.find("A", "B", dates)).thenReturn(ImmutableSet.of(flight1));
        when(flights.find("B", "A", dates)).thenReturn(ImmutableSet.of(flight2));

        assertThat(itineraries.generate("A", "B", dates), is(not(empty())));
        assertThat(itineraries.generate("B", "A", dates), is(empty()));
    }
}