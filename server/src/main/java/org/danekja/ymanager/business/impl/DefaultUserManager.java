package org.danekja.ymanager.business.impl;

import org.danekja.ymanager.business.UserManager;
import org.danekja.ymanager.business.auth.anot.IsEmployer;
import org.danekja.ymanager.business.auth.anot.IsOwner;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.User;
import org.danekja.ymanager.dto.BasicProfileUser;
import org.danekja.ymanager.repository.UserRepository;
import org.danekja.ymanager.repository.VacationRepository;
import org.danekja.ymanager.ws.rest.exceptions.RESTFullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Default implementation of User-related application logic.
 * <p>
 * Also implements {@link UserDetailsService} to allow integration with Spring Security framework.
 */
@Service
public class DefaultUserManager implements UserManager {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultUserManager.class);

    private final UserRepository userRepository;
    private final VacationRepository vacationRepository;

    @Autowired
    public DefaultUserManager(UserRepository userRepository, VacationRepository vacationRepository) {
        this.userRepository = userRepository;
        this.vacationRepository = vacationRepository;
    }

    @Override
    @IsOwner
    public User getUser(Long userId) {
        return userRepository.getUser(userId);
    }

    @Override
    @IsEmployer
    public List<BasicProfileUser> getUsers(Status status) throws RESTFullException {
        try {
            List<BasicProfileUser> users = userRepository.getAllBasicUsers(status == null ? Status.ACCEPTED : status);

            LocalDate today = LocalDate.now();
            LocalDate weekBefore = today.minusWeeks(1);
            LocalDate weekAfter = today.plusWeeks(1);
            for (BasicProfileUser user : users) {
                user.setCalendar(vacationRepository.getVacationDays(user.getId(), weekBefore, weekAfter));
            }

            return users;

        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

}
