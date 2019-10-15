package org.danekja.ymanager.repository.jdbc.mappers;

import org.danekja.ymanager.domain.DefaultSettings;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultSettingsMapper implements RowMapper<DefaultSettings> {

    /**
     * Maps a row from a result of a query to an DefaultSettings.
     *
     * @param resultSet the row from the result
     * @param i         the index of the row
     * @return the DefaultSettings object
     * @throws java.sql.SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
     */
    @Override
    public DefaultSettings mapRow(ResultSet resultSet, int i) throws SQLException {
        final DefaultSettings settings = new DefaultSettings();
        settings.setSickDayCount(resultSet.getInt("no_sick_days"));
        settings.setNotification(resultSet.getTimestamp("alert").toLocalDateTime());
        return settings;
    }
}
