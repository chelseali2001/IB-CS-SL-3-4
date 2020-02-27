import java.util.ArrayList;

/**
 * Last name class
 * @author Chelsea Li
 */
public class LastName {

    private String lastName;
    static ArrayList<LastName> lastNames;

    static {
        lastNames = new ArrayList<LastName>();
    }

    /**
     * Stores the new last name object
     * @param lname
     */
    public LastName(String lname) {
        this.lastName = lname;
        lastNames.add(this);
    }

    /**
     * Gets last name
     * @return lastName
     */
    public String getLastName() {
        return lastName; 
    }

    /**
     * Changes last name
     * @param lname
     */
    public void setLastName(String lname) {
        lastName = lname;
    }
}