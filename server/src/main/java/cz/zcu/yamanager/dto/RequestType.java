package cz.zcu.yamanager.dto;

public enum  RequestType {
    VACATION, AUTHORIZATION;

    public static RequestType getType(String type) {
        if (type == null || type.isEmpty()) return null;
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
