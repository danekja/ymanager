package cz.zcu.yamanager.dto;

/**
 * The enum {@code VacationType} represents different types of a vacation.
 */
public enum VacationType {
    /**
     * The vacation represents a sick day.
     */
    SICKDAY,

    /**
     * The vacation represents an overtime.
     */
    VACATION;

    /**
     * Creates the type of a vacation from the provided string.
     * The method is case insensitive. If the given string does not correspond to any values of the enum the method returns null.
     *
     * @param type the string representation of the type of a vacation
     * @return the value of the enum or null
     */
    public static VacationType getVacationType(final String type) {
        if (type == null || type.isEmpty()) return null;
        try {
            return VacationType.valueOf(type.toUpperCase());
        } catch (final IllegalArgumentException e) {
            return null;
        }
    }
}
