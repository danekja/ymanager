package org.danekja.ymanager.repository.jdbc.mappers;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.Vacation;
import org.danekja.ymanager.domain.VacationType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Maps "vacation_day" query rows into Vacation object.
 */
public class VacationMapper implements RowMapper<Vacation> {
    @Override
    public Vacation mapRow(ResultSet rs, int i) throws SQLException {
        Vacation vacation = new Vacation();
        vacation.setId(rs.getLong("id"));
        vacation.setDate(rs.getDate("vacation_date").toLocalDate());

        /*
            When a result contains a sick day it doesn't have specified a start and end time because
            it can be taken only as a whole day. In this case the v.time_from and v.time_to are null.
            Which must be handled.
        */
        final Time timeFrom = rs.getTime("time_from");
        if (timeFrom != null) {
            vacation.setFrom(timeFrom.toLocalTime());
        }

        final Time timeTo = rs.getTime("time_to");
        if (timeTo != null) {
            vacation.setTo(timeTo.toLocalTime());
        }

        vacation.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
        vacation.setStatus(Status.valueOf(rs.getString("status")));
        vacation.setType(VacationType.getVacationType(rs.getString("vacation_type")));
        vacation.setUserId(rs.getLong("user_id"));
        return vacation;
    }
}
