package cz.zcu.yamanager.dto;

public enum VacationType {
    VACATION, SICKDAY;

    public static VacationType getVacationType(String type) {
        if (type == null || type.isEmpty()) return null;
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
