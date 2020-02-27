import java.util.ArrayList;

public class OptionalExtension {

    private String theoptionalExtension;
    static ArrayList<OptionalExtension> optionalExtension;

    static {
        optionalExtension = new ArrayList<OptionalExtension>();
    }

    public OptionalExtension(String oe) {
        this.theoptionalExtension = oe;
        optionalExtension.add(this);
    }

    //First Name
    public String getOptionalExtension() {
        return theoptionalExtension; 
    }

    public void setOptionalExtension(String oe) {
        theoptionalExtension = oe;
    }
}