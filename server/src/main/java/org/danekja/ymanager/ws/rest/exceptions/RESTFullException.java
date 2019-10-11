package org.danekja.ymanager.ws.rest.exceptions;

public class RESTFullException extends Exception {

    private final String messageKey;

    public RESTFullException(String message, String messageKey) {
        super(message);
        this.messageKey = messageKey;
    }

    @Override
    public String getLocalizedMessage() {
        return messageKey;
    }
}
