package org.danekja.ymanager.dto;

import org.danekja.ymanager.domain.UserRole;

import java.time.LocalDateTime;

/**
 * The messenger class {@code UserSettings} is used to change user settings by an employer.
 * This class is used in communication with a frontend.
 */
public class UserSettingsDTO {
    /**
     * The user's ID.
     */
    private Long id;

    /**
     * The number of user's remaining hours of an overtime.
     */
    private Float vacationCount;

    /**
     * The number of user's sick days available during a year.
     */
    private Integer sickDayCount;

    /**
     * The user's role.
     */
    private UserRole role;

    /**
     * The date and time of sending an email warning about an incoming reset of remaining overtimes and sick days.
     */
    private LocalDateTime notification;

    /**
     * Returns the user's ID.
     *
     * @return the user's ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Replaces the user's ID with the specified value.
     *
     * @param id the new user's ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Returns the number of user's remaining hours of an overtime.
     *
     * @return the number of user's remaining hours of the overtime
     */
    public Float getVacationCount() {
        return this.vacationCount;
    }

    /**
     * Replaces the number of user's remaining hours of an overtime with the specified value.
     *
     * @param vacationCount the new number of remaining hours of the overtime.
     */
    public void setVacationCount(final Float vacationCount) {
        this.vacationCount = vacationCount;
    }

    /**
     * Returns the number of user's sick days available during a year.
     *
     * @return the number of user's sick days available during the year
     */
    public Integer getSickDayCount() {
        return this.sickDayCount;
    }

    /**
     * Replaces the number of user's sick days available during a year with the new value.
     *
     * @param sickDayCount the new number of sick days available during the year
     */
    public void setSickDayCount(final Integer sickDayCount) {
        this.sickDayCount = sickDayCount;
    }

    /**
     * Returns the user's role.
     *
     * @return the user's role
     */
    public UserRole getRole() {
        return this.role;
    }

    /**
     * Replaces the user's role with the new provided value.
     *
     * @param role the new role
     */
    public void setRole(final UserRole role) {
        this.role = role;
    }

    /**
     * Returns the date and time of sending an email warning about an incoming reset of remaining overtimes and sick days.
     *
     * @return the date and time
     */
    public LocalDateTime getNotification() {
        return this.notification;
    }

    /**
     * Replaces the date and time of sending an email warning about an incoming reset of remaining overtimes and sick days with the given value.
     *
     * @param notification the new date and time
     */
    public void setNotification(final LocalDateTime notification) {
        this.notification = notification;
    }
}
