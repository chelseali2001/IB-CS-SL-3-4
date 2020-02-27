import java.util.ArrayList;

/**
 * Email address class
 * @author Chelsea Li
 */
public class EmailAddress {

    private String theemailAddress;
    static ArrayList<EmailAddress> emailAddress;

    static {
        emailAddress = new ArrayList<EmailAddress>();
    }

    /**
     * Stores email address
     * @param eaddress
     */
    public EmailAddress(String eaddress) {
        this.theemailAddress = eaddress;
        emailAddress.add(this);
    }

    /**
     * Gets email address
     * @return theemailAddress
     */
    public String getEmailAddress() {
        return theemailAddress; 
    }

    /**
     * Changes email address
     * @param eaddress
     */
    public void setEmailAddress(String eaddress) {
        theemailAddress = eaddress;
    }
}