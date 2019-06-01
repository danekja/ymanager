package cz.zcu.yamanager.domain;

import cz.zcu.yamanager.dto.Status;
import cz.zcu.yamanager.dto.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
        this.user.setVacationCount(10);
        assertEquals(10, this.user.getVacationCount());
    }

    /**
     * Tests the method {@code setVacationCount} with zero which is a threshold value.
     */
    @Test
    void testSetVacationCountZero() {
        this.user.setVacationCount(0);
        assertEquals(0, this.user.getVacationCount());
    }

    /**
     * Tests the method {@code setVacationCount} with negative one which is a threshold value.
     */
    @Test
    void testSetVacationCountNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setVacationCount(-1));
    }

    /**
     * Tests the method {@code setVacationCount} with negative value.
     */
    @Test
    void testSetVacationCountNegative() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setVacationCount(-10));
    }

    /**
     * Tests the method {@code decreaseVacationCount} with common values where no problem should occur.
     */
    @Test
    void testDecreaseVacationCount() {
        this.user.setVacationCount(10);
        this.user.decreaseVacationCount(4);
        assertEquals(6, this.user.getVacationCount());
    }

    /**
     * Tests the method {@code decreaseVacationCount} with result equals to zero.
     */
    @Test
    void testDecreaseVacationCountZeroResult() {
        this.user.setVacationCount(10);
        this.user.decreaseVacationCount(10);
        assertEquals(0, this.user.getVacationCount());
    }

    /**
     * Tests the method {@code decreaseVacationCount} with result equals to -1.
     */
    @Test
    void testDecreaseVacationCountNegativeOneResult() {
        this.user.setVacationCount(10);
        assertThrows(IllegalArgumentException.class, () -> this.user.decreaseVacationCount(11));
    }

    /**
     * Tests the method {@code decreaseVacationCount} with result equals to negative number.
     */
    @Test
    void testDecreaseVacationCountNegativeResult() {
        this.user.setVacationCount(10);
        assertThrows(IllegalArgumentException.class, () -> this.user.decreaseVacationCount(50));
    }

    /**
     * Tests the method {@code decreaseVacationCount} with negative input.
     */
    @Test
    void testDecreaseVacationCountNegativeInput() {
        this.user.setVacationCount(10);
        this.user.decreaseVacationCount(-10);
        assertEquals(20, this.user.getVacationCount());
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
     * Tests the method {@code setTakenSickDayCount} with common values where no problem should occur.
     */
    @Test
    void testSetTakenSickDayCount() {
        this.user.setTakenSickDayCount(10);
        assertEquals(10, this.user.getTakenSickDayCount());
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with zero which is a threshold value.
     */
    @Test
    void testSetTakenSickDayCountZero() {
        this.user.setTakenSickDayCount(0);
        assertEquals(0, this.user.getTakenSickDayCount());
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with negative one which is a threshold value.
     */
    @Test
    void testSetTakenSickDayCountNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setTakenSickDayCount(-1));
    }

    /**
     * Tests the method {@code setTakenSickDayCount} with negative value.
     */
    @Test
    void testSetTakenSickDayCountNegative() {
        assertThrows(IllegalArgumentException.class, () -> this.user.setTakenSickDayCount(-10));
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
     * Tests the method {@code toString}.
     */
    @Test
    void testToString() {
        User user = new User(5, "Jan", "Novák", 0, 10, 5, LocalDateTime.of(2008,10,30,20,0), "tokenContent", "novak@email.com", "url", LocalDateTime.of(2010,6,31,12,5), UserRole.EMPLOYER, Status.ACCEPTED);
        assertEquals("User{id=5, firstName=Jan, lastName=Novák, vacationsCount=0, totalSickDaysCount=10, takenSickDaysCount=5, notification=2008-10-30T20:00, token=tokenContent, email=novak@email.com, photo=url, creationDate=2010-6-31T12:05 , role=EMPLOYER, status=ACCEPTED}", user.toString());
    }
}
