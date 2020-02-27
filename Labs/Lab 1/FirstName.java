import java.util.ArrayList;

/**
 * First name class
 * @author Chelsea Li
 */
public class FirstName {

    private String firstName;
    static ArrayList<FirstName> firstNames;

    static {
        firstNames = new ArrayList<FirstName>();
    }

    /** 
     * Stores the new first name object
     * @param fname
     */
    public FirstName(String fname) {
        this.firstName = fname;
        firstNames.add(this);
    }

    /**
     * Gets first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName; 
    }

    /**
     * Changes the first name
     * @param fname
     */
    public void setFirstName(String fname) {
        firstName = fname;
    }
}