package org.danekja.ymanager.ws.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
        Logger log = getExceptionLogger(ex);
        log.error(ex.getMessage());
        log.debug(ex.getMessage(), ex);

        Map<String, String> result = new HashMap<>();
        result.put("message", ex.getMessage());

        return super.handleExceptionInternal(ex, result, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        Logger log = getExceptionLogger(ex);
        log.debug(ex.getMessage());
        log.debug(ex.getMessage(), ex);

        return super.handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Logger log = getExceptionLogger(ex);
        log.debug(ex.getMessage());
        log.debug(ex.getMessage(), ex);

        Map<String, String> result = new HashMap<>();
        result.put("message", ex.getMessage());

        return super.handleExceptionInternal(ex, result, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        Logger log = getExceptionLogger(ex);
        log.error(ex.getMessage());
        log.debug(ex.getMessage(), ex);

        Map<String, String> result = new HashMap<>();
        result.put("message", ex.getMessage());

        return super.handleExceptionInternal(ex, result, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        Logger log = getExceptionLogger(ex);
        log.error(ex.getMessage());
        log.debug(ex.getMessage(), ex);

        Map<String, String> result = new HashMap<>();
        result.put("message", ex.getReason());

        return super.handleExceptionInternal(ex, result, new HttpHeaders(), ex.getStatus(), request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public final ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        Logger log = getExceptionLogger(ex);
        log.error(ex.getMessage());
        log.debug(ex.getMessage(), ex);

        Map<String, String> result = new HashMap<>();
        result.put("message", ex.getMessage());

        return super.handleExceptionInternal(ex, result, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    protected Logger getExceptionLogger(Exception ex) {
        return LoggerFactory.getLogger(ex.getStackTrace()[0].getClassName());
    }
}
