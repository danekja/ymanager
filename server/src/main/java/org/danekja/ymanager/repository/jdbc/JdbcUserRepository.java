package org.danekja.ymanager.repository.jdbc;

import org.danekja.ymanager.domain.*;
import org.danekja.ymanager.repository.UserRepository;
import org.danekja.ymanager.repository.jdbc.mappers.UserDataRowMapper;
import org.danekja.ymanager.repository.jdbc.mappers.UserRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final RowMapper<UserData> USER_DATA_MAPPER = new UserDataRowMapper();
    private static final RowMapper<RegisteredUser> USER_MAPPER = new UserRowMapper(USER_DATA_MAPPER);

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(JdbcUserRepository.class);

    /**
     * The connection to a database.
     */
    private final JdbcTemplate jdbc;

    /**
     * Creates a new instance of the class UserRepository which selects and updates users and their settings in a database.
     *
     * @param jdbc A connection to the database.
     */
    @Autowired
    public JdbcUserRepository(final JdbcTemplate jdbc) {
        JdbcUserRepository.log.trace("Creating a new instance of the class UserRepository");
        this.jdbc = jdbc;
    }

    @Override
    public List<RegisteredUser> getUsers(final Status status) {
        return this.jdbc.query("SELECT * FROM end_user WHERE status = ?", USER_MAPPER, status.name());
    }

    @Override
    public RegisteredUser getUser(final String email) {
        return this.jdbc.queryForObject("SELECT * FROM end_user WHERE email = ?", USER_MAPPER, email);
    }

    @Override
    public RegisteredUser getUser(final long id) {
        return this.jdbc.queryForObject("SELECT * FROM end_user WHERE id = ?", USER_MAPPER, id);
    }

    @Override
    public UserData getUserData(final long id) {
        return this.jdbc.queryForObject("SELECT * FROM end_user WHERE id = ?", USER_DATA_MAPPER, id);
    }

    @Override
    public void updateUser(final User user) {
        this.jdbc.update("UPDATE end_user SET first_name = ?, last_name = ?, no_vacations = ?, taken_sick_days = ?, email = ?, photo = ?, user_role = ?, status = ? WHERE id = ?",
                user.getFirstName(), user.getLastName(), user.getVacationCount(), user.getTakenSickDayCount(), user.getEmail(), user.getPhoto(), user.getRole().name(), user.getStatus().name(), user.getId());
    }

    @Override
    public long insertUser(final User user) {
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory("INSERT INTO end_user (first_name, last_name, no_vacations, no_sick_days, taken_sick_days, alert, email, photo, user_role, status) VALUES (?,?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR,
                Types.VARCHAR,
                Types.FLOAT,
                Types.INTEGER,
                Types.INTEGER,
                Types.TIMESTAMP,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR);


        factory.setReturnGeneratedKeys(true);

        PreparedStatementCreator stmt = factory.newPreparedStatementCreator(new Object[]{user.getFirstName(), user.getLastName(), user.getVacationCount(), user.getTotalSickDayCount(), user.getTakenSickDayCount(), user.getNotification(), user.getEmail(), user.getPhoto(), user.getRole().name(), user.getStatus().name()});

        GeneratedKeyHolder key = new GeneratedKeyHolder();
        this.jdbc.update(stmt, key);

        return Objects.requireNonNull(key.getKey()).longValue();
    }

    @Override
    public void insertSettings(final DefaultSettings settings) {
        this.jdbc.update("INSERT INTO default_settings (no_sick_days, alert) VALUES (?, ?)", settings.getSickDayCount(), settings.getNotification());
    }

    @Override
    public void updateUserSettings(User user) {
        jdbc.update("UPDATE end_user SET alert = ?, user_role = ?, no_sick_days = ?, no_vacations = ? WHERE id = ?",
                user.getNotification(), user.getRole().name(), user.getTotalSickDayCount(), user.getVacationCount(), user.getId());
    }

    @Override
    public void updateUserTakenVacation(User user) {
        jdbc.update("UPDATE end_user SET no_vacations = ? WHERE id = ?",
                user.getVacationCount(), user.getId());
    }

    @Override
    public void updateUserTakenSickDay(User user) {
        jdbc.update("UPDATE end_user SET taken_sick_days = ? WHERE id = ?",
                user.getTakenSickDayCount(), user.getId());
    }
}
