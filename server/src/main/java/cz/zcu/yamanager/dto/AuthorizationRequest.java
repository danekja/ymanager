package cz.zcu.yamanager.dto;

import java.time.LocalDateTime;

/**
 * An instance of the messenger class {@code AuthorizationRequest} is just a different view of a user.
 * It stores information relevant to a user's authorization in the application.
 * This class is used to communication with a frontend.
 */
public class AuthorizationRequest {
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
     * The user's authorization status.
     */
    private Status status;

    /**
     * The date and time of a creation of this request.
     */
    private LocalDateTime timestamp;

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
     * Returns the date and time of a creation of this user's request.
     *
     * @return the date and time of the creation of this user's request
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Replaces the date and time of a creation of this user's request with the given value.
     *
     * @param timestamp the new date and time of the creation of this user's request
     */
    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
