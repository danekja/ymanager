package org.danekja.ymanager.dto;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.VacationType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * An instance of the messenger class {@code VacationRequest} is just a different view of a user's vacation.
 * It stores information relevant to the vacation which helps an employer to accept or reject the user's vacation.
 * This class is used in a communication with a frontend.
 */
public class VacationRequest {
    /**
     * The ID of this vacation request.
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
     * The date of this vacation request.
     */
    private LocalDate date;

    /**
     * The starting time of this vacation request.
     */
    private LocalTime from;

    /**
     * The ending time of this vacation request.
     */
    private LocalTime to;

    /**
     * The type of this vacation request.
     */
    private VacationType type;

    /**
     * The approval status of this vacation request.
     */
    private Status status;

    /**
     * The date and time of a creation of this vacation request.
     */
    private LocalDateTime timestamp;

    /**
     * Returns the ID of this vacation request.
     *
     * @return the ID of this vacation request
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Replaces the ID of this vacation request with the specified value.
     *
     * @param id the new ID
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
     * Returns the date of this vacation request.
     *
     * @return the date of this vacation request
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Replaces the date of this vacation request with the specified date.
     *
     * @param date the new date
     */
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the starting time of this vacation request.
     *
     * @return the starting time of this vacation request
     */
    public LocalTime getFrom() {
        return this.from;
    }

    /**
     * Replaces the starting time of this vacation request with the given time.
     *
     * @param from the new starting time
     */
    public void setFrom(final LocalTime from) {
        this.from = from;
    }

    /**
     * Returns the ending time of this vacation request.
     *
     * @return the ending time of this vacation request
     */
    public LocalTime getTo() {
        return this.to;
    }

    /**
     * Replaces the ending time of this vacation request with the provided time.
     *
     * @param to the new ending time
     */
    public void setTo(final LocalTime to) {
        this.to = to;
    }

    /**
     * Returns the type of this vacation request.
     *
     * @return the type of this vacation request
     */
    public VacationType getType() {
        return this.type;
    }

    /**
     * Replaces the type of this vacation request with the handed type.
     *
     * @param type the new type
     */
    public void setType(final VacationType type) {
        this.type = type;
    }

    /**
     * Returns the approval status of this vacation request.
     *
     * @return the approval status of this vacation request
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Replaces the approval status of this vacation request with the given value.
     *
     * @param status the new approval status
     */
    public void setStatus(final Status status) {
        this.status = status;
    }

    /**
     * Returns the date and time of a creation of this vacation request.
     *
     * @return the date and time of the creation of this vacation request
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Replaces the date and time of a creation of this vacation request with the specified timestamp.
     *
     * @param timestamp the date and time of the creation of this vacation request
     */
    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
