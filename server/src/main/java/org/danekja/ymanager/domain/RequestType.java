package org.danekja.ymanager.domain;

/**
 * The enum {@code RequestType} represents different types of a request.
 */
public enum RequestType {
    /**
     * The request for a vacation.
     */
    VACATION,

    /**
     * The request for an authorization.
     */
    AUTHORIZATION;

    /**
     * Creates the type of a request from the provided string.
     * The method is case insensitive. If the given string does not correspond to any values of the enum the method returns null.
     *
     * @param type the string representation of the type of a request
     * @return the value of the enum or null
     */
    public static RequestType getType(final String type) {
        if (type == null || type.isEmpty()) return null;
        try {
            return RequestType.valueOf(type.toUpperCase());
        } catch (final IllegalArgumentException e) {
            return null;
        }
    }
}
