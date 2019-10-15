package org.danekja.ymanager.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * The domain class {@code DefaultSettings} represents a single record in the 'default_settings' table of a database.
 * It contains default settings of users' informations that are known to the application. Informations consist of a number of
 * available sick days, date and time of an email notification. Default settings are mainly used when the user is added to
 * the application which means he/she can't have his/her own values.
 */
public class DefaultSettings {
    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(DefaultSettings.class);

    /**
     * The ID of default settings.
     */
    private Long id;

    /**
     * The default number of available sick days.
     */
    private Integer sickDayCount;

    /**
     * The default date and time of sending an email warning about an incoming reset of remaining overtimes and sick days.
     */
    private LocalDateTime notification;

    public DefaultSettings() {
    }

    public DefaultSettings(Integer sickDayCount, LocalDateTime notification) {
        this.setSickDayCount(sickDayCount);
        this.setNotification(notification);
    }

    /**
     * Returns the ID of this default settings.
     *
     * @return the ID of this default settings
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Replaces the ID of this default settings with the given one.
     *
     * @param id the given ID
     */
    public void setId(final Long id) {
        DefaultSettings.log.debug("Setting a new id: {}", id);

        this.id = id;
    }

    /**
     * Returns the default number of available sick days in this default settings.
     *
     * @return the default number of available sick days
     */
    public int getSickDayCount() {
        return this.sickDayCount;
    }

    /**
     * Replaces the default number of available sick days in this default settings with the given one.
     * If the given number is negative or null the method throws an exception.
     *
     * @param sickDayCount the new default number of available sick days
     * @throws IllegalArgumentException when the given value is negative or null
     */
    public void setSickDayCount(final Integer sickDayCount) {
        DefaultSettings.log.debug("Setting a new number of available sick days: {}", sickDayCount);

        if (sickDayCount == null) {
            DefaultSettings.log.warn("The given number of available sick days must not be null");
            throw new IllegalArgumentException("sick.day.null.error");
        } else if (sickDayCount < 0) {
            DefaultSettings.log.warn("The number of available sick days was negative");
            throw new IllegalArgumentException("sick.day.count.error");
        }

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
     * If the given notification is null the method throws an exception.
     *
     * @param notification the new default date and time
     * @throws IllegalArgumentException when the given notification is null
     */
    public void setNotification(final LocalDateTime notification) throws IllegalArgumentException {
        DefaultSettings.log.debug("Setting a new default date and time of sending an email warning: {}", notification);

        if (notification == null) {
            DefaultSettings.log.warn("The notification must not be null");
            throw new IllegalArgumentException("notification.null.error");
        }

        this.notification = notification;
    }

    /**
     * Returns a string representation of this default settings. The representation consists of the id, number of sick days and notification of the default settings.
     *
     * @return the string representation of this default settings
     */
    @Override
    public String toString() {
        return "DefaultSettings{" +
                "id=" + this.id +
                ", sickDayCount=" + this.sickDayCount +
                ", notification=" + this.notification +
                '}';
    }
}
