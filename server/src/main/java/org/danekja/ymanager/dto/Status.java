package org.danekja.ymanager.dto;

/**
 * The enum {@code Status} represents states of authorization and vacation approval process.
 */
public enum Status {
    /**
     * The subject of a request is accepted.
     */
    ACCEPTED,

    /**
     * The subject of a request is waiting for an answer.
     */
    PENDING,

    /**
     * The subject of a request is rejected.
     */
    REJECTED;

    /**
     * Creates the status from the provided string.
     * The method is case insensitive. If the given string does not correspond to any values of the enum the method returns null.
     *
     * @param status the string representation of the status
     * @return the value of the enum or null
     */
    public static Status getStatus(final String status) {
        if (status == null || status.isEmpty()) return null;
        try {
            return Status.valueOf(status.toUpperCase());
        } catch (final IllegalArgumentException e) {
            return null;
        }
    }
}
