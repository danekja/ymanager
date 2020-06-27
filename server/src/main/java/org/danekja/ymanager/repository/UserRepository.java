package org.danekja.ymanager.repository;

import org.danekja.ymanager.domain.*;
import org.danekja.ymanager.dto.BasicProfileUser;
import org.danekja.ymanager.dto.FullUserProfile;
import org.danekja.ymanager.repository.jdbc.mappers.UserDataRowMapper;
import org.danekja.ymanager.repository.jdbc.mappers.UserRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.danekja.ymanager.domain.UserRole.getUserRole;

/**
 * An instance of the class UserRepository handles queries which selects and updates users and their settings in a database.
 */
@Repository
public class UserRepository {

    private final RowMapper<UserData> USER_DATA_MAPPER = new UserDataRowMapper();
    private final RowMapper<RegisteredUser> USER_MAPPER = new UserRowMapper(USER_DATA_MAPPER);


    /**
     * The mapper maps a row from a result of a query to an BasicProfileUser.
     */
    private class BasicProfileUserMapper implements RowMapper<BasicProfileUser> {

        /**
         * Maps a row from a result of a query to an BasicProfileUser.
         * @param resultSet the row from the result
         * @param i the index of the row
         * @return the BasicProfileUser object
         * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
         */
        @Override
        public BasicProfileUser mapRow(ResultSet resultSet, int i) throws SQLException {
            final BasicProfileUser user = new BasicProfileUser();
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setPhoto(resultSet.getString("photo"));
            return user;
        }
    }

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

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
    public UserRepository(final JdbcTemplate jdbc) {
        UserRepository.log.trace("Creating a new instance of the class UserRepository");
        this.jdbc = jdbc;
    }

    private Map<String, Object> getUserColumns(final long id) {
        final List<SqlParameter> paramList = new ArrayList<>();
        paramList.add(new SqlParameter("in_id", Types.BIGINT));
        paramList.add(new SqlOutParameter("out_id", Types.BIGINT));
        paramList.add(new SqlOutParameter("out_first_name", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_last_name", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_no_vacations", Types.FLOAT));
        paramList.add(new SqlOutParameter("out_no_sick_days", Types.INTEGER));
        paramList.add(new SqlOutParameter("out_taken_sick_days", Types.INTEGER));
        paramList.add(new SqlOutParameter("out_alert", Types.TIMESTAMP));
        paramList.add(new SqlOutParameter("out_token", Types.LONGVARCHAR));
        paramList.add(new SqlOutParameter("out_email", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_photo", Types.LONGVARCHAR));
        paramList.add(new SqlOutParameter("out_creation_date", Types.TIMESTAMP));
        paramList.add(new SqlOutParameter("out_role", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_status", Types.VARCHAR));

        return jdbc.call(con -> {
            final CallableStatement callableStatement = con.prepareCall("{call GetUserId(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setLong(1, id);
            callableStatement.registerOutParameter(2, Types.BIGINT);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.FLOAT);
            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.registerOutParameter(7, Types.INTEGER);
            callableStatement.registerOutParameter(8, Types.TIMESTAMP);
            callableStatement.registerOutParameter(9, Types.LONGVARCHAR);
            callableStatement.registerOutParameter(10, Types.VARCHAR);
            callableStatement.registerOutParameter(11, Types.LONGVARCHAR);
            callableStatement.registerOutParameter(12, Types.TIMESTAMP);
            callableStatement.registerOutParameter(13, Types.VARCHAR);
            callableStatement.registerOutParameter(14, Types.VARCHAR);
            return callableStatement;
        }, paramList);
    }

    //---------------------------------- DTO -----------------------------------

    /**
     * Gets basic profile of each user from a database. The basic profile contains default, the most important
     * informations which helps identify a user like a name or photo. Every line of output is converted to a BasicProfileUser
     * object filled with data from the database. If there aren't any users in the database, the method returns an empty list.
     *
     * @return A list of all basic profiles.
     */
    public List<BasicProfileUser> getAllBasicUsers() {
        UserRepository.log.trace("Selecting basic profiles of all users from a database.");

        return this.jdbc.query("SELECT id, first_name, last_name, photo FROM end_user", new BasicProfileUserMapper());
    }

    /**
     * Gets basic profile of each user with the given authorization status from a database. The basic profile contains
     * default, the most important informations which helps identify a user like a name or a photo.
     * Every line of output is converted to a BasicProfileUser object filled with data from the database.
     * If there aren't any users with the given authorization status in the database, the method returns an empty list.
     *
     * @param status The authentication status.
     * @return A list of all basic profiles with the given status.
     */
    public List<BasicProfileUser> getAllBasicUsers(final Status status) {
        UserRepository.log.trace("Selecting basic profiles of all users with a required status from a database.");
        UserRepository.log.debug("Status: {}", status);

        return this.jdbc.query("SELECT id, first_name, last_name, photo FROM end_user WHERE status = ?",
                new Object[]{status.name()}, new BasicProfileUserMapper());
    }

    public UserRole getPermission(Long id) {
        return jdbc.queryForObject("SELECT user_role FROM end_user WHERE id = ?" ,new Object[]{id}, (rs, rowNum) ->
            getUserRole(rs.getString("user_role"))
        );
    }

    /**
     *
     * @param id
     * @return
     */
    public FullUserProfile getFullUser(final long id) {
        UserRepository.log.debug("Selecting full profile of a user with the specified id from a database: {}", id);

        final Map<String, Object> resultMap = this.getUserColumns(id);

        final FullUserProfile user = new FullUserProfile();
        user.setId(id);
        user.setFirstName((String) resultMap.get("out_first_name"));
        user.setLastName((String) resultMap.get("out_last_name"));
        user.setVacationCount(((Double) resultMap.get("out_no_vacations")).floatValue());
        user.setSickDayCount((Integer) resultMap.get("out_no_sick_days"));
        user.setTakenSickDayCount((Integer) resultMap.get("out_taken_sick_days"));
        user.setNotification(((Timestamp) resultMap.get("out_alert")).toLocalDateTime());
        user.setEmail((String) resultMap.get("out_email"));
        user.setPhoto((String) resultMap.get("out_photo"));
        user.setRole(getUserRole((String) resultMap.get("out_role")));
        user.setStatus(Status.valueOf((String) resultMap.get("out_status")));
        return user;

    }



    //---------------------------------- DOMAIN -----------------------------------

    /**
     * Gets user from database based on its email
     *
     * @param email email value, used as search key
     * @return found user object or null (if not found)
     */
    public RegisteredUser getUser(final String email) {
        return this.jdbc.queryForObject("SELECT * FROM end_user WHERE email = ?", USER_MAPPER, email);
    }

    /**
     * Gets user from database based on its id
     *
     * @param id id value, used as search key
     * @return found user object or null (if not found)
     */
    public RegisteredUser getUser(final long id) {
        return this.jdbc.queryForObject("SELECT * FROM end_user WHERE id = ?", USER_MAPPER, id);
    }

    /**
     * Gets user data by user's id
     *
     * @param id TODO replace by subject Id for google and numeric id for registered (multiple queries)
     * @return
     */
    public UserData getUserData(final long id) {
        return this.jdbc.queryForObject("SELECT * FROM end_user WHERE id = ?", USER_DATA_MAPPER, id);
    }

    public void updateUser(final User user) {
        this.jdbc.update("UPDATE end_user SET first_name = ?, last_name = ?, no_vacations = ?, taken_sick_days = ?, email = ?, photo = ?, user_role = ?, status = ? WHERE id = ?",
                user.getFirstName(), user.getLastName(), user.getVacationCount(), user.getTakenSickDayCount(), user.getEmail(), user.getPhoto(), user.getRole().name(), user.getStatus().name(), user.getId());
    }

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

    public void insertSettings(final DefaultSettings settings) {
        this.jdbc.update("INSERT INTO default_settings (no_sick_days, alert) VALUES (?, ?)", settings.getSickDayCount(), settings.getNotification());
    }

    public void updateUserSettings(User user) {
        jdbc.update("UPDATE end_user SET alert = ?, user_role = ?, no_sick_days = ?, no_vacations = ? WHERE id = ?",
                user.getNotification(), user.getRole().name(), user.getTotalSickDayCount(), user.getVacationCount(), user.getId());
    }

    public void updateUserTakenVacation(User user) {
        jdbc.update("UPDATE end_user SET no_vacations = ? WHERE id = ?",
                user.getVacationCount(), user.getId());
    }

    public void updateUserTakenSickDay(User user) {
        jdbc.update("UPDATE end_user SET taken_sick_days = ? WHERE id = ?",
                user.getTakenSickDayCount(), user.getId());
    }
}
