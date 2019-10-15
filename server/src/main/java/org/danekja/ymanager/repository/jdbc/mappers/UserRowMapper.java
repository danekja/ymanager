package org.danekja.ymanager.repository.jdbc.mappers;

import org.danekja.ymanager.domain.RegisteredUser;
import org.danekja.ymanager.domain.UserData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Maps "end_user" query rows into User object.
 */
public class UserRowMapper implements RowMapper<RegisteredUser> {

    private final RowMapper<UserData> dataRowMapper;

    public UserRowMapper(RowMapper<UserData> dataRowMapper) {
        this.dataRowMapper = dataRowMapper;
    }

    @Override
    public RegisteredUser mapRow(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");

        String email = resultSet.getString("email");
        String photo = resultSet.getNString("photo");

        Timestamp date = resultSet.getTimestamp("creation_date");
        LocalDateTime creationDate = date.toLocalDateTime();

        UserData userData = dataRowMapper.mapRow(resultSet, i);
        return new RegisteredUser(id, firstName, lastName, email, photo, creationDate, userData);
    }
}
