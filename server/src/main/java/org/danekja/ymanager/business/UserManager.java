package org.danekja.ymanager.business;

import org.danekja.ymanager.domain.User;

/**
 * Interface for application logic handler of User entities.
 */
public interface UserManager {

    /**
     * Gets user by email (username)
     *
     * @param email email value, used as search key
     * @return found user Object or null
     */
    User getUser(String email);
}
