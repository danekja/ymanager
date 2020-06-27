package org.danekja.ymanager.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public class RegisteredUser extends User implements UserDetails {

    /**
     * The maximal length of a name.
     */
    private static final int NAME_LENGTH = 45;

    /**
     * The maximal length of an email address.
     */
    private static final int EMAIL_ADDRESS_LENGTH = 100;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

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
    private LocalDateTime creationDate;

    /**
     * Granted authorities.
     */
    private final Collection<? extends GrantedAuthority> authorities;

    public RegisteredUser() {
        this.authorities = Collections.emptySet();
    }

    public RegisteredUser(Long id, String firstName, String lastName, String email, String photo, LocalDateTime creationDate, UserData userData) {
        super(id, userData);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photo = photo;
        this.creationDate = creationDate;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(userData.getRole().name()));
    }

    /*
########################### UserDetails API #################################
 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        //unsupported functionality
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //unsupported functionality
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //unsupported functionality
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userData.getStatus() == Status.ACCEPTED;
    }

    /*
    ################# UserDetails API (END) #########################
     */

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
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

        if (firstName == null) {
            User.log.warn("The given first name must not be null");
            throw new IllegalArgumentException("The first name has to be filled in.");
        } else if (firstName.length() > NAME_LENGTH) {
            User.log.warn("The length of the given first name exceeded a limit");
            throw new IllegalArgumentException("The length of a name mustn't exceed 45.");
        }

        this.firstName = firstName;
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

        if (lastName == null) {
            User.log.warn("The given last name must not be null");
            throw new IllegalArgumentException("The last name has to be filled in.");
        } else if (lastName.length() > NAME_LENGTH) {
            User.log.warn("The length of the given last name exceeded a limit");
            throw new IllegalArgumentException("The length of a name mustn't exceed 45.");
        }

        this.lastName = lastName;
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

        if (email == null) {
            User.log.warn("The given email must not be null");
            throw new IllegalArgumentException("The email address has to be filled in.");
        } else if (email.length() > EMAIL_ADDRESS_LENGTH) {
            User.log.warn("The length of the email address exceeded a limit");
            throw new IllegalArgumentException("The length of an email address mustn't exceed 100.");
        }

        this.email = email;
    }

    /**
     * Replaces the URL of a user's photo with the given link.
     *
     * @param photo the new URL of the user's photo
     */
    public void setPhoto(final String photo) {
        User.log.debug("Setting a new url of a photo: {}", photo);

        this.photo = photo == null ? User.DEFAULT_PHOTO : photo;
    }

    /**
     * Replaces the user's creation date with the given date and time.
     *
     * @param creationDate the new creation date
     */
    public void setCreationDate(final LocalDateTime creationDate) {
        User.log.debug("Setting a new user's creation date: {}", creationDate);

        this.creationDate = creationDate;
    }
}
