package cz.zcu.yamanager.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class VacationRequest {

    class User {
        public UserName name;
    }

    private long id;
    private User user;
    private LocalDate date;
    private LocalTime from;
    private LocalTime to;
    private VacationType type;
    private RequestStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUserName(UserName name) {
        this.user = new User();
        this.user.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getFrom() {
        return from;
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public LocalTime getTo() {
        return to;
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }

    public VacationType getType() {
        return type;
    }

    public void setType(VacationType type) {
        this.type = type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
