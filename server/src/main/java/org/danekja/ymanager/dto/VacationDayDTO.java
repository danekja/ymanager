package org.danekja.ymanager.dto;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.Vacation;
import org.danekja.ymanager.domain.VacationType;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The messenger class {@code Vacation} holds informations of an overtime or a sick day taken by a user.
 * This class is used in communication with a frontend.
 */
public class VacationDayDTO {
    /**
     * The ID of this vacation.
     */
    private Long id;

    /**
     * The date of this vacation.
     */
    private LocalDate date;

    /**
     * The starting time of this vacation.
     */
    private LocalTime from;

    /**
     * The ending time of this vacation.
     */
    private LocalTime to;

    /**
     * The type of this vacation.
     */
    private VacationType type;

    /**
     * The approval status of this vacation.
     */
    private Status status;

    public VacationDayDTO() {
    }

    public VacationDayDTO(Vacation vacation) {
        this.id = vacation.getId();
        this.date = vacation.getDate();
        this.from = vacation.getFrom();
        this.to = vacation.getTo();
        this.type = vacation.getType();
        this.status = vacation.getStatus();
    }

    /**
     * Returns the ID of this vacation.
     *
     * @return the ID of this vacation
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Replaces the ID of this vacation with the specified value.
     *
     * @param id the new ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Returns the date of this vacation.
     *
     * @return the date of this vacation
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Replaces the date of this vacation with the specified date.
     *
     * @param date the new date
     */
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the starting time of this vacation.
     *
     * @return the starting time of this vacation
     */
    public LocalTime getFrom() {
        return this.from;
    }

    /**
     * Replaces the starting time of this vacation with the given time.
     *
     * @param from the new starting time
     */
    public void setFrom(final LocalTime from) {
        this.from = from;
    }

    /**
     * Returns the ending time of this vacation.
     *
     * @return the ending time of this vacation
     */
    public LocalTime getTo() {
        return this.to;
    }

    /**
     * Replaces the ending time of this vacation with the provided time.
     *
     * @param to the new ending time
     */
    public void setTo(final LocalTime to) {
        this.to = to;
    }

    /**
     * Returns the type of this vacation.
     *
     * @return the type of this vacation
     */
    public VacationType getType() {
        return this.type;
    }

    /**
     * Replaces the type of this vacation with the handed type.
     *
     * @param type the new type
     */
    public void setType(final VacationType type) {
        this.type = type;
    }

    /**
     * Returns the approval status of this vacation.
     *
     * @return the approval status of this vacation
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Replaces the approval status of this vacation with the given value.
     *
     * @param status the new approval status
     */
    public void setStatus(final Status status) {
        this.status = status;
    }
}
