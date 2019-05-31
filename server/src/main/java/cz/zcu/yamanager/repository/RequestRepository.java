package cz.zcu.yamanager.repository;

import cz.zcu.yamanager.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.List;

/**
 * An instance of the class RequestRepository handles queries which selects and updates requests in a database.
 */
@Repository
public class RequestRepository {
    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(RequestRepository.class);

    /**
     * The connection to a database.
     */
    private final JdbcTemplate jdbc;

    /**
     * Creates a new instance of the class RequestRepository which selects and updates requests in a database.
     *
     * @param jdbc A connection to the database.
     */
    @Autowired
    public RequestRepository(final JdbcTemplate jdbc) {
        RequestRepository.log.trace("Creating a new instance of the class RequestRepository");
        this.jdbc = jdbc;
    }

    /**
     * Gets all authorization request from a database.
     *
     * @return A list of all authorization requests.
     */
    public List<AuthorizationRequest> getAllAuthorizations() {
        RequestRepository.log.trace("Selecting all authorization requests from a database.");

        return this.jdbc.query("SELECT id, first_name, last_name, creation_date, status FROM end_user",
                (ResultSet rs, int rowNum) -> {
                    final AuthorizationRequest request = new AuthorizationRequest();
                    request.setId(rs.getLong("id"));
                    request.setFirstName(rs.getString("first_name"));
                    request.setLastName(rs.getString("last_name"));
                    request.setTimestamp(rs.getTimestamp("creation_date").toLocalDateTime());
                    request.setStatus(Status.getStatus(rs.getString("status")));
                    return request;
                });
    }

    /**
     * Gets all authorization request with the given status from a database.
     *
     * @param status The approval status of the requests.
     * @return A list of all authorization requests.
     */
    public List<AuthorizationRequest> getAllAuthorizations(final Status status) {
        RequestRepository.log.debug("Selecting all authorization requests from a database with requested status: {}", status);

        if (status == null) throw new InvalidParameterException();
        return this.jdbc.query("SELECT id, first_name, last_name, creation_date FROM end_user WHERE status = ?",
                new Object[]{status.name()},
                (ResultSet rs, int rowNum) -> {
                    final AuthorizationRequest request = new AuthorizationRequest();
                    request.setId(rs.getLong("id"));
                    request.setFirstName(rs.getString("first_name"));
                    request.setLastName(rs.getString("last_name"));
                    request.setTimestamp(rs.getTimestamp("creation_date").toLocalDateTime());
                    request.setStatus(status);
                    return request;
                });
    }

    /**
     * Updates a status of an authorization request with the given id.
     *
     * @param id     The id of the request.
     * @param status The new status of the request.
     */
    public void updateAuthorization(final long id, final Status status) {
        RequestRepository.log.debug("Updating authorization request with id {} in a database to {}.", id, status);

        this.jdbc.update("UPDATE end_user SET status=? WHERE id=?", status.name(), id);
    }

    /**
     * Updates a status of an authorization request from a BasicRequest object.
     *
     * @param request The BasicRequest object with new values of the vacation request.
     */
    public void updateAuthorization(final BasicRequest request) {
        updateAuthorization(request.getId(), request.getStatus());
    }

    /**
     * Updates a status of an authorization request from an AuthorizationRequest object.
     *
     * @param request The AuthorizationRequest object with new values of the authorization request.
     */
    public void updateAuthorization(final AuthorizationRequest request) {
        updateAuthorization(request.getId(), request.getStatus());
    }

    /**
     * Gets all vacation request from a database.
     *
     * @return A list of all vacation requests.
     */
    public List<VacationRequest> getAllVacationRequests() {
        RequestRepository.log.trace("Selecting all vacation requests from a database.");

        return this.jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.creation_date, v.vacation_type, v.status, u.first_name, u.last_name " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id",
                (ResultSet rs, int rowNum) -> {
                    final VacationRequest request = new VacationRequest();
                    request.setId(rs.getLong("v.id"));
                    request.setFirstName(rs.getString("u.first_name"));
                    request.setLastName(rs.getString("u.last_name"));
                    request.setDate(rs.getDate("v.vacation_date").toLocalDate());
                    final Time timeFrom = rs.getTime("v.time_from");
                    if (timeFrom != null) {
                        request.setFrom(timeFrom.toLocalTime());
                    }

                    final Time timeTo = rs.getTime("v.time_to");
                    if (timeTo != null) {
                        request.setTo(timeTo.toLocalTime());
                    }

                    request.setTimestamp(rs.getTimestamp("v.creation_date").toLocalDateTime());
                    request.setType(VacationType.getVacationType(rs.getString("v.vacation_type")));
                    request.setStatus(Status.getStatus(rs.getString("v.status")));
                    return request;
                });
    }

    /**
     * Gets all vacation request with the given status from a database.
     *
     * @param status The approval status of the requests.
     * @return A list of all vacation requests.
     */
    public List<VacationRequest> getAllVacationRequests(final Status status) {
        RequestRepository.log.debug("Selecting all vacation requests from a database with requested status: {}", status);

        if (status == null) throw new InvalidParameterException();
        return this.jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.creation_date, v.vacation_type, v.status, u.first_name, u.last_name " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.status=?",
                new Object[]{status.name()},
                (ResultSet rs, int rowNum) -> {
                    final VacationRequest request = new VacationRequest();
                    request.setId(rs.getLong("v.id"));
                    request.setFirstName(rs.getString("u.first_name"));
                    request.setLastName(rs.getString("u.last_name"));
                    request.setDate(rs.getDate("v.vacation_date").toLocalDate());
                    final Time timeFrom = rs.getTime("v.time_from");
                    if (timeFrom != null) {
                        request.setFrom(timeFrom.toLocalTime());
                    }

                    final Time timeTo = rs.getTime("v.time_to");
                    if (timeTo != null) {
                        request.setTo(timeTo.toLocalTime());
                    }

                    request.setTimestamp(rs.getTimestamp("v.creation_date").toLocalDateTime());
                    request.setType(VacationType.getVacationType(rs.getString("v.vacation_type")));
                    request.setStatus(status);
                    return request;
                });
    }

    /**
     * Updates a status of a vacation request with the given id.
     *
     * @param id     The id of the request.
     * @param status The new status of the request.
     */
    public void updateVacationRequest(final long id, final Status status) {
        RequestRepository.log.debug("Updating vacation request with id {} in a database to {}.", id, status);

        this.jdbc.update("UPDATE vacation_day SET status=? WHERE id=?", status.name(), id);
    }

    /**
     * Updates a status of a vacation request from a BasicRequest object.
     *
     * @param request The BasicRequest object with new values of the vacation request.
     */
    public void updateVacationRequest(final BasicRequest request) {
        updateVacationRequest(request.getId(), request.getStatus());
    }

    /**
     * Updates a status of a vacation request from a VacationRequest object.
     *
     * @param request The VacationRequest object with new values of the vacation request.
     */
    public void updateVacationRequest(final VacationRequest request) {
        updateAuthorization(request.getId(), request.getStatus());
    }
}
