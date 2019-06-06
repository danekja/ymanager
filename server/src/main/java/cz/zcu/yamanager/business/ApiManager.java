package cz.zcu.yamanager.business;

import cz.zcu.yamanager.domain.User;
import cz.zcu.yamanager.dto.*;
import cz.zcu.yamanager.repository.RequestRepository;
import cz.zcu.yamanager.repository.UserRepository;
import cz.zcu.yamanager.repository.VacationRepository;
import cz.zcu.yamanager.ws.rest.RESTFullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ApiManager implements Manager {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private static final int WEEK_LENGTH = 7;

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
    public List<BasicProfileUser> getUsers(Status status) throws RESTFullException {
        try {
            List<BasicProfileUser> users = status == null ? this.userRepository.getAllBasicUsers() : this.userRepository.getAllBasicUsers(status);

            LocalDate today = LocalDate.now();
            LocalDate weekBefore = today.minusDays(ApiManager.WEEK_LENGTH);
            LocalDate weekAfter = today.plusDays(ApiManager.WEEK_LENGTH);
            for (BasicProfileUser user : users) {
                user.setCalendar(this.vacationRepository.getVacationDays(user.getId(), weekBefore, weekAfter));
            }

            return users;
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public List<VacationRequest> getVacationRequests(Status status) throws RESTFullException {
        try {
            return status == null ? this.requestRepository.getAllVacationRequests() : this.requestRepository.getAllVacationRequests(status);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public List<AuthorizationRequest> getAuthorizationRequests(Status status) throws RESTFullException {
        try {
            return status == null ? this.requestRepository.getAllAuthorizations() : this.requestRepository.getAllAuthorizations(status);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public FullUserProfile getUserProfile(Long userId) throws RESTFullException {
        try {
            return this.userRepository.getFullUser(userId);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public DefaultSettings getDefaultSettings() throws RESTFullException {
        try {
            return this.userRepository.getLastDefaultSettings().orElse(new DefaultSettings());
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
                vacations = this.vacationRepository.getVacationDays(userId, fromDate);
            } else if (status == null) {
                vacations = this.vacationRepository.getVacationDays(userId, fromDate, toDate);
            } else if (toDate != null) {
                vacations = this.vacationRepository.getVacationDays(userId, fromDate, toDate, status);
            } else {
                vacations = this.vacationRepository.getVacationDays(userId, fromDate, status);
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
            cz.zcu.yamanager.domain.DefaultSettings defaultSettings = new cz.zcu.yamanager.domain.DefaultSettings();
            defaultSettings.setSickDayCount(settings.getSickDayCount());
            defaultSettings.setNotification(settings.getNotification());
            this.userRepository.insertSettings(defaultSettings);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public void createVacation(Long userId, VacationDay vacationDay) throws RESTFullException {
        try {
            User user = this.userRepository.getUser(userId);
            vacationDay.setStatus(user.getRole() == UserRole.EMPLOYER ? Status.ACCEPTED : Status.PENDING);

            cz.zcu.yamanager.domain.VacationDay vacation = new cz.zcu.yamanager.domain.VacationDay();
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

            this.vacationRepository.insertVacationDay(userId, vacation);
            this.userRepository.updateUser(user);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public void changeSettings(Long userId, UserSettings settings) throws RESTFullException {
        try {
            User user = this.userRepository.getUser(userId);

            if (settings.getRole() == null && settings.getSickDayCount() == null && settings.getVacationCount() == null) {
                user.setNotification(settings.getNotification());
            } else {
                user.addVacationCount(settings.getVacationCount());
                user.setTotalSickDayCount(settings.getSickDayCount());
                user.setRole(settings.getRole());
            }

            this.userRepository.updateUserSettings(user);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public void changeVacation(Long userId, VacationDay vacationDay) throws RESTFullException {
        try {
            Optional<cz.zcu.yamanager.domain.VacationDay> vacation = this.vacationRepository.getVacationDay(vacationDay.getId());
            if (vacation.isPresent()) {
                vacation.get().setDate(vacationDay.getDate());
                vacation.get().setStatus(vacationDay.getStatus());
                vacation.get().setType(vacationDay.getType());
                vacation.get().setTime(vacationDay.getFrom(), vacationDay.getTo());
                this.vacationRepository.updateVacationDay(vacation.get());
            } else {

            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public void changeRequest(RequestType type, BasicRequest request) throws RESTFullException {
        try {
            if (RequestType.VACATION == type) {
                Optional<User> user = this.vacationRepository.findUserByVacationID(request.getId());
                Optional<cz.zcu.yamanager.domain.VacationDay> vacationDay = this.vacationRepository.getVacationDay(request.getId());
                if (user.isPresent() && request.getStatus() == Status.REJECTED) {
                    if (vacationDay.get().getType() == VacationType.SICK_DAY) {
                        user.get().addTakenSickDayCount(-1);
                    } else {
                        user.get().addVacationCount(vacationDay.get().getFrom(), vacationDay.get().getTo());
                    }
                }

                this.requestRepository.updateVacationRequest(request);
            } else {
                this.requestRepository.updateAuthorization(request);
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }

    @Override
    public void deleteVacation(Long userId, Long vacationId) throws RESTFullException {
        try {
            User user = this.userRepository.getUser(userId);
            Optional<cz.zcu.yamanager.domain.VacationDay> vacation = this.vacationRepository.getVacationDay(vacationId);
            if (vacation.isPresent()) {
                if (vacation.get().getType() == VacationType.SICK_DAY) {
                    user.addTakenSickDayCount(-1);
                } else {
                    user.addVacationCount(vacation.get().getFrom(), vacation.get().getTo());
                }
            }

            this.userRepository.updateUser(user);
            this.vacationRepository.deleteVacationDay(vacationId);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RESTFullException(e.getMessage(), "database.error");
        }
    }
}
