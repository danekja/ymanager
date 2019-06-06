package cz.zcu.yamanager.domain;

import cz.zcu.yamanager.dto.Status;
import cz.zcu.yamanager.dto.VacationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The domain class {@code Vacation} represents a single record in the 'vacation_day' table of a database.
 * Class holds informations of an overtime or a sick day taken by a user.
 */
public class Vacation {
    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(Vacation.class);

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
     * The date and time of a creation of this vacation.
     */
    private LocalDateTime creationDate;

    /**
     * The approval status of this vacation.
     */
    private Status status;

    /**
     * The type of this vacation.
     */
    private VacationType type;

    private Long userId;

    /**
     * Returns the ID of this vacation.
     *
     * @return the ID of this vacation
     */
    public long getId() {
        return this.id;
    }

    /**
     * Replaces the ID of this vacation with the given one.
     *
     * @param id the given ID
     */
    public void setId(final Long id) {
        Vacation.log.debug("Setting a new id: {}", id);

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
     * If the given date is null the method throws an exception.
     *
     * @param date the new date
     * @throws IllegalArgumentException when the given date is null
     */
    public void setDate(final LocalDate date) throws IllegalArgumentException {
        Vacation.log.debug("Settings a new value of a date: {}", date);

        if (date == null) {
            Vacation.log.warn("The given date must not be null.");
            throw new IllegalArgumentException("date.null.error");
        }

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
     * If the given time is later or equals the ending time of this vacation the method throws an exception.
     *
     * @param from the new starting time
     * @throws IllegalArgumentException when the given time is later or equals the ending time of this vacation
     */
    public void setFrom(final LocalTime from) throws IllegalArgumentException {
        Vacation.log.debug("Settings a new value of the starting time of this vacation: {}", from);

        if (from != null && this.type == VacationType.SICK_DAY) {
            Vacation.log.warn("A sick day must not have a starting or an ending time");
            throw new IllegalArgumentException("time.sick.day.error");
        } else if(from == null && this.type == VacationType.VACATION) {
            Vacation.log.warn("A vacation has to have a starting and an ending time");
            throw new IllegalArgumentException("time.vacation.error");
        } else if (from != null && this.to != null && from.compareTo(this.to) >= 0) {
            Vacation.log.warn("A vacation must not start after it ends. from={}, to={}", from, this.to);
            throw new IllegalArgumentException("time.order.error");
        }

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
     * If the given time is earlier or equals the starting time of this vacation the method throws an exception.
     *
     * @param to the new ending time
     * @throws IllegalArgumentException when the given time is earlier or equals the starting time of this vacation
     */
    public void setTo(final LocalTime to) throws IllegalArgumentException {
        Vacation.log.debug("Settings a new value of the ending time of this vacation: {}", to);

        if (to != null && this.type == VacationType.SICK_DAY) {
            Vacation.log.warn("A sick day must not have a starting or an ending time");
            throw new IllegalArgumentException("time.sick_day.error");
        } else if(to == null && this.type == VacationType.VACATION) {
            Vacation.log.warn("A vacation has to have a starting and an ending time");
            throw new IllegalArgumentException("time.vacation.error");
        } else if (to != null && this.from != null && to.compareTo(this.from) <= 0) {
            Vacation.log.warn("A vacation must not end after it starts. from={}, to={}", this.from, to);
            throw new IllegalArgumentException("time.order.error");
        }

        this.to = to;
    }

    /**
     * Replaces the starting and ending time of this vacation with the provided values.
     * If the starting time is later or equals the ending time the method throws an exception.
     *
     * @param from the new starting time
     * @param to   the new ending time
     * @throws IllegalArgumentException when the ending time is earlier or equals the starting time
     */
    public void setTime(final LocalTime from, final LocalTime to) throws IllegalArgumentException {
        Vacation.log.debug("Settings a new value of the starting {} and the ending {} time of this vacation.", from, to);

        if ((from != null || to != null) && this.type == VacationType.SICK_DAY) {
            Vacation.log.warn("A sick day must not have a starting or an ending time");
            throw new IllegalArgumentException("time.sick.day_error");
        } else if((from == null || to == null) && this.type == VacationType.VACATION) {
            Vacation.log.warn("A vacation has to have a starting and an ending time");
            throw new IllegalArgumentException("time.vacation.error");
        } else if (from != null && to != null && from.compareTo(to) >= 0) {
            Vacation.log.warn("A vacation must not start after it ends. from={}, to={}", from, to);
            throw new IllegalArgumentException("time.order.error");
        }

        this.from = from;
        this.to = to;
    }

    /**
     * Returns the date and time of a creation of this vacation.
     *
     * @return the date and time of the creation of this vacation
     */
    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    /**
     * Replaces the creation date of this vacation with the given date and time.
     *
     * @param creationDate the new creation date
     */
    public void setCreationDate(final LocalDateTime creationDate) {
        Vacation.log.debug("Setting a new creation date of this vacation: {}", creationDate);

        this.creationDate = creationDate;
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
     * If the given status is null the method throws an exception.
     *
     * @param status the new approval status
     * @throws IllegalArgumentException when the given status is null
     */
    public void setStatus(final Status status) throws IllegalArgumentException {
        Vacation.log.debug("Setting a new approval status: {}", status);

        if (status == null) {
            Vacation.log.warn("The given status must not be null");
            throw new IllegalArgumentException("status.null.error");
        }

        this.status = status;
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
     * If the given type is SICK_DAY the method sets the starting and the ending time to null.
     * If the given type is null the method throws an exception.
     *
     * @param type the new type
     * @throws IllegalArgumentException when the given type is null
     */
    public void setType(final VacationType type) throws IllegalArgumentException {
        Vacation.log.debug("Setting a new type of this vacation: {}", type);

        if(type == VacationType.SICK_DAY) {
            this.from = null;
            this.to = null;
        } else if (type == null) {
            Vacation.log.warn("The given type of a vacation must not be null");
            throw new IllegalArgumentException("type.null.error");
        }

        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets a string representation of this vacation. The representation consists of its id, date, starting time, ending time, creation date, status and type.
     *
     * @return the string representation of this vacation
     */
    @Override
    public String toString() {
        return "Vacation{" +
                "id=" + this.id +
                ", date=" + this.date +
                ", from=" + this.from +
                ", to=" + this.to +
                ", creationDate=" + this.creationDate +
                ", status=" + this.status +
                ", type=" + this.type +
                '}';
    }
}
