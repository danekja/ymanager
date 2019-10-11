package org.danekja.ymanager.ws.rest;

import org.apache.commons.lang3.StringUtils;
import org.danekja.ymanager.business.FileService;
import org.danekja.ymanager.business.Manager;
import org.danekja.ymanager.domain.RequestType;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.dto.BasicRequest;
import org.danekja.ymanager.dto.DefaultSettings;
import org.danekja.ymanager.dto.UserSettings;
import org.danekja.ymanager.dto.VacationDay;
import org.danekja.ymanager.util.localization.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ApiController extends BaseController {

    private final Manager manager;

    private final FileService fileService;

    @Autowired
    public ApiController(Manager manager, FileService fileService) {
        this.manager = manager;
        this.fileService = fileService;
    }

    private Long getUserId(String id) {
        // TODO verify permission
        if (id.toLowerCase().equals("me")) {
            return 1L;
        } else if (StringUtils.isNumeric(id)) {
            return Long.valueOf(id);
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    private Long getUserId(Long id) {
        return id == null ? getUserId("me") : id;
    }

    // *********************** GET ****************************

    @RequestMapping(value = "/users/requests/vacation", method=GET)
    public ResponseEntity usersRequestsVacation(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "status", required = false) String status)
    {
        return handle(Language.getLanguage(lang), () ->
                manager.getVacationRequests(Status.getStatus(status))
        );
    }

    @RequestMapping(value = "/users/requests/authorization", method=GET)
    public ResponseEntity userRequestsAuthorization(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "status", required = false) String status)
    {
        return handle(Language.getLanguage(lang), () ->
                manager.getAuthorizationRequests(Status.getStatus(status))
         );
    }

    @RequestMapping(value = "/user/{id}/calendar", method=GET)
    public ResponseEntity userCalendar(
            @PathVariable("id") String id,
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "from", required = true) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "status", required = false) String status)
    {
        return handle(Language.getLanguage(lang), () -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate fromDate = LocalDate.parse(from, formatter);
            LocalDate toDate = to != null ? LocalDate.parse(to, formatter) : null;
            return manager.getUserCalendar(getUserId(id), fromDate, toDate, Status.getStatus(status));
        });
    }

    @RequestMapping(value = "/settings", method=GET)
    public ResponseEntity settings(
            @RequestParam(value = "lang", required = false) String lang)
    {
        return handle(Language.getLanguage(lang), () ->
                manager.getDefaultSettings()
        );
    }

    // *********************** POST ****************************

    @RequestMapping(value = "/settings", method=POST)
    public ResponseEntity settings(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody DefaultSettings settings)
    {
        return handle(Language.getLanguage(lang), () ->
                manager.createSettings(settings)
        );
    }

    @RequestMapping(value = "/user/calendar/create", method=POST)
    public ResponseEntity userCalendarCreate(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody VacationDay vacationDay)
    {
        return handle(Language.getLanguage(lang), () ->
                manager.createVacation(getUserId("me"), vacationDay)
        );
    }

    // *********************** PUT ****************************


    @RequestMapping(value = "/user/settings", method=PUT)
    public ResponseEntity userSettings(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody UserSettings settings)
    {
        return handle(Language.getLanguage(lang), () ->
                manager.changeSettings(getUserId("me"), settings)
        );
    }

    @RequestMapping(value = "/user/calendar/edit", method=PUT)
    public ResponseEntity userCalendarEdit(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody VacationDay vacationDay)
    {
        return handle(Language.getLanguage(lang), () ->
                manager.changeVacation(getUserId("me"), vacationDay)
        );
    }

    @RequestMapping(value = "/user/requests", method=PUT)
    public ResponseEntity userRequests(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "type", required = true) String type,
            @RequestBody BasicRequest request)
    {
        return handle(Language.getLanguage(lang), () ->
                manager.changeRequest(RequestType.getType(type), request)
        );
    }

    // *********************** DELETE ****************************

    @RequestMapping(value = "/calendar/{id}/delete", method=DELETE)
    public ResponseEntity calendarDelete(
            @PathVariable("id") String id,
            @RequestParam(value = "lang", required = false) String lang)
    {
        return handle(Language.getLanguage(lang), () ->
            manager.deleteVacation(getUserId("me"), StringUtils.isNumeric(id) ? Long.parseLong(id) : -1)
        );
    }

    // *********************** FILE ****************************

    @RequestMapping(value = "/import/xls", method=POST)
    public ResponseEntity importXLSFile(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam("file") MultipartFile file)
    {
        return handle(Language.getLanguage(lang), () ->
            fileService.parseXLSFile(file.getOriginalFilename(), file.getBytes())
        );
    }

    @RequestMapping(value = "/export/pdf", method=GET)
    public ResponseEntity exportPDFFile(
            @RequestParam(value = "lang", required = false) String lang)
    {
        return handle(Language.getLanguage(lang),
                () -> fileService.createPDFFile(),
                (res) -> new String[]{HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + res.getName() + "\""},
                (res) -> res.getBytes()
        );
    }
}
