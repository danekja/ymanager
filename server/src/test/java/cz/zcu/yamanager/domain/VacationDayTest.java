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
     * Tests the method {@code setDate} with common values where no problem should occur.
     */
    @Test
    void setDate_valid() {
        LocalDate date = LocalDate.of(2000,10,12);
        this.vacationDay.setDate(date);
        assertEquals(date, this.vacationDay.getDate());
    }

    /**
     * Tests the method {@code setDate} with null input value.
     */
    @Test
    void setDate_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setDate(null));
    }

    /**
     * Tests the method {@code setFrom} with a null input, null ending time and null vacation type.
     */
    @Test
    void setFrom_nullFrom_nullTo_nullType_valid() {
        this.vacationDay.setFrom(null);
        assertNull(this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with a null input, ending time and null vacation type.
     */
    @Test
    void setFrom_nullFrom_to_nullType_valid() {
        this.vacationDay.setTo(LocalTime.of(20, 0));
        this.vacationDay.setFrom(null);
        assertNull(this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with an input, null ending time and null vacation type.
     */
    @Test
    void setFrom_from_nullTo_nullType_valid() {
        LocalTime from = LocalTime.of(10,0);
        this.vacationDay.setFrom(from);
        assertEquals(from, this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with an input, ending time and null vacation type.
     */
    @Test
    void setFrom_from_to_nullType_valid() {
        LocalTime to = LocalTime.of(20, 0);
        this.vacationDay.setTo(to);
        LocalTime from = LocalTime.of(10,0);
        this.vacationDay.setFrom(from);
        assertEquals(from, this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with same input and ending time and null vacation type.
     */
    @Test
    void setFrom_from_to_nullType_same() {
        LocalTime to = LocalTime.of(10, 0);
        this.vacationDay.setTo(to);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setFrom(to));
    }

    /**
     * Tests the method {@code setFrom} with same input and ending time and null vacation type.
     */
    @Test
    void setFrom_from_to_nullType_wrongOrder() {
        LocalTime to = LocalTime.of(10, 0);
        this.vacationDay.setTo(to);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setFrom(LocalTime.of(20,0)));
    }

    /**
     * Tests the method {@code setFrom} with a null input, null ending time and sick day vacation type.
     */
    @Test
    void setFrom_nullFrom_nullTo_sickDay_valid() {
        this.vacationDay.setType(VacationType.SICK_DAY);
        this.vacationDay.setFrom(null);
        assertNull(this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with an input and sick day vacation type.
     */
    @Test
    void setFrom_from_sickDay() {
        this.vacationDay.setType(VacationType.SICK_DAY);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setFrom(LocalTime.of(20,0)));
    }

    /**
     * Tests the method {@code setFrom} with a null input, null ending time and vacation vacation type.
     */
    @Test
    void setFrom_nullFrom_vacation_valid() {
        this.vacationDay.setFrom(null);
        assertNull(this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with an input, null ending time and vacation vacation type.
     */
    @Test
    void setFrom_from_nullTo_vacation_valid() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(10,0);
        this.vacationDay.setFrom(from);
        assertEquals(from, this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with an input, ending time and vacation vacation type.
     */
    @Test
    void setFrom_from_to_vacation_valid() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime to = LocalTime.of(20, 0);
        this.vacationDay.setTo(to);
        LocalTime from = LocalTime.of(10,0);
        this.vacationDay.setFrom(from);
        assertEquals(from, this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setFrom} with same input and ending time and vacation vacation type.
     */
    @Test
    void setFrom_from_to_vacation_same() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime to = LocalTime.of(10, 0);
        this.vacationDay.setTo(to);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setFrom(LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setFrom} with same input and ending time and vacation vacation type.
     */
    @Test
    void setFrom_from_to_vacation_wrongOrder() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime to = LocalTime.of(10, 0);
        this.vacationDay.setTo(to);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setFrom(LocalTime.of(20,0)));
    }

    /**
     * Tests the method {@code setTo} with a null input, null starting time and null vacation type.
     */
    @Test
    void setTo_nullTo_nullFrom_nullType_valid() {
        this.vacationDay.setTo(null);
        assertNull(this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTo} with a null input, starting time and null vacation type.
     */
    @Test
    void setTo_nullTo_from_nullType_valid() {
        this.vacationDay.setFrom(LocalTime.of(20, 0));
        this.vacationDay.setTo(null);
        assertNull(this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTo} with an input, null starting time and null vacation type.
     */
    @Test
    void setTo_to_nullFrom_nullType_valid() {
        LocalTime to = LocalTime.of(10,0);
        this.vacationDay.setTo(to);
        assertEquals(to, this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTo} with an input, starting time and null vacation type.
     */
    @Test
    void setTo_to_from_nullType_valid() {
        LocalTime from = LocalTime.of(10, 0);
        this.vacationDay.setFrom(from);
        LocalTime to = LocalTime.of(20,0);
        this.vacationDay.setTo(to);
        assertEquals(to, this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTo} with same input and starting time and null vacation type.
     */
    @Test
    void setTo_to_from_nullType_same() {
        LocalTime from = LocalTime.of(10, 0);
        this.vacationDay.setFrom(from);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTo(LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setTo} with same input and starting time and null vacation type.
     */
    @Test
    void setTo_to_from_nullType_wrongOrder() {
        LocalTime from = LocalTime.of(20, 0);
        this.vacationDay.setFrom(from);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTo(LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setTo} with a null input, null starting time and sick day vacation type.
     */
    @Test
    void setTo_nullTo_nullFrom_sickDay_valid() {
        this.vacationDay.setType(VacationType.SICK_DAY);
        this.vacationDay.setTo(null);
        assertNull(this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTo} with an input and sick day vacation type.
     */
    @Test
    void setTo_to_sickDay() {
        this.vacationDay.setType(VacationType.SICK_DAY);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTo(LocalTime.of(20,0)));
    }

    /**
     * Tests the method {@code setTo} with a null input, null starting time and vacation vacation type.
     */
    @Test
    void setTo_nullTo_vacation_valid() {
        this.vacationDay.setTo(null);
        assertNull(this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTo} with an input, null starting time and vacation vacation type.
     */
    @Test
    void setTo_to_nullFrom_vacation_valid() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime to = LocalTime.of(10,0);
        this.vacationDay.setTo(to);
        assertEquals(to, this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTo} with an input, starting time and vacation vacation type.
     */
    @Test
    void setTo_to_from_vacation_valid() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(10, 0);
        this.vacationDay.setFrom(from);
        LocalTime to = LocalTime.of(20,0);
        this.vacationDay.setTo(to);
        assertEquals(to, this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTo} with same input and starting time and vacation vacation type.
     */
    @Test
    void setTo_to_from_vacation_same() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(10, 0);
        this.vacationDay.setFrom(from);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTo(from));
    }

    /**
     * Tests the method {@code setTo} with same input and starting time and vacation vacation type.
     */
    @Test
    void setTo_to_from_vacation_wrongOrder() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(20, 0);
        this.vacationDay.setFrom(from);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTo(LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setTime} with common values where no problem should occur.
     */
    @Test
    void setTime_nullType_valid() {
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
    void setTime_nullType_same() {
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(from, to));
    }

    /**
     * Tests the method {@code setTime} with later start.
     */
    @Test
    void setTime_nullType_wrongOrder() {
        LocalTime from = LocalTime.of(20,0);
        LocalTime to = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(from, to));
    }

    /**
     * Tests the method {@code setTime} with null {@code from} input.
     */
    @Test
    void setTime_nullFrom_nullType_valid() {
        LocalTime to = LocalTime.of(10,0);
        this.vacationDay.setTime(null, to);
        assertNull(this.vacationDay.getFrom());
        assertEquals(to, this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTime} with null {@code to} input.
     */
    @Test
    void setTime_nullTo_nullType_valid() {
        LocalTime from = LocalTime.of(20,0);
        this.vacationDay.setTime(from, null);
        assertNull(this.vacationDay.getTo());
        assertEquals(from, this.vacationDay.getFrom());
    }

    /**
     * Tests the method {@code setTime} with null input.
     */
    @Test
    void setTime_nullFrom_nullTo_nullType_valid() {
        this.vacationDay.setTime(null, null);
        assertNull(this.vacationDay.getFrom());
        assertNull(this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTime} with null input and sick day vacation type.
     */
    @Test
    void setTime_nullFrom_nullTo_sickDay_valid() {
        this.vacationDay.setType(VacationType.SICK_DAY);
        this.vacationDay.setTime(null, null);
        assertNull(this.vacationDay.getFrom());
        assertNull(this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTime} with from, null to and sick day vacation type.
     */
    @Test
    void setTime_from_nullTo_sickDay() {
        this.vacationDay.setType(VacationType.SICK_DAY);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(LocalTime.of(10,0), null));
    }

    /**
     * Tests the method {@code setTime} with null from, to and sick day vacation type.
     */
    @Test
    void setTime_nullFrom_to_sickDay() {
        this.vacationDay.setType(VacationType.SICK_DAY);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(null, LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setTime} with from, to and sick day vacation type.
     */
    @Test
    void setTime_from_to_sickDay() {
        this.vacationDay.setType(VacationType.SICK_DAY);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(LocalTime.of(10,0), LocalTime.of(20,0)));
    }

    /**
     * Tests the method {@code setTime} with null input and vacation vacation type.
     */
    @Test
    void setTime_nullFrom_nullTo_vacation() {
        this.vacationDay.setType(VacationType.VACATION);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(null, null));
    }

    /**
     * Tests the method {@code setTime} with from, null to and vacation vacation type.
     */
    @Test
    void setTime_from_nullTo_vacation() {
        this.vacationDay.setType(VacationType.VACATION);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(LocalTime.of(10,0), null));
    }

    /**
     * Tests the method {@code setTime} with null from, to and vacation vacation type.
     */
    @Test
    void setTime_nullFrom_to_vacation() {
        this.vacationDay.setType(VacationType.VACATION);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(null, LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code setTime} with from, to and vacation vacation type.
     */
    @Test
    void setTime_from_to_vacation_valid() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(20,0);
        this.vacationDay.setTime(from, to);
        assertEquals(from, this.vacationDay.getFrom());
        assertEquals(to, this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setTime} with same from and to and vacation vacation type.
     */
    @Test
    void setTime_from_to_vacation_same() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(from, to));
    }

    /**
     * Tests the method {@code setTime} with later from than to and vacation vacation type.
     */
    @Test
    void setTime_from_to_vacation_wrongOrder() {
        this.vacationDay.setType(VacationType.VACATION);
        LocalTime from = LocalTime.of(20,0);
        LocalTime to = LocalTime.of(10,0);
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setTime(from, to));
    }

    /**
     * Tests the method {@code setStatus} with common values where no problem should occur.
     */
    @Test
    void setStatus_valid() {
        this.vacationDay.setStatus(Status.ACCEPTED);
        assertEquals(Status.ACCEPTED, this.vacationDay.getStatus());
    }

    /**
     * Tests the method {@code setStatus} with null value.
     */
    @Test
    void setStatus_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setStatus(null));
    }

    /**
     * Tests the method {@code setType} with with vacation input.
     */
    @Test
    void setType_valid() {
        this.vacationDay.setType(VacationType.VACATION);
        assertEquals(VacationType.VACATION, this.vacationDay.getType());
    }

    /**
     * Tests the method {@code setType} with sick day input.
     */
    @Test
    void setType_sickDay_valid() {
        this.vacationDay.setType(VacationType.SICK_DAY);
        assertEquals(VacationType.SICK_DAY, this.vacationDay.getType());
        assertNull(this.vacationDay.getFrom());
        assertNull(this.vacationDay.getTo());
    }

    /**
     * Tests the method {@code setType} with null value.
     */
    @Test
    void setType_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.vacationDay.setType(null));
    }

    /**
     * Tests the method {@code toString}.
     */
    @Test
    void toString_valid() {
        VacationDay vacationDay = new VacationDay();
        vacationDay.setId(5L);
        vacationDay.setDate(LocalDate.of(2010,1,9));
        vacationDay.setFrom(LocalTime.of(12,15));
        vacationDay.setTo(LocalTime.of(22,30));
        vacationDay.setCreationDate(LocalDateTime.of(2008,10,30,20,0));
        vacationDay.setStatus(Status.ACCEPTED);
        vacationDay.setType(VacationType.VACATION);
        assertEquals("VacationDay{id=5, date=2010-01-09, from=12:15, to=22:30, creationDate=2008-10-30T20:00, status=ACCEPTED, type=VACATION}", vacationDay.toString());
    }

    /**
     * Tests the method {@code toString} for a sick day.
     */
    @Test
    void toString_null() {
        VacationDay vacationDay = new VacationDay();
        assertEquals("VacationDay{id=null, date=null, from=null, to=null, creationDate=null, status=null, type=null}", vacationDay.toString());
    }
}
