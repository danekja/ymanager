package cz.zcu.yamanager.business;

import cz.zcu.yamanager.dto.*;
import cz.zcu.yamanager.repository.RequestRepository;
import cz.zcu.yamanager.repository.UserRepository;
import cz.zcu.yamanager.repository.VacationRepository;
import cz.zcu.yamanager.ws.rest.RESTFullException;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class ApiManager implements Manager {

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
        List<BasicProfileUser> users = this.userRepository.getAllBasicUsers();
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
        List<VacationRequest> requests = this.requestRepository.getAllVacationRequests(status);
        return requests == null ? Collections.emptyList() : requests;
    }

    @Override
    public List<AuthorizationRequest> getAuthorizationRequests(Status status) throws RESTFullException {
        List<AuthorizationRequest> requests = this.requestRepository.getAllAuthorizations(status);
        return requests == null ? Collections.emptyList() : requests;
    }

    @Override
    public FullUserProfile getUserProfile(Long userId) throws RESTFullException {
        FullUserProfile userProfile = this.userRepository.getFullUser(userId);
        return userProfile == null ? new FullUserProfile() : userProfile;
    }

    @Override
    public DefaultSettings getDefaultSettings() throws RESTFullException {
        DefaultSettings settings = this.userRepository.getLastDefaultSettings();
        return settings == null ? new DefaultSettings() : settings;
    }

    @Override
    public List<VacationDay> getUserCalendar(Long userId, LocalDate fromDate, LocalDate toDate, Status status) throws RESTFullException {
        List<VacationDay> vacations = this.vacationRepository.getVacationDays(userId, fromDate, toDate, status);
        return vacations == null ? Collections.emptyList() : vacations;
    }

    @Override
    public void createSettings(DefaultSettings settings) throws RESTFullException {
        this.userRepository.insertSettings(settings);
    }

    @Override
    public void createVacation(Long userId, VacationDay vacationDay) throws RESTFullException {
        this.vacationRepository.insertVacationDay(userId, vacationDay);
    }

    @Override
    public void changeSettings(Long userId, UserSettings settings) throws RESTFullException {
        this.userRepository.updateUserSettings(settings);
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
