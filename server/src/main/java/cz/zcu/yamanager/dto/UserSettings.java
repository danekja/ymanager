package cz.zcu.yamanager.dto;

public class UserSettings {

    private UserRole role;
    private VacationInfo vacation;
    private VacationInfo sickDay;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public VacationInfo getVacation() {
        return vacation;
    }

    public void setVacation(VacationInfo vacation) {
        this.vacation = vacation;
    }

    public VacationInfo getSickDay() {
        return sickDay;
    }

    public void setSickDay(VacationInfo sickDay) {
        this.sickDay = sickDay;
    }
}
