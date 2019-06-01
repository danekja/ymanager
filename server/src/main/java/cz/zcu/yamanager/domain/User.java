package cz.zcu.yamanager.domain;

import cz.zcu.yamanager.dto.Status;
import cz.zcu.yamanager.dto.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * The domain class {@code User} represents a single record in the 'end_user' table of a database.
 * User holds all informations about a user which is logged to the application.
 */
public class User {
    /**
     * The maximal length of a name.
     */
    private static final int NAME_LENGTH = 45;

    /**
     * The maximal length of an email address.
     */
    private static final int EMAIL_ADDRESS_LENGTH = 100;

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(User.class);

    /**
     * The user's ID.
     */
    private final long id;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The number of user's remaining hours of an overtime.
     */
    private float vacationCount;

    /**
     * The number of user's sick days available during a year.
     */
    private int totalSickDayCount;

    /**
     * The number of user's taken sick days.
     */
    private int takenSickDayCount;

    /**
     * The date and time of sending an email warning about an incoming reset of remaining overtimes and sick days.
     */
    private LocalDateTime notification;

    /**
     * The token for the Google oAuth.
     */
    private String token;

    /**
     * The user's email address.
     */
    private String email;

    /**
     * The URL of a user's photo.
     */
    private String photo;

    /**
     * The date and time of a user's creation.
     */
    private final LocalDateTime creationDate;

    /**
     * The user's role.
     */
    private UserRole role;

    /**
     * The user's authorization status.
     */
    private Status status;

    /**
     * Creates an empty user for testing purposes only.
     * It just sets id to zero and creation date to now.
     */
    public User() {
        User.log.trace("Creating a new instance of the class User.");
        this.id = 0;
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Creates a new user and sets all its attributes.
     *
     * @param id                the user's ID
     * @param firstName         the user's first name
     * @param lastName          the user's last name.
     * @param vacationCount     the number of user's remaining hours of an overtime
     * @param totalSickDayCount the number of user's sick days available during a year
     * @param takenSickDayCount the number of user's taken sick days
     * @param notification      the date and time of sending an email warning about an incoming reset of remaining overtimes and sick days
     * @param token             the token for the Google oAuth
     * @param email             the user's email address
     * @param photo             the URL of a user's photo
     * @param creationDate      the date and time of a user's creation
     * @param role              the user's role
     * @param status            the user's authorization status
     * @throws IllegalArgumentException when the vacationCount, totalSickDayCount or takenSickDayCount are negative or first name, last name or email exceed the maximal permitted number of characters
     */
    public User(final long id, final String firstName, final String lastName, final float vacationCount, final int totalSickDayCount, final int takenSickDayCount, final LocalDateTime notification, final String token, final String email, final String photo, final LocalDateTime creationDate, final UserRole role, final Status status) throws IllegalArgumentException {
        User.log.trace("Creating a new instance of the class User.");
        User.log.debug("User: id={},\nfirstName={},\nlastName={},\nvacationCount={},\ntotalSickDayCount={},\ntakenSickDayCount={},\nnotification={},\ntoken={},\nemail={},\nphoto={},\ncreationDate={},\nrole={},\nstatus={}", id, firstName, lastName, vacationCount, totalSickDayCount, takenSickDayCount, notification, token, email, photo, creationDate, role, status);

        this.id = id;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setVacationCount(vacationCount);
        this.setTotalSickDayCount(totalSickDayCount);
        this.setTakenSickDayCount(takenSickDayCount);
        this.notification = notification;
        this.token = token;
        this.setEmail(email);
        this.photo = photo;
        this.creationDate = creationDate;
        this.role = role;
        this.status = status;
    }

    /**
     * Returns the user's ID.
     *
     * @return the user's ID
     */
    public long getId() {
        return this.id;
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
     * If the given name is longer than the maximal allowed number of characters the method throws an exception.
     *
     * @param firstName the new first name
     * @throws IllegalArgumentException when the given name is longer than the maximal allowed number of characters
     */
    public void setFirstName(final String firstName) throws IllegalArgumentException {
        User.log.debug("Setting a new first name: {}", firstName);

        if (firstName.length() > User.NAME_LENGTH) {
            User.log.warn("The length of the given first name exceeded the limit.");
            throw new IllegalArgumentException("name.length.error");
        }

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
     * If the given name is longer than the maximal allowed number of characters the method throws an exception.
     *
     * @param lastName the new last name
     * @throws IllegalArgumentException when the given name is longer than the maximal allowed number of characters
     */
    public void setLastName(final String lastName) throws IllegalArgumentException {
        User.log.debug("Setting a new last name: {}", lastName);

        if (lastName.length() > User.NAME_LENGTH) {
            User.log.warn("The length of the given last name exceeded the limit.");
            throw new IllegalArgumentException("name.length.error");
        }

        this.lastName = lastName;
    }

    /**
     * Returns the number of user's remaining hours of an overtime.
     *
     * @return the number of user's remaining hours of the overtime
     */
    public float getVacationCount() {
        return this.vacationCount;
    }

    /**
     * Replaces the number of user's remaining hours of an overtime with the specified value.
     * If the given number is negative the method throws an exception.
     *
     * @param vacationCount the new number of remaining hours of the overtime.
     * @throws IllegalArgumentException when the given value is negative
     */
    public void setVacationCount(final float vacationCount) throws IllegalArgumentException {
        User.log.debug("Setting a new number of remaining overtime: {}", vacationCount);

        if (vacationCount < 0) {
            User.log.warn("The given number of remaining overtime must not be negative.");
            throw new IllegalArgumentException("vacation.count.error");
        }

        this.vacationCount = vacationCount;
    }

    /**
     * Increases the number of user's remaining hours of an overtime by the given amount.
     *
     * @param amount the amount of hours
     */
    public void increaseVacationCount(final float amount) {
        User.log.debug("Increasing the number of remaining overtime by {}", amount);
        this.vacationCount += amount;
    }

    /**
     * Decreases the number of user's remaining hours of an overtime by the given amount.
     * If the new number of remaining hours is below zero the method throws an exception.
     *
     * @param amount the amount of hours
     * @throws IllegalArgumentException when the result of the subtraction is negative
     */
    public void decreaseVacationCount(final float amount) throws IllegalArgumentException {
        User.log.debug("Decreasing the number of remaining overtime by {}", amount);

        if (this.vacationCount - amount < 0) {
            User.log.warn("The result of the decrease must not be negative.");
            throw new IllegalArgumentException("vacation.count.error");
        }

        this.vacationCount -= amount;
    }

    /**
     * Returns the number of user's sick days available during a year.
     *
     * @return the number of user's sick days available during the year
     */
    public int getTotalSickDayCount() {
        return this.totalSickDayCount;
    }

    /**
     * Replaces the number of user's sick days available during a year with the new value.
     * If the given number is negative the method throws an exception.
     *
     * @param totalSickDayCount the new number of sick days available during the year
     * @throws IllegalArgumentException when the given value is negative
     */
    public void setTotalSickDayCount(final int totalSickDayCount) throws IllegalArgumentException {
        User.log.debug("Setting a new number of user's sick days available during a year: {}", totalSickDayCount);

        if (totalSickDayCount < 0) {
            User.log.warn("The given number of user's sick days available during a year must not be negative.");
            throw new IllegalArgumentException("sick.day.count.error");
        }

        this.totalSickDayCount = totalSickDayCount;
    }

    /**
     * Returns the number of user's taken sick days.
     *
     * @return the number of user's taken sick days
     */
    public int getTakenSickDayCount() {
        return this.takenSickDayCount;
    }

    /**
     * Replaces the number of user's taken sick days with the new value.
     * If the given number is negative the method throws an exception.
     *
     * @param takenSickDayCount the new number of taken sick days
     * @throws IllegalArgumentException when the given value is negative
     */
    public void setTakenSickDayCount(final int takenSickDayCount) throws IllegalArgumentException {
        User.log.debug("Setting a new number of user's taken sick days: {}", takenSickDayCount);

        if (takenSickDayCount < 0) {
            User.log.warn("The given number number of user's taken sick days must not be negative.");
            throw new IllegalArgumentException("sick.day.count.error");
        }

        this.takenSickDayCount = takenSickDayCount;
    }

    /**
     * Returns the date and time of sending an email warning about an incoming reset of remaining overtimes and sick days.
     *
     * @return the date and time
     */
    public LocalDateTime getNotification() {
        return this.notification;
    }

    /**
     * Replaces the date and time of sending an email warning about an incoming reset of remaining overtimes and sick days with the given value.
     *
     * @param notification the new date and time
     */
    public void setNotification(final LocalDateTime notification) {
        User.log.debug("Setting a new date and time of sending an email warning: {}", notification);
        this.notification = notification;
    }

    /**
     * Returns the user's token for the Google oAuth.
     *
     * @return the user's token for the Google oAuth
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Replaces the user's token for the Google oAuth with the specified string.
     *
     * @param token the new token for the Google oAuth
     */
    public void setToken(final String token) {
        User.log.debug("Setting a new token: {}", token);
        this.token = token;
    }

    /**
     * Returns the user's email address.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Replaces the user's email address with the new given value.
     * If the given email is longer than the maximal allowed number of characters the method throws an exception.
     *
     * @param email the new email address
     * @throws IllegalArgumentException when the given email is longer than the maximal allowed number of characters
     */
    public void setEmail(final String email) throws IllegalArgumentException {
        User.log.debug("Setting a new email address: {}", email);

        if (email.length() > User.EMAIL_ADDRESS_LENGTH) {
            User.log.warn("The length of the email address exceeded the limit.");
            throw new IllegalArgumentException("email.length.error");
        }

        this.email = email;
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
        User.log.debug("Setting a new url of a photo: {}", photo);
        this.photo = photo;
    }

    /**
     * Returns the date and time of a user's creation.
     *
     * @return the date and time of the user's creation
     */
    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    /**
     * Returns the user's role.
     *
     * @return the user's role
     */
    public UserRole getRole() {
        return this.role;
    }

    /**
     * Replaces the user's role with the new provided value.
     *
     * @param role the new role
     */
    public void setRole(final UserRole role) {
        User.log.debug("Setting a new user's role: {}", role);
        this.role = role;
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
        User.log.debug("Setting a new authorization status: {}", status);
        this.status = status;
    }

    /**
     * Gets a string representation of this user. The representation contains the id, first name, last name,
     * number of user's remaining hours of an overtime, number of user's sick days available during a year,
     * number of user's taken sick days, date and time of sending an email warning about an incoming reset of
     * remaining overtimes and sick days, token for the Google oAuth, email address, URL of a photo, role and
     * authorization status.
     *
     * @return the string representation of this user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + this.id +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", vacationCount=" + this.vacationCount +
                ", totalSickDayCount=" + this.totalSickDayCount +
                ", takenSickDayCount=" + this.takenSickDayCount +
                ", notification=" + this.notification +
                ", token='" + this.token + '\'' +
                ", email='" + this.email + '\'' +
                ", photo='" + this.photo + '\'' +
                ", creationDate=" + this.creationDate +
                ", role=" + this.role +
                ", status=" + this.status +
                '}';
    }
}