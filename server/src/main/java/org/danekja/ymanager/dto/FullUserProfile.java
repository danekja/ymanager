package org.danekja.ymanager.dto;

import org.danekja.ymanager.domain.User;

import java.time.LocalDateTime;

/**
 * The messenger class {@code FullUserProfile} holds all informations about a user which is logged to the application.
 * This class is used to communicate with a frontend.
 */
public class FullUserProfile {
    /**
     * The user's ID.
     */
    private Long id;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The URL of a user's photo.
     */
    private String photo;

    /**
     * The number of user's remaining hours of an overtime.
     */
    private Float vacationCount;

    /**
     * The number of user's sick days available during a year.
     */
    private Integer sickDayCount;

    /**
     * The number of user's taken sick days.
     */
    private Integer takenSickDayCount;

    /**
     * The user's authorization status.
     */
    private Status status;

    /**
     * The user's role.
     */
    private UserRole role;

    /**
     * The date and time of sending an email warning about an incoming reset of remaining overtimes and sick days.
     */
    private LocalDateTime notification;

    /**
     * The user's email address.
     */
    private String email;

    public FullUserProfile() {
    }

    public FullUserProfile(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getPhoto(), user.getVacationCount(), user.getTotalSickDayCount(), user.getTakenSickDayCount(), user.getStatus(), user.getRole(), user.getNotification(), user.getEmail());
    }

    public FullUserProfile(Long id, String firstName, String lastName, String photo, Float vacationCount, Integer sickDayCount, Integer takenSickDayCount, Status status, UserRole role, LocalDateTime notification, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.vacationCount = vacationCount;
        this.sickDayCount = sickDayCount;
        this.takenSickDayCount = takenSickDayCount;
        this.status = status;
        this.role = role;
        this.notification = notification;
        this.email = email;
    }

    /**
     * Returns the user's email address.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Replaces the user's email address with the new given value.
     *
     * @param email the new email address
     */
    public void setEmail(final String email) {
        this.email = email;
    }

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
     * Returns the user's first name.
     *
     * @return the user's first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the number of user's taken sick days.
     *
     * @return the number of user's taken sick days
     */
    public Integer getTakenSickDayCount() {
        return this.takenSickDayCount;
    }

    /**
     * Replaces the number of user's taken sick days with the new value.
     *
     * @param takenSickDayCount the new number of taken sick days
     */
    public void setTakenSickDayCount(final Integer takenSickDayCount) {
        this.takenSickDayCount = takenSickDayCount;
    }

    /**
     * Replaces the user's first name with the new specified value.
     *
     * @param firstName the new first name
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the user's last name.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Replaces the user's last name with the given one.
     *
     * @param lastName the new last name
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the URL of a user's photo.
     *
     * @return the URL of the user's photo
     */
    public String getPhoto() {
        return this.photo;
    }

    /**
     * Replaces the URL of a user's photo with the given link.
     *
     * @param photo the new URL of the user's photo
     */
    public void setPhoto(final String photo) {
        this.photo = photo;
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
     * Returns the user's authorization status.
     *
     * @return the user's authorization status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Replaces the authorization status with the handed value.
     *
     * @param status the new authorization status
     */
    public void setStatus(final Status status) {
        this.status = status;
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