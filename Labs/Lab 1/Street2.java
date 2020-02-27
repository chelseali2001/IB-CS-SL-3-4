import java.util.ArrayList;

/**
 * Street 2 class
 * @author Chelsea Li
 */
public class Street2 {

    private String thestreet2;
    static ArrayList<Street2> street2;

    static {
        street2 = new ArrayList<Street2>();
    }

    /**
     * Stores new street 2 object
     * @param str2
     */
    public Street2(String str2) {
        this.thestreet2 = str2;
        street2.add(this);
    }

    /**
     * Gets street 2
     * @return thestreet2
     */
    public String getStreet2() {
        return thestreet2; 
    }

    /**
     * Changes street 2
     * @param str2
     */
    public void setStreet2(String str2) {
        thestreet2 = str2;
    }
}