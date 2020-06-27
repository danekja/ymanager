package org.danekja.ymanager.ws.rest;

import org.danekja.ymanager.business.UserManager;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.User;
import org.danekja.ymanager.dto.BasicProfileUserDTO;
import org.danekja.ymanager.dto.FullUserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Users collection of WS API.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserManager userManager;

    @Autowired
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public List<BasicProfileUserDTO> users(@RequestParam(value = "status", required = false) Status status) {
        return userManager.getUsers(status);
    }


    /**
     * Returns currently authenticated user.
     *
     * @param auth current authentication object
     * @return user information object
     */
    @GetMapping("/current/profile")
    public FullUserProfileDTO getCurrentUser(Authentication auth) throws Exception {

        if (auth instanceof AnonymousAuthenticationToken
                || auth.getPrincipal() == null
                || !(auth.getPrincipal() instanceof User)) {
            return null;
        }

        return getUserProfile(((User) auth.getPrincipal()).getId());
    }

    @GetMapping("/{id}/profile")
    public FullUserProfileDTO getUserProfile(@PathVariable("id") Long id) throws Exception {
        User u = userManager.getUser(id);

        return new FullUserProfileDTO(u);
    }
}
