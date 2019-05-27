package cz.zcu.yamanager.dto;


import java.time.LocalDateTime;

public class FullUserProfile {

    private Long id;
    private String firstName;
    private String lastName;
    private String photo;
    private Float vacationCount;
    private Integer sickdayCount;
    private Float takenVacationCount;
    private Integer takenSickdayCount;
    private Status status;
    private UserRole role;
    private LocalDateTime notification;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Float getTakenVacationCount() {
        return takenVacationCount;
    }

    public void setTakenVacationCount(Float takenVacationCount) {
        this.takenVacationCount = takenVacationCount;
    }

    public Integer getTakenSickdayCount() {
        return takenSickdayCount;
    }

    public void setTakenSickdayCount(Integer takenSickdayCount) {
        this.takenSickdayCount = takenSickdayCount;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getNotification() {
        return notification;
    }

    public void setNotification(LocalDateTime notification) {
        this.notification = notification;
    }
}