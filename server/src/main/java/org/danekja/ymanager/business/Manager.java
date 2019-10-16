package org.danekja.ymanager.business;

import org.danekja.ymanager.domain.DefaultSettings;
import org.danekja.ymanager.domain.RequestType;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.dto.*;
import org.danekja.ymanager.ws.rest.exceptions.RESTFullException;

import java.time.LocalDate;
import java.util.List;

public interface Manager {


    List<VacationRequest> getVacationRequests(Status status) throws RESTFullException;

    List<AuthorizationRequest> getAuthorizationRequests(Status status) throws RESTFullException;

    DefaultSettings getDefaultSettings() throws RESTFullException;

    List<VacationDay> getUserCalendar(Long userId, LocalDate fromDate, LocalDate toDate, Status status) throws RESTFullException;

    void createSettings(DefaultSettings settings) throws RESTFullException;

    void createVacation(Long userId, VacationDay vacationDay) throws RESTFullException;

    void changeSettings(Long userId, UserSettings settings) throws RESTFullException;

    void changeVacation(VacationDay vacationDay) throws RESTFullException;

    void changeRequest(RequestType type, BasicRequest request) throws RESTFullException;

    void deleteVacation(Long vacationId) throws RESTFullException;
}