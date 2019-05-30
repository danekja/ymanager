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
     * @param jdbc A connection to the database.
     */
    @Autowired
    public VacationRepository(JdbcTemplate jdbc) {
        log.trace("Creating a new instance of the class VacationRepository");
        this.jdbc = jdbc;
    }

    public List<VacationDay> getVacationDays(String token, LocalDate from) {
        return jdbc.query("SELECT VD.vacation_date, VD.time_from, VD.time_to, VT.name, APS.name " +
                        "FROM vacation_day VD " +
                        "INNER JOIN end_user EU ON VD.user_id = EU.id " +
                        "INNER JOIN vacation_type VT ON VD.type_id = VT.id " +
                        "INNER JOIN approval_status APS ON VD.status_id = APS.id " +
                        "WHERE EU.token = ? AND VD.vacation_date >= ?",
                new Object[]{token, from}, (ResultSet rs, int rowNum) -> {
                    VacationDay item = new VacationDay();
                    item.setDate(rs.getDate("VD.vacation_date").toLocalDate());
                    item.setFrom(rs.getTime("VD.time_from").toLocalTime());
                    item.setTo(rs.getTime("VD.time_to").toLocalTime());
                    item.setType(VacationType.getVacationType(rs.getString("VT.name")));
                    item.setStatus(Status.getStatus(rs.getString("APS.name")));
                    return item;
                });
    }

    public List<VacationDay> getVacationDays(String token, LocalDate from, LocalDate to) {
        return jdbc.query("SELECT VD.vacation_date, VD.time_from, VD.time_to, VT.name, APS.name " +
                        "FROM vacation_day VD " +
                        "INNER JOIN end_user EU ON VD.user_id = EU.id " +
                        "INNER JOIN vacation_type VT ON VD.type_id = VT.id " +
                        "INNER JOIN approval_status APS ON VD.status_id = APS.id " +
                        "WHERE EU.token = ? AND VD.vacation_date >= ? AND VD.vacation_date <= ?",
                new Object[]{token, from, to}, (ResultSet rs, int rowNum) -> {
                    VacationDay item = new VacationDay();
                    item.setDate(rs.getDate("VD.vacation_date").toLocalDate());
                    item.setFrom(rs.getTime("VD.time_from").toLocalTime());
                    item.setTo(rs.getTime("VD.time_to").toLocalTime());
                    item.setType(VacationType.getVacationType(rs.getString("VT.name")));
                    item.setStatus(Status.getStatus(rs.getString("APS.name")));
                    return item;
                });
    }

    public List<VacationDay> getVacationDays(long userId, LocalDate from) {
        return jdbc.query("SELECT VD.vacation_date, VD.time_from, VD.time_to, VT.name, APS.name " +
                        "FROM vacation_day VD " +
                        "INNER JOIN end_user EU ON VD.user_id = EU.id " +
                        "INNER JOIN vacation_type VT ON VD.type_id = VT.id " +
                        "INNER JOIN approval_status APS ON VD.status_id = APS.id " +
                        "WHERE VD.user_id = ? AND VD.vacation_date >= ?",
                new Object[]{userId, from}, (ResultSet rs, int rowNum) -> {
                    VacationDay item = new VacationDay();
                    item.setDate(rs.getDate("VD.vacation_date").toLocalDate());
                    item.setFrom(rs.getTime("VD.time_from").toLocalTime());
                    item.setTo(rs.getTime("VD.time_to").toLocalTime());
                    item.setType(VacationType.getVacationType(rs.getString("VT.name")));
                    item.setStatus(Status.getStatus(rs.getString("APS.name")));
                    return item;
                });
    }

    public List<VacationDay> getVacationDays(long userId, LocalDate from, LocalDate to) {
        return jdbc.query("SELECT VD.vacation_date, VD.time_from, VD.time_to, VT.name, APS.name " +
                        "FROM vacation_day VD " +
                        "INNER JOIN end_user EU ON VD.user_id = EU.id " +
                        "INNER JOIN vacation_type VT ON VD.type_id = VT.id " +
                        "INNER JOIN approval_status APS ON VD.status_id = APS.id " +
                        "WHERE VD.user_id=? AND VD.vacation_date >= ? AND VD.vacation_date <= ?",
                new Object[]{userId, from, to}, (ResultSet rs, int rowNum) -> {
                    VacationDay item = new VacationDay();
                    item.setDate(rs.getDate("VD.vacation_date").toLocalDate());
                    item.setFrom(rs.getTime("VD.time_from").toLocalTime());
                    item.setTo(rs.getTime("VD.time_to").toLocalTime());
                    item.setType(VacationType.getVacationType(rs.getString("VT.name")));
                    item.setStatus(Status.getStatus(rs.getString("APS.name")));
                    return item;
                });
    }

    public List<VacationDay> getVacationDays(long userId, LocalDate from, LocalDate to, Status status) {
        return jdbc.query("SELECT VD.vacation_date, VD.time_from, VD.time_to, VT.name, APS.name " +
                        "FROM vacation_day VD " +
                        "INNER JOIN end_user EU ON VD.user_id = EU.id " +
                        "INNER JOIN vacation_type VT ON VD.type_id = VT.id " +
                        "INNER JOIN approval_status APS ON VD.status_id = APS.id " +
                        "WHERE VD.user_id=? AND VD.vacation_date >= ? AND VD.vacation_date <= ? AND APS.name = ?",
                new Object[]{userId, from, to, status.name()}, (ResultSet rs, int rowNum) -> {
                    VacationDay item = new VacationDay();
                    item.setDate(rs.getDate("VD.vacation_date").toLocalDate());
                    item.setFrom(rs.getTime("VD.time_from").toLocalTime());
                    item.setTo(rs.getTime("VD.time_to").toLocalTime());
                    item.setType(VacationType.getVacationType(rs.getString("VT.name")));
                    item.setStatus(Status.getStatus(rs.getString("APS.name")));
                    return item;
                });
    }

    public void insertVacationDay(Long userId, VacationDay day) {
        jdbc.update("INSERT INTO vacation_day (vacation_date, time_from, time_to, user_id, status_id, type_id) VALUES (?,?,?,?,?,?)",
                day.getDate(), day.getFrom(), day.getTo(), userId, day.getStatus().ordinal(), day.getType().ordinal());
    }

    public void updateVacationDay(VacationDay item) {
        jdbc.update("UPDATE vacation_day SET vacation_date=?, time_from=?, time_to=?, status_id=?, type_id=? WHERE id=?",
                item.getDate(), item.getFrom(), item.getTo(), item.getStatus().ordinal(), item.getType().ordinal(), item.getId());
    }

    public void deleteVacationDay(long id) {
        jdbc.update("DELETE FROM vacation_day WHERE id=?", id);
    }
}
