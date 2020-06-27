package org.danekja.ymanager.dto;

import org.danekja.ymanager.domain.Status;

/**
 * An instance of the messenger class {@code BasicRequest} can represent an authorization or a vacation request.
 * The class is used for changing a status of the authorization or the vacation request when a client sends a request on an api endpoint.
 * This class is used to communicate with a frontend.
 */
public class BasicRequestDTO {
    /**
     * The ID of this request.
     */
    private Long id;

    /**
     * The approval/authorization status of this request.
     */
    private Status status;

    /**
     * Returns the ID of this request.
     *
     * @return the ID of this request
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Replaces the user's ID with the specified value.
     *
     * @param id the new user's ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Returns the approval/authorization status of this request.
     *
     * @return the approval status of this vacation
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Replaces the approval/authorization status of this request with the given value.
     *
     * @param status the new approval/authorization status
     */
    public void setStatus(final Status status) {
        this.status = status;
    }
}
