package org.danekja.ymanager.domain;

/**
 * The enum {@code UserRole} represents different roles of a user.
 */
public enum UserRole {
    /**
     * The user is an employee.
     */
    EMPLOYEE,

    /**
     * The user is na employer. This user has more rights than an employee.
     */
    EMPLOYER;

    /**
     * Creates the role from the provided string.
     * The method is case insensitive. If the given string does not correspond to any values of the enum the method returns null.
     *
     * @param role the string representation of the role
     * @return the value of the enum or null
     */
    public static UserRole getUserRole(final String role) {
        if (role == null || role.isEmpty()) return null;
        try {
            return UserRole.valueOf(role.toUpperCase());
        } catch (final IllegalArgumentException e) {
            return null;
        }
    }
}
