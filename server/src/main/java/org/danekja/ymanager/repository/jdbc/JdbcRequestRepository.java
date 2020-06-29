package org.danekja.ymanager.repository.jdbc;

import org.danekja.ymanager.domain.AuthorizationRequest;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.VacationRequest;
import org.danekja.ymanager.dto.AuthorizationRequestDTO;
import org.danekja.ymanager.dto.BasicRequestDTO;
import org.danekja.ymanager.repository.RequestRepository;
import org.danekja.ymanager.repository.jdbc.mappers.AuthorizationRequestMapper;
import org.danekja.ymanager.repository.jdbc.mappers.VacationRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcRequestRepository implements RequestRepository {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(JdbcRequestRepository.class);

    private static final VacationRequestMapper VACATION_WITH_USER_MAPPER = new VacationRequestMapper();
    private static final AuthorizationRequestMapper AUTHORIZATION_REQUEST_MAPPER = new AuthorizationRequestMapper();

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
    public JdbcRequestRepository(final JdbcTemplate jdbc) {
        JdbcRequestRepository.log.trace("Creating a new instance of the class RequestRepository");

        this.jdbc = jdbc;
    }

    @Override
    public List<AuthorizationRequest> getAllAuthorizations() {
        JdbcRequestRepository.log.trace("Selecting all authorization requests from a database.");

        return this.jdbc.query("SELECT id, first_name, last_name, creation_date, status FROM end_user", AUTHORIZATION_REQUEST_MAPPER);
    }

    @Override
    public List<AuthorizationRequest> getAllAuthorizations(final Status status) {
        JdbcRequestRepository.log.trace("Selecting all authorization requests from a database with requested status.");
        JdbcRequestRepository.log.debug("Status: {}", status);

        return this.jdbc.query("SELECT id, first_name, last_name, creation_date, status FROM end_user WHERE status = ?", AUTHORIZATION_REQUEST_MAPPER, status.name());
    }

    @Override
    public void updateAuthorization(final long id, final Status status) {
        JdbcRequestRepository.log.trace("Updating status of an authorization request.");
        JdbcRequestRepository.log.debug("Id: {}, status: {}.", id, status);

        this.jdbc.update("UPDATE end_user SET status=? WHERE id=?", status.name(), id);
    }

    @Override
    public void updateAuthorization(final BasicRequestDTO request) {
        this.updateAuthorization(request.getId(), request.getStatus());
    }

    @Override
    public List<VacationRequest> getAllVacationRequests() {
        JdbcRequestRepository.log.trace("Selecting all vacation requests from a database.");

        return this.jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.creation_date, v.vacation_type, v.status, u.first_name, u.last_name " +
                "FROM vacation_day v " +
                "INNER JOIN end_user u ON v.user_id = u.id", VACATION_WITH_USER_MAPPER);
    }

    @Override
    public List<VacationRequest> getAllVacationRequests(final Status status) {
        JdbcRequestRepository.log.trace("Selecting all vacation requests from a database with requested status.");
        JdbcRequestRepository.log.debug("Status: {}", status);

        return this.jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.creation_date, v.vacation_type, v.status, u.first_name, u.last_name " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.status=?", VACATION_WITH_USER_MAPPER, status.name());
    }

    @Override
    public void updateVacationRequest(final long id, final Status status) {
        JdbcRequestRepository.log.trace("Updating status of a vacation request");
        JdbcRequestRepository.log.debug("Id: {}, status: {}.", id, status);

        this.jdbc.update("UPDATE vacation_day SET status=? WHERE id=?", status.name(), id);
    }

    @Override
    public void updateVacationRequest(final BasicRequestDTO request) {
        this.updateVacationRequest(request.getId(), request.getStatus());
    }
}
