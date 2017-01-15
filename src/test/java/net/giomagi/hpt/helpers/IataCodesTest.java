package net.giomagi.hpt.helpers;

import com.google.common.collect.ImmutableList;
import net.giomagi.hpt.model.NotFoundException;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class IataCodesTest {

    IataCodes codes = new IataCodes(ImmutableList.of(
            "1524,\"Malpensa\",\"Milano\",\"Italy\",\"MXP\",\"LIMC\",45.630606,8.728111,767,1,\"E\",\"Europe/Rome\"",
            "502,\"Gatwick\",\"London\",\"United Kingdom\",\"LGW\",\"EGKK\",51.148056,-0.190278,202,0,\"E\",\"Europe/London\"",
            "502,\"Gatwick\",\"London\",\"United Kingdom\",\"LGW\",\"EGKK\",51.148056,-0.190278,202,0,\"E\",\"Europe/London\"",
            "503,\"City\",\"London\",\"United Kingdom\",\"LCY\",\"EGLC\",51.505278,0.055278,19,0,\"E\",\"Europe/London\""
    ));

    @Test
    public void returnsCodesForAirports() {

        assertThat(codes.airportNameFor("MXP"), equalTo("Milano Malpensa"));
        assertThat(codes.airportNameFor("LCY"), equalTo("London City"));
    }

    @Test(expected = NotFoundException.class)
    public void throwsNotFoundForUnknownCodes() {
        codes.airportNameFor("XYZ");
    }

    @Test(expected = NotFoundException.class)
    public void inconsistentDataIsNotUsed() {
        codes.airportNameFor("LGW");
    }
}