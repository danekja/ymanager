package org.danekja.ymanager.dto;

import java.time.LocalDateTime;

/**
 * The messenger class {@code DefaultSettings} contains default settings of users' informations that are known to the application.
 * Informations consist of a number of available sick days, date and time of an email notification.
 * Default settings are mainly used when the user is added to the application which means he/she can't have his/her own values.
 * This class is used to communicate with a frontend.
 */
public class DefaultSettingsDTO {
    /**
     * The default number of available sick days.
     */
    private Integer sickDayCount;

    /**
     * The default date and time of sending an email warning about an incoming reset of remaining overtimes and sick days.
     */
    private LocalDateTime notification;

    public DefaultSettingsDTO() {
    }

    public DefaultSettingsDTO(Integer sickDayCount, LocalDateTime notification) {
        this.sickDayCount = sickDayCount;
        this.notification = notification;
    }

    public DefaultSettingsDTO(org.danekja.ymanager.domain.DefaultSettings src) {
        this.sickDayCount = src.getSickDayCount();
        this.notification = src.getNotification();
    }

    /**
     * Returns the default number of available sick days in this default settings.
     *
     * @return the default number of available sick days
     */
    public Integer getSickDayCount() {
        return this.sickDayCount;
    }

    /**
     * Replaces the default number of available sick days in this default settings with the given one.
     *
     * @param sickDayCount the new default number of available sick days
     */
    public void setSickDayCount(final Integer sickDayCount) {
        this.sickDayCount = sickDayCount;
    }

    /**
     * Returns the default date and time of sending an email warning about an incoming reset of remaining overtimes and sick days.
     *
     * @return the default date and time
     */
    public LocalDateTime getNotification() {
        return this.notification;
    }

    /**
     * Replaces the default date and time of sending an email warning about an incoming reset of remaining overtimes and sick days with the specified one.
     *
     * @param notification the new default date and time
     */
    public void setNotification(final LocalDateTime notification) {
        this.notification = notification;
    }

    public org.danekja.ymanager.domain.DefaultSettings toEntity() {
        return new org.danekja.ymanager.domain.DefaultSettings(getSickDayCount(), getNotification());
    }
}

