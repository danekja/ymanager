package cz.zcu.yamanager.domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A domain class {@code VacationDay} represents a single record in the Vacation_day table of a database.
 * Class holds informations of a vacation day taken by a user.
 */
public class VacationDay {

    /**
     * ID of the vacation day.
     */
    private final int id;

    /**
     * Date of the vacation day.
     */
    private LocalDate date;

    /**
     * Starting time of a vacation.
     */
    private LocalTime from;

    /**
     * Ending time of a vacation.
     */
    private LocalTime to;

    /**
     * Approval status of the vacation day.
     */
    private Status status;

    /**
     * Creates a new instance of the class {@code VacationDay}.
     * @param id ID of the vacation day.
     * @param date Date of the vacation day.
     * @param from Starting time of a vacation.
     * @param to Ending time of a vacation.
     * @param status Approval status of the vacation day.
     */
    public VacationDay(int id, LocalDate date, LocalTime from, LocalTime to, Status status) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.status = status;
    }

    /**
     * Gets an ID of the vacation day.
     * @return The ID of the vacation day.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets a date of the vacation day.
     * @return The date of the vacation day.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets a new date.
     * @param date The new date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets a starting time of a vacation.
     * @return The starting time of a vacation.
     */
    public LocalTime getFrom() {
        return from;
    }

    /**
     * Sets a new starting time.
     * @param from The new starting time.
     */
    public void setFrom(LocalTime from) {
        this.from = from;
    }

    /**
     * Gets an ending time of a vacation.
     * @return The ending time of a vacation.
     */
    public LocalTime getTo() {
        return to;
    }

    /**
     * Sets a new ending time.
     * @param to The new ending time.
     */
    public void setTo(LocalTime to) {
        this.to = to;
    }

    /**
     * Gets an approval status of the vacation day.
     * @return The approval status of the vacation day.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets a new approval status.
     * @param status The new approval status.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets a string representation of the class {@code VacationDay}.
     * @return The string representation of the class.
     */
    @Override
    public String toString() {
        return "VacationDay{" +
                "id=" + id +
                ", date=" + date +
                ", from=" + from +
                ", to=" + to +
                ", status=" + status +
                '}';
    }
}
