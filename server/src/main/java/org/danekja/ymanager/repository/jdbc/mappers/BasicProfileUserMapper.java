package org.danekja.ymanager.repository.jdbc.mappers;

import org.danekja.ymanager.dto.BasicProfileUserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The mapper maps a row from a result of a query to an BasicProfileUser.
 */
public class BasicProfileUserMapper implements RowMapper<BasicProfileUserDTO> {

    /**
     * Maps a row from a result of a query to an BasicProfileUser.
     * @param resultSet the row from the result
     * @param i the index of the row
     * @return the BasicProfileUser object
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    @Override
    public BasicProfileUserDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        final BasicProfileUserDTO user = new BasicProfileUserDTO();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setPhoto(resultSet.getString("photo"));
        return user;
    }
}
