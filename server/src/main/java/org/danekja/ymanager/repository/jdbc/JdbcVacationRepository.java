package org.danekja.ymanager.repository.jdbc;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.Vacation;
import org.danekja.ymanager.domain.VacationType;
import org.danekja.ymanager.dto.VacationDayDTO;
import org.danekja.ymanager.repository.VacationRepository;
import org.danekja.ymanager.repository.jdbc.mappers.VacationDayMapper;
import org.danekja.ymanager.repository.jdbc.mappers.VacationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class JdbcVacationRepository implements VacationRepository {

    private static final RowMapper<Vacation> VACATION_MAPPER = new VacationMapper();
    private static final RowMapper<VacationDayDTO> VACATION_DAY_MAPPER = new VacationDayMapper();

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(JdbcVacationRepository.class);

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
    public JdbcVacationRepository(final JdbcTemplate jdbc) {
        JdbcVacationRepository.log.trace("Creating a new instance of the class VacationRepository");

        this.jdbc = jdbc;
    }

    @Override
    public List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id = ? AND v.vacation_date >= ?",
                VACATION_DAY_MAPPER, userId, from);
    }

    @Override
    public List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from, final Status status) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id = ? AND v.vacation_date >= ? AND v.status = ?",
                VACATION_DAY_MAPPER, userId, from, status.name());
    }

    @Override
    public List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from, final LocalDate to) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id=? AND v.vacation_date >= ? AND v.vacation_date <= ?",
                VACATION_DAY_MAPPER, userId, from, to);

    }

    @Override
    public List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from, final LocalDate to, final Status status) {
        return jdbc.query("SELECT v.id, v.vacation_date, v.time_from, v.time_to, v.status, v.vacation_type " +
                        "FROM vacation_day v " +
                        "INNER JOIN end_user u ON v.user_id = u.id " +
                        "WHERE v.user_id=? AND v.vacation_date >= ? AND v.vacation_date <= ? AND v.status = ?",
                VACATION_DAY_MAPPER, userId, from, to, status.name());
    }

    @Override
    public Optional<Vacation> getVacationDay(final long id) {
        return ofNullable(jdbc.queryForObject("SELECT * FROM vacation_day WHERE id = ?", VACATION_MAPPER, id));
    }

    @Override
    public void insertVacationDay(final Long userId, final Vacation day) {
        jdbc.update("INSERT INTO vacation_day (vacation_date, time_from, time_to, status, vacation_type, user_id) VALUES (?,?,?,?,?,?)",
                day.getDate(), day.getFrom(), day.getTo(), day.getStatus().name(), day.getType().name(), userId);
    }

    @Override
    public void updateVacationDay(final Vacation item) {
        jdbc.update("UPDATE vacation_day SET vacation_date=?, time_from=?, time_to=?, status=?, vacation_type=? WHERE id=?",
                item.getDate(), item.getFrom(), item.getTo(), item.getStatus().name(), item.getType().name(), item.getId());
    }

    @Override
    public void deleteVacationDay(final long id) {
        jdbc.update("DELETE FROM vacation_day WHERE id=?", id);
    }

    @Override
    public boolean isExistVacationForUser(Long userId, LocalDate date) {
        return jdbc.queryForObject("SELECT count(id) AS exist FROM vacation_day WHERE vacation_date = ? AND user_id = ?",
                new Object[]{date, userId}, (rs, rowNum) -> rs.getBoolean("exist"));
    }
}
