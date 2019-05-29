package cz.zcu.yamanager.business.mock;

import cz.zcu.yamanager.dto.*;
import cz.zcu.yamanager.business.Manager;
import cz.zcu.yamanager.ws.rest.RESTFullException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Component
public class ManagerMock implements Manager {

    private List<DefaultSettings> settings = new LinkedList<>();

    private Map<Long, List<VacationDay>> vacations = new HashMap<>();

    private Map<Long, UserSettings> userSettings = new HashMap<>();

    private BasicProfileUser createBasicProfileUser(long id, Status status) {
        BasicProfileUser user = new BasicProfileUser();

        user.setId(id);
        user.setFirstName("Tomas");
        user.setLastName(status == null ? "unknown" : status.name().toLowerCase());
        user.setPhoto("https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg");
        user.setCalendar(asList(
                createVacationDay(1L, LocalDate.now(), VacationType.VACATION, Status.ACCEPTED),
                createVacationDay(2L, LocalDate.now().plusDays(1), VacationType.VACATION, Status.ACCEPTED))
        );

        return user;
    }

    private VacationDay createVacationDay(long id, LocalDate date, VacationType vacationType, Status status) {
        VacationDay vacationDay = new VacationDay();

        vacationDay.setId(id);
        vacationDay.setType(vacationType);
        vacationDay.setDate(date);
        vacationDay.setStatus(status);

        switch (vacationType) {
            case SICKDAY: {
                vacationDay.setFrom(null);
                vacationDay.setTo(null);
            } break;
            case VACATION: {
                vacationDay.setFrom(LocalTime.of(9, 00));
                vacationDay.setTo(LocalTime.of(13, 00));
            } break;
        }

        return vacationDay;
    }

    private VacationRequest createVacationRequest(long id, VacationType vacationType, Status status) {
        VacationRequest request = new VacationRequest();

        request.setId(id);
        request.setFirstName("Tomas");
        request.setLastName("Novak");
        request.setDate(LocalDate.now());
        request.setType(vacationType);
        request.setStatus(status);
        request.setTimestamp(LocalDateTime.now());

        switch (vacationType) {
            case SICKDAY: {
                request.setFrom(null);
                request.setTo(null);
            } break;
            case VACATION: {
                request.setFrom(LocalTime.of(9, 30));
                request.setTo(LocalTime.of(18, 30));
            } break;
        }

        return request;
    }

    private AuthorizationRequest createAuthorizationRequest(long id, Status status) {
        AuthorizationRequest request = new AuthorizationRequest();

        request.setId(id);
        request.setFirstName("Tomas");
        request.setLastName("Novak");
        request.setStatus(status);
        request.setTimestamp(LocalDateTime.now());

        return request;
    }

    private FullUserProfile createFullUserProfile(Long id) {
        FullUserProfile user = new FullUserProfile();

        user.setId(id);
        user.setFirstName("Tomas");
        user.setLastName("Novak");
        user.setPhoto("https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg");
        user.setStatus(Status.ACCEPTED);

        if (userSettings.containsKey(id)) {
            UserSettings settings = userSettings.get(id);
            user.setVacationCount(settings.getVacationCount());
            user.setSickdayCount(settings.getSickdayCount());
            user.setRole(settings.getRole());
        } else {
            user.setVacationCount(8.5F);
            user.setSickdayCount(3);
            user.setRole(UserRole.EMPLOYER);
        }

        user.setNotification(LocalDateTime.of(2000, 12, 1, 9, 0));

        return user;
    }

    private DefaultSettings createDefaultSettings() {
        DefaultSettings settings = new DefaultSettings();

        settings.setSickdayCount(3);
        settings.setNotification(LocalDateTime.now());

        return settings;
    }

    @Override
    public List<BasicProfileUser> getUsers(Status status) throws RESTFullException {
        int count = 5;
        List<BasicProfileUser> users = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            users.add(createBasicProfileUser(i, status));
        }
        return users;
    }

    @Override
    public List<VacationRequest> getVacationRequests(Status status) throws RESTFullException {
        int count = 5;
        List<VacationRequest> requests = new ArrayList<>(count);
        int length = VacationType.values().length;
        for (int i = 1; i <= count; i++) {
            requests.add(createVacationRequest(i, VacationType.values()[i % length], status));
        }
        return requests;
    }

    @Override
    public List<AuthorizationRequest> getAuthorizationRequests(Status status) {
        int count = 5;
        List<AuthorizationRequest> requests = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            requests.add(createAuthorizationRequest(i, status));
        }
        return requests;
    }

    @Override
    public FullUserProfile getUserProfile(Long userId) {
        return createFullUserProfile(userId);
    }

    @Override
    public DefaultSettings getDefaultSettings() {
        return settings.isEmpty() ? createDefaultSettings() : settings.get(settings.size()-1);
    }

    @Override
    public List<VacationDay> getUserCalendar(Long userId, LocalDate fromDate, LocalDate toDate, Status status) {
        if (vacations.containsKey(userId)) {
            List<VacationDay> vacationDays = vacations.get(userId);
            Predicate<VacationDay> predicate = (day) -> day.getDate().isAfter(fromDate);
            predicate = predicate.or((day) -> day.getDate().isEqual(fromDate));
            if (toDate != null) {
                predicate = predicate.and((day) -> day.getDate().isBefore(toDate)).or((day) -> day.getDate().isEqual(toDate));
            }
            return vacationDays.stream().filter(predicate).collect(Collectors.toList());

        } else {
            int daysDifference = toDate == null ? 5 : Period.between(fromDate, toDate).getDays();
            List<VacationDay> days = new ArrayList<>(daysDifference);
            for (int i = 0; i < daysDifference; i++) {
                days.add(createVacationDay(i + 1, fromDate.plusDays(i), VacationType.values()[i % 2], status));
            }
            return days;
        }
    }

    @Override
    public void createSettings(DefaultSettings sett) {
        settings.add(sett);
    }

    @Override
    public void createVacation(Long userId, VacationDay vacationDay) {
        if (!vacations.containsKey(userId)) {
            vacations.put(userId, new ArrayList<>());
        }

        List<VacationDay> days = vacations.get(userId);
        days.add(vacationDay);
        vacationDay.setId((long)days.size());
    }

    @Override
    public void changeSettings(Long userId, UserSettings settings) {

        if (userSettings.containsKey(userId)) {
            if (settings.getId().equals(userId)) {
                userSettings.get(userId).setNotification(settings.getNotification());
            }
        } else {
            userSettings.put(userId, settings);
        }
    }

    @Override
    public void changeVacation(Long userId, VacationDay vacationDay) throws RESTFullException {
        if (vacationDay.getId() == null) {
            throw new RESTFullException("Vacation does not contain ID.", "rest.exception.generic");
        }

        if (!vacations.containsKey(userId)) {
            throw new RESTFullException("User does not exist.", "rest.exception.generic");
        }

        List<VacationDay> vacs = vacations.get(userId);
        boolean founded = false;
        for (int i = 0; i < vacs.size(); i++) {
            if (vacs.get(i).getId() == vacationDay.getId()) {
                VacationDay day = vacs.get(i);
                day.setStatus(vacationDay.getStatus());
                day.setFrom(vacationDay.getFrom());
                day.setTo(vacationDay.getTo());
                day.setDate(vacationDay.getDate());
                day.setType(vacationDay.getType());
                founded = true;
                break;
            }
        }

        if (!founded) {
            throw new RESTFullException("Vacation does not exist.", "rest.exception.generic");
        }
    }

    @Override
    public void changeRequest(RequestType type, BasicRequest request) throws RESTFullException {

        if (request.getId() == null) {
            throw new RESTFullException("ID of request can not be empty.", "rest.exception.generic");
        }

        switch (type) {
            case VACATION: {
                boolean found = false;
                for (List<VacationDay> value : vacations.values()) {
                    for (VacationDay day : value) {
                        if (day.getId() == request.getId()) {
                            found = true;
                            day.setStatus(request.getStatus());
                            break;
                        }
                    }
                    if (found) break;
                }
                if (!found) {
                    throw new RESTFullException("Vacation does not exist.", "rest.exception.generic");
                }
            } break;
            case AUTHORIZATION: {
                throw new RESTFullException("User does not exist.", "rest.exception.generic");
            }
        }
    }

    @Override
    public void deleteVacation(Long userId, Long vacationId) throws RESTFullException{
        if (!vacations.containsKey(userId)) {
            throw new RESTFullException("User does not exist.", "rest.exception.generic");
        }

        List<VacationDay> vacs = vacations.get(userId);
        if (!vacs.removeIf(day -> day.getId() == vacationId)) {
            throw new RESTFullException("Vacation does not exist for the user.", "rest.exception.generic");
        }
    }
}
