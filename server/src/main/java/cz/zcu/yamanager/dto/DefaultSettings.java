package cz.zcu.yamanager.dto;

import java.time.LocalDateTime;

public class DefaultSettings {

    private VacationInfo sickDay;
    private LocalDateTime notification;

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

    public LocalDateTime getNotification() {
        return notification;
    }

    public void setNotification(LocalDateTime notification) {
        this.notification = notification;
    }
}
