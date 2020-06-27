package org.danekja.ymanager.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class tests methods of the {@code DefaultSettings} class.
 * @see DefaultSettings
 */
class DefaultSettingsDTOTest {

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
    void setSickDaysCount_valid() {
        this.defaultSettings.setSickDayCount(10);
        assertEquals(10, this.defaultSettings.getSickDayCount());
    }

    /**
     * Tests the method {@code setSickDayCount} with zero which is a threshold value.
     */
    @Test
    void setSickDaysCount_zeroInput() {
        this.defaultSettings.setSickDayCount(0);
        assertEquals(0, this.defaultSettings.getSickDayCount());
    }

    /**
     * Tests the method {@code setSickDayCount} with negative one which is a threshold value.
     */
    @Test
    void setSickDaysCount_negativeOneInput() {
        assertThrows(IllegalArgumentException.class, () -> this.defaultSettings.setSickDayCount(-1));
    }

    /**
     * Tests the method {@code setSickDayCount} with negative value.
     */
    @Test
    void setSickDaysCount_negativeInput() {
        assertThrows(IllegalArgumentException.class, () -> this.defaultSettings.setSickDayCount(-10));
    }

    /**
     * Tests the method {@code setSickDayCount} with null value.
     */
    @Test
    void setSickDaysCount_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.defaultSettings.setSickDayCount(null));
    }

    /**
     * Tests the method {@code setNotification} with common values where no problem should occur.
     */
    @Test
    void setNotification_valid() {
        this.defaultSettings.setNotification(LocalDateTime.of(2010,5,1,20,0));
        assertEquals(LocalDateTime.of(2010,5,1,20,0), this.defaultSettings.getNotification());
    }

    /**
     * Tests the method {@code setNotification} with null value.
     */
    @Test
    void setNotification_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> this.defaultSettings.setNotification(null));
    }

    /**
     * Tests the method {@code toString}.
     */
    @Test
    void testToString() {
        DefaultSettings defaultSettings = new DefaultSettings();
        defaultSettings.setId(5L);
        defaultSettings.setSickDayCount(1);
        defaultSettings.setNotification(LocalDateTime.of(2008,10,30,20,0));
        assertEquals("DefaultSettings{id=5, sickDayCount=1, notification=2008-10-30T20:00}", defaultSettings.toString());
    }
}
