package org.danekja.ymanager.business.impl;

import org.danekja.ymanager.business.AuthorizationService;
import org.danekja.ymanager.domain.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * {@link AuthorizationService} implementation based on Spring Security framework.
 */
@Service
public class SpringAuthorizationService implements AuthorizationService {

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth instanceof AnonymousAuthenticationToken) ? (User) auth.getPrincipal() : null;
    }

    @Override
    public boolean isSignedIn() {
        return getCurrentUser() != null;
    }

}
