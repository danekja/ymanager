package cz.zcu.yamanager.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {

    private static final Logger log = LoggerFactory.getLogger(TestRepository.class);

    private final JdbcTemplate jdbc;

    @Autowired
    public TestRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /*
    public List<AuthorizationRequest> getAllUserRequests() {
        return jdbc.query("SELECT id, first_name, last_name FROM end_user", (ResultSet rs, int rowNum) -> {
            AuthorizationRequest request = new AuthorizationRequest();
            request.setId(rs.getLong("id"));
            request.setUserName(rs.getString("first_name"), rs.getString("last_name"));

            // No timestamp

            return request;
        });
    }

    public List<AuthorizationRequest> getAllPendingUserRequests() {
        return jdbc.query("SELECT id, first_name, last_name FROM end_user WHERE status_id=2", (ResultSet rs, int rowNum) -> {
            AuthorizationRequest request = new AuthorizationRequest();
            request.setId(rs.getLong("id"));
            request.setUserName(rs.getString("first_name"), rs.getString("last_name"));

            // No timestamp

            return request;
        });
    }

    public void updateApproval(BasicRequest request) {
        if (request.getType() == RequestType.AUTHORIZATION) {
            jdbc.update("UPDATE end_user SET status_id=? WHERE id=?", request.getStatus().ordinal(), request.getId());
        } else {
            jdbc.update("UPDATE vacation_day SET status_id=? WHERE id=?", request.getStatus().ordinal(), request.getId());
        }
    }

    public List<VacationDay> getCalendarItems(long userId, LocalDate from, LocalDate to) {
        return jdbc.query("SELECT vacation_date, time_from, time_to, type_id, status_id FROM vacation_day WHERE user_id=? AND vacation_date >= ? AND vacation_date <= ?",
                new Object[]{userId, from, to}, (ResultSet rs, int rowNum) -> {
            VacationDay item = new VacationDay();
            item.setDate(rs.getDate("day.vacation_date").toLocalDate());
            item.setFrom(rs.getTime("day.time_from").toLocalTime());
            item.setTo(rs.getTime("day.time_to").toLocalTime());
            item.setType(VacationType.values()[rs.getByte("type_id")]);
            item.setStatus(RequestStatus.values()[rs.getByte("status_id")]);
            return item;
        });
    }

    public void insertCalendarItem(VacationDay item) {
        jdbc.update("INSERT INTO vacation_day (vacation_date, time_from, time_to, user_id, status_id, type_id) VALUES (?,?,?,?,?,?)",
                item.getDate(), item.getFrom(), item.getTo(), item.getUserId(), item.getStatus().ordinal(), item.getType().ordinal());
    }

    public void updateCalendarItem(VacationDay item) {
        jdbc.update("UPDATE vacation_day SET vacation_date=?, time_from=?, time_to=?, user_id=?, status_id=?, type_id=? WHERE id=?",
                item.getDate(), item.getFrom(), item.getTo(), item.getUserId(), item.getStatus().ordinal(), item.getType().ordinal(), item.getId());
    }

    public DefaultSettings getLastSettings() {
        return jdbc.queryForObject("SELECT * FROM default_settings ORDER BY id DESC LIMIT 1", (ResultSet rs, int rowNum) -> {
            DefaultSettings settings = new DefaultSettings();
            settings.setSickDay(rs.getShort("no_sick_days"), VacationUnit.DAY);
            settings.setNotification(rs.getTimestamp("alert").toLocalDateTime());
            return settings;
        });
    }

    public void insertSettings(DefaultSettings settings) {
        jdbc.update("INSERT INTO default_settings (no_sick_days, alert) VALUES (?, ?)", settings.getSickDay().getValue(), settings.getNotification());
    }

    public List<BasicProfileUser> getAllBasicUsers() {
        return jdbc.query("SELECT id, first_name, last_name, photo FROM end_user", (ResultSet rs, int rowNum) -> {
            BasicProfileUser user = new BasicProfileUser();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("first_name"), rs.getString("last_name"));
            user.setPhoto(rs.getString("photo"));
            return user;
        });
    }

    public FullUserProfile getFullUser(long id) {
        return jdbc.queryForObject("SELECT first_name, last_name, no_vacation, no_sick_days, alert, photo, role_id, status_id FROM end_user WHERE id=?",
                new Object[]{id}, (ResultSet rs, int rowNum) -> {
            FullUserProfile user = new FullUserProfile();
            user.setId(id);
            user.setName(rs.getString("first_name"), rs.getString("last_name"));
            user.setVacation(rs.getFloat("no_vacation"), VacationUnit.HOUR);
            user.setSickDay(rs.getShort("no_sick_days"), VacationUnit.DAY);
            user.setNotification(rs.getTimestamp("alert").toLocalDateTime());
            user.setPhoto(rs.getString("photo"));
            user.setRole(UserRole.values()[rs.getByte("role_id")]);
            user.setStatus(Status.values()[rs.getByte("status_id")]);
            return user;
        });
    }

    public FullUserProfile getFullUser(String token) {
        return jdbc.queryForObject("SELECT id, first_name, last_name, no_vacation, no_sick_days, alert, photo, role_id, status_id FROM end_user WHERE token=?",
                new Object[]{token}, (ResultSet rs, int rowNum) -> {
            FullUserProfile user = new FullUserProfile();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("first_name"), rs.getString("last_name"));
            user.setVacation(rs.getFloat("no_vacation"), VacationUnit.HOUR);
            user.setSickDay(rs.getShort("no_sick_days"), VacationUnit.DAY);
            user.setNotification(rs.getTimestamp("alert").toLocalDateTime());
            user.setPhoto(rs.getString("photo"));
            user.setRole(UserRole.values()[rs.getByte("role_id")]);
            user.setStatus(Status.values()[rs.getByte("status_id")]);
            return user;
        });
    }
    */
}
