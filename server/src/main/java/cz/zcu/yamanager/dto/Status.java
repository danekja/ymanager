package cz.zcu.yamanager.dto;

public enum Status {
    ACCEPTED, PENDING, REJECTED

    ;
    public static Status getStatus(String status) {
        if (status == null || status.isEmpty()) return null;
        try {
            return valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
