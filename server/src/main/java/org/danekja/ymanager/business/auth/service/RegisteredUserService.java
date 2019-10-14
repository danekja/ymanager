package org.danekja.ymanager.business.auth.service;

import org.danekja.ymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RegisteredUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public RegisteredUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails details = userRepository.getUser(username);

        //interface contract enforces this behavior
        if (details == null) {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }

        return details;
    }
}
