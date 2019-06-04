package cz.zcu.yamanager.repository;

import cz.zcu.yamanager.dto.Status;
import cz.zcu.yamanager.dto.VacationDay;
import cz.zcu.yamanager.dto.VacationType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Repository
public class VacationRepository {
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
        return this.jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id = ? AND v.vacation_date >= ?",
                new Object[]{userId, from}, (ResultSet rs, int rowNum) -> {
                    final VacationDay item = new VacationDay();
                    item.setId(rs.getLong("v.id"));
                    item.setDate(rs.getDate("v.vacation_date").toLocalDate());
                    final Time timeFrom = rs.getTime("v.time_from");
                    if (timeFrom != null) {
                        item.setFrom(timeFrom.toLocalTime());
                    }

                    final Time timeTo = rs.getTime("v.time_to");
                    if (timeTo != null) {
                        item.setTo(timeTo.toLocalTime());
                    }

                    item.setStatus(Status.getStatus(rs.getString("v.status")));
                    item.setType(VacationType.getVacationType(rs.getString("v.vacation_type")));
                    return item;
                });
    }

    public List<VacationDay> getVacationDays(final long userId, final LocalDate from, final Status status) {
        return this.jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id = ? AND v.vacation_date >= ? AND v.status = ?",
                new Object[]{userId, from, status.name()}, (ResultSet rs, int rowNum) -> {
                    final VacationDay item = new VacationDay();
                    item.setId(rs.getLong("v.id"));
                    item.setDate(rs.getDate("v.vacation_date").toLocalDate());
                    final Time timeFrom = rs.getTime("v.time_from");
                    if (timeFrom != null) {
                        item.setFrom(timeFrom.toLocalTime());
                    }

                    final Time timeTo = rs.getTime("v.time_to");
                    if (timeTo != null) {
                        item.setTo(timeTo.toLocalTime());
                    }

                    item.setStatus(Status.getStatus(rs.getString("v.status")));
                    item.setType(VacationType.getVacationType(rs.getString("v.vacation_type")));
                    return item;
                });
    }

    public List<VacationDay> getVacationDays(final long userId, final LocalDate from, final LocalDate to) {
        return this.jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id=? AND v.vacation_date >= ? AND v.vacation_date <= ?",
                new Object[]{userId, from, to}, (ResultSet rs, int rowNum) -> {
                    final VacationDay item = new VacationDay();
                    item.setId(rs.getLong("v.id"));
                    item.setDate(rs.getDate("v.vacation_date").toLocalDate());
                    final Time timeFrom = rs.getTime("v.time_from");
                    if (timeFrom != null) {
                        item.setFrom(timeFrom.toLocalTime());
                    }

                    final Time timeTo = rs.getTime("v.time_to");
                    if (timeTo != null) {
                        item.setTo(timeTo.toLocalTime());
                    }

                    item.setStatus(Status.getStatus(rs.getString("v.status")));
                    item.setType(VacationType.getVacationType(rs.getString("v.vacation_type")));
                    return item;
                });

    }

    public List<VacationDay> getVacationDays(final long userId, final LocalDate from, final LocalDate to, final Status status) {
        return this.jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id=? AND v.vacation_date >= ? AND v.vacation_date <= ? AND v.status = ?",
                new Object[]{userId, from, to, status.name()}, (ResultSet rs, int rowNum) -> {
                    final VacationDay item = new VacationDay();
                    item.setId(rs.getLong("v.id"));
                    item.setDate(rs.getDate("v.vacation_date").toLocalDate());

                    final Time timeFrom = rs.getTime("v.time_from");
                    if (timeFrom != null) {
                        item.setFrom(timeFrom.toLocalTime());
                    }

                    final Time timeTo = rs.getTime("v.time_to");
                    if (timeTo != null) {
                        item.setTo(timeTo.toLocalTime());
                    }

                    item.setStatus(Status.getStatus(rs.getString("v.status")));
                    item.setType(VacationType.getVacationType(rs.getString("v.vacation_type")));
                    return item;
                });
    }

    public cz.zcu.yamanager.domain.VacationDay getVacationDay(final long id) {
        return this.jdbc.queryForObject("SELECT id, vacation_date, time_from, time_to, creation_date, status, vacation_type" +
                        "FROM vacation_day " +
                        "WHERE id = ?", new Object[]{id},
                (ResultSet rs, int rowNum) ->
                    new cz.zcu.yamanager.domain.VacationDay(
                            rs.getLong("id"),
                            rs.getDate("vacation_date").toLocalDate(),
                            rs.getTime("time_from").toLocalTime(),
                            rs.getTime("time_to").toLocalTime(),
                            rs.getTimestamp("creation_date").toLocalDateTime(),
                            Status.getStatus(rs.getString("status")),
                            VacationType.getVacationType(rs.getString("v.vacation_type")))
                );
    }

    public void insertVacationDay(final Long userId, final cz.zcu.yamanager.domain.VacationDay day) {
        this.jdbc.update("INSERT INTO vacation_day (vacation_date, time_from, time_to, status, vacation_type, user_id) VALUES (?,?,?,?,?,?)",
                day.getDate(), day.getFrom(), day.getTo(), day.getStatus().name(), day.getType().name(), userId);
    }

    public void updateVacationDay(final cz.zcu.yamanager.domain.VacationDay item) {
        this.jdbc.update("UPDATE vacation_day SET vacation_date=?, time_from=?, time_to=?, status=?, vacation_type=? WHERE id=?",
                item.getDate(), item.getFrom(), item.getTo(), item.getStatus().name(), item.getType().name(), item.getId());
    }

    public void deleteVacationDay(final long id) {
        this.jdbc.update("DELETE FROM vacation_day WHERE id=?", id);
    }
}
