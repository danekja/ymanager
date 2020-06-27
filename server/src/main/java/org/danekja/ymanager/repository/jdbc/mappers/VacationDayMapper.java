package org.danekja.ymanager.repository.jdbc.mappers;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.VacationType;
import org.danekja.ymanager.dto.VacationDayDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * The mapper maps a row from a result of a query to an Vacation.
 */
public class VacationDayMapper implements RowMapper<VacationDayDTO> {

    /**
     * Maps a row from a result of a query to an Vacation.
     * @param resultSet the row from the result
     * @param i the index of the row
     * @return the Vacation object
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    @Override
    public VacationDayDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        final VacationDayDTO item = new VacationDayDTO();
        item.setId(resultSet.getLong("v.id"));
        item.setDate(resultSet.getDate("v.vacation_date").toLocalDate());

        /*
            When a result contains a sick day it doesn't have specified a start and end time because
            it can be taken only as a whole day. In this case the v.time_from and v.time_to are null.
            Which must be handled.
        */
        final Time timeFrom = resultSet.getTime("v.time_from");
        if (timeFrom != null) {
            item.setFrom(timeFrom.toLocalTime());
        }

        final Time timeTo = resultSet.getTime("v.time_to");
        if (timeTo != null) {
            item.setTo(timeTo.toLocalTime());
        }

        item.setStatus(Status.valueOf(resultSet.getString("v.status")));
        item.setType(VacationType.getVacationType(resultSet.getString("v.vacation_type")));
        return item;
    }
}
