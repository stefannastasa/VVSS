package tasks.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceTaskTest {

    private DateService dateService;

    @BeforeEach
    public void setUp() {
        dateService = new DateService();
    }

    @AfterEach
    public void tearDown() {
    }

    private static Stream<Arguments> provideValidParams() {
        return Stream.of(
                Arguments.of("8:00",
                        new GregorianCalendar(2023, Calendar.MARCH, 31).getTime(),
                        new GregorianCalendar(2023, Calendar.MARCH, 31, 8, 0).getTime()),
                Arguments.of("0:0",
                        new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(1970, Calendar.JANUARY, 1, 0, 0).getTime()),
                Arguments.of("1:1",
                        new GregorianCalendar(1970, Calendar.JANUARY, 2).getTime(),
                        new GregorianCalendar(1970, Calendar.JANUARY, 2, 1, 1).getTime())
        );
    }

    @Order(1)
    @ParameterizedTest
    @MethodSource("provideValidParams")
    @DisplayName("getDateMergedWithTime with valid parameters")
    public void getDateMergedWithTimeValid(String time, Date noTimeDate, Date expectedDate) {
        Date date = dateService.getDateMergedWithTime(time, noTimeDate);
        assertEquals(expectedDate, date);
    }

    @Order(2)
    @Test
    @DisplayName("getDateMergedWithTime with invalid hour")
    public void getDateMergedWithTimeInvalidHour() {
        String time = "24:00";
        Date noTimeDate = new GregorianCalendar(2023, Calendar.MARCH, 31).getTime();
        assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(time, noTimeDate));
    }

    @Order(3)
    @Test
    @DisplayName("getDateMergedWithTime with invalid minute")
    public void getDateMergedWithTimeInvalidMinute() {
        String time = "23:60";
        Date noTimeDate = new GregorianCalendar(2023, Calendar.MARCH, 31).getTime();
        assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(time, noTimeDate));
    }

    @Order(4)
    @Test
    @DisplayName("getDateMergedWithTime with invalid time format")
    public void getDateMergedWithTimeInvalidFormat() {
        String time = "invalid";
        Date noTimeDate = new GregorianCalendar(2023, Calendar.MARCH, 31).getTime();
        assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(time, noTimeDate));
    }

    @Order(5)
    @Test
    @DisplayName("getDateMergedWithTime with negative hour")
    public void getDateMergedWithTimeNegativeHour() {
        String time = "-1:00";
        Date noTimeDate = new GregorianCalendar(2023, Calendar.MARCH, 31).getTime();
        assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(time, noTimeDate));
    }

    @Order(6)
    @Test
    @DisplayName("getDateMergedWithTime with negative minute")
    public void getDateMergedWithTimeNegativeMinute() {
        String time = "10:-1";
        Date noTimeDate = new GregorianCalendar(2023, Calendar.MARCH, 31).getTime();
        assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(time, noTimeDate));
    }

}
