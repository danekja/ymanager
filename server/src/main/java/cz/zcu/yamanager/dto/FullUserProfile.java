package cz.zcu.yamanager.dto;


import java.time.LocalDateTime;

public class FullUserProfile {

    class Settings {
        public LocalDateTime notification;
    }

    private long id;
    private UserName name;
    private String photo;
    private Settings settings;
    private VacationInfo vacation;
    private VacationInfo sickDay;
    private UserStatus status;
    private UserRole role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserName getName() {
        return name;
    }

    public void setName(UserName name) {
        this.name = name;
    }

    public void setName(String firstName, String lastName) {
        UserName name = new UserName();
        name.setFirst(firstName);
        name.setLast(lastName);
        this.setName(name);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setNotification(LocalDateTime notification) {
        this.settings = new Settings();
        this.settings.notification = notification;
    }

    public VacationInfo getVacation() {
        return vacation;
    }

    public void setVacation(VacationInfo vacation) {
        this.vacation = vacation;
    }

    public void setVacation(float count, VacationUnit unit) {
        VacationInfo info = new VacationInfo();
        info.setValue(count);
        info.setUnit(unit);
        this.setVacation(info);
    }

    public VacationInfo getSickDay() {
        return sickDay;
    }

    public void setSickDay(VacationInfo sickDay) {
        this.sickDay = sickDay;
    }

    public void setSickDay(short count, VacationUnit unit) {
        VacationInfo info = new VacationInfo();
        info.setValue(count);
        info.setUnit(unit);
        this.setSickDay(info);
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}