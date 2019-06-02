package cz.zcu.yamanager.domain;

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
    private final long id;

    /**
     * The default number of available sick days.
     */
    private int sickDayCount;

    /**
     * The default date and time of sending an email warning about an incoming reset of remaining overtimes and sick days.
     */
    private LocalDateTime notification;

    /**
     * Creates an empty default settings for testing purposes only.
     * It just sets id to zero.
     */
    public DefaultSettings() {
        DefaultSettings.log.trace("Creating a new instance of the class DefaultSettings.");
        this.id = 0;
    }

    /**
     * Creates a new default settings with the specified id, number of available sick days, date and time of a notification.
     *
     * @param id           the ID of default settings
     * @param sickDayCount the default number of available sick days
     * @param notification the default date and time of sending an email warning about an incoming reset of remaining overtimes and sick days
     * @throws IllegalArgumentException when the given sickDayCount value is negative
     */
    public DefaultSettings(final long id, final int sickDayCount, final LocalDateTime notification) throws IllegalArgumentException {
        DefaultSettings.log.trace("Creating a new instance of the class DefaultSettings.");
        DefaultSettings.log.debug("DefaultSettings: id={}, sickDayCount={}, notification={}", id, sickDayCount, notification);

        this.id = id;
        this.setSickDayCount(sickDayCount);
        this.notification = notification;
    }

    /**
     * Returns the ID of this default settings.
     *
     * @return the ID of this default settings
     */
    public long getId() {
        return this.id;
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
     * If the given number is negative the method throws an exception.
     *
     * @param sickDayCount the new default number of available sick days
     * @throws IllegalArgumentException when the given value is negative
     */
    public void setSickDayCount(final int sickDayCount) throws IllegalArgumentException {
        DefaultSettings.log.debug("Setting a new number of available sick days: {}.", sickDayCount);

        if (sickDayCount < 0) {
            DefaultSettings.log.warn("The number of available sick days was negative.");
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
     *
     * @param notification the new default date and time
     */
    public void setNotification(final LocalDateTime notification) {
        DefaultSettings.log.debug("Setting a new default date and time of sending an email warning: {}.", notification);
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
