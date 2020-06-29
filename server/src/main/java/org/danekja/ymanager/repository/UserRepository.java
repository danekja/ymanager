package org.danekja.ymanager.repository;

import org.danekja.ymanager.domain.*;

import java.util.List;

/**
 * An instance of the class UserRepository handles queries which selects and updates users and their settings in a database.
 */
public interface UserRepository {
    /**
     * Gets users with the given authorization status from a database. If there aren't any users with
     * the given authorization status in the database, the method returns an empty list.
     *
     * @param status The authentication status.
     * @return A list of all users with the given status.
     */
    List<RegisteredUser> getUsers(final Status status);

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
