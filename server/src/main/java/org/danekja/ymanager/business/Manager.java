package org.danekja.ymanager.business;

import org.danekja.ymanager.domain.DefaultSettings;
import org.danekja.ymanager.domain.RequestType;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface Manager {


    List<VacationRequest> getVacationRequests(Status status);

    List<AuthorizationRequest> getAuthorizationRequests(Status status);

    DefaultSettings getDefaultSettings();

    List<VacationDay> getUserCalendar(long userId, LocalDate fromDate, LocalDate toDate, Status status);

    void createSettings(DefaultSettings settings);

    void createVacation(long userId, VacationDay vacationDay);

    void changeSettings(long userId, UserSettings settings);

    void changeVacation(VacationDay vacationDay);

    void changeRequest(RequestType type, BasicRequest request);

    void deleteVacation(long vacationId);
}
