package cz.zcu.yamanager.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class tests methods of the {@code DefaultSettings} class.
 * @see DefaultSettings
 */
class DefaultSettingsTest {

    /**
     * The empty instance of the {@code DefaultSettings}.
     */
    private DefaultSettings defaultSettings;

    /**
     * Prepares the instance of the {@code DefaultSettings}.
     */
    @BeforeEach
    void setUp() {
        this.defaultSettings = new DefaultSettings();
    }

    /**
     * Tests the method {@code setSickDayCount} with common values where no problem should occur.
     */
    @Test
    void testSetSickDaysCount() {
        this.defaultSettings.setSickDayCount(10);
        assertEquals(10, this.defaultSettings.getSickDayCount());
    }

    /**
     * Tests the method {@code setSickDayCount} with zero which is a threshold value.
     */
    @Test
    void testSetSickDaysCountZero() {
        this.defaultSettings.setSickDayCount(0);
        assertEquals(0, this.defaultSettings.getSickDayCount());
    }

    /**
     * Tests the method {@code setSickDayCount} with negative one which is a threshold value.
     */
    @Test
    void testSetSickDaysCountNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> this.defaultSettings.setSickDayCount(-1));
    }

    /**
     * Tests the method {@code setSickDayCount} with negative value.
     */
    @Test
    void testSetSickDaysCountNegative() {
        assertThrows(IllegalArgumentException.class, () -> this.defaultSettings.setSickDayCount(-10));
    }

    /**
     * Tests the method {@code toString}.
     */
    @Test
    void testToString() {
        DefaultSettings defaultSettings = new DefaultSettings(5, 1, LocalDateTime.of(2008,10,30,20,0));
        assertEquals("DefaultSettings{id=5, sickDayCount=1, notification=2008-10-30T20:00}", defaultSettings.toString());
    }
}
