package org.danekja.ymanager.repository;

import org.danekja.ymanager.domain.*;
import org.danekja.ymanager.dto.BasicProfileUserDTO;
import org.danekja.ymanager.dto.FullUserProfileDTO;

import java.util.List;

/**
 * An instance of the class UserRepository handles queries which selects and updates users and their settings in a database.
 */
public interface UserRepository {
    /**
     * Gets basic profile of each user from a database. The basic profile contains default, the most important
     * informations which helps identify a user like a name or photo. Every line of output is converted to a BasicProfileUser
     * object filled with data from the database. If there aren't any users in the database, the method returns an empty list.
     *
     * @return A list of all basic profiles.
     */
    List<BasicProfileUserDTO> getAllBasicUsers();

    /**
     * Gets basic profile of each user with the given authorization status from a database. The basic profile contains
     * default, the most important informations which helps identify a user like a name or a photo.
     * Every line of output is converted to a BasicProfileUser object filled with data from the database.
     * If there aren't any users with the given authorization status in the database, the method returns an empty list.
     *
     * @param status The authentication status.
     * @return A list of all basic profiles with the given status.
     */
    List<BasicProfileUserDTO> getAllBasicUsers(final Status status);

    UserRole getPermission(Long id);

    /**
     *
     * @param id
     * @return
     */
    FullUserProfileDTO getFullUser(final long id);

    /**
     * Gets user from database based on its email
     *
     * @param email email value, used as search key
     * @return found user object or null (if not found)
     */
    RegisteredUser getUser(final String email);

    /**
     * Gets user from database based on its id
     *
     * @param id id value, used as search key
     * @return found user object or null (if not found)
     */
    RegisteredUser getUser(final long id);

    /**
     * Gets user data by user's id
     *
     * @param id TODO replace by subject Id for google and numeric id for registered (multiple queries)
     * @return
     */
    UserData getUserData(final long id);

    void updateUser(final User user);

    long insertUser(final User user);

    void insertSettings(final DefaultSettings settings);

    void updateUserSettings(User user);

    void updateUserTakenVacation(User user);

    void updateUserTakenSickDay(User user);
}
