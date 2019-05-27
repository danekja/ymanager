package cz.zcu.yamanager.dto;

public enum  UserRole {
    EMPLOYEE, EMPLOYER;

    public static UserRole getUserRole(String role) {
        if (role == null || role.isEmpty()) return null;
        try {
            return valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
