package cz.zcu.yamanager.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarItem {

    private LocalDate date;
    private LocalTime from;
    private LocalTime to;
    private VacationType type;

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
}
