package cz.zcu.yamanager.domain;

/**
 * A domain class {@code Right} represents a single record in the Right table of a database.
 * It holds informations about a user's right, which says what can a user do in the application.
 * Rights are grouped to roles ({@code Role}).
 */
public class Right {

    /**
     * ID of the right.
     */
    private final int id;

    /**
     * Name of the right.
     */
    private String name;

    /**
     * Description of the right.
     */
    private String description;

    /**
     * Creates a new instance of the class {@code Right}.
     * @param id ID of the right.
     * @param name Name of the right.
     * @param description Description of the right.
     */
    public Right(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Gets an ID of the right.
     * @return The ID of the right.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets a name of the right.
     * @return The name of the right.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets a new name.
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets a description of the right.
     * @return The description of the right.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets a new description.
     * @param description The new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets a string representation of the class {@code Right}.
     * @return The string representation of the class.
     */
    @Override
    public String toString() {
        return "Right{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                '}';
    }
}