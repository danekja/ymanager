package org.danekja.ymanager.repository;

import org.danekja.ymanager.domain.AuthorizationRequest;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.VacationRequest;
import org.danekja.ymanager.dto.AuthorizationRequestDTO;
import org.danekja.ymanager.dto.BasicRequestDTO;

import java.util.List;

/**
 * An instance of the class RequestRepository handles queries which selects and updates requests in a database.
 * Requests are of two types. The first is an authorization request which is just a different view of a user.
 * It shows information relevant to a user's authorization in the application. The second one is called a vacation
 * request which works like the authorization request but for vacations and contains informations showed to an employer
 * to help him approve or reject the vacation.
 */
public interface RequestRepository {

    /**
     * Gets all authorization requests from a database. Method returns all authorization requests despite
     * its authorization status. It returns accepted, pending even rejected users.
     * Every line of output is converted to an AuthorizationRequest object filled with data from the database.
     * If there aren't any authorization requests (that means any users) in the database, the method returns an empty list.
     *
     * @return the list of all authorization requests
     */
    List<AuthorizationRequest> getAllAuthorizations();

    /**
     * Gets all authorization request with the given authorization status from a database.
     * Every line of output is converted to an AuthorizationRequest object filled with data from the database.
     * If there aren't any authorization requests (that means any users) with the given status in the database,
     * the method returns an empty list.
     *
     * @param status the approval status of the requests
     * @return the list of all authorization requests with the given status
     */
    List<AuthorizationRequest> getAllAuthorizations(final Status status);

    /**
     * Updates the status of an authorization request with the given id.
     *
     * @param id     the id of the request
     * @param status the new status of the request
     */
    void updateAuthorization(final long id, final Status status);

    /**
     * Updates the status of an authorization request from the specified BasicRequest object.
     *
     * @param request the BasicRequest object with new values of the vacation request
     */
    void updateAuthorization(final BasicRequestDTO request);

    /**
     * Gets all vacation requests from a database. Method returns all vacation requests despite
     * its authorization status. It returns accepted, pending even rejected vacations.
     * Every line of output is converted to a VacationRequest object filled with data from the database.
     * If there aren't any vacation requests in the database, the method returns an empty list.
     *
     * @return the list of all vacation requests
     */
    List<VacationRequest> getAllVacationRequests();

    /**
     * Gets all vacation requests with the given approval status from a database.
     * Every line of output is converted to a VacationRequest object filled with data from the database.
     * If there aren't any vacation requests with the given status in the database, the method returns an empty list.
     *
     * @param status the approval status of the requests
     * @return the list of all vacation requests with the given status
     */
    List<VacationRequest> getAllVacationRequests(final Status status);

    /**
     * Updates a status of a vacation request with the given id.
     *
     * @param id     the id of the request
     * @param status the new status of the request
     */
    void updateVacationRequest(final long id, final Status status);

    /**
     * Updates a status of a vacation request from a BasicRequest object.
     *
     * @param request the BasicRequest object with new values of the vacation request
     */
    void updateVacationRequest(final BasicRequestDTO request);
}
