package cz.zcu.yamanager.domain;

import java.time.LocalDate;

/**
 * A domain class {@code SickDay} represents a single record in the Sick_days table of a database.
 * Class holds informations of a sick day taken by a user.
 */
public class SickDay {

    /**
     * ID of the sick day.
     */
    private final int id;

    /**
     * Date of the sick day.
     */
    private LocalDate date;

    /**
     * Approval status of the sick day.
     */
    private Status status;

    /**
     * Creates a new instance of the class {@code SickDay}.
     * @param id ID of the sick day.
     * @param date Date of the sick day.
     * @param status Approval status of the sick day.
     */
    public SickDay(int id, LocalDate date, Status status) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    /**
     * Gets an ID of the sick day.
     * @return The ID of the sick day.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets a date of the sick day.
     * @return The date of the sick day.
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
     * Gets an approval status of the sick day.
     * @return The approval status of the sick day.
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
     * Gets a string representation of the class {@code SickDay}.
     * @return The string representation of the class.
     */
    @Override
    public String toString() {
        return "SickDay{" +
                "id=" + id +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
