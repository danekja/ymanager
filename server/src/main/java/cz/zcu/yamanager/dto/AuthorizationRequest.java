package cz.zcu.yamanager.dto;

import java.time.LocalDateTime;

public class AuthorizationRequest {

    class User {
        public UserName name;
    }

    private long id;
    private User user;
    private LocalDateTime date;

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

    public void setUserName(String firstName, String lastName) {
        UserName name = new UserName();
        name.setFirst(firstName);
        name.setLast(lastName);
        this.setUserName(name);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
