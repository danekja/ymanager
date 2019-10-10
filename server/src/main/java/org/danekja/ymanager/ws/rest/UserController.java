package org.danekja.ymanager.ws.rest;

import org.danekja.ymanager.business.UserManager;
import org.danekja.ymanager.domain.User;
import org.danekja.ymanager.dto.FullUserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Users collection of WS API.
 */
@RestController("/users")
public class UserController {

    private final UserManager manager;

    @Autowired
    public UserController(UserManager manager) {
        this.manager = manager;
    }

    /**
     * Returns currently authenticated user.
     *
     * @param auth current authentication object
     * @return user information object
     */
    @GetMapping("/current/profile")
    public FullUserProfile getCurrentUser(Authentication auth) {

        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }

        User user = manager.getUser(auth.getName());
        return user != null ? new FullUserProfile(user) : null;
    }
}
