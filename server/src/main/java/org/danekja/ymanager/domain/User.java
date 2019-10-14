package org.danekja.ymanager.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * The domain class {@code User} represents a single record in the 'end_user' table of a database.
 * User holds all informations about a user which is logged to the application.
 */
public abstract class User {

    /**
     * The url of a generic photo.
     */
    protected static final String DEFAULT_PHOTO = "https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg";

    /**
     * The logger.
     */
    protected static final Logger log = LoggerFactory.getLogger(User.class);

    /**
     * The user's ID.
     */
    protected Long id;

    /**
     * The number of user's remaining hours of an overtime.
     */
    protected Float vacationCount;

    /**
     * The number of user's sick days available during a year.
     */
    protected Integer totalSickDayCount;

    /**
     * The number of user's taken sick days.
     */
    protected Integer takenSickDayCount;

    /**
     * The date and time of sending an email warning about an incoming reset of remaining overtimes and sick days.
     */
    protected LocalDateTime notification;

    /**
     * The token for the Google oAuth.
     */
    protected String token;

    /**
     * The user's role.
     */
    protected UserRole role;

    /**
     * The user's authorization status.
     */
    protected Status status;

    public User() {
    }

    public User(Long id, Float vacationCount, Integer totalSickDayCount, Integer takenSickDayCount, LocalDateTime notification, String token, UserRole role, Status status) {
        this.id = id;
        this.vacationCount = vacationCount;
        this.totalSickDayCount = totalSickDayCount;
        this.takenSickDayCount = takenSickDayCount;
        this.notification = notification;
        this.token = token;
        this.role = role;
        this.status = status;
    }

    /**
     * Returns the user's ID.
     *
     * @return the user's ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Replaces the user's ID with the given one.
     *
     * @param id the given ID
     */
    public void setId(final Long id) {
        User.log.debug("Setting a new id: {}", id);

        this.id = id;
    }

    /**
     * Returns the user's first name.
     *
     * @return the user's first name
     */
    public abstract String getFirstName();

    /**
     * Returns the user's last name.
     *
     * @return the user's last name
     */
    public abstract String getLastName();

    /**
     * Returns the number of user's remaining hours of an overtime.
     *
     * @return the number of user's remaining hours of the overtime
     */
    public Float getVacationCount() {
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
     * Adds the given amount to the number of user's available vacations. If the given parameter is null
     * or a result of the addition is negative the method throws an exception.
     *
     * @param value the given amount that is going to be added
     * @throws IllegalArgumentException when he given parameter is null or a result of the addition is negative
     */
    public void addVacationCount(final Float value) {
        User.log.debug("Adding the given number of hours {} to the vacation", value);

        if(value == null) {
            User.log.warn("The given value must not be null");
            throw new IllegalArgumentException("vacation.null.error");
        } else if (this.vacationCount + value < 0) {
            User.log.warn("The number of remaining overtime must not be negative");
            throw new IllegalArgumentException("negative.vacation.error");
        }

        this.vacationCount += value;
    }

    /**
     * Adds a difference of the given starting and the ending time of a vacation
     * to the number of user's available vacations. If some of the given parameters are null
     * or the times are not in order the method throws an exception.
     *
     * @param from the starting time of a vacation
     * @param to the ending time of a vacation
     * @throws IllegalArgumentException when some of the given parameters are null
     *          or the times are not in order
     */
    public void addVacationCount(final LocalTime from, final LocalTime to) {
        User.log.debug("Adding a vacation from {} to {}", from, to);

        if(from == null || to == null) {
            User.log.warn("A vacation has to have a starting and an ending time");
            throw new IllegalArgumentException("time.vacation.error");
        } else if (from.compareTo(to) >= 0) {
            User.log.warn("A vacation must not start after it ends. from={}, to={}", from, to);
            throw new IllegalArgumentException("time.order.error");
        }

        final float difference = from.until(to, MINUTES) / 60f;
        this.vacationCount += difference;
    }

    /**
     * Returns the number of user's sick days available during a year.
     *
     * @return the number of user's sick days available during the year
     */
    public Integer getTotalSickDayCount() {
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

        if (totalSickDayCount != null && totalSickDayCount < 0) {
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
    public Integer getTakenSickDayCount() {
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
     * Adds the given amount to the number of user's remaining hours of an overtime.
     * If the given number is null or a result of the addition is negative the method throws an exception.
     *
     * @param value the given amount that is going to be added
     * @throws IllegalArgumentException when the given number is null or a result of the addition is negative
     */
    public void addTakenSickDayCount(final Integer value) throws IllegalArgumentException {
        User.log.debug("Increasing the number of remaining overtime by {}", value);

        if (value == null) {
            User.log.warn("The given value must not be null");
            throw new IllegalArgumentException("vacation.null.error");
        } else if (this.takenSickDayCount + value < 0) {
            User.log.warn("The number number of user's taken sick days must not be negative");
            throw new IllegalArgumentException("negative.sick.day.error");
        } else if (this.takenSickDayCount + value > this.totalSickDayCount) {
            User.log.warn("The number number of user's taken sick days must not greater than his/her available sick days");
            throw new IllegalArgumentException("taken.sick.day.count.error");
        }

        this.takenSickDayCount += value;
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

        if (token == null) {
            User.log.warn("The given token must not be null");
            throw new IllegalArgumentException("token.null.error");
        }

        this.token = token;
    }

    /**
     * Returns the user's email address.
     *
     * @return the user's email address
     */
    public abstract String getEmail();

    /**
     * Returns the URL of a user's photo.
     *
     * @return the URL of the user's photo
     */
    public String getPhoto() {
        return DEFAULT_PHOTO;
    }

    ;


    /**
     * Returns the date and time of a user's creation.
     *
     * @return the date and time of the user's creation
     */
    public abstract LocalDateTime getCreationDate();

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
                "id=" + id +
                ", vacationCount=" + vacationCount +
                ", totalSickDayCount=" + totalSickDayCount +
                ", takenSickDayCount=" + takenSickDayCount +
                ", notification=" + notification +
                ", token='" + token + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}