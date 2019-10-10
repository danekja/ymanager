package org.danekja.ymanager.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests methods of the {@code User} class.
 * @see User
 */
class UserTest {

    /**
     * The empty instance of the {@code User}.
     */
    private User user;

    /**
     * Prepares the instance of the {@code User}.
     */
    @BeforeEach
    void setUp() {
        this.user = new User();
        this.user.setVacationCount(0f);
        this.user.setTotalSickDayCount(0);
        this.user.setTakenSickDayCount(0);
    }

    /**
     * Tests the method {@code setFirstName} with common values where no problem should occur.
     */
    @Test
    void setFirstName_valid() {
        this.user.setFirstName("aaaaaa");
        assertEquals("aaaaaa", this.user.getFirstName());
    }

    /**
     * Tests the method {@code setFirstName} with the maximal length of a name.
     */
    @Test
    void setFirstName_maxLength_valid() {
        this.user.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", this.user.getFirstName());
    }

    /**
     * Tests the method {@code setFirstName} with name that exceeds maximal length which should throw an exception.
     */
    @Test
    void setFirstName_tooLong() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    /**
     * Tests the method {@code setFirstName} with null input which should throw an exception.
     */
    @Test
    void setFirstName_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setFirstName(null));
    }

    /**
     * Tests the method {@code setLastName} with common values where no problem should occur.
     */
    @Test
    void setLastName_valid() {
        this.user.setLastName("aaaaaa");
        assertEquals("aaaaaa", this.user.getLastName());
    }

    /**
     * Tests the method {@code setLastName} with the maximal length of a name.
     */
    @Test
    void setLastName_maxLength_valid() {
        this.user.setLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", this.user.getLastName());
    }

    /**
     * Tests the method {@code setLastName} with name that exceeds maximal length which should throw an exception.
     */
    @Test
    void setLastName_tooLong() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    /**
     * Tests the method {@code setLastName} with null input which should throw an exception.
     */
    @Test
    void setLastName_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setLastName(null));
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
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(20,0);
        this.user.addVacationCount(from, to);
        assertEquals(10f, this.user.getVacationCount().floatValue());
    }

    /**
     * Tests the method {@code addVacationCount} with wrong order of parameters which should throw an exception.
     */
    @Test
    void addVacationCount_timeInput_order() {
        LocalTime from = LocalTime.of(10,0);
        LocalTime to = LocalTime.of(20,0);
        assertThrows(IllegalArgumentException.class, () -> this.user.addVacationCount(to, from));
    }

    /**
     * Tests the method {@code addVacationCount} with null from parameter which should throw an exception.
     */
    @Test
    void addVacationCount_timeInput_nullFrom() {
        LocalTime to = LocalTime.of(20,0);
        assertThrows(IllegalArgumentException.class, () -> this.user.addVacationCount(null, to));
    }

    /**
     * Tests the method {@code addVacationCount} with null to parameter which should throw an exception.
     */
    @Test
    void addVacationCount_timeInput_nullTo() {
        LocalTime from = LocalTime.of(20,0);
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
        this.user.setNotification(LocalDateTime.of(2010,5,1,20,0));
        assertEquals(LocalDateTime.of(2010,5,1,20,0), this.user.getNotification());
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
     * Tests the method {@code setToken} with common values where no problem should occur.
     */
    @Test
    void setToken_valid() {
        this.user.setToken("aaaaa");
        assertEquals("aaaaa", this.user.getToken());
    }

    /**
     * Tests the method {@code setToken} with null value.
     */
    @Test
    void setToken_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setToken(null));
    }

    /**
     * Tests the method {@code setEmail} with common values where no problem should occur.
     */
    @Test
    void setEmail_valid() {
        this.user.setEmail("aaaaaa");
        assertEquals("aaaaaa", this.user.getEmail());
    }

    /**
     * Tests the method {@code setEmail} with the maximal length of an email address.
     */
    @Test
    void setEmail_maxLength() {
        this.user.setEmail("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", this.user.getEmail());
    }

    /**
     * Tests the method {@code setEmail} with email address that exceeds maximal length which should throw an exception.
     */
    @Test
    void setEmail_tooLong() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setEmail("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    /**
     * Tests the method {@code setEmail} with null value.
     */
    @Test
    void setEmail_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setEmail(null));
    }

    /**
     * Tests the method {@code setPhoto} with common values where no problem should occur.
     */
    @Test
    void setPhoto_valid() {
        this.user.setPhoto("aaaaa");
        assertEquals("aaaaa", this.user.getPhoto());
    }

    /**
     * Tests the method {@code setPhoto} with null value.
     */
    @Test
    void setPhoto_nullInput_valid() {
        this.user.setPhoto(null);
        assertEquals("https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg", this.user.getPhoto());
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

    /**
     * Tests the method {@code takeVacation} with common values where no problem should occur.
     */
    @Test
    void takeVacation_valid() {
        this.user.setVacationCount(10f);
        this.user.takeVacation(LocalTime.of(15,0), LocalTime.of(20, 0));
        assertEquals(5, this.user.getVacationCount().intValue());
    }

    /**
     * Tests the method {@code takeVacation} when there is not enough vacations.
     */
    @Test
    void takeVacation_notEnough() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeVacation(LocalTime.of(15,0), LocalTime.of(20, 0)));
    }

    /**
     * Tests the method {@code takeVacation} with switched from and to parameters.
     */
    @Test
    void takeVacation_order() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeVacation( LocalTime.of(20,0), LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code takeVacation} with from argument null.
     */
    @Test
    void takeVacation_nullFrom() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeVacation(null, LocalTime.of(15,0)));
    }

    /**
     * Tests the method {@code takeVacation} with to argument null.
     */
    @Test
    void takeVacation_nullTo() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeVacation(LocalTime.of(15,0), null));
    }

    /**
     * Tests the method {@code takeVacation } with both arguments null.
     */
    @Test
    void takeVacation_nullBoth() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeVacation(null, null));
    }

    /**
     * Tests the method {@code takeVacation} with common values where no problem should occur.
     */
    @Test
    void takeSickDay_valid() {
        this.user.setTotalSickDayCount(5);
        this.user.takeSickDay();
        assertEquals(1, this.user.getTakenSickDayCount().intValue());
    }

    /**
     * Tests the method {@code takeVacation} when there is not enough available sick days.
     */
    @Test
    void takeSickDay_notEnough() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeSickDay());
    }

    /**
     * Tests the method {@code toString}.
     */
    @Test
    void testToString() {
        User user = new User();
        user.setId(5L);
        user.setFirstName("Jan");
        user.setLastName("Nov\u00E1k");
        user.setVacationCount(0f);
        user.setTotalSickDayCount(10);
        user.setTakenSickDayCount(5);
        user.setNotification(LocalDateTime.of(2008,10,30,20,0));
        user.setToken("tokenContent");
        user.setEmail("novak@email.com");
        user.setPhoto("url");
        user.setCreationDate(LocalDateTime.of(2010,7,31,12,5));
        user.setRole(UserRole.EMPLOYER);
        user.setStatus(Status.ACCEPTED);
        assertEquals("User{id=5, firstName='Jan', lastName='Nov\u00E1k', vacationCount=0.0, totalSickDayCount=10, takenSickDayCount=5, notification=2008-10-30T20:00, token='tokenContent', email='novak@email.com', photo='url', creationDate=2010-07-31T12:05, role=EMPLOYER, status=ACCEPTED}", user.toString());
    }
}
