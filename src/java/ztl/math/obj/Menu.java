/*
 * This Application and all Its resources are the property of
 * John Mtonga(Application Developer)
 */
package ztl.math.obj;

/**
 *
 * @author John Mtonga(Application Developer)
 */
public enum Menu {

    Addition(1, "Addition"),
    Substraction(2, "Substraction");

    private final int ID;
    private final String description;

    private Menu(int ID, String description) {
        this.ID = ID;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static Menu getMenu(int menuID) {
        for (Menu m : Menu.values()) {
            if (m.getID() == menuID) {
                return m;
            }
        }
        return null;
    }

}
