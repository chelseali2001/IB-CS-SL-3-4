import java.util.ArrayList;

/**
 * Street 1 class
 * @author Chelsea Li
 */
public class Street1 {

    private String thestreet1;
    static ArrayList<Street1> street1;

    static {
        street1 = new ArrayList<Street1>();
    }

    /**
     * Stores new street 1 object
     * @param str1
     */
    public Street1(String str1) {
        this.thestreet1 = str1;
        street1.add(this);
    }

    /**
     * Gets street 1
     * @return thestreet1
     */
    public String getStreet1() {
        return thestreet1; 
    }

    /**
     * Changes street 1
     * @param str1
     */
    public void setStreet1(String str1) {
        thestreet1 = str1;
    }
}