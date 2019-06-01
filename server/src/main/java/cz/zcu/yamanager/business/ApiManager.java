package cz.zcu.yamanager.business;

import cz.zcu.yamanager.dto.*;
import cz.zcu.yamanager.repository.RequestRepository;
import cz.zcu.yamanager.repository.UserRepository;
import cz.zcu.yamanager.repository.VacationRepository;
import cz.zcu.yamanager.ws.rest.RESTFullException;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@Component
public class ApiManager implements Manager {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private static final int DAYS_IN_WEEK = 7;

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
        List<BasicProfileUser> users;
        if(status == null) {
            users = this.userRepository.getAllBasicUsers();
        } else {
            users = this.userRepository.getAllBasicUsers(status);
        }

        LocalDate today = LocalDate.now();
        LocalDate weekBefore = today.minusDays(ApiManager.DAYS_IN_WEEK);
        LocalDate weekAfter = today.plusDays(ApiManager.DAYS_IN_WEEK);
        for (BasicProfileUser user : users) {
            user.setCalendar(this.vacationRepository.getVacationDays(user.getId(), weekBefore, weekAfter));
        }

        return users;

    }

    @Override
    public List<VacationRequest> getVacationRequests(Status status) throws RESTFullException {
        List<VacationRequest> requests;
        if(status == null) {
            requests = this.requestRepository.getAllVacationRequests();
        } else {
            requests = this.requestRepository.getAllVacationRequests(status);
        }

        return requests;
    }

    @Override
    public List<AuthorizationRequest> getAuthorizationRequests(Status status) throws RESTFullException {
        List<AuthorizationRequest> requests;
        if(status == null) {
            requests = this.requestRepository.getAllAuthorizations();
        } else {
            requests = this.requestRepository.getAllAuthorizations(status);
        }

        return requests;
    }

    @Override
    public FullUserProfile getUserProfile(Long userId) throws RESTFullException {
        return this.userRepository.getFullUser(userId);
    }

    @Override
    public DefaultSettings getDefaultSettings() throws RESTFullException {
        DefaultSettings settings = this.userRepository.getLastDefaultSettings();
        return settings == null ? new DefaultSettings() : settings;
    }

    @Override
    public List<VacationDay> getUserCalendar(Long userId, LocalDate fromDate, LocalDate toDate, Status status) throws RESTFullException {
        List<VacationDay> vacations;
        if(status == null && toDate == null) {
            vacations = this.vacationRepository.getVacationDays(userId, fromDate);
        } else if(status == null) {
            vacations = this.vacationRepository.getVacationDays(userId, fromDate, toDate);
        } else if(toDate != null) {
            vacations = this.vacationRepository.getVacationDays(userId, fromDate, toDate, status);
        } else {
            vacations = this.vacationRepository.getVacationDays(userId, fromDate, status);
        }

        return vacations;
    }

    @Override
    public void createSettings(DefaultSettings settings) throws RESTFullException {
        this.userRepository.insertSettings(settings);
    }

    @Override
    public void createVacation(Long userId, VacationDay vacationDay) throws RESTFullException {
        UserRole role = this.userRepository.getUserRole(userId);
        vacationDay.setStatus(role == UserRole.EMPLOYER ? Status.ACCEPTED : Status.PENDING);

        if(vacationDay.getType() == VacationType.VACATION) {
            this.userRepository.decreaseVacationCount(userId, vacationDay.getFrom().until(vacationDay.getTo(), MINUTES) / 60f);
        } else {
            this.userRepository.increaseTakenSickdays(userId);
        }

        this.vacationRepository.insertVacationDay(userId, vacationDay);
    }

    @Override
    public void changeSettings(Long userId, UserSettings settings) throws RESTFullException {
        settings.setId(userId);
        if(settings.getRole() == null && settings.getSickDayCount() == null && settings.getVacationCount() == null) {
            this.userRepository.updateNotification(settings);
        } else {
            this.userRepository.updateUserSettings(settings);
        }
    }

    @Override
    public void changeVacation(Long userId, VacationDay vacationDay) throws RESTFullException {
        this.vacationRepository.updateVacationDay(vacationDay);
    }

    @Override
    public void changeRequest(RequestType type, BasicRequest request) throws RESTFullException {
        if(RequestType.VACATION == type) {
            this.requestRepository.updateVacationRequest(request);
        } else {
            this.requestRepository.updateAuthorization(request);
        }
    }

    @Override
    public void deleteVacation(Long userId, Long vacationId) throws RESTFullException {
        this.vacationRepository.deleteVacationDay(vacationId);
    }
}
