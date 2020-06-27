package org.danekja.ymanager.business;

import org.danekja.ymanager.domain.DefaultSettings;
import org.danekja.ymanager.domain.RequestType;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface Manager {


    List<VacationRequestDTO> getVacationRequests(Status status);

    List<AuthorizationRequestDTO> getAuthorizationRequests(Status status);

    DefaultSettings getDefaultSettings();

    List<VacationDayDTO> getUserCalendar(long userId, LocalDate fromDate, LocalDate toDate, Status status);

    void createSettings(DefaultSettings settings);

    void createVacation(long userId, VacationDayDTO vacationDayDTO);

    void changeSettings(long userId, UserSettingsDTO settings);

    void changeVacation(VacationDayDTO vacationDayDTO);

    void changeRequest(RequestType type, BasicRequestDTO request);

    void deleteVacation(long vacationId);
}
