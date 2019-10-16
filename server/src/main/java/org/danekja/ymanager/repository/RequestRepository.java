package org.danekja.ymanager.repository;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.VacationType;
import org.danekja.ymanager.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

/**
 * An instance of the class RequestRepository handles queries which selects and updates requests in a database.
 * Requests are of two types. The first is an authorization request which is just a different view of a user.
 * It shows information relevant to a user's authorization in the application. The second one is called a vacation
 * request which works like the authorization request but for vacations and contains informations showed to an employer
 * to help him approve or reject the vacation.
 */
@Repository
public class RequestRepository {
    /**
     * The mapper maps a row from a result of a query to an AuthorizationRequest.
     */
    private class AuthorizationRequestMapper implements RowMapper<AuthorizationRequest> {

        /**
         * Maps a row from a result of a query to an AuthorizationRequest.
         * @param resultSet the row from the result
         * @param i the index of the row
         * @return the AuthorizationRequest object
         * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
         */
        @Override
        public AuthorizationRequest mapRow(ResultSet resultSet, int i) throws SQLException {
            final AuthorizationRequest request = new AuthorizationRequest();
            request.setId(resultSet.getLong("id"));
            request.setFirstName(resultSet.getString("first_name"));
            request.setLastName(resultSet.getString("last_name"));
            request.setTimestamp(resultSet.getTimestamp("creation_date").toLocalDateTime());
            request.setStatus(Status.getStatus(resultSet.getString("status")));
            return request;
        }
    }

    /**
     * The mapper maps each row from a result of a query to a VacationRequest.
     */
    private class VacationRequestMapper implements RowMapper<VacationRequest> {

        /**
         * Maps a row from a result of a query to an VacationRequest.
         * @param resultSet the row from the result
         * @param i the index of the row
         * @return the VacationRequest object
         * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
         */
        @Override
        public VacationRequest mapRow(ResultSet resultSet, int i) throws SQLException {
            final VacationRequest request = new VacationRequest();
            request.setId(resultSet.getLong("v.id"));
            request.setFirstName(resultSet.getString("u.first_name"));
            request.setLastName(resultSet.getString("u.last_name"));
            request.setDate(resultSet.getDate("v.vacation_date").toLocalDate());

            /*
                When a result contains a sick day it doesn't have specified a start and end time because
                it can be taken only as a whole day. In this case the v.time_from and v.time_to are null.
                Which must be handled.
            */
            final Time timeFrom = resultSet.getTime("v.time_from");
            if (timeFrom != null) {
                request.setFrom(timeFrom.toLocalTime());
            }

            final Time timeTo = resultSet.getTime("v.time_to");
            if (timeTo != null) {
                request.setTo(timeTo.toLocalTime());
            }

            request.setTimestamp(resultSet.getTimestamp("v.creation_date").toLocalDateTime());
            request.setType(VacationType.getVacationType(resultSet.getString("v.vacation_type")));
            request.setStatus(Status.getStatus(resultSet.getString("v.status")));
            return request;
        }
    }

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(RequestRepository.class);

    /**
     * The connection to a database.
     */
    private final JdbcTemplate jdbc;

    /**
     * Creates a manager which can select and update requests in a database with the specified connection.
     *
     * @param jdbc the connection to the database
     */
    @Autowired
    public RequestRepository(final JdbcTemplate jdbc) {
        RequestRepository.log.trace("Creating a new instance of the class RequestRepository");

        this.jdbc = jdbc;
    }

    /**
     * Gets all authorization requests from a database. Method returns all authorization requests despite
     * its authorization status. It returns accepted, pending even rejected users.
     * Every line of output is converted to an AuthorizationRequest object filled with data from the database.
     * If there aren't any authorization requests (that means any users) in the database, the method returns an empty list.
     *
     * @return the list of all authorization requests
     */
    public List<AuthorizationRequest> getAllAuthorizations() {
        RequestRepository.log.trace("Selecting all authorization requests from a database.");

        return this.jdbc.query("SELECT id, first_name, last_name, creation_date, status FROM end_user", new AuthorizationRequestMapper());
    }

    /**
     * Gets all authorization request with the given authorization status from a database.
     * Every line of output is converted to an AuthorizationRequest object filled with data from the database.
     * If there aren't any authorization requests (that means any users) with the given status in the database,
     * the method returns an empty list.
     *
     * @param status the approval status of the requests
     * @return the list of all authorization requests with the given status
     */
    public List<AuthorizationRequest> getAllAuthorizations(final Status status) {
        RequestRepository.log.trace("Selecting all authorization requests from a database with requested status.");
        RequestRepository.log.debug("Status: {}", status);

        return this.jdbc.query("SELECT id, first_name, last_name, creation_date, status FROM end_user WHERE status = ?", new Object[]{status.name()}, new AuthorizationRequestMapper());
    }

    /**
     * Updates the status of an authorization request with the given id.
     *
     * @param id     the id of the request
     * @param status the new status of the request
     */
    public void updateAuthorization(final long id, final Status status) {
        RequestRepository.log.trace("Updating status of an authorization request.");
        RequestRepository.log.debug("Id: {}, status: {}.", id, status);

        this.jdbc.update("UPDATE end_user SET status=? WHERE id=?", status.name(), id);
    }

    /**
     * Updates the status of an authorization request from the specified BasicRequest object.
     *
     * @param request the BasicRequest object with new values of the vacation request
     */
    public void updateAuthorization(final BasicRequest request) {
        this.updateAuthorization(request.getId(), request.getStatus());
    }

    /**
     * Gets all vacation requests from a database. Method returns all vacation requests despite
     * its authorization status. It returns accepted, pending even rejected vacations.
     * Every line of output is converted to a VacationRequest object filled with data from the database.
     * If there aren't any vacation requests in the database, the method returns an empty list.
     *
     * @return the list of all vacation requests
     */
    public List<VacationRequest> getAllVacationRequests() {
        RequestRepository.log.trace("Selecting all vacation requests from a database.");

        return this.jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.creation_date, v.vacation_type, v.status, u.first_name, u.last_name " +
                "FROM vacation_day v " +
                "INNER JOIN end_user u ON v.user_id = u.id", new VacationRequestMapper());
    }

    /**
     * Gets all vacation requests with the given approval status from a database.
     * Every line of output is converted to a VacationRequest object filled with data from the database.
     * If there aren't any vacation requests with the given status in the database, the method returns an empty list.
     *
     * @param status the approval status of the requests
     * @return the list of all vacation requests with the given status
     */
    public List<VacationRequest> getAllVacationRequests(final Status status) {
        RequestRepository.log.trace("Selecting all vacation requests from a database with requested status.");
        RequestRepository.log.debug("Status: {}", status);

        return this.jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.creation_date, v.vacation_type, v.status, u.first_name, u.last_name " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.status=?",
                new Object[]{status.name()}, new VacationRequestMapper());
    }

    /**
     * Updates a status of a vacation request with the given id.
     *
     * @param id     the id of the request
     * @param status the new status of the request
     */
    public void updateVacationRequest(final long id, final Status status) {
        RequestRepository.log.trace("Updating status of a vacation request");
        RequestRepository.log.debug("Id: {}, status: {}.", id, status);

        this.jdbc.update("UPDATE vacation_day SET status=? WHERE id=?", status.name(), id);
    }

    /**
     * Updates a status of a vacation request from a BasicRequest object.
     *
     * @param request the BasicRequest object with new values of the vacation request
     */
    public void updateVacationRequest(final BasicRequest request) {
        this.updateVacationRequest(request.getId(), request.getStatus());
    }
}