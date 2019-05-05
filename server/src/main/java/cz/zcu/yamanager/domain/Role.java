package cz.zcu.yamanager.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * A domain class {@code Role} represents a single record in the Role table of a database.
 * Role groups user's rights to a bigger wholes.
 */
public class Role {

    /**
     * ID of the role.
     */
    private final int id;

    /**
     * Name of the role.
     */
    private String name;

    /**
     * List of rights of the role.
     */
    private List<Right> rightList;

    /**
     * Creates a new instance of the class {@code Role}.
     * @param id ID of the role.
     * @param name Name of the role.
     * @param rightList List of rights of the role.
     */
    public Role(int id, String name, List<Right> rightList) {
        this.id = id;
        this.name = name;
        this.rightList = rightList;
    }

    /**
     * Gets an ID of the role.
     * @return The ID of the right.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets a name of the role.
     * @return The name of the role.
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
     * Returns a copy of a list of rights.
     * @return The copy of the list of rights.
     */
    public List<Right> getRightList() {
        return new ArrayList<>(this.rightList);
    }

    /**
     * Sets a copy of the given list of rights.
     * @param rightList The given list of rights.
     */
    public void setRightList(List<Right> rightList) {
        this.rightList = new ArrayList<>(rightList);
    }

    /**
     * Gets a string representation of the class {@code Role}.
     * @return The string representation of the class.
     */
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rightList=" + rightList +
                '}';
    }
}