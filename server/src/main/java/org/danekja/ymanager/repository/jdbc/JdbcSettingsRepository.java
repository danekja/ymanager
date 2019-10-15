package org.danekja.ymanager.repository.jdbc;

import org.danekja.ymanager.domain.DefaultSettings;
import org.danekja.ymanager.repository.SettingsRepository;
import org.danekja.ymanager.repository.jdbc.mappers.DefaultSettingsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSettingsRepository implements SettingsRepository {

    private static final RowMapper<DefaultSettings> SETTINGS_ROW_MAPPER = new DefaultSettingsMapper();

    /**
     * The connection to a database.
     */
    private final JdbcTemplate jdbc;

    @Autowired
    public JdbcSettingsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public DefaultSettings get() {
        //TODO order by creation date
        return this.jdbc.queryForObject("SELECT * FROM default_settings ORDER BY id DESC LIMIT 1", SETTINGS_ROW_MAPPER);
    }

}
