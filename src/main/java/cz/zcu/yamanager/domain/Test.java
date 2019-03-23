package cz.zcu.yamanager.domain;

public class Test {

    private final long id;
    private final String message;

    public Test(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("Test[id=%d, message='%s']", id, message);
    }
}