package cz.zcu.yamanager.ws.rest;

import cz.zcu.yamanager.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ApiController {

    // *********************** POST ****************************

    @RequestMapping(value = "/users", method=GET)
    public List<BasicProfileUser> users(@RequestParam("status") String status) {
        UserStatus userStatus = UserStatus.valueOf(status);

        List<BasicProfileUser> users = new ArrayList<>();

        BasicProfileUser user = new BasicProfileUser();
        user.setId(1);
        UserName userName = new UserName();
        userName.setFirst("Tomas");
        userName.setLast("Novak");
        user.setName(userName);
        user.setPhoto("https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg");

        List<CalendarItem> calendarItems = new ArrayList<>();

        CalendarItem calendarItem = new CalendarItem();
        calendarItem.setType(VacationType.SICKDAY);
        calendarItem.setDate(LocalDate.now());
        calendarItem.setFrom(LocalTime.now());
        calendarItem.setTo(LocalTime.now());
        calendarItems.add(calendarItem);
        user.setCalendar(calendarItems);

        users.add(user);

        return users;
    }

    @RequestMapping(value = "/user/{id}/profile", method=GET)
    public FullUserProfile userProfile(@PathVariable("id") String id) {
        long userId = Long.valueOf(id);
        FullUserProfile user = new FullUserProfile();

        user.setId(userId);
        UserName userName = new UserName();
        userName.setFirst("Tomas");
        userName.setLast("Novak");
        user.setName(userName);
        user.setPhoto("https://st2.depositphotos.com/9223672/12056/v/950/depositphotos_120568236-stock-illustration-male-face-avatar-logo-template.jpg");
        user.setRole(UserRole.EMPLOYEE);
        user.setNotification(LocalDateTime.of(2010, 5, 4, 16, 10));
        user.setStatus(UserStatus.AUTHORIZED);
        VacationInfo sickday = new VacationInfo();
        sickday.setValue(3);
        sickday.setUnit(VacationUnit.DAY);
        user.setSickDay(sickday);
        VacationInfo vacation = new VacationInfo();
        vacation.setValue(15);
        vacation.setUnit(VacationUnit.HOUR);
        user.setVacation(vacation);
        return user;
    }

    @RequestMapping(value = "/users/requests", method=GET)
    public UserRequest userRequests(@RequestParam("type") String type) {
        RequestType requestType = RequestType.valueOf(type);

        UserName userName = new UserName();
        userName.setFirst("Tomas");
        userName.setLast("Novak");

        UserRequest request = new UserRequest();

        List<AuthorizationRequest> authorization = new ArrayList<>();
        AuthorizationRequest authRequest = new AuthorizationRequest();
        authRequest.setId(1);
        authRequest.setUserName(userName);
        authRequest.setDate(LocalDateTime.now());

        authorization.add(authRequest);
        request.setAuthorization(authorization);

        List<VacationRequest> vacation = new ArrayList<>();
        VacationRequest vacRequest = new VacationRequest();
        vacRequest.setId(1);
        vacRequest.setUserName(userName);
        vacRequest.setStatus(RequestStatus.PENDING);
        vacRequest.setType(VacationType.SICKDAY);
        vacRequest.setDate(LocalDate.now());
        vacRequest.setFrom(LocalTime.of(9, 30));
        vacRequest.setTo(LocalTime.of(18, 30));

        vacation.add(vacRequest);
        request.setVacation(vacation);

        return request;
    }


    @RequestMapping(value = "/user/calendar", method=GET)
    public List<CalendarView> personalCalendarView(@RequestParam("viewType") String viewType, @RequestParam("value") String value) {
        CalendarViewType calendarViewType = CalendarViewType.valueOf(viewType);
        int numberOfView = Integer.valueOf(value);

        List<CalendarView> calendarViews = new ArrayList<>();

        CalendarView view = new CalendarView();
        view.setStatus(RequestStatus.PENDING);
        view.setType(VacationType.SICKDAY);
        view.setDate(LocalDate.now());
        view.setFrom(LocalTime.of(9, 30));
        view.setTo(LocalTime.of(18, 30));

        calendarViews.add(view);

        return calendarViews;
    }

    @RequestMapping(value = "/user/{id}/calendar", method=GET)
    public List<CalendarView> userCalendarView(@PathVariable("id") String id, @RequestParam("viewType") String viewType, @RequestParam("value") String value) {
        CalendarViewType calendarViewType = CalendarViewType.valueOf(viewType);
        int numberOfView = Integer.valueOf(value);
        long userId = Long.valueOf(id);

        List<CalendarView> calendarViews = new ArrayList<>();

        CalendarView view = new CalendarView();
        view.setStatus(RequestStatus.PENDING);
        view.setType(VacationType.SICKDAY);
        view.setDate(LocalDate.now());
        view.setFrom(LocalTime.of(9, 30));
        view.setTo(LocalTime.of(18, 30));

        calendarViews.add(view);

        return calendarViews;
    }

    @RequestMapping(value = "/settings/default", method=GET)
    public DefaultSettings defaultSettings() {

        DefaultSettings settings = new DefaultSettings();

        VacationInfo sickDay = new VacationInfo();
        sickDay.setUnit(VacationUnit.DAY);
        sickDay.setValue(3);
        settings.setSickDay(sickDay);
        settings.setNotification(LocalDateTime.now());

        return settings;
    }

    // *********************** POST ****************************

    @RequestMapping(value = "/user/calendar", method=POST)
    public ResponseEntity personalCalendarView(@RequestBody CalendarView calendarView) {

        if (calendarView == null) {
            return ResponseEntity.badRequest().build();
        }

        return ok(OK);
    }

    @RequestMapping(value = "/user/requests", method=POST)
    public ResponseEntity userRequest(@RequestBody BasicRequest request) {

        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        return ok(OK);
    }

    @RequestMapping(value = "/user/{id}/settings", method=POST)
    public ResponseEntity userSettings(@PathVariable("id") String id, @RequestBody UserSettings settings) {

        if (settings == null) {
            return ResponseEntity.badRequest().build();
        }

         return ok(OK);
    }


    @RequestMapping(value = "/settings/default", method=POST)
    public ResponseEntity defaultSettings(@RequestBody DefaultSettings settings) {

        if (settings == null) {
            return ResponseEntity.badRequest().build();
        }

        return ok(OK);
    }
}
