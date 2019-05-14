package cz.zcu.yamanager.dto;

import java.time.LocalDateTime;

public class DefaultSettings {

    private Integer sickdayCount;
    private LocalDateTime notification;

    public Integer getSickdayCount() {
        return sickdayCount;
    }

    public void setSickdayCount(Integer sickdayCount) {
        this.sickdayCount = sickdayCount;
    }

    public LocalDateTime getNotification() {
        return notification;
    }

    public void setNotification(LocalDateTime notification) {
        this.notification = notification;
    }
}

