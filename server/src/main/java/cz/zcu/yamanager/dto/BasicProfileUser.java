package cz.zcu.yamanager.dto;

import java.util.List;

public class BasicProfileUser {
    private long id;
    private UserName name;
    private String photo;
    private List<CalendarItem> calendar;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<CalendarItem> getCalendar() {
        return calendar;
    }

    public void setCalendar(List<CalendarItem> calendar) {
        this.calendar = calendar;
    }
}
