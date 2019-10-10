package org.danekja.ymanager.ws.rest;

import org.danekja.ymanager.business.Manager;
import org.danekja.ymanager.domain.User;
import org.danekja.ymanager.util.localization.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Users collection of WS API.
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    private final Manager manager;

    @Autowired
    public UserController(Manager manager) {
        this.manager = manager;
    }

    /**
     * Returns currently authenticated user.
     *
     * @param auth current authentication object
     * @return user information object
     */
    @GetMapping("/current/profile")
    public ResponseEntity getCurrentUser(Authentication auth) {

        if (auth instanceof AnonymousAuthenticationToken
                || auth.getPrincipal() == null
                || !(auth.getPrincipal() instanceof User)) {
            return null;
        }

        return getUserProfile(((User) auth.getPrincipal()).getId(), null);
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity getUserProfile(
            @PathVariable("id") Long id,
            @RequestParam(value = "lang", required = false) String lang) {
        return handle(Language.getLanguage(lang), () ->
                manager.getUserProfile(id)
        );
    }
}
