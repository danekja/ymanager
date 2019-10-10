package org.danekja.ymanager.business.impl;

import org.danekja.ymanager.business.UserManager;
import org.danekja.ymanager.domain.User;
import org.danekja.ymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Default implementation of User-related application logic.
 * <p>
 * Also implements {@link UserDetailsService} to allow integration with Spring Security framework.
 */
@Service
public class DefaultUserManager implements UserManager, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public DefaultUserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String email) {
        return userRepository.getUser(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails details = getUser(username);

        //interface contract enforces this behavior
        if (details == null) {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }

        return details;
    }
}
