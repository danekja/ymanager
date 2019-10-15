package org.danekja.ymanager.repository.jdbc.mappers;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.UserData;
import org.danekja.ymanager.domain.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserDataRowMapper implements RowMapper<UserData> {

    @Override
    public UserData mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        float vacations = resultSet.getFloat("no_vacations");
        int sickDays = resultSet.getInt("no_sick_days");
        int takenSickDays = resultSet.getInt("taken_sick_days");

        Timestamp date = resultSet.getTimestamp("alert");
        LocalDateTime alert = date != null ? ((Timestamp) date).toLocalDateTime() : null;


        UserRole role = UserRole.valueOf(resultSet.getString("user_role"));
        Status status = Status.valueOf(resultSet.getString("status"));

        return new UserData(vacations, sickDays, takenSickDays, alert, role, status);
    }
}
