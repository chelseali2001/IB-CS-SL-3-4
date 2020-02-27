import java.util.ArrayList;

/**
 * Address type class
 * @author Chelsea Li
 */
public class AddressType {

    private String theaddressType;
    static ArrayList<AddressType> addressType;

    static {
        addressType = new ArrayList<AddressType>();
    }

    /**
     * Stores new address type object 
     * @param atype
     */
    public AddressType(String atype) {
        this.theaddressType = atype;
        addressType.add(this);
    }

    /**
     * Gets address type
     * @return theaddressType
     */
    public String getAddressType() {
        return theaddressType; 
    }

    /**
     * Changes address type 
     * @param atype
     */
    public void setAddressType(String atype) {
        theaddressType = atype;
    }
}