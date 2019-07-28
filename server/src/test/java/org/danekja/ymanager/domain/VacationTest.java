package org.danekja.ymanager.domain;

import org.danekja.ymanager.dto.Status;
import org.danekja.ymanager.dto.VacationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests methods of the {@code Vacation} class.
 * @see Vacation
 */
class VacationTest {
    /**
     * The empty instance of the {@code Vacation}.
     */
    private Vacation vacation;

    /**
     * Prepares the instance of the {@code Vacation}.
     */
    @BeforeEach
    void setUp() {
        this.vacation = new Vacation();
    }

    /**
     * Tests the method {@code setDate} with common values where no problem should occur.
     */
    @Test
    void setDate_valid() {
        LocalDate date = LocalDate.of(2000,10,12);
        this.vacation.setDate(date);
        assertEquals(date, this.vacation.getDate());
    }

    /**
     * Tests the method {@code setDate} with null input value.
     */
    @Test
    void setDate_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setDate(null));
    }

    /**
     * Tests the method {@code setFrom} with a null input, null ending time and null vacation type.
     */
    @Test
    void setFrom_nullFrom_nullTo_nullType_valid() {
        this.vacation.setFrom(null);
        assertNull(this.vacation.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with a null input, ending time and null vacation type.
     */
    @Test
    void setFrom_nullFrom_to_nullType_valid() {
        this.vacation.setTo(LocalTime.of(20, 0));
        this.vacation.setFrom(null);
        assertNull(this.vacation.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with an input, null ending time and null vacation type.
     */
    @Test
    void setFrom_from_nullTo_nullType_valid() {
        LocalTime from = LocalTime.of(10,0);
        this.vacation.setFrom(from);
        assertEquals(from, this.vacation.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with an input, ending time and null vacation type.
     */
    @Test
    void setFrom_from_to_nullType_valid() {
        LocalTime to = LocalTime.of(20, 0);
        this.vacation.setTo(to);
        LocalTime from = LocalTime.of(10,0);
        this.vacation.setFrom(from);
        assertEquals(from, this.vacation.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with same input and ending time and null vacation type.
     */
    @Test
    void setFrom_from_to_nullType_same() {
        LocalTime to = LocalTime.of(10, 0);
        this.vacation.setTo(to);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setFrom(to));
    }

    /**
     * Tests the method {@code setFrom} with same input and ending time and null vacation type.
     */
    @Test
    void setFrom_from_to_nullType_wrongOrder() {
        LocalTime to = LocalTime.of(10, 0);
        this.vacation.setTo(to);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setFrom(LocalTime.of(20,0)));
    }

    /**
     * Tests the method {@code setFrom} with a null input, null ending time and sick day vacation type.
     */
    @Test
    void setFrom_nullFrom_nullTo_sickDay_valid() {
        this.vacation.setType(VacationType.SICK_DAY);
        this.vacation.setFrom(null);
        assertNull(this.vacation.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with an input and sick day vacation type.
     */
    @Test
    void setFrom_from_sickDay() {
        this.vacation.setType(VacationType.SICK_DAY);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setFrom(LocalTime.of(20,0)));
    }

    /**
     * Tests the method {@code setFrom} with a null input, null ending time and vacation vacation type.
     */
    @Test
    void setFrom_nullFrom_vacation_valid() {
        this.vacation.setFrom(null);
        assertNull(this.vacation.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with an input, null ending time and vacation vacation type.
     */
    @Test
    void setFrom_from_nullTo_vacation_valid() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(10,0);
        this.vacation.setFrom(from);
        assertEquals(from, this.vacation.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with an input, ending time and vacation vacation type.
     */
    @Test
    void setFrom_from_to_vacation_valid() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime to = LocalTime.of(20, 0);
        this.vacation.setTo(to);
        LocalTime from = LocalTime.of(10,0);
        this.vacation.setFrom(from);
        assertEquals(from, this.vacation.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with same input and ending time and vacation vacation type.
     */
    @Test
    void setFrom_from_to_vacation_same() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime to = LocalTime.of(10, 0);
        this.vacation.setTo(to);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setFrom(LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setFrom} with same input and ending time and vacation vacation type.
     */
    @Test
    void setFrom_from_to_vacation_wrongOrder() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime to = LocalTime.of(10, 0);
        this.vacation.setTo(to);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setFrom(LocalTime.of(20,0)));
    }

    /**
     * Tests the method {@code setTo} with a null input, null starting time and null vacation type.
     */
    @Test
    void setTo_nullTo_nullFrom_nullType_valid() {
        this.vacation.setTo(null);
        assertNull(this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTo} with a null input, starting time and null vacation type.
     */
    @Test
    void setTo_nullTo_from_nullType_valid() {
        this.vacation.setFrom(LocalTime.of(20, 0));
        this.vacation.setTo(null);
        assertNull(this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTo} with an input, null starting time and null vacation type.
     */
    @Test
    void setTo_to_nullFrom_nullType_valid() {
        LocalTime to = LocalTime.of(10,0);
        this.vacation.setTo(to);
        assertEquals(to, this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTo} with an input, starting time and null vacation type.
     */
    @Test
    void setTo_to_from_nullType_valid() {
        LocalTime from = LocalTime.of(10, 0);
        this.vacation.setFrom(from);
        LocalTime to = LocalTime.of(20,0);
        this.vacation.setTo(to);
        assertEquals(to, this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTo} with same input and starting time and null vacation type.
     */
    @Test
    void setTo_to_from_nullType_same() {
        LocalTime from = LocalTime.of(10, 0);
        this.vacation.setFrom(from);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTo(LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setTo} with same input and starting time and null vacation type.
     */
    @Test
    void setTo_to_from_nullType_wrongOrder() {
        LocalTime from = LocalTime.of(20, 0);
        this.vacation.setFrom(from);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTo(LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setTo} with a null input, null starting time and sick day vacation type.
     */
    @Test
    void setTo_nullTo_nullFrom_sickDay_valid() {
        this.vacation.setType(VacationType.SICK_DAY);
        this.vacation.setTo(null);
        assertNull(this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTo} with an input and sick day vacation type.
     */
    @Test
    void setTo_to_sickDay() {
        this.vacation.setType(VacationType.SICK_DAY);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTo(LocalTime.of(20,0)));
    }

    /**
     * Tests the method {@code setTo} with a null input, null starting time and vacation vacation type.
     */
    @Test
    void setTo_nullTo_vacation_valid() {
        this.vacation.setTo(null);
        assertNull(this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTo} with an input, null starting time and vacation vacation type.
     */
    @Test
    void setTo_to_nullFrom_vacation_valid() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime to = LocalTime.of(10,0);
        this.vacation.setTo(to);
        assertEquals(to, this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTo} with an input, starting time and vacation vacation type.
     */
    @Test
    void setTo_to_from_vacation_valid() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(10, 0);
        this.vacation.setFrom(from);
        LocalTime to = LocalTime.of(20,0);
        this.vacation.setTo(to);
        assertEquals(to, this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTo} with same input and starting time and vacation vacation type.
     */
    @Test
    void setTo_to_from_vacation_same() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(10, 0);
        this.vacation.setFrom(from);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTo(from));
    }

    /**
     * Tests the method {@code setTo} with same input and starting time and vacation vacation type.
     */
    @Test
    void setTo_to_from_vacation_wrongOrder() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(20, 0);
        this.vacation.setFrom(from);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTo(LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setTime} with common values where no problem should occur.
     */
    @Test
    void setTime_nullType_valid() {
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(20,0);
        this.vacation.setTime(from, to);
        assertEquals(from, this.vacation.getFrom());
        assertEquals(to, this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTime} with the same times.
     */
    @Test
    void setTime_nullType_same() {
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTime(from, to));
    }

    /**
     * Tests the method {@code setTime} with later start.
     */
    @Test
    void setTime_nullType_wrongOrder() {
        LocalTime from = LocalTime.of(20,0);
        LocalTime to = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTime(from, to));
    }

    /**
     * Tests the method {@code setTime} with null {@code from} input.
     */
    @Test
    void setTime_nullFrom_nullType_valid() {
        LocalTime to = LocalTime.of(10,0);
        this.vacation.setTime(null, to);
        assertNull(this.vacation.getFrom());
        assertEquals(to, this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTime} with null {@code to} input.
     */
    @Test
    void setTime_nullTo_nullType_valid() {
        LocalTime from = LocalTime.of(20,0);
        this.vacation.setTime(from, null);
        assertNull(this.vacation.getTo());
        assertEquals(from, this.vacation.getFrom());
    }

    /**
     * Tests the method {@code setTime} with null input.
     */
    @Test
    void setTime_nullFrom_nullTo_nullType_valid() {
        this.vacation.setTime(null, null);
        assertNull(this.vacation.getFrom());
        assertNull(this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTime} with null input and sick day vacation type.
     */
    @Test
    void setTime_nullFrom_nullTo_sickDay_valid() {
        this.vacation.setType(VacationType.SICK_DAY);
        this.vacation.setTime(null, null);
        assertNull(this.vacation.getFrom());
        assertNull(this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTime} with from, null to and sick day vacation type.
     */
    @Test
    void setTime_from_nullTo_sickDay() {
        this.vacation.setType(VacationType.SICK_DAY);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTime(LocalTime.of(10,0), null));
    }

    /**
     * Tests the method {@code setTime} with null from, to and sick day vacation type.
     */
    @Test
    void setTime_nullFrom_to_sickDay() {
        this.vacation.setType(VacationType.SICK_DAY);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTime(null, LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setTime} with from, to and sick day vacation type.
     */
    @Test
    void setTime_from_to_sickDay() {
        this.vacation.setType(VacationType.SICK_DAY);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTime(LocalTime.of(10,0), LocalTime.of(20,0)));
    }

    /**
     * Tests the method {@code setTime} with null input and vacation vacation type.
     */
    @Test
    void setTime_nullFrom_nullTo_vacation() {
        this.vacation.setType(VacationType.VACATION);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTime(null, null));
    }

    /**
     * Tests the method {@code setTime} with from, null to and vacation vacation type.
     */
    @Test
    void setTime_from_nullTo_vacation() {
        this.vacation.setType(VacationType.VACATION);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTime(LocalTime.of(10,0), null));
    }

    /**
     * Tests the method {@code setTime} with null from, to and vacation vacation type.
     */
    @Test
    void setTime_nullFrom_to_vacation() {
        this.vacation.setType(VacationType.VACATION);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTime(null, LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setTime} with from, to and vacation vacation type.
     */
    @Test
    void setTime_from_to_vacation_valid() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(20,0);
        this.vacation.setTime(from, to);
        assertEquals(from, this.vacation.getFrom());
        assertEquals(to, this.vacation.getTo());
    }

    /**
     * Tests the method {@code setTime} with same from and to and vacation vacation type.
     */
    @Test
    void setTime_from_to_vacation_same() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTime(from, to));
    }

    /**
     * Tests the method {@code setTime} with later from than to and vacation vacation type.
     */
    @Test
    void setTime_from_to_vacation_wrongOrder() {
        this.vacation.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(20,0);
        LocalTime to = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setTime(from, to));
    }

    /**
     * Tests the method {@code setStatus} with common values where no problem should occur.
     */
    @Test
    void setStatus_valid() {
        this.vacation.setStatus(Status.ACCEPTED);
        assertEquals(Status.ACCEPTED, this.vacation.getStatus());
    }

    /**
     * Tests the method {@code setStatus} with null value.
     */
    @Test
    void setStatus_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setStatus(null));
    }

    /**
     * Tests the method {@code setType} with with vacation input.
     */
    @Test
    void setType_valid() {
        this.vacation.setType(VacationType.VACATION);
        assertEquals(VacationType.VACATION, this.vacation.getType());
    }

    /**
     * Tests the method {@code setType} with sick day input.
     */
    @Test
    void setType_sickDay_valid() {
        this.vacation.setType(VacationType.SICK_DAY);
        assertEquals(VacationType.SICK_DAY, this.vacation.getType());
        assertNull(this.vacation.getFrom());
        assertNull(this.vacation.getTo());
    }

    /**
     * Tests the method {@code setType} with null value.
     */
    @Test
    void setType_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.vacation.setType(null));
    }

    /**
     * Tests the method {@code toString}.
     */
    @Test
    void toString_valid() {
        Vacation vacation = new Vacation();
        vacation.setId(5L);
        vacation.setDate(LocalDate.of(2010,1,9));
        vacation.setFrom(LocalTime.of(12,15));
        vacation.setTo(LocalTime.of(22,30));
        vacation.setCreationDate(LocalDateTime.of(2008,10,30,20,0));
        vacation.setStatus(Status.ACCEPTED);
        vacation.setType(VacationType.VACATION);
        assertEquals("Vacation{id=5, date=2010-01-09, from=12:15, to=22:30, creationDate=2008-10-30T20:00, status=ACCEPTED, type=VACATION}", vacation.toString());
    }

    /**
     * Tests the method {@code toString} for a sick day.
     */
    @Test
    void toString_null() {
        Vacation vacation = new Vacation();
        assertEquals("Vacation{id=null, date=null, from=null, to=null, creationDate=null, status=null, type=null}", vacation.toString());
    }
}
