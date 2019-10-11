package org.danekja.ymanager.ws.rest;

import org.danekja.ymanager.util.localization.Language;
import org.danekja.ymanager.util.localization.Message;
import org.danekja.ymanager.ws.rest.exceptions.RESTFullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

public abstract class BaseController {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected <T> ResponseEntity handle(Language language, RESTGetHandler<T> handler) {
        return handle(language, handler, null, null);
    }

    protected <T> ResponseEntity handle(Language language, RESTGetHandler<T> handler, Function<T, String[]> header, Function<T, Object> bodyValue) {
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
            LOG.error(e.getMessage());
            return sendError(400, e.getLocalizedMessage(), language);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return sendError(401, "rest.exception.generic", language);
        }
    }

    protected ResponseEntity sendError(Integer errorCode, String messageKey, Language language) {
        String localizedMessage = Message.getString(language, messageKey);
        Map<String, String> result = new HashMap<>();
        result.put("error", errorCode.toString());
        result.put("message", localizedMessage);
        return ResponseEntity.status(errorCode).contentType(MediaType.APPLICATION_JSON).body(result);
    }

    protected <T> ResponseEntity handle(Language language, RESTInvokeHandler<T> handler) {
        try {
            handler.invoke();
            return ok(OK);
        } catch (RESTFullException e) {
            LOG.error(e.getMessage());
            return sendError(400, e.getLocalizedMessage(), language);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return sendError(401, e.getMessage(), language);
        }
    }
}
