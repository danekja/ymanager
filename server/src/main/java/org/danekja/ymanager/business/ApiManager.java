package org.danekja.ymanager.business;

import org.danekja.ymanager.business.auth.anot.CanModifyVacation;
import org.danekja.ymanager.business.auth.anot.IsEmployer;
import org.danekja.ymanager.business.auth.anot.IsOwner;
import org.danekja.ymanager.business.auth.anot.IsSignedIn;
import org.danekja.ymanager.domain.*;
import org.danekja.ymanager.dto.BasicRequestDTO;
import org.danekja.ymanager.dto.UserSettingsDTO;
import org.danekja.ymanager.dto.VacationDayDTO;
import org.danekja.ymanager.repository.RequestRepository;
import org.danekja.ymanager.repository.SettingsRepository;
import org.danekja.ymanager.repository.UserRepository;
import org.danekja.ymanager.repository.VacationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.DenyAll;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ApiManager implements Manager {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final VacationRepository vacationRepository;
    private final SettingsRepository settingsRepository;

    private final AuthorizationService authService;

    @Autowired
    public ApiManager(RequestRepository requestRepository, UserRepository userRepository, VacationRepository vacationRepository, SettingsRepository settingsRepository, AuthorizationService authService) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.vacationRepository = vacationRepository;
        this.settingsRepository = settingsRepository;
        this.authService = authService;
    }

    @Override
    @IsEmployer
    public List<VacationRequest> getVacationRequests(Status status) {
        if (status == null) {
            return requestRepository.getAllVacationRequests();
        } else {
            return requestRepository.getAllVacationRequests(status);
        }
    }

    @Override
    @IsEmployer
    public List<AuthorizationRequest> getAuthorizationRequests(Status status) {
        if (status == null) {
            return requestRepository.getAllAuthorizations();
        } else {
            return requestRepository.getAllAuthorizations(status);
        }
    }

    @Override
    @IsSignedIn
    public DefaultSettings getDefaultSettings() {
        return settingsRepository.get();
    }

    @Override
    @IsOwner
    public List<Vacation> getUserCalendar(long userId, LocalDate fromDate, LocalDate toDate, Status status) {
        List<Vacation> vacations;
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
    }

    @Override
    @IsEmployer
    public void createSettings(DefaultSettings settings) {
        DefaultSettings defaultSettings = new DefaultSettings();
        defaultSettings.setSickDayCount(settings.getSickDayCount());
        defaultSettings.setNotification(settings.getNotification());
        userRepository.insertSettings(defaultSettings);
    }

    @Override
    @IsOwner
    public void createVacation(long userId, VacationDayDTO vacationDayDTO) {
        if (vacationDayDTO.getDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vacation cannot be token in past.");
        }

        if (vacationRepository.isExistVacationForUser(userId, vacationDayDTO.getDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot take a double vacation for the same day.");
        }

        User user = userRepository.getUser(userId);
        vacationDayDTO.setStatus(user.getRole() == UserRole.EMPLOYER ? Status.ACCEPTED : Status.PENDING);

        Vacation vacation = new Vacation();
        vacation.setDate(vacationDayDTO.getDate());
        vacation.setFrom(vacationDayDTO.getFrom());
        vacation.setTo(vacationDayDTO.getTo());
        vacation.setStatus(vacationDayDTO.getStatus());
        vacation.setType(vacationDayDTO.getType());

        if (vacation.getType() == VacationType.VACATION) {
            user.takeVacation(vacation.getFrom(), vacation.getTo());
        } else {
            user.takeSickDay();
        }

        vacationRepository.insertVacationDay(userId, vacation);
        userRepository.updateUser(user);
    }

    private void changeSettingsByEmployee(User user, UserSettingsDTO settings, DefaultSettings defaultSettings) {
        if (settings.getNotification() != null && !settings.getNotification().equals(user.getNotification())) {
            user.setNotification(settings.getNotification());
        }

        if (user.getTotalSickDayCount().equals(defaultSettings.getSickDayCount())) {
            user.setTotalSickDayCount(null);
        }
    }

    private void changeSettingsByEmployer(User user, UserSettingsDTO settings, DefaultSettings defaultSettings) {

        if (settings.getRole() != null && !settings.getRole().equals(user.getRole())) {
            user.setRole(settings.getRole());
        }

        if (settings.getSickDayCount() != null) {

            if (user.getTakenSickDayCount() > settings.getSickDayCount()) {
                throw new IllegalArgumentException("You cannot set a number of sick day lower than is taken at this moment.");
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
    @IsOwner
    public void changeSettings(long userId, UserSettingsDTO settings) {
        UserRole invokedUserPermission = authService.getCurrentUser().getRole();
        boolean invokedUserIsAdmin = invokedUserPermission.equals(UserRole.EMPLOYER);
        DefaultSettings defaultSettings = getDefaultSettings();

        User userForChange = userRepository.getUser(settings.getId() == null ? userId : settings.getId());

        if (invokedUserIsAdmin) {
            changeSettingsByEmployer(userForChange, settings, defaultSettings);
        } else {
            changeSettingsByEmployee(userForChange, settings, defaultSettings);
        }

        userRepository.updateUserSettings(userForChange);
    }

    @Override
    //TODO not called anyway, allow after reimplementation
    @DenyAll
    public void changeVacation(VacationDayDTO vacationDayDTO) {
        Optional<Vacation> vacation = vacationRepository.getVacationDay(vacationDayDTO.getId());
        if (vacation.isPresent()) {
            vacation.get().setDate(vacationDayDTO.getDate());
            vacation.get().setStatus(vacationDayDTO.getStatus());
            vacation.get().setType(vacationDayDTO.getType());
            vacation.get().setTime(vacationDayDTO.getFrom(), vacationDayDTO.getTo());
            vacationRepository.updateVacationDay(vacation.get());
        }
    }

    @Override
    @IsEmployer
    public void changeRequest(RequestType type, BasicRequestDTO request) {
        switch (type) {
            case VACATION:
                Optional<Vacation> vacationDayOpt = vacationRepository.getVacationDay(request.getId());
                if (!vacationDayOpt.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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
                break;

            case AUTHORIZATION:
                requestRepository.updateAuthorization(request);
                break;
        }
    }

    @Override
    @CanModifyVacation
    public void deleteVacation(long vacationId) {
        Optional<Vacation> vacation = vacationRepository.getVacationDay(vacationId);
        if (!vacation.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Vacation vacationDay = vacation.get();

        if (!vacationDay.getDate().isAfter(LocalDate.now())) {
            return;
        }

        if (vacationDay.getStatus().equals(Status.REJECTED)) {
            return;
        }

        User user = userRepository.getUser(vacationDay.getUserId());

        switch (vacationDay.getType()) {
            case VACATION:
                user.addVacationCount(vacationDay.getFrom(), vacationDay.getTo());
                userRepository.updateUserTakenVacation(user);
                break;

            case SICK_DAY:
                user.addTakenSickDayCount(-1);
                userRepository.updateUserTakenSickDay(user);
                break;
        }

        vacationRepository.deleteVacationDay(vacationDay.getId());
    }
}
