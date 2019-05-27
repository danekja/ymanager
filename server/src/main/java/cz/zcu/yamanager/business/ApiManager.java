package cz.zcu.yamanager.business;

import cz.zcu.yamanager.dto.*;
import cz.zcu.yamanager.repository.RequestRepository;
import cz.zcu.yamanager.repository.UserRepository;
import cz.zcu.yamanager.repository.VacationRepository;
import cz.zcu.yamanager.ws.rest.RESTFullException;
import org.apache.tomcat.jni.Local;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

public class ApiManager implements Manager {

    private static final int DAYS_IN_WEEK = 7;

    private RequestRepository requestRepository;
    private UserRepository userRepository;
    private VacationRepository vacationRepository;

    public ApiManager(JdbcTemplate jdbc) {
        this.requestRepository = new RequestRepository(jdbc);
        this.userRepository = new UserRepository(jdbc);
        this.vacationRepository = new VacationRepository(jdbc);
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
        return this.requestRepository.getAllVacationRequests(status);
    }

    @Override
    public List<AuthorizationRequest> getAuthorizationRequests(Status status) throws RESTFullException {
        return this.requestRepository.getAllAuthorizations(status);
    }

    @Override
    public FullUserProfile getUserProfile(Long userId) throws RESTFullException {
        return this.userRepository.getFullUser(userId);
    }

    @Override
    public DefaultSettings getDefaultSettings() throws RESTFullException {
        return this.userRepository.getLastDefaultSettings();
    }

    @Override
    public List<VacationDay> getUserCalendar(Long userId, LocalDate fromDate, LocalDate toDate, Status status) throws RESTFullException {
        return this.vacationRepository.getVacationDays(userId, fromDate, toDate, status);
    }

    @Override
    public void createSettings(DefaultSettings settings) throws RESTFullException {
        this.userRepository.insertSettings(settings);
    }

    @Override
    public void createVacation(Long userId, VacationDay vacationDay) throws RESTFullException {
        //this.vacationRepository.
        // TODO
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
