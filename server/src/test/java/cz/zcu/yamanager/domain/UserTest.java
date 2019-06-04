package cz.zcu.yamanager.domain;

import cz.zcu.yamanager.dto.Status;
import cz.zcu.yamanager.dto.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    }

    /**
     * Tests the method {@code setFirstName} with common values where no problem should occur.
     */
    @Test
    void testSetFirstName() {
        this.user.setFirstName("aaaaaa");
        assertEquals("aaaaaa", this.user.getFirstName());
    }

    /**
     * Tests the method {@code setFirstName} with the maximal length of a name.
     */
    @Test
    void testSetFirstNameMax() {
        this.user.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", this.user.getFirstName());
    }

    /**
     * Tests the method {@code setFirstName} with name that exceeds maximal length which should throw an exception.
     */
    @Test
    void testSetFirstNameException() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    /**
     * Tests the method {@code setLastName} with common values where no problem should occur.
     */
    @Test
    void testSetLastName() {
        this.user.setLastName("aaaaaa");
        assertEquals("aaaaaa", this.user.getLastName());
    }

    /**
     * Tests the method {@code setLastName} with the maximal length of a name.
     */
    @Test
    void testSetLastNameMax() {
        this.user.setLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", this.user.getLastName());
    }

    /**
     * Tests the method {@code setLastName} with name that exceeds maximal length which should throw an exception.
     */
    @Test
    void testSetLastNameException() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    /**
     * Tests the method {@code setVacationCount} with common values where no problem should occur.
     */
    @Test
    void testSetVacationCount() {
        this.user.setVacationCount(10f);
        assertEquals(10, this.user.getVacationCount());
    }

    /**
     * Tests the method {@code setVacationCount} with zero which is a threshold value.
     */
    @Test
    void testSetVacationCountZero() {
        this.user.setVacationCount(0f);
        assertEquals(0, this.user.getVacationCount());
    }

    /**
     * Tests the method {@code setVacationCount} with negative one which is a threshold value.
     */
    @Test
    void testSetVacationCountNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setVacationCount(-1f));
    }

    /**
     * Tests the method {@code setVacationCount} with negative value.
     */
    @Test
    void testSetVacationCountNegative() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setVacationCount(-10f));
    }

    /**
     * Tests the method {@code setVacationCount} with null value.
     */
    @Test
    void testSetVacationCountObjectNull() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setVacationCount(null));
    }

    /**
     * Tests the method {@code setTotalSickDayCount} with common values where no problem should occur.
     */
    @Test
    void testSetTotalSickDayCount() {
        this.user.setTotalSickDayCount(10);
        assertEquals(10, this.user.getTotalSickDayCount());
    }

    /**
     * Tests the method {@code setTotalSickDayCount} with zero which is a threshold value.
     */
    @Test
    void testSetTotalSickDayCountZero() {
        this.user.setTotalSickDayCount(0);
        assertEquals(0, this.user.getTotalSickDayCount());
    }

    /**
     * Tests the method {@code setTotalSickDayCount} with negative one which is a threshold value.
     */
    @Test
    void testSetTotalSickDayCountNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setTotalSickDayCount(-1));
    }

    /**
     * Tests the method {@code setTotalSickDayCount} with negative value.
     */
    @Test
    void testSetTotalSickDayCountNegative() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setTotalSickDayCount(-10));
    }

    /**
     * Tests the method {@code setTotalSickDayCount} with null value.
     */
    @Test
    void testSetTotalSickDayCountObjectNull() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setTotalSickDayCount(null));
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with common values where no problem should occur.
     */
    @Test
    void testSetTakenSickDayCount() {
        this.user.setTotalSickDayCount(50);
        this.user.setTakenSickDayCount(10);
        assertEquals(10, this.user.getTakenSickDayCount());
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with zero which is a threshold value.
     */
    @Test
    void testSetTakenSickDayCountZero() {
        this.user.setTotalSickDayCount(50);
        this.user.setTakenSickDayCount(0);
        assertEquals(0, this.user.getTakenSickDayCount());
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with negative one which is a threshold value.
     */
    @Test
    void testSetTakenSickDayCountNegativeOne() {
        this.user.setTotalSickDayCount(10);
        assertThrows(IllegalArgumentException.class, () -> this.user.setTakenSickDayCount(-1));
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with negative value.
     */
    @Test
    void testSetTakenSickDayCountNegative() {
        this.user.setTotalSickDayCount(10);
        assertThrows(IllegalArgumentException.class, () -> this.user.setTakenSickDayCount(-10));
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with a value that is greater than total sick days.
     */
    @Test
    void testSetTakenSickDayCountOver() {
        this.user.setTotalSickDayCount(10);
        assertThrows(IllegalArgumentException.class, () -> this.user.setTakenSickDayCount(20));
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with a value that is equals the total sick days.
     */
    @Test
    void testSetTakenSickDayCountSame() {
        this.user.setTotalSickDayCount(10);
        this.user.setTakenSickDayCount(10);
        assertEquals(10, this.user.getTakenSickDayCount());
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with null value.
     */
    @Test
    void testSetTakenSickDayCountObjectNull() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setTakenSickDayCount(null));
    }

    /**
     * Tests the method {@code setNotification} with common values where no problem should occur.
     */
    @Test
    void testSetNotification() {
        this.user.setNotification(LocalDateTime.of(2010,5,1,20,0));
        assertEquals(LocalDateTime.of(2010,5,1,20,0), this.user.getNotification());
    }

    /**
     * Tests the method {@code setNotification} with null value.
     */
    @Test
    void testSetNotificationNull() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setNotification(null));
    }

    /**
     * Tests the method {@code setEmail} with common values where no problem should occur.
     */
    @Test
    void testSetEmail() {
        this.user.setEmail("aaaaaa");
        assertEquals("aaaaaa", this.user.getEmail());
    }

    /**
     * Tests the method {@code setEmail} with the maximal length of an email address.
     */
    @Test
    void testSetEmailMax() {
        this.user.setEmail("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", this.user.getEmail());
    }

    /**
     * Tests the method {@code setEmail} with email address that exceeds maximal length which should throw an exception.
     */
    @Test
    void testSetEmailException() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setEmail("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    /**
     * Tests the method {@code setRole} with common values where no problem should occur.
     */
    @Test
    void testSetRole() {
        this.user.setRole(UserRole.EMPLOYER);
        assertEquals(UserRole.EMPLOYER, this.user.getRole());
    }

    /**
     * Tests the method {@code setRole} with null value.
     */
    @Test
    void testSetRoleNull() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setRole(null));
    }

    /**
     * Tests the method {@code setStatus} with common values where no problem should occur.
     */
    @Test
    void testSetStatus() {
        this.user.setStatus(Status.ACCEPTED);
        assertEquals(Status.ACCEPTED, this.user.getStatus());
    }

    /**
     * Tests the method {@code setStatus} with null value.
     */
    @Test
    void testSetStatusNull() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setStatus(null));
    }

    /**
     * Tests the method {@code takeVacation} with common values where no problem should occur.
     */
    @Test
    void testTakeVacation() {
        this.user.setVacationCount(10f);
        this.user.takeVacation(LocalTime.of(15,0), LocalTime.of(20, 0));
        assertEquals(5, this.user.getVacationCount());
    }

    /**
     * Tests the method {@code takeVacation} when there is not enough vacations.
     */
    @Test
    void testTakeVacationException() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeVacation(LocalTime.of(15,0), LocalTime.of(20, 0)));
    }

    /**
     * Tests the method {@code takeVacation} with switched from and to parameters.
     */
    @Test
    void testTakeVacationOrder() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeVacation( LocalTime.of(20,0), LocalTime.of(10,0)));
    }

    /**
     * Tests the method {@code takeVacation} with from argument null.
     */
    @Test
    void testTakeVacationNullFrom() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeVacation(null, LocalTime.of(15,0)));
    }

    /**
     * Tests the method {@code takeVacation} with to argument null.
     */
    @Test
    void testTakeVacationNullTo() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeVacation(LocalTime.of(15,0), null));
    }

    /**
     * Tests the method {@code takeVacation } with both arguments null.
     */
    @Test
    void testTakeVacationNullBoth() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeVacation(null, null));
    }

    /**
     * Tests the method {@code takeVacation} with common values where no problem should occur.
     */
    @Test
    void testTakeSickDay() {
        this.user.setTotalSickDayCount(5);
        this.user.takeSickDay();
        assertEquals(1, this.user.getTakenSickDayCount());
    }

    /**
     * Tests the method {@code takeVacation} when there is not enough available sick days.
     */
    @Test
    void testTakeSickDayException() {
        assertThrows(IllegalArgumentException.class, () -> this.user.takeSickDay());
    }

    /**
     * Tests the method {@code toString}.
     */
    @Test
    void testToString() {
        User user = new User(5, "Jan", "Nov\u00E1k",0f, 10, 5, LocalDateTime.of(2008,10,30,20,0), "tokenContent", "novak@email.com", "url", LocalDateTime.of(2010,7,31,12,5), UserRole.EMPLOYER, Status.ACCEPTED);
        assertEquals("User{id=5, firstName='Jan', lastName='Nov\u00E1k', vacationCount=0.0, totalSickDayCount=10, takenSickDayCount=5, notification=2008-10-30T20:00, token='tokenContent', email='novak@email.com', photo='url', creationDate=2010-07-31T12:05, role=EMPLOYER, status=ACCEPTED}", user.toString());
    }
}
