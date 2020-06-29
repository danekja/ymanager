package org.danekja.ymanager.repository.jdbc.mappers;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.VacationType;
import org.danekja.ymanager.domain.VacationRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * The mapper maps each row from a result of a query to a VacationRequest.
 */
public class VacationRequestMapper implements RowMapper<VacationRequest> {

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

        request.setCreationDate(resultSet.getTimestamp("v.creation_date").toLocalDateTime());
        request.setType(VacationType.getVacationType(resultSet.getString("v.vacation_type")));
        request.setStatus(Status.valueOf(resultSet.getString("v.status")));
        return request;
    }
}
