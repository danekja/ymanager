package cz.zcu.yamanager.repository;

import cz.zcu.yamanager.domain.User;
import cz.zcu.yamanager.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An instance of the class UserRepository handles queries which selects and updates users and their settings in a database.
 */
@Repository
public class UserRepository {
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

    private Map<String, Object> getUserColumns(final String token) {
        final List<SqlParameter> paramList = new ArrayList<>();
        paramList.add(new SqlParameter(Types.LONGVARCHAR));
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
            final CallableStatement callableStatement = con.prepareCall("{call GetUserToken(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, token);
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

        return this.jdbc.query("SELECT id, first_name, last_name, photo FROM end_user", (ResultSet rs, int rowNum) -> {
            final BasicProfileUser user = new BasicProfileUser();
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setPhoto(rs.getString("photo"));
            return user;
        });
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
                new Object[]{status.name()}, (ResultSet rs, int rowNum) -> {
                    final BasicProfileUser user = new BasicProfileUser();
                    user.setId(rs.getLong("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setPhoto(rs.getString("photo"));
                    return user;
                });
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
        user.setRole(UserRole.getUserRole((String) resultMap.get("out_role")));
        user.setStatus(Status.getStatus((String) resultMap.get("out_status")));
        return user;

    }

    public FullUserProfile getFullUser(final String token) {
        UserRepository.log.trace("Selecting full profile of a user with the specified token from a database: {}", token);

        final Map<String, Object> resultMap = this.getUserColumns(token);

        final FullUserProfile user = new FullUserProfile();
        user.setId((Long) resultMap.get("out_id"));
        user.setFirstName((String) resultMap.get("out_first_name"));
        user.setLastName((String) resultMap.get("out_last_name"));
        user.setVacationCount(((Double) resultMap.get("out_no_vacations")).floatValue());
        user.setSickDayCount((Integer) resultMap.get("out_no_sick_days"));
        user.setTakenSickDayCount((Integer) resultMap.get("out_taken_sick_days"));
        user.setNotification(((Timestamp) resultMap.get("out_alert")).toLocalDateTime());
        user.setEmail((String) resultMap.get(("out_email")));
        user.setPhoto((String) resultMap.get("out_photo"));
        user.setRole(UserRole.getUserRole((String) resultMap.get("out_role")));
        user.setStatus(Status.getStatus((String) resultMap.get("out_status")));
        return user;
    }

    public DefaultSettings getLastDefaultSettings() {
        return this.jdbc.queryForObject("SELECT * FROM default_settings ORDER BY id DESC LIMIT 1", (ResultSet rs, int rowNum) -> {
            final DefaultSettings settings = new DefaultSettings();
            settings.setSickDayCount(rs.getInt("no_sick_days"));
            settings.setNotification(rs.getTimestamp("alert").toLocalDateTime());
            return settings;
        });
    }

    //---------------------------------- DOMAIN -----------------------------------

    public User getUser(final long id) {
        final Map<String, Object> resultMap = this.getUserColumns(id);
        return new User(
                id,
                (String) resultMap.get("out_first_name"),
                (String) resultMap.get("out_last_name"),
                ((Double) resultMap.get("out_no_vacations")).floatValue(),
                (Integer) resultMap.get("out_no_sick_days"),
                (Integer) resultMap.get("out_taken_sick_days"),
                ((Timestamp) resultMap.get("out_alert")).toLocalDateTime(),
                (String) resultMap.get("out_token"),
                (String) resultMap.get("out_email"),
                (String) resultMap.get("out_photo"),
                ((Timestamp) resultMap.get("out_creation_date")).toLocalDateTime(),
                UserRole.getUserRole((String) resultMap.get("out_role")),
                Status.getStatus((String) resultMap.get("out_status"))
        );
    }

    public void updateUser(final cz.zcu.yamanager.domain.User user) {
        this.jdbc.update("UPDATE end_user SET first_name = ?, last_name = ?, no_vacations = ?, no_sick_days = ?, taken_sick_days = ?, alert = ?, token = ?, email = ?, photo = ?, user_role = ?, status = ? WHERE id = ?",
                user.getFirstName(), user.getLastName(), user.getVacationCount(), user.getTotalSickDayCount(), user.getTakenSickDayCount(), user.getNotification(), user.getToken(), user.getEmail(), user.getPhoto(), user.getRole().name(), user.getStatus().name(), user.getId());
    }

    public void insertUser(final User user) {
        this.jdbc.update("INSERT INTO end_user (first_name, last_name, no_vacations, no_sick_days, taken_sick_days, alert, token, email, photo, user_role, status) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                user.getFirstName(), user.getLastName(), user.getVacationCount(), user.getTotalSickDayCount(), user.getTakenSickDayCount(), user.getNotification(), user.getToken(), user.getEmail(), user.getPhoto(), user.getRole().name(), user.getStatus().name());
    }

    public void insertSettings(final cz.zcu.yamanager.domain.DefaultSettings settings) {
        this.jdbc.update("INSERT INTO default_settings (no_sick_days, alert) VALUES (?, ?)", settings.getSickDayCount(), settings.getNotification());
    }
}
