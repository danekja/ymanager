package org.danekja.ymanager.dto;

import org.danekja.ymanager.domain.DefaultSettings;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit tests of {@link DefaultSettingsDTO}.
 */
class DefaultSettingsDTOTest {
    @Test
    void constructor_noArguments() {
        DefaultSettingsDTO defaultSettingsDTO = new DefaultSettingsDTO();

        assertNull(defaultSettingsDTO.getNotification());
        assertNull(defaultSettingsDTO.getSickDayCount());
    }

    @Test
    void constructor_properties() {
        DefaultSettingsDTO defaultSettingsDTO = new DefaultSettingsDTO(5, LocalDateTime.MAX);

        assertEquals(5, (int) defaultSettingsDTO.getSickDayCount());
        assertEquals(LocalDateTime.MAX, defaultSettingsDTO.getNotification());
    }

    @Test
    void constructor_domainObject() {
        DefaultSettings defaultSettings = new DefaultSettings(5, LocalDateTime.MAX);
        DefaultSettingsDTO defaultSettingsDTO = new DefaultSettingsDTO(defaultSettings);

        assertEquals(5, (int) defaultSettingsDTO.getSickDayCount());
        assertEquals(LocalDateTime.MAX, defaultSettingsDTO.getNotification());
    }

    @Test
    void toEntity() {
        DefaultSettingsDTO defaultSettingsDTO = new DefaultSettingsDTO(5, LocalDateTime.MAX);
        DefaultSettings defaultSettings = defaultSettingsDTO.toEntity();

        assertEquals(5, defaultSettings.getSickDayCount());
        assertEquals(LocalDateTime.MAX, defaultSettings.getNotification());
    }
}
