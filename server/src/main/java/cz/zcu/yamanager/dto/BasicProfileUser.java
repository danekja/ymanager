package cz.zcu.yamanager.dto;

import java.util.List;

/**
 * An instance of the messenger class {@code BasicProfileUser} represents a basic profile of a user in a database.
 * The basic profile contains default, the most important informations which helps identify a user like a name or photo.
 * This class is used to communicate with a frontend.
 */
public class BasicProfileUser {
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
     * The list of user's vacations.
     */
    private List<VacationDay> calendar;

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
     * Returns the list of user's vacations.
     *
     * @return the list of user's vacations
     */
    public List<VacationDay> getCalendar() {
        return this.calendar;
    }

    /**
     * Replaces the list of user's vacations with the given list.
     *
     * @param calendar the new list of vacations
     */
    public void setCalendar(final List<VacationDay> calendar) {
        this.calendar = calendar;
    }
}
