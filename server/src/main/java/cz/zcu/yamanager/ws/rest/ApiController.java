package cz.zcu.yamanager.ws.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.zcu.yamanager.business.FileService;
import cz.zcu.yamanager.business.Manager;
import cz.zcu.yamanager.dto.BasicRequest;
import cz.zcu.yamanager.dto.DefaultSettings;
import cz.zcu.yamanager.dto.UserSettings;
import cz.zcu.yamanager.dto.VacationDay;
import cz.zcu.yamanager.util.localization.Language;
import cz.zcu.yamanager.util.localization.Message;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static cz.zcu.yamanager.dto.RequestType.getType;
import static cz.zcu.yamanager.dto.Status.getStatus;
import static cz.zcu.yamanager.util.localization.Language.getLanguage;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    private final Manager manager;

    private final FileService fileService;

    @Autowired
    public ApiController(Manager manager, FileService fileService) {
        this.manager = manager;
        this.fileService = fileService;
    }

    private ResponseEntity sendError(Integer errorCode, String messageKey, Language language) {
        String localizedMessage = Message.getString(language, messageKey);
        Map<String, String> result = new HashMap<>();
        result.put("error", errorCode.toString());
        result.put("message", localizedMessage);
        return ResponseEntity.status(errorCode).contentType(MediaType.APPLICATION_JSON).body(result);
    }

    private <T> ResponseEntity handle(Language language, RESTInvokeHandler<T> handler) {
        try {
            handler.invoke();
            return ok(OK);
        } catch (RESTFullException e) {
            log.error(e.getMessage());
            return sendError(400, e.getLocalizedMessage(), language);
        } catch (Exception e) {
            log.error(e.getMessage());
            return sendError(401, "rest.exception.generic", language);
        }
    }

    private <T> ResponseEntity handle(Language language, RESTGetHandler<T> handler) {
        return handleWithHeader(language, handler, null, null);
    }

    private <T> ResponseEntity handleWithHeader(Language language, RESTGetHandler<T> handler, Function<T, String[]> header, Function<T, Object> bodyValue) {
        try {
            T result = handler.get();

            ResponseEntity.BodyBuilder response = ResponseEntity.ok();

            if (header != null) {
                String[] headers = header.apply(result);

                if (headers.length > 1) {
                    response.header(headers[0], Arrays.copyOfRange(headers, 1, headers.length - 1));
                } else if (headers.length == 1) {
                    response.header(headers[0]);
                }
            }

            return response.body(bodyValue != null ? bodyValue.apply(result) : result);

        } catch (RESTFullException e) {
            log.error(e.getMessage());
            return sendError(400, e.getLocalizedMessage(), language);
        } catch (Exception e) {
            log.error(e.getMessage());
            return sendError(401, "rest.exception.generic", language);
        }
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

    // *********************** GET ****************************

    @RequestMapping(value = "/users", method=GET)
    public ResponseEntity users(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "status", required = false) String status)
    {
        return handle(getLanguage(lang), () ->
                manager.getUsers(getStatus(status))
        );
    }

    @RequestMapping(value = "/users/requests/vacation", method=GET)
    public ResponseEntity usersRequestsVacation(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "status", required = false) String status)
    {
        return handle(getLanguage(lang), () ->
                manager.getVacationRequests(getStatus(status))
        );
    }

    @RequestMapping(value = "/users/requests/authorization", method=GET)
    public ResponseEntity userRequestsAuthorization(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "status", required = false) String status)
    {
        return handle(getLanguage(lang), () ->
                manager.getAuthorizationRequests(getStatus(status))
         );
    }

    @RequestMapping(value = "/user/{id}/profile", method=GET)
    public ResponseEntity userProfile(
            @PathVariable("id") String id,
            @RequestParam(value = "lang", required = false) String lang)
    {
        return handle(getLanguage(lang), () ->
                manager.getUserProfile(getUserId(id))
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
        return handle(getLanguage(lang), () -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate fromDate = LocalDate.parse(from, formatter);
            LocalDate toDate = to != null ? LocalDate.parse(to, formatter) : null;
            return manager.getUserCalendar(getUserId(id), fromDate, toDate, getStatus(status));
        });
    }

    @RequestMapping(value = "/settings", method=GET)
    public ResponseEntity settings(
            @RequestParam(value = "lang", required = false) String lang)
    {
        return handle(getLanguage(lang), () ->
                manager.getDefaultSettings()
        );
    }

    // *********************** POST ****************************

    @RequestMapping(value = "/settings", method=POST)
    public ResponseEntity settings(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody DefaultSettings settings)
    {
        return handle(getLanguage(lang), () ->
                manager.createSettings(settings)
        );
    }

    @RequestMapping(value = "/user/calendar/create", method=POST)
    public ResponseEntity userCalendarCreate(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody VacationDay vacationDay)
    {
        return handle(getLanguage(lang), () ->
                manager.createVacation(getUserId("me"), vacationDay)
        );
    }

    // *********************** PUT ****************************


    @RequestMapping(value = "/user/settings", method=PUT)
    public ResponseEntity userSettings(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody UserSettings settings)
    {
        return handle(getLanguage(lang), () ->
                manager.changeSettings(getUserId("me"), settings)
        );
    }

    @RequestMapping(value = "/user/calendar/edit", method=PUT)
    public ResponseEntity userCalendarEdit(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody VacationDay vacationDay)
    {
        return handle(getLanguage(lang), () ->
                manager.changeVacation(getUserId("me"), vacationDay)
        );
    }

    @RequestMapping(value = "/user/requests", method=PUT)
    public ResponseEntity userRequests(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam(value = "type", required = true) String type,
            @RequestBody BasicRequest request)
    {
        return handle(getLanguage(lang), () ->
                manager.changeRequest(getType(type), request)
        );
    }

    // *********************** DELETE ****************************

    @RequestMapping(value = "/calendar/delete", method=DELETE)
    public ResponseEntity calendarDelete(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestBody String id)
    {
        return handle(getLanguage(lang), () -> {
            Long vacationId = ((Integer) new ObjectMapper().readValue(id, HashMap.class).get("id")).longValue();
            manager.deleteVacation(getUserId("me"), vacationId);
        });
    }

    // *********************** FILE ****************************

    @RequestMapping(value = "/import/xls", method=POST)
    public ResponseEntity importXLSFile(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam("file") MultipartFile file)
    {
        return handle(getLanguage(lang), () ->
            fileService.parseXLSFile(file.getOriginalFilename(), file.getBytes())
        );
    }

    @RequestMapping(value = "/export/pdf", method=GET)
    public ResponseEntity exportPDFFile(
            @RequestParam(value = "lang", required = false) String lang)
    {
        return handleWithHeader(getLanguage(lang),
                () -> fileService.createPDFFile(),
                (res) -> new String[]{HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + res.getName() + "\""},
                (res) -> res.getBytes()
        );
    }
}
