package cz.zcu.yamanager.dto;

public class UserSettings {

    private Long id;
    private Float vacationCount;
    private Integer sickdayCount;
    private UserRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getVacationCount() {
        return vacationCount;
    }

    public void setVacationCount(Float vacationCount) {
        this.vacationCount = vacationCount;
    }

    public Integer getSickdayCount() {
        return sickdayCount;
    }

    public void setSickdayCount(Integer sickdayCount) {
        this.sickdayCount = sickdayCount;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
