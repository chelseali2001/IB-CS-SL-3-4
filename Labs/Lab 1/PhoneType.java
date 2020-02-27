import java.util.ArrayList;

public class PhoneType {

    private String thephoneType;
    static ArrayList<PhoneType> phoneType;

    static {
        phoneType = new ArrayList<PhoneType>();
    }

    public PhoneType(String ptype) {
        this.thephoneType = ptype;
        phoneType.add(this);
    }

    public String getPhoneType() {
        return thephoneType; 
    }

    public void setPhoneType(String ptype) {
        thephoneType = ptype;
    }
}