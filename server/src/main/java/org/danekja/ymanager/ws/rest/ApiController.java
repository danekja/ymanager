package org.danekja.ymanager.ws.rest;

import org.danekja.ymanager.business.FileExportResult;
import org.danekja.ymanager.business.FileService;
import org.danekja.ymanager.business.Manager;
import org.danekja.ymanager.domain.*;
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
import java.util.stream.Collectors;

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
    public List<VacationRequestDTO> usersRequestsVacation(@RequestParam(value = "status", required = false) Status status) {
        List<VacationRequest> requests = manager.getVacationRequests(status);

        return requests.stream()
                .map(VacationRequestDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/requests/authorization")
    public List<AuthorizationRequestDTO> userRequestsAuthorization(@RequestParam(value = "status", required = false) Status status) {
        List<AuthorizationRequest> requests = manager.getAuthorizationRequests(status);

        return requests.stream()
                .map(AuthorizationRequestDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{id}/calendar")
    public List<VacationDayDTO> userCalendar(@PathVariable("id") Long id, @RequestParam("from") String from, @RequestParam(value = "to", required = false) String to, @RequestParam(value = "status", required = false) Status status) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = to != null ? LocalDate.parse(to, formatter) : null;
        List<Vacation> vacations = manager.getUserCalendar(id, fromDate, toDate, status);

        return vacations.stream()
                .map(VacationDayDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/settings")
    public DefaultSettingsDTO settings() {
        return new DefaultSettingsDTO(manager.getDefaultSettings());
    }

    // *********************** POST ****************************

    @PostMapping("/settings")
    public ResponseEntity<Void> settings(@RequestBody DefaultSettingsDTO settings) {
        manager.createSettings(settings.toEntity());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/calendar/create")
    public ResponseEntity<Void> userCalendarCreate(@RequestBody VacationDayDTO vacationDayDTO, Authentication auth) {
        //TODO make api endpoint contain userId in path as part of #39, also drop the create part of path
        //TODO drop the auth parameter afterwards
        manager.createVacation(((User) auth.getPrincipal()).getId(), vacationDayDTO);

        return ResponseEntity.ok().build();
    }

    // *********************** PUT ****************************


    @PutMapping("/user/settings")
    public ResponseEntity<Void> userSettings(@RequestBody UserSettingsDTO settings, Authentication auth) {
        //TODO make api endpoint contain userId in path as part of #39
        //TODO drop the auth parameter afterwards
        manager.changeSettings(((User) auth.getPrincipal()).getId(), settings);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/calendar/edit")
    public ResponseEntity<Void> userCalendarEdit(@RequestBody VacationDayDTO vacationDayDTO) {
        //TODO make api endpoint point to vacation endpoint as part of #39, also drop the edit part of path
        //TODO drop the auth parameter afterwards
        manager.changeVacation(vacationDayDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/requests")
    public ResponseEntity<Void> userRequests(@RequestParam("type") RequestType type, @RequestBody BasicRequestDTO request) {
        manager.changeRequest(type, request);

        return ResponseEntity.ok().build();
    }

    // *********************** DELETE ****************************

    @DeleteMapping("/calendar/{id}/delete")
    public ResponseEntity<Void> calendarDelete(@PathVariable("id") Long id) {
        manager.deleteVacation(id);

        return ResponseEntity.ok().build();
    }

    // *********************** FILE ****************************

    @PostMapping("/import/xls")
    public ResponseEntity<Void> importXLSFile(@RequestParam("file") MultipartFile file) throws Exception {
        fileService.parseXLSFile(file.getOriginalFilename(), file.getBytes());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPDFFile() throws Exception {
        FileExportResult result = fileService.createPDFFile();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", result.getName()))
                .body(result.getBytes());
    }
}
