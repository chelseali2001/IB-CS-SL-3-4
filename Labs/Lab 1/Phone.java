import java.util.ArrayList;

public class Phone {

    private PhoneType thephoneType;
    private PhoneNumber thephoneNumber;
    private OptionalExtension theoptionalExtension;
    static ArrayList<Phone> phone;

    static {
        phone = new ArrayList<Phone>();
    }

    public Phone(String ptype, String pnum, String oe) {
        thephoneType = new PhoneType(ptype);
        thephoneNumber = new PhoneNumber(pnum);
        theoptionalExtension = new OptionalExtension(oe);
        phone.add(this);
    }

    public String getPhoneType() {
        return thephoneType.getPhoneType();
    }

    public String getPhoneNumber() {
        return thephoneNumber.getPhoneNumber();
    }

    public String getOptionalExtension() {
        return theoptionalExtension.getOptionalExtension();
    }
}