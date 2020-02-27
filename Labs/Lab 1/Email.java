import java.util.ArrayList;

/**
 * Email class
 * @author Chelsea Li
 */
public class Email {

    private EmailType theemailType;
    private EmailAddress theemailAddress;
    static ArrayList<Email> email;

    static {
        email = new ArrayList<Email>();
    }

    /**
     * Creates and stores a new email object
     * @param etype
     * @param eaddress
     */
    public Email(String etype, String eaddress) {
        theemailType = new EmailType(etype);
        theemailAddress = new EmailAddress(eaddress);
        email.add(this);
    }

    /**
     * Gets email type
     * @return email type
     */
    public String getEmailType() {
        return theemailType.getEmailType();
    }

    /**
     * Gets email address
     * @return email address
     */
    public String getEmailAddress() {
        return theemailAddress.getEmailAddress();
    }
}