package org.danekja.ymanager.repository.jdbc;

import org.danekja.ymanager.domain.*;
import org.danekja.ymanager.dto.BasicProfileUserDTO;
import org.danekja.ymanager.dto.FullUserProfileDTO;
import org.danekja.ymanager.repository.UserRepository;
import org.danekja.ymanager.repository.jdbc.mappers.BasicProfileUserMapper;
import org.danekja.ymanager.repository.jdbc.mappers.UserDataRowMapper;
import org.danekja.ymanager.repository.jdbc.mappers.UserRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.danekja.ymanager.domain.UserRole.getUserRole;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final RowMapper<BasicProfileUserDTO> BASIC_PROFILE_USER_MAPPER = new BasicProfileUserMapper();
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

    @Override
    public List<BasicProfileUserDTO> getAllBasicUsers() {
        JdbcUserRepository.log.trace("Selecting basic profiles of all users from a database.");

        return this.jdbc.query("SELECT id, first_name, last_name, photo FROM end_user", BASIC_PROFILE_USER_MAPPER);
    }

    @Override
    public List<BasicProfileUserDTO> getAllBasicUsers(final Status status) {
        JdbcUserRepository.log.trace("Selecting basic profiles of all users with a required status from a database.");
        JdbcUserRepository.log.debug("Status: {}", status);

        return this.jdbc.query("SELECT id, first_name, last_name, photo FROM end_user WHERE status = ?",
                BASIC_PROFILE_USER_MAPPER, status.name());
    }

    @Override
    public UserRole getPermission(Long id) {
        return jdbc.queryForObject("SELECT user_role FROM end_user WHERE id = ?" ,new Object[]{id}, (rs, rowNum) ->
            getUserRole(rs.getString("user_role"))
        );
    }

    @Override
    public FullUserProfileDTO getFullUser(final long id) {
        JdbcUserRepository.log.debug("Selecting full profile of a user with the specified id from a database: {}", id);

        final Map<String, Object> resultMap = this.getUserColumns(id);

        final FullUserProfileDTO user = new FullUserProfileDTO();
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
