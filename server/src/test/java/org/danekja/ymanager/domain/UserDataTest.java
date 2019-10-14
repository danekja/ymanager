package org.danekja.ymanager.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataTest {

    private UserData user;

    /**
     * Prepares the instance of the {@code RegisteredUser}.
     */
    @BeforeEach
    void setUp() {
        this.user = new UserData();
        this.user.setVacationCount(0f);
        this.user.setTotalSickDayCount(0);
        this.user.setTakenSickDayCount(0);
    }

    /**
     * Tests the method {@code setVacationCount} with common values where no problem should occur.
     */
    @Test
    void setVacationCount_valid() {
        this.user.setVacationCount(10f);
        assertEquals(10f, this.user.getVacationCount().floatValue());
    }

    /**
     * Tests the method {@code setVacationCount} with zero which is a threshold value.
     */
    @Test
    void setVacationCount_zeroInput_valid() {
        this.user.setVacationCount(0f);
        assertEquals(0f, this.user.getVacationCount().floatValue());
    }

    /**
     * Tests the method {@code setVacationCount} with negative one which is a threshold value.
     */
    @Test
    void setVacationCount_negativeOneInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setVacationCount(-1f));
    }

    /**
     * Tests the method {@code setVacationCount} with negative value.
     */
    @Test
    void setVacationCount_negativeInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setVacationCount(-10f));
    }

    /**
     * Tests the method {@code setVacationCount} with null value which should throw an exception.
     */
    @Test
    void setVacationCount_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setVacationCount(null));
    }

    /**
     * Tests the method {@code addVacationCount} with common values where no problem should occur.
     */
    @Test
    void addVacationCount_floatInput_valid() {
        this.user.addVacationCount(10f);
        assertEquals(10f, this.user.getVacationCount().floatValue());
    }

    /**
     * Tests the method {@code addVacationCount} with common values where no problem should occur.
     */
    @Test
    void addVacationCount_addFloatInput_valid() {
        this.user.setVacationCount(2f);
        this.user.addVacationCount(10f);
        assertEquals(12f, this.user.getVacationCount().floatValue());
    }

    /**
     * Tests the method {@code addVacationCount} with common values where no problem should occur.
     */
    @Test
    void addVacationCount_subtractFloatInput_valid() {
        this.user.setVacationCount(20f);
        this.user.addVacationCount(-10f);
        assertEquals(10f, this.user.getVacationCount().floatValue());
    }

    /**
     * Tests the method {@code addVacationCount} with negative result of the operation which should throw an exception.
     */
    @Test
    void addVacationCount_floatInput_negativeResult() {
        assertThrows(IllegalArgumentException.class, () -> this.user.addVacationCount(-10f));
    }

    /**
     * Tests the method {@code addVacationCount} with null value which should throw an exception.
     */
    @Test
    void addVacationCount_nullFloatInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.addVacationCount(null));
    }

    /**
     * Tests the method {@code addVacationCount} with common values where no problem should occur.
     */
    @Test
    void addVacationCount_timeInput_valid() {
        LocalTime from = LocalTime.of(10, 0);
        LocalTime to = LocalTime.of(20, 0);
        this.user.addVacationCount(from, to);
        assertEquals(10f, this.user.getVacationCount().floatValue());
    }

    /**
     * Tests the method {@code addVacationCount} with wrong order of parameters which should throw an exception.
     */
    @Test
    void addVacationCount_timeInput_order() {
        LocalTime from = LocalTime.of(10, 0);
        LocalTime to = LocalTime.of(20, 0);
        assertThrows(IllegalArgumentException.class, () -> this.user.addVacationCount(to, from));
    }

    /**
     * Tests the method {@code addVacationCount} with null from parameter which should throw an exception.
     */
    @Test
    void addVacationCount_timeInput_nullFrom() {
        LocalTime to = LocalTime.of(20, 0);
        assertThrows(IllegalArgumentException.class, () -> this.user.addVacationCount(null, to));
    }

    /**
     * Tests the method {@code addVacationCount} with null to parameter which should throw an exception.
     */
    @Test
    void addVacationCount_timeInput_nullTo() {
        LocalTime from = LocalTime.of(20, 0);
        assertThrows(IllegalArgumentException.class, () -> this.user.addVacationCount(from, null));
    }

    /**
     * Tests the method {@code addVacationCount} with null parameters which should throw an exception.
     */
    @Test
    void addVacationCount_timeNullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.addVacationCount(null, null));
    }

    /**
     * Tests the method {@code setTotalSickDayCount} with common values where no problem should occur.
     */
    @Test
    void setTotalSickDayCount_valid() {
        this.user.setTotalSickDayCount(10);
        assertEquals(10, this.user.getTotalSickDayCount().intValue());
    }

    /**
     * Tests the method {@code setTotalSickDayCount} with zero which is a threshold value.
     */
    @Test
    void setTotalSickDayCount_zeroInput_valid() {
        this.user.setTotalSickDayCount(0);
        assertEquals(0, this.user.getTotalSickDayCount().intValue());
    }

    /**
     * Tests the method {@code setTotalSickDayCount} with negative one which is a threshold value.
     */
    @Test
    void setTotalSickDayCount_negativeOneInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setTotalSickDayCount(-1));
    }

    /**
     * Tests the method {@code setTotalSickDayCount} with negative value.
     */
    @Test
    void setTotalSickDayCount_negativeInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setTotalSickDayCount(-10));
    }

    /**
     * Tests the method {@code setTotalSickDayCount} with null value.
     */
    @Test
    void setTotalSickDayCount_nullInput_valid() {
        this.user.setTotalSickDayCount(null);
        assertNull(this.user.getTotalSickDayCount());
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with common values where no problem should occur.
     */
    @Test
    void setTakenSickDayCount_valid() {
        this.user.setTotalSickDayCount(20);
        this.user.setTakenSickDayCount(10);
        assertEquals(10, this.user.getTakenSickDayCount().intValue());
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with zero which is a threshold value.
     */
    @Test
    void setTakenSickDayCount_zeroInput_valid() {
        this.user.setTakenSickDayCount(0);
        assertEquals(0, this.user.getTakenSickDayCount().intValue());
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with negative one which is a threshold value.
     */
    @Test
    void setTakenSickDayCount_negativeOneInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setTakenSickDayCount(-1));
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with negative value.
     */
    @Test
    void setTakenSickDayCount_negativeInput() {
        this.user.setTotalSickDayCount(10);
        assertThrows(IllegalArgumentException.class, () -> this.user.setTakenSickDayCount(-10));
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with a value that is greater than total sick days.
     */
    @Test
    void setTakenSickDayCount_over() {
        this.user.setTotalSickDayCount(10);
        assertThrows(IllegalArgumentException.class, () -> this.user.setTakenSickDayCount(20));
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with a value that is equals the total sick days.
     */
    @Test
    void setTakenSickDayCount_same() {
        this.user.setTotalSickDayCount(10);
        this.user.setTakenSickDayCount(10);
        assertEquals(10, this.user.getTakenSickDayCount().intValue());
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with null value.
     */
    @Test
    void setTakenSickDayCount_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setTakenSickDayCount(null));
    }

    /**
     * Tests the method {@code addTakenSickDayCount} with common values where no problem should occur.
     */
    @Test
    void addTakenSickDayCount_valid() {
        this.user.setTotalSickDayCount(50);
        this.user.addTakenSickDayCount(10);
        assertEquals(10, this.user.getTakenSickDayCount().intValue());
    }

    /**
     * Tests the method {@code addTakenSickDayCount} with common values where no problem should occur.
     */
    @Test
    void addTakenSickDayCount_add_valid() {
        this.user.setTotalSickDayCount(50);
        this.user.setTakenSickDayCount(2);
        this.user.addTakenSickDayCount(10);
        assertEquals(12f, this.user.getTakenSickDayCount().intValue());
    }

    /**
     * Tests the method {@code addTakenSickDayCount} with common values where no problem should occur.
     */
    @Test
    void addTakenSickDayCount_subtract_valid() {
        this.user.setTotalSickDayCount(50);
        this.user.setTakenSickDayCount(20);
        this.user.addTakenSickDayCount(-10);
        assertEquals(10f, this.user.getTakenSickDayCount().intValue());
    }

    /**
     * Tests the method {@code addTakenSickDayCount} with negative result of the operation which should throw an exception.
     */
    @Test
    void addTakenSickDayCount_negativeResult() {
        assertThrows(IllegalArgumentException.class, () -> this.user.addTakenSickDayCount(-10));
    }

    /**
     * Tests the method {@code addTakenSickDayCount} with null value which should throw an exception.
     */
    @Test
    void addTakenSickDayCount_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.addTakenSickDayCount(null));
    }

    /**
     * Tests the method {@code addTakenSickDayCount} with a value that is greater than total sick days.
     */
    @Test
    void addTakenSickDayCount_over() {
        this.user.setTotalSickDayCount(10);
        assertThrows(IllegalArgumentException.class, () -> this.user.addTakenSickDayCount(20));
    }

    /**
     * Tests the method {@code addTakenSickDayCount} with a value that is equals the total sick days.
     */
    @Test
    void addTakenSickDayCount_same() {
        this.user.setTotalSickDayCount(10);
        this.user.addTakenSickDayCount(10);
        assertEquals(10, this.user.getTakenSickDayCount().intValue());
    }


    /**
     * Tests the method {@code setNotification} with common values where no problem should occur.
     */
    @Test
    void setNotification_valid() {
        this.user.setNotification(LocalDateTime.of(2010, 5, 1, 20, 0));
        assertEquals(LocalDateTime.of(2010, 5, 1, 20, 0), this.user.getNotification());
    }

    /**
     * Tests the method {@code setNotification} with null value.
     */
    @Test
    void setNotification_nullInput_valid() {
        this.user.setNotification(null);
        assertNull(this.user.getNotification());
    }

    /**
     * Tests the method {@code setRole} with common values where no problem should occur.
     */
    @Test
    void setRole_valid() {
        this.user.setRole(UserRole.EMPLOYER);
        assertEquals(UserRole.EMPLOYER, this.user.getRole());
    }

    /**
     * Tests the method {@code setRole} with null value.
     */
    @Test
    void setRole_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setRole(null));
    }

    /**
     * Tests the method {@code setStatus} with common values where no problem should occur.
     */
    @Test
    void setStatus_valid() {
        this.user.setStatus(Status.ACCEPTED);
        assertEquals(Status.ACCEPTED, this.user.getStatus());
    }

    /**
     * Tests the method {@code setStatus} with null value.
     */
    @Test
    void setStatus_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setStatus(null));
    }


}
