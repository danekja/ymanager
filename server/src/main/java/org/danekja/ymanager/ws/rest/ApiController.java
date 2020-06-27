package org.danekja.ymanager.ws.rest;

import org.danekja.ymanager.business.FileExportResult;
import org.danekja.ymanager.business.FileService;
import org.danekja.ymanager.business.Manager;
import org.danekja.ymanager.domain.RequestType;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.User;
import org.danekja.ymanager.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ApiController {

    private final Manager manager;

    private final FileService fileService;

    @Autowired
    public ApiController(Manager manager, FileService fileService) {
        this.manager = manager;
        this.fileService = fileService;
    }

    // *********************** GET ****************************

    @RequestMapping(value = "/users/requests/vacation", method=GET)
    public List<VacationRequest> usersRequestsVacation(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "status", required = false) String status)
    {
        return manager.getVacationRequests(Status.getStatus(status));
    }

    @RequestMapping(value = "/users/requests/authorization", method=GET)
    public List<AuthorizationRequest> userRequestsAuthorization(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "status", required = false) String status)
    {
        return manager.getAuthorizationRequests(Status.getStatus(status));
    }

    @RequestMapping(value = "/user/{id}/calendar", method=GET)
    public List<VacationDay> userCalendar(
            @PathVariable("id") Long id,
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "status", required = false) String status)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = to != null ? LocalDate.parse(to, formatter) : null;
        return manager.getUserCalendar(id, fromDate, toDate, Status.getStatus(status));
    }

    @RequestMapping(value = "/settings", method=GET)
    public DefaultSettings settings(
            @RequestParam(value = "lang", required = false) String lang)
    {
        return new DefaultSettings(manager.getDefaultSettings());
    }

    // *********************** POST ****************************

    @RequestMapping(value = "/settings", method=POST)
    public ResponseEntity<Void> settings(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody DefaultSettings settings)
    {
        manager.createSettings(settings.toEntity());

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/user/calendar/create", method=POST)
    public ResponseEntity<Void> userCalendarCreate(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody VacationDay vacationDay,
            Authentication auth)
    {
        //TODO make api endpoint contain userId in path as part of #39, also drop the create part of path
        //TODO drop the auth parameter afterwards
        manager.createVacation(((User) auth.getPrincipal()).getId(), vacationDay);

        return ResponseEntity.ok().build();
    }

    // *********************** PUT ****************************


    @RequestMapping(value = "/user/settings", method=PUT)
    public ResponseEntity<Void> userSettings(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody UserSettings settings,
            Authentication auth)
    {
        //TODO make api endpoint contain userId in path as part of #39
        //TODO drop the auth parameter afterwards
        manager.changeSettings(((User) auth.getPrincipal()).getId(), settings);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/user/calendar/edit", method=PUT)
    public ResponseEntity<Void> userCalendarEdit(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody VacationDay vacationDay)
    {
        //TODO make api endpoint point to vacation endpoint as part of #39, also drop the edit part of path
        //TODO drop the auth parameter afterwards
        manager.changeVacation(vacationDay);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/user/requests", method=PUT)
    public ResponseEntity<Void> userRequests(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "type", required = true) String type,
            @RequestBody BasicRequest request)
    {
        manager.changeRequest(RequestType.getType(type), request);

        return ResponseEntity.ok().build();
    }

    // *********************** DELETE ****************************

    @RequestMapping(value = "/calendar/{id}/delete", method=DELETE)
    public ResponseEntity<Void> calendarDelete(
            @PathVariable("id") Long id,
            @RequestParam(value = "lang", required = false) String lang)
    {
        manager.deleteVacation(id);

        return ResponseEntity.ok().build();
    }

    // *********************** FILE ****************************

    @RequestMapping(value = "/import/xls", method=POST)
    public ResponseEntity<Void> importXLSFile(@RequestParam(value = "lang", required = false) String lang, @RequestParam("file") MultipartFile file) throws Exception {
        fileService.parseXLSFile(file.getOriginalFilename(), file.getBytes());

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/export/pdf", method=GET)
    public ResponseEntity<byte[]> exportPDFFile(@RequestParam(value = "lang", required = false) String lang) throws Exception {
        FileExportResult result = fileService.createPDFFile();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", result.getName()))
                .body(result.getBytes());
    }
}
