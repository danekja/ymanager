package org.danekja.ymanager.repository.jdbc.mappers;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.dto.AuthorizationRequestDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The mapper maps a row from a result of a query to an AuthorizationRequest.
 */
public class AuthorizationRequestMapper implements RowMapper<AuthorizationRequestDTO> {

    /**
     * Maps a row from a result of a query to an AuthorizationRequest.
     * @param resultSet the row from the result
     * @param i the index of the row
     * @return the AuthorizationRequest object
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    @Override
    public AuthorizationRequestDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        final AuthorizationRequestDTO request = new AuthorizationRequestDTO();
        request.setId(resultSet.getLong("id"));
        request.setFirstName(resultSet.getString("first_name"));
        request.setLastName(resultSet.getString("last_name"));
        request.setTimestamp(resultSet.getTimestamp("creation_date").toLocalDateTime());
        request.setStatus(Status.valueOf(resultSet.getString("status")));
        return request;
    }
}
