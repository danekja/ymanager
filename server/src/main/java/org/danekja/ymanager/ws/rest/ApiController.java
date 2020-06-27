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

    @GetMapping("/users/requests/vacation")
    public List<VacationRequest> usersRequestsVacation(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "status", required = false) Status status)
    {
        return manager.getVacationRequests(status);
    }

    @GetMapping("/users/requests/authorization")
    public List<AuthorizationRequest> userRequestsAuthorization(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "status", required = false) Status status)
    {
        return manager.getAuthorizationRequests(status);
    }

    @GetMapping("/user/{id}/calendar")
    public List<VacationDay> userCalendar(
            @PathVariable("id") Long id,
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "status", required = false) Status status)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = to != null ? LocalDate.parse(to, formatter) : null;
        return manager.getUserCalendar(id, fromDate, toDate, status);
    }

    @GetMapping("/settings")
    public DefaultSettings settings(
            @RequestParam(value = "lang", required = false) String lang)
    {
        return new DefaultSettings(manager.getDefaultSettings());
    }

    // *********************** POST ****************************

    @PostMapping("/settings")
    public ResponseEntity<Void> settings(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody DefaultSettings settings)
    {
        manager.createSettings(settings.toEntity());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/calendar/create")
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


    @PutMapping("/user/settings")
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

    @PutMapping("/user/calendar/edit")
    public ResponseEntity<Void> userCalendarEdit(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody VacationDay vacationDay)
    {
        //TODO make api endpoint point to vacation endpoint as part of #39, also drop the edit part of path
        //TODO drop the auth parameter afterwards
        manager.changeVacation(vacationDay);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/requests")
    public ResponseEntity<Void> userRequests(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "type") RequestType type,
            @RequestBody BasicRequest request)
    {
        manager.changeRequest(type, request);

        return ResponseEntity.ok().build();
    }

    // *********************** DELETE ****************************

    @DeleteMapping("/calendar/{id}/delete")
    public ResponseEntity<Void> calendarDelete(
            @PathVariable("id") Long id,
            @RequestParam(value = "lang", required = false) String lang)
    {
        manager.deleteVacation(id);

        return ResponseEntity.ok().build();
    }

    // *********************** FILE ****************************

    @PostMapping("/import/xls")
    public ResponseEntity<Void> importXLSFile(@RequestParam(value = "lang", required = false) String lang, @RequestParam("file") MultipartFile file) throws Exception {
        fileService.parseXLSFile(file.getOriginalFilename(), file.getBytes());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPDFFile(@RequestParam(value = "lang", required = false) String lang) throws Exception {
        FileExportResult result = fileService.createPDFFile();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", result.getName()))
                .body(result.getBytes());
    }
}
