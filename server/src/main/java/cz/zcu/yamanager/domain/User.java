package cz.zcu.yamanager.domain;

import java.time.LocalDate;

/**
 * A domain class {@code User} represents a single record in the User table of a database.
 * User hold all informations about a user which logged to the application.
 */
public class User {

    /**
     * User's ID.
     */
    private final int id;

    /**
     * User's first name.
     */
    private String firstName;

    /**
     * User's last name.
     */
    private String lastName;

    /**
     * User's remaining vacation hours.
     */
    private int noVacations;

    /**
     * User's remaining sick days.
     */
    private int noSickDays;

    /**
     * Date of an email warning about an incoming reset of the vacation hours and sick days.
     */
    private LocalDate alertDate;

    /**
     * Token for the Google oAuth.
     */
    private String token;

    /**
     * User's email address.
     */
    private String email;

    /**
     * URL of a user's photo.
     */
    private String photo;

    /**
     * User's role.
     */
    private Role role;

    /**
     * User's approval status.
     */
    private Status status;

    /**
     * Creates a new instance of the class {@code User}.
     * @param id User's ID.
     * @param firstName User's first name.
     * @param lastName User's last name.
     * @param noVacations User's remaining vacation hours.
     * @param noSickDays User's remaining sick days.
     * @param alertDate Date of email warning about an incoming reset of the vacation hours and sick days.
     * @param token Token for the Google oAuth.
     * @param email User's email address.
     * @param photo URL of a user's photo.
     * @param role User's role.
     * @param status User's approval status.
     */
    public User(int id, String firstName, String lastName, int noVacations, int noSickDays, LocalDate alertDate, String token, String email, String photo, Role role, Status status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.noVacations = noVacations;
        this.noSickDays = noSickDays;
        this.alertDate = alertDate;
        this.token = token;
        this.email = email;
        this.photo = photo;
        this.role = role;
        this.status = status;
    }

    /**
     * Gets a user's ID.
     * @return The user's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets a user's first name.
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets a new first name.
     * @param firstName The new first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets a user's last name.
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets a new last name.
     * @param lastName The new last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets a user's remaining vacation hours.
     * @return The user's remaining vacation hours.
     */
    public int getNoVacations() {
        return noVacations;
    }

    /**
     * Sets a new number of remaining vacation hours.
     * If the given value is negative the number of vacation hours is set to zero.
     * @param noVacations The new number of remaining vacation hours.
     */
    public void setNoVacations(int noVacations) {
        this.noVacations = noVacations < 0 ? 0 : noVacations;
    }

    /**
     * Gets a user's remaining sick days.
     * @return The user's remaining sick days.
     */
    public int getNoSickDays() {
        return noSickDays;
    }

    /**
     * Sets a new number of remaining sick days.
     * If the given value is negative the number of sick days is set to zero.
     * @param noSickDays The new number of remaining sick days.
     */
    public void setNoSickDays(int noSickDays) {
        this.noSickDays = noSickDays < 0 ? 0 : noSickDays;
    }

    /**
     * Gets a date of an email warning about an incoming reset of the vacation hours and sick days.
     * @return The date of the email warning about the incoming reset of the vacation hours and sick days.
     */
    public LocalDate getAlertDate() {
        return alertDate;
    }

    /**
     * Sets a new date of an email warning about an incoming reset of the vacation hours and sick days.
     * @param alertDate The new date of the email warning about the incoming reset of the vacation hours and sick days.
     */
    public void setAlertDate(LocalDate alertDate) {
        this.alertDate = alertDate;
    }

    /**
     * Gets a user's token for the Google oAuth.
     * @return The user's token for the Google oAuth.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets a new token for the Google oAuth.
     * @param token The new token for the Google oAuth.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets a user's email address.
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets a new email address.
     * @param email The new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets a URL of a user's photo.
     * @return The URL of the user's photo.
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Sets a new URL of a user's photo.
     * @param photo The new URL of a user's photo.
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Gets a user's role.
     * @return The user's role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets a new role.
     * @param role The new role.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets a user's approval status.
     * @return The user's approval status.
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
     * Gets a string representation of the class {@code User}.
     * @return The string representation of the class.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", noVacations=" + noVacations +
                ", noSickDays=" + noSickDays +
                ", alertDate=" + alertDate +
                ", token='" + token + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}