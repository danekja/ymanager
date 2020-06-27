package org.danekja.ymanager.repository;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.Vacation;
import org.danekja.ymanager.domain.VacationType;
import org.danekja.ymanager.dto.VacationDayDTO;
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

import static java.util.Optional.ofNullable;

@Repository
public class VacationRepository {
    /**
     * The mapper maps a row from a result of a query to an Vacation.
     */
    private class VacationDayMapper implements RowMapper<VacationDayDTO> {

        /**
         * Maps a row from a result of a query to an Vacation.
         * @param resultSet the row from the result
         * @param i the index of the row
         * @return the Vacation object
         * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called on a closed result set
         */
        @Override
        public VacationDayDTO mapRow(ResultSet resultSet, int i) throws SQLException {
            final VacationDayDTO item = new VacationDayDTO();
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

            item.setStatus(Status.valueOf(resultSet.getString("v.status")));
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

    public List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id = ? AND v.vacation_date >= ?",
                new Object[]{userId, from}, new VacationDayMapper());
    }

    public List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from, final Status status) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id = ? AND v.vacation_date >= ? AND v.status = ?",
                new Object[]{userId, from, status.name()}, new VacationDayMapper());
    }

    public List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from, final LocalDate to) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id=? AND v.vacation_date >= ? AND v.vacation_date <= ?",
                new Object[]{userId, from, to}, new VacationDayMapper());

    }

    public List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from, final LocalDate to, final Status status) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id=? AND v.vacation_date >= ? AND v.vacation_date <= ? AND v.status = ?",
                new Object[]{userId, from, to, status.name()}, new VacationDayMapper());
    }

    public Optional<Vacation> getVacationDay(final long id) {
        return ofNullable(jdbc.queryForObject("SELECT id, vacation_date, time_from, time_to, creation_date, status, vacation_type, user_id " +
                        "FROM vacation_day WHERE id = ?", new Object[]{id},
                (ResultSet rs, int rowNum) -> {
                    Vacation vacation = new Vacation();
                    vacation.setId(rs.getLong("id"));
                    vacation.setDate(rs.getDate("vacation_date").toLocalDate());
                    /*
                        When a result contains a sick day it doesn't have specified a start and end time because
                        it can be taken only as a whole day. In this case the v.time_from and v.time_to are null.
                        Which must be handled.
                    */
                    final Time timeFrom = rs.getTime("time_from");
                    if (timeFrom != null) {
                        vacation.setFrom(timeFrom.toLocalTime());
                    }

                    final Time timeTo = rs.getTime("time_to");
                    if (timeTo != null) {
                        vacation.setTo(timeTo.toLocalTime());
                    }

                    vacation.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
                    vacation.setStatus(Status.valueOf(rs.getString("status")));
                    vacation.setType(VacationType.getVacationType(rs.getString("vacation_type")));
                    vacation.setUserId(rs.getLong("user_id"));
                    return vacation;
                }));
    }

    public void insertVacationDay(final Long userId, final Vacation day) {
        jdbc.update("INSERT INTO vacation_day (vacation_date, time_from, time_to, status, vacation_type, user_id) VALUES (?,?,?,?,?,?)",
                day.getDate(), day.getFrom(), day.getTo(), day.getStatus().name(), day.getType().name(), userId);
    }

    public void updateVacationDay(final Vacation item) {
        jdbc.update("UPDATE vacation_day SET vacation_date=?, time_from=?, time_to=?, status=?, vacation_type=? WHERE id=?",
                item.getDate(), item.getFrom(), item.getTo(), item.getStatus().name(), item.getType().name(), item.getId());
    }

    public void deleteVacationDay(final long id) {
        jdbc.update("DELETE FROM vacation_day WHERE id=?", id);
    }


    public boolean isExistVacationForUser(Long userId, LocalDate date) {
        return jdbc.queryForObject("SELECT count(id) AS exist FROM vacation_day WHERE vacation_date = ? AND user_id = ?",
                new Object[]{date, userId}, (rs, rowNum) -> rs.getBoolean("exist"));
    }
}
