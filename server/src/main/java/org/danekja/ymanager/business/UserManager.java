package org.danekja.ymanager.business;

import org.danekja.ymanager.domain.GoogleUser;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.User;
import org.danekja.ymanager.dto.BasicProfileUser;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import java.util.List;

/**
 * Interface for application logic handler of User entities.
 */
public interface UserManager {

    /**
     * List all users with given status.
     * <p>
     * TODO: refactor to return domain class instead of DTO as part of #32
     *
     * @param status status filter value
     * @return list of users or empty list if none found
     */
    List<BasicProfileUser> getUsers(Status status);

    /**
     * Gets user by id (PK)
     *
     * @param userId id value, used as search key
     * @return found user Object or null
     */
    User getUser(Long userId);

    GoogleUser registerUser(OidcIdToken user);

    GoogleUser getUser(OidcIdToken token);
}
