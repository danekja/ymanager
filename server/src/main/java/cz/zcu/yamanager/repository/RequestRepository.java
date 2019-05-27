package cz.zcu.yamanager.repository;

import cz.zcu.yamanager.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
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

        return jdbc.query("SELECT EU.id, EU.first_name, EU.last_name, EU.creation_date, APS.name " +
                "FROM end_user EU " +
                "INNER JOIN approval_status APS ON EU.status_id = APS.id",
                (ResultSet rs, int rowNum) -> {
            AuthorizationRequest request = new AuthorizationRequest();
            request.setId(rs.getLong("EU.id"));
            request.setFirstName(rs.getString("EU.first_name"));
            request.setLastName(rs.getString("EU.last_name"));
            request.setStatus(Status.getStatus(rs.getString("APS.name")));
            request.setTimestamp(rs.getTimestamp("EU.creation_date").toLocalDateTime());
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

        return jdbc.query("SELECT EU.id, EU.first_name, EU.last_name, EU.creation_date " +
                "FROM end_user EU " +
                "INNER JOIN approval_status APS ON EU.status_id = APS.id " +
                "WHERE APS.name=?",
                new Object[]{status.name()},
                (ResultSet rs, int rowNum) -> {
            AuthorizationRequest request = new AuthorizationRequest();
            request.setId(rs.getLong("EU.id"));
            request.setFirstName(rs.getString("EU.first_name"));
            request.setLastName(rs.getString("EU.last_name"));
            request.setStatus(status);
            request.setTimestamp(rs.getTimestamp("EU.creation_date").toLocalDateTime());
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

        return jdbc.query("SELECT VD.id, VD.vacation_date, VD.time_from, VD.time_to, VD.creation_date, VT.name, APS.name, EU.first_name, EU.last_name " +
                "FROM vacation_day VD " +
                "INNER JOIN vacation_type VT on VD.type_id=VT.id " +
                "INNER JOIN approval_status APS ON VD.status_id=APS.id" +
                "INNER JOIN end_user EU ON VD.user_id=EU.id",
                (ResultSet rs, int rowNum) -> {
            VacationRequest request = new VacationRequest();
            request.setId(rs.getLong("VD.id"));
            request.setFirstName(rs.getString("EU.first_name"));
            request.setLastName(rs.getString("EU.last_name"));
            request.setDate(rs.getDate("VD.vacation_date").toLocalDate());
            request.setFrom(rs.getTime("VD.time_from").toLocalTime());
            request.setTo(rs.getTime("VD.time_to").toLocalTime());
            request.setType(VacationType.getVacationType("VT.name"));
            request.setStatus(Status.getStatus("APS.name"));
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

        return jdbc.query("SELECT VD.id, VD.vacation_date, VD.time_from, VD.time_to, VD.creation_date, VT.name, APS.name, EU.first_name, EU.last_name " +
                "FROM vacation_day VD " +
                "INNER JOIN vacation_type VT on VD.type_id=VT.id " +
                "INNER JOIN approval_status APS ON VD.status_id=APS.id" +
                "INNER JOIN end_user EU ON VD.user_id=EU.id" +
                "WHERE APS.name=?",
                new Object[]{status.name()},
                (ResultSet rs, int rowNum) -> {
            VacationRequest request = new VacationRequest();
            request.setId(rs.getLong("VD.id"));
            request.setFirstName(rs.getString("EU.first_name"));
            request.setLastName(rs.getString("EU.last_name"));
            request.setDate(rs.getDate("VD.vacation_date").toLocalDate());
            request.setFrom(rs.getTime("VD.time_from").toLocalTime());
            request.setTo(rs.getTime("VD.time_to").toLocalTime());
            request.setType(VacationType.getVacationType("VT.name"));
            request.setStatus(status);
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
