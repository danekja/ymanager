package cz.zcu.yamanager.domain;

import cz.zcu.yamanager.dto.Status;
import cz.zcu.yamanager.dto.VacationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests methods of the {@code VacationDay} class.
 * @see VacationDay
 */
class VacationDayTest {
    /**
     * The empty instance of the {@code VacationDay}.
     */
    private VacationDay vacationDay;

    /**
     * Prepares the instance of the {@code VacationDay}.
     */
    @BeforeEach
    void setUp() {
        this.vacationDay = new VacationDay();
    }

    /**
     * Tests the method {@code setTime} with common values where no problem should occur.
     */
    @Test
    void testSetTime() {
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(20,0);
        this.vacationDay.setTime(from, to);
        assertEquals(from, this.vacationDay.getFrom());
        assertEquals(to, this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTime} with the same times.
     */
    @Test
    void testSetTimeSame() {
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(from, to));
    }

    /**
     * Tests the method {@code setTime} with later start.
     */
    @Test
    void testSetTimeLater() {
        LocalTime from = LocalTime.of(20,0);
        LocalTime to = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(from, to));
    }

    /**
     * Tests the method {@code setTime} with null {@code from} input.
     */
    @Test
    void testSetTimeNullFrom() {
        LocalTime to = LocalTime.of(10,0);
        this.vacationDay.setTime(null, to);
        assertNull(this.vacationDay.getFrom());
        assertEquals(to, this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTime} with null {@code to} input.
     */
    @Test
    void testSetTimeNullTo() {
        LocalTime from = LocalTime.of(20,0);
        this.vacationDay.setTime(from, null);
        assertNull(this.vacationDay.getTo());
        assertEquals(from, this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setTime} with null input.
     */
    @Test
    void setSetTimeNull() {
        this.vacationDay.setTime(null, null);
        assertNull(this.vacationDay.getFrom());
        assertNull(this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setFrom} with common values where no problem should occur.
     */
    @Test
    void testSetFrom() {
        LocalTime to = LocalTime.of(20, 0);
        this.vacationDay.setTo(to);
        LocalTime from = LocalTime.of(10,0);
        this.vacationDay.setFrom(from);
        assertEquals(from, this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setFrom} when ending time is null.
     */
    @Test
    void testSetFromNull() {
        LocalTime from = LocalTime.of(10,0);
        this.vacationDay.setFrom(from);
        assertEquals(from, this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with the same times.
     */
    @Test
    void testSetFromSame() {
        LocalTime to = LocalTime.of(10, 0);
        this.vacationDay.setTo(to);
        LocalTime from = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setFrom(from));
    }

    /**
     * Tests the method {@code setFrom} with later start.
     */
    @Test
    void testSetFromLater() {
        LocalTime to = LocalTime.of(10, 0);
        this.vacationDay.setTo(to);
        LocalTime from = LocalTime.of(20,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setFrom(from));
    }

    /**
     * Tests the method {@code setFrom} with a null input.
     */
    @Test
    void testSetFromNullInput() {
        this.vacationDay.setFrom(null);
        assertNull(this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setTo} with common values where no problem should occur.
     */
    @Test
    void testSetTo() {
        LocalTime from = LocalTime.of(10,0);
        this.vacationDay.setFrom(from);
        LocalTime to = LocalTime.of(20, 0);
        this.vacationDay.setTo(to);
        assertEquals(to, this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTo} when starting time is null.
     */
    @Test
    void testSetToNull() {
        LocalTime to = LocalTime.of(10,0);
        this.vacationDay.setTo(to);
        assertEquals(to, this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTo} with the same times.
     */
    @Test
    void testSetToSame() {
        LocalTime from = LocalTime.of(10,0);
        this.vacationDay.setFrom(from);
        LocalTime to = LocalTime.of(10, 0);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTo(to));
    }

    /**
     * Tests the method {@code setTo} with earlier end.
     */
    @Test
    void testSetToEarlier() {
        LocalTime from = LocalTime.of(20,0);
        this.vacationDay.setFrom(from);
        LocalTime to = LocalTime.of(10, 0);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTo(to));
    }

    /**
     * Tests the method {@code setTo} with a null input.
     */
    @Test
    void testSetToNullInput() {
        this.vacationDay.setTo(null);
        assertNull(this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code toString} for a vacation.
     */
    @Test
    void testToStringVacation() {
        VacationDay vacationDay = new VacationDay(5, LocalDate.of(2010,1,9), LocalTime.of(12,15), LocalTime.of(22,30), LocalDateTime.of(2008,10,30,20,0), Status.ACCEPTED);
        assertEquals("VacationDay{id=5, date=2010-01-09, from=12:15, to=22:30, creationDate=2008-10-30T20:00, status=ACCEPTED, type=VACATION}", vacationDay.toString());
    }

    /**
     * Tests the method {@code toString} for a sick day.
     */
    @Test
    void testToStringSickDay() {
        VacationDay vacationDay = new VacationDay(5, LocalDate.of(2010,1,9), LocalDateTime.of(2008,10,30,20,0), Status.ACCEPTED);
        assertEquals("VacationDay{id=5, date=2010-01-09, from=null, to=null, creationDate=2008-10-30T20:00, status=ACCEPTED, type=SICKDAY}", vacationDay.toString());
    }
}
