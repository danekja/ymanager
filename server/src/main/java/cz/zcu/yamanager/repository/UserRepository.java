package cz.zcu.yamanager.repository;

import cz.zcu.yamanager.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * Creates a new instance of the class UserRepository which selects and updates users in a database.
     * @param jdbc A connection to the database.
     */
    @Autowired
    public UserRepository(JdbcTemplate jdbc) {
        log.trace("Creating a new instance of the class UserRepository");
        this.jdbc = jdbc;
    }

    public List<BasicProfileUser> getAllBasicUsers() {
        return jdbc.query("SELECT id, first_name, last_name, photo FROM end_user", (ResultSet rs, int rowNum) -> {
            BasicProfileUser user = new BasicProfileUser();
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setPhoto(rs.getString("photo"));
            return user;
        });
    }

    public FullUserProfile getFullUser(long id) {
        List<SqlParameter> paramList = new ArrayList<>();
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

        Map<String, Object> resultMap = this.jdbc.call(con -> {
            CallableStatement callableStatement = con.prepareCall("{call GetUserId(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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

        FullUserProfile user = new FullUserProfile();
        user.setId((Long)resultMap.get("out_id"));
        user.setFirstName((String)resultMap.get("out_first_name"));
        user.setLastName((String)resultMap.get("out_last_name"));
        user.setVacationCount(((Double)resultMap.get("out_no_vacations")).floatValue());
        user.setSickdayCount((Integer)resultMap.get("out_no_sick_days"));
        user.setTakenSickdayCount((Integer)resultMap.get("out_taken_sick_days"));
        user.setNotification(((Timestamp)resultMap.get("out_alert")).toLocalDateTime());
        user.setEmail((String)resultMap.get(("out_email")));
        user.setPhoto((String)resultMap.get("out_photo"));
        user.setRole(UserRole.getUserRole((String)resultMap.get("out_role_id")));
        user.setStatus(Status.getStatus((String)resultMap.get("out_status_id")));
        return user;

    }

    public FullUserProfile getFullUser(String token) {
        List<SqlParameter> paramList = new ArrayList<>();
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

        Map<String, Object> resultMap = this.jdbc.call(con -> {
            CallableStatement callableStatement = con.prepareCall("{call GetUserToken(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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

        FullUserProfile user = new FullUserProfile();
        user.setId((Long)resultMap.get("out_id"));
        user.setFirstName((String)resultMap.get("out_first_name"));
        user.setLastName((String)resultMap.get("out_last_name"));
        user.setVacationCount(((Double)resultMap.get("out_no_vacations")).floatValue());
        user.setSickdayCount((Integer)resultMap.get("out_no_sick_days"));
        user.setTakenSickdayCount((Integer)resultMap.get("out_taken_sick_days"));
        user.setNotification(((Timestamp)resultMap.get("out_alert")).toLocalDateTime());
        user.setEmail((String)resultMap.get(("out_email")));
        user.setPhoto((String)resultMap.get("out_photo"));
        user.setRole(UserRole.getUserRole((String)resultMap.get("out_role_id")));
        user.setStatus(Status.getStatus((String)resultMap.get("out_status_id")));
        return user;
    }

    public UserSettings getUserSettings(long id) {
        return jdbc.queryForObject("SELECT no_vacations, no_sick_days, role_id FROM end_user WHERE = id=?",
                new Object[]{id}, (ResultSet rs, int rowNum)->{
            UserSettings settings = new UserSettings();
            settings.setId(id);
            settings.setSickdayCount(rs.getInt("no_sick_day"));
            settings.setVacationCount(rs.getFloat("no_vacations"));
            settings.setRole(UserRole.values()[rs.getByte("role_id")]);
            return settings;
        });
    }

    public void updateUserSettings(UserSettings settings) {
        jdbc.update("UPDATE end_user EU, role R SET EU.no_vacations=?, EU.no_sick_days=?, EU.role_id=R.id WHERE EU.id = ? AND R.name=?",
                settings.getVacationCount(), settings.getSickdayCount(), settings.getId(), settings.getRole().name());
    }

    public DefaultSettings getLastDefaultSettings() {
        return jdbc.queryForObject("SELECT * FROM default_settings ORDER BY id DESC LIMIT 1", (ResultSet rs, int rowNum) -> {
            DefaultSettings settings = new DefaultSettings();
            settings.setSickdayCount(rs.getInt("no_sick_days"));
            settings.setNotification(rs.getTimestamp("alert").toLocalDateTime());
            return settings;
        });
    }

    public void insertSettings(DefaultSettings settings) {
        jdbc.update("INSERT INTO default_settings (no_sick_days, alert) VALUES (?, ?)", settings.getSickdayCount(), settings.getNotification());
    }
}
