package cz.zcu.yamanager.domain;

import cz.zcu.yamanager.dto.Status;
import cz.zcu.yamanager.dto.VacationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The domain class {@code VacationDay} represents a single record in the 'vacation_day' table of a database.
 * Class holds informations of an overtime or a sick day taken by a user.
 */
public class VacationDay {
    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(VacationDay.class);

    /**
     * The ID of this vacation.
     */
    private final int id;

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
    private final LocalDateTime creationDate;

    /**
     * The approval status of this vacation.
     */
    private Status status;

    /**
     * The type of this vacation.
     */
    private VacationType type;

    /**
     * Creates an empty vacation for testing purposes only.
     * It just sets id to zero and creation date to nowte.
     */
    public VacationDay() {
        VacationDay.log.trace("Creating a new instance of the class VacationDay.");
        this.id = 0;
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Creates a new sick day with the specified id, date, date when a sick day request was created and its approval status.
     *
     * @param id           the ID of the sick day
     * @param date         the date of the sick day
     * @param creationDate the date and time of the creation of the sick day
     * @param status       the approval status of the sick day
     */
    public VacationDay(final int id, final LocalDate date, final LocalDateTime creationDate, final Status status) {
        this(id, date, null, null, creationDate, status, VacationType.SICKDAY);
    }

    /**
     * Creates a new overtime with the specified id, date, starting time, ending time, date when an overtime request was created and its approval status.
     *
     * @param id           the ID of the overtime
     * @param date         the date of the overtime
     * @param from         the starting time of the overtime
     * @param to           the ending time of the overtime
     * @param creationDate the date and time of the creation of the overtime
     * @param status       the approval status of the overtime
     */
    public VacationDay(final int id, final LocalDate date, final LocalTime from, final LocalTime to, final LocalDateTime creationDate, final Status status) {
        this(id, date, from, to, creationDate, status, VacationType.VACATION);
    }

    /**
     * Creates a new overtime or sick day with all attributes.
     *
     * @param id           the ID of a vacation
     * @param date         the date of a vacation
     * @param from         the starting time of a vacation
     * @param to           the ending time of a vacation
     * @param creationDate the date and time of a creation of a vacation
     * @param status       the approval status of a vacation
     * @param type         the type of a vacation
     */
    private VacationDay(final int id, final LocalDate date, final LocalTime from, final LocalTime to, final LocalDateTime creationDate, final Status status, final VacationType type) {
        VacationDay.log.trace("Creating a new instance of the class VacationDay.");
        VacationDay.log.debug("VacationDay: id={}, date={}, from={}, to={}, creationDate={}, status={}, type={}", id, date, from, to, creationDate, status, type);

        this.id = id;
        this.date = date;
        this.setTime(from, to);
        this.creationDate = creationDate;
        this.status = status;
        this.type = type;
    }

    /**
     * Returns the ID of this vacation.
     *
     * @return the ID of this vacation
     */
    public int getId() {
        return this.id;
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
        VacationDay.log.debug("Settings a new value of a date: {}", date);
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
        VacationDay.log.debug("Settings a new value of the starting time of this vacation: {}", from);

        if (from != null && this.to != null && from.compareTo(this.to) >= 0) {
            VacationDay.log.warn("The vacation must not start after it ends. from={}, to={}", from, this.to);
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
        VacationDay.log.debug("Settings a new value of the ending time of this vacation: {}", to);

        if (to != null && this.from != null && to.compareTo(this.from) <= 0) {
            VacationDay.log.warn("The vacation must not end after it starts. from={}, to={}", this.from, to);
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
        VacationDay.log.debug("Settings a new value of the starting {} and the ending {} time of this vacation.", from, to);

        if (from != null && to != null && from.compareTo(to) >= 0) {
            VacationDay.log.warn("The vacation must not start after it ends. from={}, to={}", from, to);
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
        VacationDay.log.debug("Setting a new approval status: {}", status);
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
     * If the given type is a SICKDAY the method sets the starting and the ending time to null.
     *
     * @param type the new type
     */
    public void setType(final VacationType type) {
        VacationDay.log.debug("Setting a new type of this vacation: {}", type);
        this.type = type;
    }

    /**
     * Gets a string representation of this vacation. The representation consists of its id, date, starting time, ending time, creation date, status and type.
     *
     * @return the string representation of this vacation
     */
    @Override
    public String toString() {
        return "VacationDay{" +
                "id=" + this.id +
                ", date=" + this.date +
                ", from=" + String.valueOf(this.from) +
                ", to=" + String.valueOf(this.to) +
                ", creationDate=" + this.creationDate +
                ", status=" + this.status +
                ", type=" + this.type +
                '}';
    }
}
