import java.util.ArrayList;

/**
 * Email type class
 * @author Chelsea Li
 */
public class EmailType {

    private String theemailType;
    static ArrayList<EmailType> emailType;

    static {
        emailType = new ArrayList<EmailType>();
    }

    /**
     * Stores new email type object
     * @param etype
     */
    public EmailType(String etype) {
        this.theemailType = etype;
        emailType.add(this);
    }

    /**
     * Gets email type
     * @return theemailType
     */
    public String getEmailType() {
        return theemailType; 
    }

    /**
     * Changes email type
     * @param etype
     */
    public void setEmailType(String etype) {
        theemailType = etype;
    }
}