package org.danekja.ymanager.business;

import org.danekja.ymanager.domain.*;
import org.danekja.ymanager.dto.BasicRequestDTO;
import org.danekja.ymanager.dto.UserSettingsDTO;
import org.danekja.ymanager.dto.VacationDayDTO;

import java.time.LocalDate;
import java.util.List;

public interface Manager {


    List<VacationRequest> getVacationRequests(Status status);

    List<AuthorizationRequest> getAuthorizationRequests(Status status);

    DefaultSettings getDefaultSettings();

    List<Vacation> getUserCalendar(long userId, LocalDate fromDate, LocalDate toDate, Status status);

    void createSettings(DefaultSettings settings);

    void createVacation(long userId, VacationDayDTO vacationDayDTO);

    void changeSettings(long userId, UserSettingsDTO settings);

    void changeVacation(VacationDayDTO vacationDayDTO);

    void changeRequest(RequestType type, BasicRequestDTO request);

    void deleteVacation(long vacationId);
}
