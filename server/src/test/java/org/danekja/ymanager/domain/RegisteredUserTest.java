package org.danekja.ymanager.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class tests methods of the {@code RegisteredUser} class.
 * @see RegisteredUser
 */
class RegisteredUserTest {

    /**
     * The empty instance of the {@code RegisteredUser}.
     */
    private RegisteredUser user;

    /**
     * Prepares the instance of the {@code RegisteredUser}.
     */
    @BeforeEach
    void setUp() {
        this.user = new RegisteredUser();
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

}
