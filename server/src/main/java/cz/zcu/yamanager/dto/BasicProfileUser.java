package cz.zcu.yamanager.dto;

import java.util.List;

public class BasicProfileUser {
    private Long id;
    private String firstName;
    private String lastName;
    private String photo;
    private List<VacationDay> calendar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<VacationDay> getCalendar() {
        return calendar;
    }

    public void setCalendar(List<VacationDay> calendar) {
        this.calendar = calendar;
    }
}
