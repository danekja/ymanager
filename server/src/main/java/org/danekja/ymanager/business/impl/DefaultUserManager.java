package org.danekja.ymanager.business.impl;

import org.danekja.ymanager.business.UserManager;
import org.danekja.ymanager.business.auth.anot.IsEmployer;
import org.danekja.ymanager.business.auth.anot.IsOwner;
import org.danekja.ymanager.domain.*;
import org.danekja.ymanager.dto.BasicProfileUser;
import org.danekja.ymanager.repository.SettingsRepository;
import org.danekja.ymanager.repository.UserRepository;
import org.danekja.ymanager.repository.VacationRepository;
import org.danekja.ymanager.ws.rest.exceptions.RESTFullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
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
    private final SettingsRepository settingsRepository;

    @Autowired
    public DefaultUserManager(UserRepository userRepository, VacationRepository vacationRepository, SettingsRepository settingsRepository) {
        this.userRepository = userRepository;
        this.vacationRepository = vacationRepository;
        this.settingsRepository = settingsRepository;
    }

    @Override
    @IsOwner
    public User getUser(Long userId) {
        return userRepository.getUser(userId);
    }

    @Override
    public GoogleUser getUser(OidcIdToken token) {
        //TODO replace with better user object creation after we update the data model - registered and oauth users should be
        //TODO in separate tables. We do this here because we need the ID value set
        User user = userRepository.getUser(token.getEmail());

        return user != null ? new GoogleUser(user.getId(), user.getUserData(), token) : null;
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

    @Override
    public GoogleUser registerUser(OidcIdToken token) {
        DefaultSettings ds = settingsRepository.get();
        //TODO state is Accepted until #45 is resolved
        GoogleUser user = new GoogleUser(null, new UserData(ds.getSickDayCount(), ds.getNotification(), UserRole.EMPLOYEE, Status.ACCEPTED), token);

        long id = userRepository.insertUser(user);
        UserData data = userRepository.getUserData(id);

        return new GoogleUser(id, data, user.getIdToken());
    }


}
