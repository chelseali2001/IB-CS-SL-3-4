import java.util.ArrayList;

/**
 * Address class
 * @author Chelsea Li
 */
public class Address {

    private AddressType theaddressType;
    private Street1 thestreet1;
    private Street2 thestreet2;
    private City thecity;
    private State thestate;
    private ZipCode thezipCode;
    public static ArrayList<Address> address;

    static {
        address = new ArrayList<Address>();
    }

    /**
     * Creates and stores new address object
     * @param atype
     * @param str1
     * @param str2
     * @param cit
     * @param stat
     * @param zcode
     */
    public Address(String atype, String str1, String str2, String cit, String stat, String zcode) {
        theaddressType = new AddressType(atype);
        thestreet1 = new Street1(str1);
        thestreet2 = new Street2(str2);
        thecity = new City(cit);
        thestate = new State(stat);
        thezipCode = new ZipCode(zcode);
        address.add(this);
    }

    /**
     * Gets address type
     * @return addrss type
     */
    public String getAddressType() {
        return theaddressType.getAddressType();
    }

    /**
     * Gets street 1
     * @return street 1
     */
    public String getStreet1() {
        return thestreet1.getStreet1();
    }

    /**
     * Gets street 2
     * @return street 2
     */
    public String getStreet2() {
        return thestreet2.getStreet2();
    }

    /**
     * Gets city
     * @return city
     */
    public String getCity() {
        return thecity.getCity();
    }

    /**
     * Gets state
     * @return state
     */
    public String getState() {
        return thestate.getState();
    }

    /**
     * Gets zip code
     * @return zip code
     */
    public String getZipCode() {
        return thezipCode.getZipCode();
    }
}