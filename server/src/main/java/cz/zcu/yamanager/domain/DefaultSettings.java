package cz.zcu.yamanager.domain;

import java.time.LocalDate;

/**
 * A domain class {@code DefaultSettings} represents a single record in the Default_settings table of a database.
 * It contains default settings of the application.
 */
public class DefaultSettings {

    /**
     * ID of the default settings.
     */
    private final int id;

    /**
     * Default remaining vacation hours.
     */
    private int noVacations;

    /**
     * Default remaining sick days.
     */
    private int noSickDays;

    /**
     * Default date of an email warning about an incoming reset of the vacation hours and sick days.
     */
    private LocalDate alertDate;

    /**
     * Creates a new instance of the class {@code DefaultSettings}.
     * @param id ID of the default settings.
     * @param noVacations Default remaining vacation hours.
     * @param noSickDays Default remaining sick days.
     * @param alertDate Default date of an email warning about an incoming reset of the vacation hours and sick days.
     */
    public DefaultSettings(int id, int noVacations, int noSickDays, LocalDate alertDate) {
        this.id = id;
        this.noVacations = noVacations;
        this.noSickDays = noSickDays;
        this.alertDate = alertDate;
    }

    /**
     * Gets an ID of the default settings.
     * @return The ID of the default settings.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets a default remaining vacation hours.
     * @return The default remaining vacation hours.
     */
    public int getNoVacations() {
        return noVacations;
    }

    /**
     * Sets a new default remaining vacation hours.
     * @param noVacations The new default remaining vacation hours.
     */
    public void setNoVacations(int noVacations) {
        this.noVacations = noVacations;
    }

    /**
     * Gets a default remaining sick days.
     * @return The default remaining sick days.
     */
    public int getNoSickDays() {
        return noSickDays;
    }

    /**
     * Sets a new default remaining sick days.
     * @param noSickDays The new default remaining sick days.
     */
    public void setNoSickDays(int noSickDays) {
        this.noSickDays = noSickDays;
    }

    /**
     * Gets a default date of an email warning about an incoming reset of the vacation hours and sick days.
     * @return The default date of an email warning about an incoming reset of the vacation hours and sick days.
     */
    public LocalDate getAlertDate() {
        return alertDate;
    }

    /**
     * Sets a new default date of an email warning about an incoming reset of the vacation hours and sick days.
     * @param alertDate The new default date of an email warning about an incoming reset of the vacation hours and sick days.
     */
    public void setAlertDate(LocalDate alertDate) {
        this.alertDate = alertDate;
    }

    /**
     * Gets a string representation of the class {@code DefaultSettings}.
     * @return The string representation of the class.
     */
    @Override
    public String toString() {
        return "DefaultSettings{" +
                "id=" + id +
                ", noVacations=" + noVacations +
                ", noSickDays=" + noSickDays +
                ", alertDate=" + alertDate +
                '}';
    }
}
