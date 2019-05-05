package cz.zcu.yamanager.domain;

/**
 * A domain class {@code Status} represents a single record in the Approval_status table of a database.
 * Approval status indicates state of user's authorization or vacation/sick days confirmation.
 */
public class Status {

    /**
     * ID of the status.
     */
    private final int id;

    /**
     * Name of the status.
     */
    private String name;

    /**
     * Creates a new instance of the class {@code Status}.
     * @param id ID of the status.
     * @param name Name of the status.
     */
    public Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets an ID of the status.
     * @return The ID of the status.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets a name of the status.
     * @return The name of the status.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name.
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets a string representation of the class {@code Status}.
     * @return The string representation of the class.
     */
    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
