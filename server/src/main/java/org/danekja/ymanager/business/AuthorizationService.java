package org.danekja.ymanager.business;

import org.danekja.ymanager.domain.User;

/**
 * Helper service which helps to get information regarding currently authenticated user and his permissions.
 */
public interface AuthorizationService {
    /**
     * @return currently signed-in user or null
     */
    User getCurrentUser();

    /**
     * @return true if there is a user associated with the current session
     */
    boolean isSignedIn();
}
