package cz.zcu.yamanager.domain;

import cz.zcu.yamanager.dto.Status;
import cz.zcu.yamanager.dto.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

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
        this.creationDate = null;
    }

    /**
     * Creates a new user and sets attributes known during an insertion.
     *
     * @param firstName         the user's first name
     * @param lastName          the user's last name.
     * @param vacationCount     the number of user's remaining hours of an overtime
     * @param totalSickDayCount the number of user's sick days available during a year
     * @param takenSickDayCount the number of user's taken sick days
     * @param notification      the date and time of sending an email warning about an incoming reset of remaining overtimes and sick days
     * @param token             the token for the Google oAuth
     * @param email             the user's email address
     * @param photo             the URL of a user's photo
     * @param role              the user's role
     * @param status            the user's authorization status
     * @throws IllegalArgumentException when the vacationCount, totalSickDayCount or takenSickDayCount are negative or first name, last name or email exceed the maximal permitted number of characters
     */
    public User(final String firstName, final String lastName, final Float vacationCount, final Integer totalSickDayCount, final Integer takenSickDayCount, final LocalDateTime notification, final String token, final String email, final String photo, final UserRole role, final Status status) throws IllegalArgumentException {
        this(0, firstName, lastName, vacationCount, totalSickDayCount, takenSickDayCount, notification, token, email, photo, null, role, status);
    }

    /**
     * Creates a new user and sets all his/hers attributes.
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
    public User(final long id, final String firstName, final String lastName, final Float vacationCount, final Integer totalSickDayCount, final Integer takenSickDayCount, final LocalDateTime notification, final String token, final String email, final String photo, final LocalDateTime creationDate, final UserRole role, final Status status) throws IllegalArgumentException {
        User.log.trace("Creating a new instance of the class User.");
        User.log.debug("User: id={}, firstName={}, lastName={}, vacationCount={}, totalSickDayCount={}, takenSickDayCount={}, notification={}, token={}, email={}, photo={}, creationDate={}, role={}, status={}", id, firstName, lastName, vacationCount, totalSickDayCount, takenSickDayCount, notification, token, email, photo, creationDate, role, status);

        this.id = id;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setVacationCount(vacationCount);
        this.setTotalSickDayCount(totalSickDayCount);
        this.setTakenSickDayCount(takenSickDayCount);
        this.setNotification(notification);
        this.token = token;
        this.setEmail(email);
        this.photo = photo;
        this.creationDate = creationDate;
        this.setRole(role);
        this.setStatus(status);
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
            User.log.warn("The length of the given first name exceeded a limit");
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
            User.log.warn("The length of the given last name exceeded a limit");
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
     * If the given number is negative or null the method throws an exception.
     *
     * @param vacationCount the new number of remaining hours of the overtime.
     * @throws IllegalArgumentException when the given value is negative
     */
    public void setVacationCount(final Float vacationCount) throws IllegalArgumentException {
        User.log.debug("Setting a new number of remaining overtime: {}", vacationCount);

        if(vacationCount == null) {
            User.log.warn("The number of remaining overtime must not be null");
            throw new IllegalArgumentException("vacation.null.error");
        }else if (vacationCount < 0) {
            User.log.warn("The number of remaining overtime must not be negative");
            throw new IllegalArgumentException("negative.vacation.error");
        }

        this.vacationCount = vacationCount;
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
     * If the given number is negative or null the method throws an exception.
     *
     * @param totalSickDayCount the new number of sick days available during the year
     * @throws IllegalArgumentException when the given value is negative
     */
    public void setTotalSickDayCount(final Integer totalSickDayCount) throws IllegalArgumentException {
        User.log.debug("Setting a new number of user's sick days available during a year: {}", totalSickDayCount);

        if (totalSickDayCount == null) {
            User.log.warn("The number of user's available sick days must not be null");
            throw new IllegalArgumentException("sick.day.null.error");
        } else if (totalSickDayCount < 0) {
            User.log.warn("The number of user's available sick days must not be negative");
            throw new IllegalArgumentException("negative.sick.day.error");
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
     * If the given number is negative or greater than the number of available sick days or null the method throws an exception.
     *
     * @param takenSickDayCount the new number of taken sick days
     * @throws IllegalArgumentException when the given value is negative
     */
    public void setTakenSickDayCount(final Integer takenSickDayCount) throws IllegalArgumentException {
        User.log.debug("Setting a new number of user's taken sick days: {}", takenSickDayCount);

        if (takenSickDayCount == null) {
            User.log.warn("The number number of user's taken sick days must not be null");
            throw new IllegalArgumentException("sick.day.null.error");
        } else if (takenSickDayCount < 0) {
            User.log.warn("The number number of user's taken sick days must not be negative");
            throw new IllegalArgumentException("negative.sick.day.error");
        } else if(takenSickDayCount > this.totalSickDayCount){
            User.log.warn("The number number of user's taken sick days must not greater than his/her available sick days");
            throw new IllegalArgumentException("taken.sick.day.count.error");
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
     * If the given notification is null the method throws an exception.
     *
     * @param notification the new date and time
     * @throws IllegalArgumentException when the given notification is null
     */
    public void setNotification(final LocalDateTime notification) throws IllegalArgumentException {
        User.log.debug("Setting a new date and time of sending an email warning: {}", notification);

        if(notification == null) {
            User.log.warn("The given notification must not be null");
            throw new IllegalArgumentException("notification.null.error");
        }

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
            User.log.warn("The length of the email address exceeded a limit");
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
     * If the given role is null the method throws an exception.
     *
     * @param role the new role
     * @throws IllegalArgumentException when the given role is null
     */
    public void setRole(final UserRole role) throws IllegalArgumentException {
        User.log.debug("Setting a new user's role: {}", role);

        if(role == null) {
            User.log.warn("The given role must not be null");
            throw new IllegalArgumentException("role.null.error");
        }

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

        if(status == null) {
            User.log.warn("The given status must not be null");
            throw new IllegalArgumentException("status.null.error");
        }

        this.status = status;
    }

    /**
     * Subtracts a difference of the given starting and the ending time of a vacation
     * from the number of user's available vacations. If some of the given parameters are null
     * or the times are not in order or there is no available vacation left the method throws an exception.
     *
     * @param from the starting time of a vacation
     * @param to the ending time of a vacation
     * @throws IllegalArgumentException when some of the given parameters are null
     *          or the times are not in order or there is no available vacation left
     */
    public void takeVacation(final LocalTime from, final LocalTime to) throws IllegalArgumentException{
        User.log.debug("Taking a vacation from {} to {}", from, to);

        if(from == null || to == null) {
            User.log.warn("A vacation has to have a starting and an ending time");
            throw new IllegalArgumentException("time.vacation.error");
        } else if (from.compareTo(to) >= 0) {
            User.log.warn("A vacation must not start after it ends. from={}, to={}", from, to);
            throw new IllegalArgumentException("time.order.error");
        }

        final float difference = from.until(to, MINUTES) / 60f;
        final float tempVacationCount = this.vacationCount - difference;

        if (tempVacationCount < 0) {
            User.log.warn("Cannot take a vacation, not enough available hours");
            throw new IllegalArgumentException("available.vacation.error");
        }

        this.vacationCount = tempVacationCount;
    }

    /**
     * Increases the number of taken sick days by one unless there are not any available sick days left. In that case
     * the method throws and exception.
     *
     * @throws IllegalArgumentException when there is not any available sick days left
     */
    public void takeSickDay() throws  IllegalArgumentException {
        User.log.trace("Taking a sick day");
        final int tempTakenSickDayCount = ++this.takenSickDayCount;

        if (tempTakenSickDayCount > this.totalSickDayCount) {
            User.log.warn("Cannot take a sick day, not enough available days");
            throw new IllegalArgumentException("available.sick.day.error");
        }

        this.takenSickDayCount = tempTakenSickDayCount;
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