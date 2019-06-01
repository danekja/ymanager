package cz.zcu.yamanager.repository;

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
        UserRepository.log.trace("Selecting full profiles of a user with a specified id from a database ");
        UserRepository.log.debug("Id: {}", id);

        final List<SqlParameter> paramList = new ArrayList<>();
        paramList.add(new SqlParameter("in_id", Types.BIGINT));
        paramList.add(new SqlOutParameter("out_id", Types.BIGINT));
        paramList.add(new SqlOutParameter("out_first_name", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_last_name", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_no_vacations", Types.FLOAT));
        paramList.add(new SqlOutParameter("out_no_sick_days", Types.INTEGER));
        paramList.add(new SqlOutParameter("out_taken_sick_days", Types.INTEGER));
        paramList.add(new SqlOutParameter("out_alert", Types.TIMESTAMP));
        paramList.add(new SqlOutParameter("out_email", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_photo", Types.LONGVARCHAR));
        paramList.add(new SqlOutParameter("out_creation_date", Types.TIMESTAMP));
        paramList.add(new SqlOutParameter("out_role", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_status", Types.VARCHAR));

        final Map<String, Object> resultMap = jdbc.call(con -> {
            final CallableStatement callableStatement = con.prepareCall("{call GetUserId(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setLong(1, id);
            callableStatement.registerOutParameter(2, Types.BIGINT);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.FLOAT);
            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.registerOutParameter(7, Types.INTEGER);
            callableStatement.registerOutParameter(8, Types.TIMESTAMP);
            callableStatement.registerOutParameter(9, Types.VARCHAR);
            callableStatement.registerOutParameter(10, Types.LONGNVARCHAR);
            callableStatement.registerOutParameter(11, Types.TIMESTAMP);
            callableStatement.registerOutParameter(12, Types.VARCHAR);
            callableStatement.registerOutParameter(13, Types.VARCHAR);
            return callableStatement;
        }, paramList);

        final FullUserProfile user = new FullUserProfile();
        user.setId(id);
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

    public UserRole getUserRole(final long id) {
        return this.jdbc.queryForObject("SELECT user_role FROM end_user WHERE id = ?", new Object[]{id}, (ResultSet rs, int rowNum) -> UserRole.getUserRole(rs.getString("status")));
    }

    public Status getUserStatus(final long id) {
        return this.jdbc.queryForObject("SELECT status FROM end_user WHERE id = ?", new Object[]{id}, (ResultSet rs, int rowNum) -> Status.getStatus(rs.getString("status")));
    }

    public FullUserProfile getFullUser(final String token) {
        final List<SqlParameter> paramList = new ArrayList<>();
        paramList.add(new SqlParameter(Types.LONGNVARCHAR));
        paramList.add(new SqlOutParameter("out_id", Types.BIGINT));
        paramList.add(new SqlOutParameter("out_first_name", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_last_name", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_no_vacations", Types.FLOAT));
        paramList.add(new SqlOutParameter("out_no_sick_days", Types.INTEGER));
        paramList.add(new SqlOutParameter("out_taken_sick_days", Types.INTEGER));
        paramList.add(new SqlOutParameter("out_alert", Types.TIMESTAMP));
        paramList.add(new SqlOutParameter("out_email", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_photo", Types.LONGVARCHAR));
        paramList.add(new SqlOutParameter("out_creation_date", Types.TIMESTAMP));
        paramList.add(new SqlOutParameter("out_role", Types.VARCHAR));
        paramList.add(new SqlOutParameter("out_status", Types.VARCHAR));

        final Map<String, Object> resultMap = jdbc.call(con -> {
            final CallableStatement callableStatement = con.prepareCall("{call GetUserToken(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, token);
            callableStatement.registerOutParameter(2, Types.BIGINT);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.FLOAT);
            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.registerOutParameter(7, Types.INTEGER);
            callableStatement.registerOutParameter(8, Types.TIMESTAMP);
            callableStatement.registerOutParameter(9, Types.VARCHAR);
            callableStatement.registerOutParameter(10, Types.LONGNVARCHAR);
            callableStatement.registerOutParameter(11, Types.TIMESTAMP);
            callableStatement.registerOutParameter(12, Types.VARCHAR);
            callableStatement.registerOutParameter(13, Types.VARCHAR);
            return callableStatement;
        }, paramList);

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

    public UserSettings getUserSettings(final long id) {
        return this.jdbc.queryForObject("SELECT no_vacations, no_sick_days, user_role FROM end_user WHERE id=?",
                new Object[]{id}, (ResultSet rs, int rowNum) -> {
                    final UserSettings settings = new UserSettings();
                    settings.setId(id);
                    settings.setSickDayCount(rs.getInt("no_sick_day"));
                    settings.setVacationCount(rs.getFloat("no_vacations"));
                    settings.setRole(UserRole.getUserRole(rs.getString("user_role")));
                    return settings;
                });
    }

    public void decreaseVacationCount(final long id, final float value) {
        this.jdbc.update("UPDATE end_user SET no_vacations = no_vacations - ? WHERE id = ?", value, id);
    }

    public void increaseTakenSickdays(final long id) {
        this.jdbc.update("UPDATE end_user SET taken_sick_days = taken_sick_days + 1 WHERE id = ?", id);
    }

    public void updateNotification(final UserSettings settings) {
        this.jdbc.update("UPDATE end_user SET alert = ? WHERE id = ?", settings.getNotification(), settings.getId());
    }

    public void updateUserSettings(final UserSettings settings) {
        this.jdbc.update("UPDATE end_user SET no_vacations=?, no_sick_days=?, user_role=? WHERE id = ?",
                settings.getVacationCount(), settings.getSickDayCount(), settings.getRole().name(), settings.getId());
    }

    public DefaultSettings getLastDefaultSettings() {
        return this.jdbc.queryForObject("SELECT * FROM default_settings ORDER BY id DESC LIMIT 1", (ResultSet rs, int rowNum) -> {
            final DefaultSettings settings = new DefaultSettings();
            settings.setSickDayCount(rs.getInt("no_sick_days"));
            settings.setNotification(rs.getTimestamp("alert").toLocalDateTime());
            return settings;
        });
    }

    public void insertSettings(final DefaultSettings settings) {
        this.jdbc.update("INSERT INTO default_settings (no_sick_days, alert) VALUES (?, ?)", settings.getSickDayCount(), settings.getNotification());
    }
}
