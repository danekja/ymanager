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
     * @param jdbc A connection to the database.
     */
    @Autowired
    public RequestRepository(JdbcTemplate jdbc) {
        log.trace("Creating a new instance of the class RequestRepository");
        this.jdbc = jdbc;
    }

    /**
     * Gets all authorization request from a database.
     * @return A list of all authorization requests.
     */
    public List<AuthorizationRequest> getAllAuthorizations() {
        log.trace("Selecting all authorization requests from a database.");

        return jdbc.query("SELECT u.id, u.first_name, u.last_name, u.creation_date, s.name " +
                "FROM end_user u " +
                "INNER JOIN approval_status s ON u.status_id = s.id",
                (ResultSet rs, int rowNum) -> {
                    AuthorizationRequest request = new AuthorizationRequest();
                    request.setId(rs.getLong("u.id"));
                    request.setFirstName(rs.getString("u.first_name"));
                    request.setLastName(rs.getString("u.last_name"));
                    request.setStatus(Status.getStatus(rs.getString("s.name")));
                    request.setTimestamp(rs.getTimestamp("u.creation_date").toLocalDateTime());
                    return request;
                });
    }

    /**
     * Gets all authorization request with the given status from a database.
     * @param status The approval status of the requests.
     * @return A list of all authorization requests.
     */
    public List<AuthorizationRequest> getAllAuthorizations(Status status) {
        log.debug("Selecting all authorization requests from a database with requested status: {}", status);

        if(status == null) throw new InvalidParameterException();
        return jdbc.query("SELECT u.id, u.first_name, u.last_name, u.creation_date, s.name " +
                "FROM end_user u " +
                "INNER JOIN approval_status s ON u.status_id = s.id " +
                "WHERE s.name = ?",
                new Object[]{status.name()},
                (ResultSet rs, int rowNum) -> {
                    AuthorizationRequest request = new AuthorizationRequest();
                    request.setId(rs.getLong("u.id"));
                    request.setFirstName(rs.getString("u.first_name"));
                    request.setLastName(rs.getString("u.last_name"));
                    request.setStatus(Status.getStatus(rs.getString("s.name")));
                    request.setTimestamp(rs.getTimestamp("u.creation_date").toLocalDateTime());
                    return request;
                });
    }

    /**
     * Updates a status of an authorization request with the given id.
     * @param id The id of the request.
     * @param status The new status of the request.
     */
    public void updateAuthorization(long id, Status status) {
        log.debug("Updating authorization request with id {} in a database to {}.", id, status);

        jdbc.update("UPDATE end_user EU, approval_status APS SET EU.status_id=APS.id WHERE EU.id=? AND APS.name=?", id, status.name());
    }

    /**
     * Updates a status of an authorization request from a BasicRequest object.
     * @param request The BasicRequest object with new values of the vacation request.
     */
    public void updateAuthorization(BasicRequest request) {
        this.updateAuthorization(request.getId(), request.getStatus());
    }

    /**
     * Updates a status of an authorization request from an AuthorizationRequest object.
     * @param request The AuthorizationRequest object with new values of the authorization request.
     */
    public void updateAuthorization(AuthorizationRequest request) {
        this.updateAuthorization(request.getId(), request.getStatus());
    }

    /**
     * Gets all vacation request from a database.
     * @return A list of all vacation requests.
     */
    public List<VacationRequest> getAllVacationRequests() {
        log.trace("Selecting all vacation requests from a database.");

        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.creation_date, t.name, s.name, u.first_name, u.last_name " +
                "FROM vacation_day v " +
                "INNER JOIN vacation_type t on v.type_id = t.id " +
                "INNER JOIN approval_status s ON v.status_id = s.id " +
                "INNER JOIN end_user u ON v.user_id = u.id",
                (ResultSet rs, int rowNum) -> {
                    VacationRequest request = new VacationRequest();
                    request.setId(rs.getLong("v.id"));
                    request.setFirstName(rs.getString("u.first_name"));
                    request.setLastName(rs.getString("u.last_name"));
                    request.setDate(rs.getDate("v.vacation_date").toLocalDate());
                    Time timeFrom = rs.getTime("v.time_from");
                    if(timeFrom != null) {
                        request.setFrom(timeFrom.toLocalTime());
                    }

                    Time timeTo = rs.getTime("v.time_to");
                    if(timeTo != null) {
                        request.setTo(timeTo.toLocalTime());
                    }
                    request.setTimestamp(rs.getTimestamp("v.creation_date").toLocalDateTime());
                    request.setType(VacationType.getVacationType(rs.getString("t.name")));
                    request.setStatus(Status.getStatus(rs.getString("s.name")));
                    return request;
                });
    }

    /**
     * Gets all vacation request with the given status from a database.
     * @param status The approval status of the requests.
     * @return A list of all vacation requests.
     */
    public List<VacationRequest> getAllVacationRequests(Status status) {
        log.debug("Selecting all vacation requests from a database with requested status: {}", status);

        if(status == null) throw new InvalidParameterException();
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.creation_date, t.name, s.name, u.first_name, u.last_name " +
                "FROM vacation_day v " +
                "INNER JOIN vacation_type t on v.type_id = t.id " +
                "INNER JOIN approval_status s ON v.status_id = s.id " +
                "INNER JOIN end_user u ON v.user_id = u.id " +
                "WHERE s.name=?",
                new Object[]{status.name()},
                (ResultSet rs, int rowNum) -> {
                    VacationRequest request = new VacationRequest();
                    request.setId(rs.getLong("v.id"));
                    request.setFirstName(rs.getString("u.first_name"));
                    request.setLastName(rs.getString("u.last_name"));
                    request.setDate(rs.getDate("v.vacation_date").toLocalDate());
                    Time timeFrom = rs.getTime("v.time_from");
                    if(timeFrom != null) {
                        request.setFrom(timeFrom.toLocalTime());
                    }

                    Time timeTo = rs.getTime("v.time_to");
                    if(timeTo != null) {
                        request.setTo(timeTo.toLocalTime());
                    }

                    request.setTimestamp(rs.getTimestamp("v.creation_date").toLocalDateTime());
                    request.setType(VacationType.getVacationType(rs.getString("t.name")));
                    request.setStatus(Status.getStatus(rs.getString("s.name")));
                    return request;
                });
    }

    /**
     * Updates a status of a vacation request with the given id.
     * @param id The id of the request.
     * @param status The new status of the request.
     */
    public void updateVacationRequest(long id, Status status) {
        log.debug("Updating vacation request with id {} in a database to {}.", id, status);

        jdbc.update("UPDATE vacation_day VD, approval_status APS SET VD.status_id=APS.id WHERE VD.id=? AND APS.name=?", id, status.name());
    }

    /**
     * Updates a status of a vacation request from a BasicRequest object.
     * @param request The BasicRequest object with new values of the vacation request.
     */
    public void updateVacationRequest(BasicRequest request) {
        this.updateVacationRequest(request.getId(), request.getStatus());
    }

    /**
     * Updates a status of a vacation request from a VacationRequest object.
     * @param request The VacationRequest object with new values of the vacation request.
     */
    public void updateVacationRequest(VacationRequest request) {
        this.updateAuthorization(request.getId(), request.getStatus());
    }
}
