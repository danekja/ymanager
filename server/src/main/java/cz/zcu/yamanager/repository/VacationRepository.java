package cz.zcu.yamanager.repository;

import cz.zcu.yamanager.domain.User;
import cz.zcu.yamanager.dto.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static cz.zcu.yamanager.dto.Status.getStatus;
import static java.util.Optional.ofNullable;

@Repository
public class VacationRepository {
    /**
     * The mapper maps a row from a result of a query to an VacationDay.
     */
    private class VacationDayMapper implements RowMapper<VacationDay> {

        /**
         * Maps a row from a result of a query to an VacationDay.
         * @param resultSet the row from the result
         * @param i the index of the row
         * @return the VacationDay object
         * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
         */
        @Override
        public VacationDay mapRow(ResultSet resultSet, int i) throws SQLException {
            final VacationDay item = new VacationDay();
            item.setId(resultSet.getLong("v.id"));
            item.setDate(resultSet.getDate("v.vacation_date").toLocalDate());

            /*
                When a result contains a sick day it doesn't have specified a start and end time because
                it can be taken only as a whole day. In this case the v.time_from and v.time_to are null.
                Which must be handled.
            */
            final Time timeFrom = resultSet.getTime("v.time_from");
            if (timeFrom != null) {
                item.setFrom(timeFrom.toLocalTime());
            }

            final Time timeTo = resultSet.getTime("v.time_to");
            if (timeTo != null) {
                item.setTo(timeTo.toLocalTime());
            }

            item.setStatus(getStatus(resultSet.getString("v.status")));
            item.setType(VacationType.getVacationType(resultSet.getString("v.vacation_type")));
            return item;
        }
    }

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(VacationRepository.class);

    /**
     * The connection to a database.
     */
    private final JdbcTemplate jdbc;

    /**
     * Creates a new instance of the class VacationRepository which selects and updates users in a database.
     *
     * @param jdbc A connection to the database.
     */
    @Autowired
    public VacationRepository(final JdbcTemplate jdbc) {
        VacationRepository.log.trace("Creating a new instance of the class VacationRepository");

        this.jdbc = jdbc;
    }

    public List<VacationDay> getVacationDays(final long userId, final LocalDate from) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id = ? AND v.vacation_date >= ?",
                new Object[]{userId, from}, new VacationDayMapper());
    }

    public List<VacationDay> getVacationDays(final long userId, final LocalDate from, final Status status) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id = ? AND v.vacation_date >= ? AND v.status = ?",
                new Object[]{userId, from, status.name()}, new VacationDayMapper());
    }

    public List<VacationDay> getVacationDays(final long userId, final LocalDate from, final LocalDate to) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id=? AND v.vacation_date >= ? AND v.vacation_date <= ?",
                new Object[]{userId, from, to}, new VacationDayMapper());

    }

    public List<VacationDay> getVacationDays(final long userId, final LocalDate from, final LocalDate to, final Status status) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id=? AND v.vacation_date >= ? AND v.vacation_date <= ? AND v.status = ?",
                new Object[]{userId, from, to, status.name()}, new VacationDayMapper());
    }

    public Optional<cz.zcu.yamanager.domain.VacationDay> getVacationDay(final long id) {
        return ofNullable(jdbc.queryForObject("SELECT id, vacation_date, time_from, time_to, creation_date, status, vacation_type, user_id " +
                        "FROM vacation_day WHERE id = ?", new Object[]{id},
                (ResultSet rs, int rowNum) -> {
                    cz.zcu.yamanager.domain.VacationDay vacationDay = new cz.zcu.yamanager.domain.VacationDay();
                    vacationDay.setId(rs.getLong("id"));
                    vacationDay.setDate(rs.getDate("vacation_date").toLocalDate());
                    /*
                        When a result contains a sick day it doesn't have specified a start and end time because
                        it can be taken only as a whole day. In this case the v.time_from and v.time_to are null.
                        Which must be handled.
                    */
                    final Time timeFrom = rs.getTime("time_from");
                    if (timeFrom != null) {
                        vacationDay.setFrom(timeFrom.toLocalTime());
                    }

                    final Time timeTo = rs.getTime("time_to");
                    if (timeTo != null) {
                        vacationDay.setTo(timeTo.toLocalTime());
                    }

                    vacationDay.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
                    vacationDay.setStatus(getStatus(rs.getString("status")));
                    vacationDay.setType(VacationType.getVacationType(rs.getString("vacation_type")));
                    vacationDay.setUserId(rs.getLong("user_id"));
                    return vacationDay;
                }));
    }

    public void insertVacationDay(final Long userId, final cz.zcu.yamanager.domain.VacationDay day) {
        jdbc.update("INSERT INTO vacation_day (vacation_date, time_from, time_to, status, vacation_type, user_id) VALUES (?,?,?,?,?,?)",
                day.getDate(), day.getFrom(), day.getTo(), day.getStatus().name(), day.getType().name(), userId);
    }

    public void updateVacationDay(final cz.zcu.yamanager.domain.VacationDay item) {
        jdbc.update("UPDATE vacation_day SET vacation_date=?, time_from=?, time_to=?, status=?, vacation_type=? WHERE id=?",
                item.getDate(), item.getFrom(), item.getTo(), item.getStatus().name(), item.getType().name(), item.getId());
    }

    public void deleteVacationDay(final long id) {
        jdbc.update("DELETE FROM vacation_day WHERE id=?", id);
    }

    public Optional<User> findUserByVacationID(final long id) {
        return ofNullable(jdbc.queryForObject(
                "SELECT u.id,u.first_name, u.last_name, u.no_vacations," +
                        "u.no_sick_days, u.taken_sick_days, u.alert, u.token, u.email," +
                        "u.photo, u.creation_date, u.user_role, u.status " +
                "FROM vacation_day v INNER JOIN end_user u ON v.user_id = u.id " +
                "WHERE v.id = ?", new Object[]{id}, (ResultSet rs, int rowNum)->
        {
            User user = new User();
            user.setId(rs.getLong("u.id"));
            user.setFirstName(rs.getString("u.first_name"));
            user.setLastName(rs.getString("u.last_name"));
            user.setVacationCount(rs.getFloat("u.no_vacations"));
            user.setTotalSickDayCount(rs.getInt("u.no_sick_days"));
            user.setTakenSickDayCount(rs.getInt("u.taken_sick_days"));
            user.setNotification(rs.getTimestamp("u.alert").toLocalDateTime());
            user.setToken(rs.getString("u.token"));
            user.setEmail(rs.getString("u.email"));
            user.setPhoto(rs.getString("u.photo"));
            user.setCreationDate(rs.getTimestamp("u.creation_date").toLocalDateTime());
            user.setRole(UserRole.getUserRole(rs.getString("u.user_role")));
            user.setStatus(getStatus(rs.getString("u.status")));
            return user;
        }));
    }
}
