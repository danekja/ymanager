package org.danekja.ymanager.business;

import org.danekja.ymanager.domain.*;
import org.danekja.ymanager.dto.DefaultSettings;
import org.danekja.ymanager.dto.*;
import org.danekja.ymanager.repository.RequestRepository;
import org.danekja.ymanager.repository.UserRepository;
import org.danekja.ymanager.repository.VacationRepository;
import org.danekja.ymanager.ws.rest.exceptions.RESTFullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ApiManager implements Manager {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private RequestRepository requestRepository;
    private UserRepository userRepository;
    private VacationRepository vacationRepository;

    @Autowired
    public ApiManager(RequestRepository requestRepository, UserRepository userRepository, VacationRepository vacationRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.vacationRepository = vacationRepository;
    }

    @Override
    public List<VacationRequest> getVacationRequests(Status status) throws RESTFullException {
        try {
            return status == null ? requestRepository.getAllVacationRequests() : requestRepository.getAllVacationRequests(status);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public List<AuthorizationRequest> getAuthorizationRequests(Status status) throws RESTFullException {
        try {
            return status == null ? requestRepository.getAllAuthorizations() : requestRepository.getAllAuthorizations(status);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public DefaultSettings getDefaultSettings() throws RESTFullException {
        try {
            return userRepository.getLastDefaultSettings().orElse(new DefaultSettings());
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public List<VacationDay> getUserCalendar(Long userId, LocalDate fromDate, LocalDate toDate, Status status) throws RESTFullException {
        try {
            List<VacationDay> vacations;
            if (status == null && toDate == null) {
                vacations = vacationRepository.getVacationDays(userId, fromDate);
            } else if (status == null) {
                vacations = vacationRepository.getVacationDays(userId, fromDate, toDate);
            } else if (toDate != null) {
                vacations = vacationRepository.getVacationDays(userId, fromDate, toDate, status);
            } else {
                vacations = vacationRepository.getVacationDays(userId, fromDate, status);
            }

            return vacations;

        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public void createSettings(DefaultSettings settings) throws RESTFullException {
        try {
            org.danekja.ymanager.domain.DefaultSettings defaultSettings = new org.danekja.ymanager.domain.DefaultSettings();
            defaultSettings.setSickDayCount(settings.getSickDayCount());
            defaultSettings.setNotification(settings.getNotification());
            userRepository.insertSettings(defaultSettings);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public void createVacation(Long userId, VacationDay vacationDay) throws RESTFullException {

        if (vacationDay.getDate().isBefore(LocalDate.now())) {
            throw new RESTFullException("Vacation cannot be token in past.", "vacation.past.error");
        }

        try {

            if (vacationRepository.isExistVacationForUser(userId, vacationDay.getDate())) {
                throw new RESTFullException("Cannot take a double vacation for the same day.", "vacation.double.error");
            }

            User user = userRepository.getUser(userId);
            vacationDay.setStatus(user.getRole() == UserRole.EMPLOYER ? Status.ACCEPTED : Status.PENDING);

            Vacation vacation = new Vacation();
            vacation.setDate(vacationDay.getDate());
            vacation.setFrom(vacationDay.getFrom());
            vacation.setTo(vacationDay.getTo());
            vacation.setStatus(vacationDay.getStatus());
            vacation.setType(vacationDay.getType());

            if (vacation.getType() == VacationType.VACATION) {
                user.takeVacation(vacation.getFrom(), vacation.getTo());
            } else {
                user.takeSickDay();
            }

            vacationRepository.insertVacationDay(userId, vacation);
            userRepository.updateUser(user);

        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    private void changeSettingsByEmployee(User user, UserSettings settings, DefaultSettings defaultSettings) {
        if (settings.getNotification() != null && !settings.getNotification().equals(user.getNotification())) {
            user.setNotification(settings.getNotification());
        }

        if (user.getTotalSickDayCount().equals(defaultSettings.getSickDayCount())) {
            user.setTotalSickDayCount(null);
        }
    }

    private void changeSettingsByEmployer(User user, UserSettings settings, DefaultSettings defaultSettings) {

        if (settings.getRole() != null && !settings.getRole().equals(user.getRole())) {
            user.setRole(settings.getRole());
        }

        if (settings.getSickDayCount() != null) {

            if (user.getTakenSickDayCount() > settings.getSickDayCount()) {
                throw new IllegalArgumentException("settings.sick.day.lt.taken.error");
            }

            if (settings.getSickDayCount().equals(defaultSettings.getSickDayCount())) {
                user.setTotalSickDayCount(null);
            } else {
                user.setTotalSickDayCount(settings.getSickDayCount());
            }
        } else if (user.getTotalSickDayCount().equals(defaultSettings.getSickDayCount())) {
            user.setTotalSickDayCount(null);
        }

        if (settings.getVacationCount() != null) {
            user.setVacationCount(user.getVacationCount() + settings.getVacationCount());
        }

        if (settings.getNotification() != null && !settings.getNotification().equals(user.getNotification())) {
            user.setNotification(settings.getNotification());
        }
    }

    @Override
    public void changeSettings(Long userId, UserSettings settings) throws RESTFullException {

        try {
            UserRole invokedUserPermission = userRepository.getPermission(userId);
            boolean invokedUserIsAdmin = invokedUserPermission.equals(UserRole.EMPLOYER);
            DefaultSettings defaultSettings = getDefaultSettings();

            User userForChange = userRepository.getUser(settings.getId() == null ? userId : settings.getId());

            if (invokedUserIsAdmin) {
                changeSettingsByEmployer(userForChange, settings, defaultSettings);
            } else {
                changeSettingsByEmployee(userForChange, settings, defaultSettings);
            }

            userRepository.updateUserSettings(userForChange);

        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public void changeVacation(Long userId, VacationDay vacationDay) throws RESTFullException {
        try {
            Optional<Vacation> vacation = vacationRepository.getVacationDay(vacationDay.getId());
            if (vacation.isPresent()) {
                vacation.get().setDate(vacationDay.getDate());
                vacation.get().setStatus(vacationDay.getStatus());
                vacation.get().setType(vacationDay.getType());
                vacation.get().setTime(vacationDay.getFrom(), vacationDay.getTo());
                vacationRepository.updateVacationDay(vacation.get());
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public void changeRequest(RequestType type, BasicRequest request) throws RESTFullException {
        try {
            switch (type) {
                case VACATION: {

                    Optional<Vacation> vacationDayOpt = vacationRepository.getVacationDay(request.getId());

                    if (!vacationDayOpt.isPresent()) {
                        throw new RESTFullException("", "");
                    }

                    Vacation vacation = vacationDayOpt.get();

                    if (request.getStatus().equals(Status.REJECTED)) {
                        User user = userRepository.getUser(vacation.getUserId());

                        switch (vacation.getType()) {
                            case VACATION: {
                                user.addVacationCount(vacation.getFrom(), vacation.getTo());
                                userRepository.updateUserTakenVacation(user);
                            } break;
                            case SICK_DAY: {
                                user.addTakenSickDayCount(-1);
                                userRepository.updateUserTakenSickDay(user);
                            } break;
                        }
                    }

                    requestRepository.updateVacationRequest(vacation.getId(), request.getStatus());

                } break;
                case AUTHORIZATION: {
                    requestRepository.updateAuthorization(request);
                } break;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        } catch (IllegalArgumentException e) {
            throw new RESTFullException("Cannot create a domain object.", e.getMessage());
        }
    }

    @Override
    public void deleteVacation(Long userId, Long vacationId) throws RESTFullException {
        try {
            User user = userRepository.getUser(userId);
            Optional<Vacation> vacation = vacationRepository.getVacationDay(vacationId);

            if (!vacation.isPresent()) {
                throw new RESTFullException("", "");
            }

            Vacation vacationDay = vacation.get();

            if (vacationDay.getDate().isAfter(LocalDate.now())) {
                if (!vacationDay.getStatus().equals(Status.REJECTED)) {
                    switch (vacationDay.getType()) {
                        case VACATION: {
                            user.addVacationCount(vacationDay.getFrom(), vacationDay.getTo());
                            userRepository.updateUserTakenVacation(user);
                        }
                        break;
                        case SICK_DAY: {
                            user.addTakenSickDayCount(-1);
                            userRepository.updateUserTakenSickDay(user);
                        }
                        break;
                    }
                }
                vacationRepository.deleteVacationDay(vacationDay.getId());
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }
}
