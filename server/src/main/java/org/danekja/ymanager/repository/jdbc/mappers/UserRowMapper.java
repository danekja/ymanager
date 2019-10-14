package org.danekja.ymanager.repository.jdbc.mappers;

import org.danekja.ymanager.domain.RegisteredUser;
import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Maps "end_user" query rows into User object.
 */
public class UserRowMapper implements RowMapper<RegisteredUser> {

    @Override
    public RegisteredUser mapRow(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        float vacations = resultSet.getFloat("no_vacations");
        int sickDays = resultSet.getInt("no_sick_days");
        int takenSickDays = resultSet.getInt("taken_sick_days");

        Timestamp date = resultSet.getTimestamp("alert");
        LocalDateTime alert = date != null ? ((Timestamp) date).toLocalDateTime() : null;

        String token = resultSet.getNString("token");
        String email = resultSet.getString("email");
        String photo = resultSet.getNString("photo");

        date = resultSet.getTimestamp("creation_date");
        LocalDateTime creationDate = date.toLocalDateTime();
        UserRole role = UserRole.valueOf(resultSet.getString("user_role"));
        Status status = Status.valueOf(resultSet.getString("status"));

        return new RegisteredUser(id, firstName, lastName, vacations, sickDays, takenSickDays, alert, token, email, photo, creationDate, role, status);
    }
}
