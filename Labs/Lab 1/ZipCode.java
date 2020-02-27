import java.util.ArrayList;

/**
 * Zip code class
 * @author Chelsea Li
 */
public class ZipCode {

    private String thezipCode;
    static ArrayList<ZipCode> zipCode;

    static {
        zipCode = new ArrayList<ZipCode>();
    }

    /**
     * Stores new zip code object
     * @param zcode
     */
    public ZipCode(String zcode) {
        this.thezipCode = zcode;
        zipCode.add(this);
    }

    /**
     * Gets zip code
     * @return thezipCode
     */
    public String getZipCode() {
        return thezipCode; 
    }

    /**
     * Changes zip code
     * @param zcode
     */
    public void setZipCode(String zcode) {
        thezipCode = zcode;
    }
}