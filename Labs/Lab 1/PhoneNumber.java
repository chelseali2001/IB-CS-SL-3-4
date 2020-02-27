import java.util.ArrayList;

public class PhoneNumber {

    private String thephoneNumber;
    static ArrayList<PhoneNumber> phoneNumber;

    static {
        phoneNumber = new ArrayList<PhoneNumber>();
    }

    public PhoneNumber(String pnum) {
        this.thephoneNumber = pnum;
        phoneNumber.add(this);
    }

    public String getPhoneNumber() {
        return thephoneNumber; 
    }

    public void setPhoneNumber(String pnum) {
        thephoneNumber = pnum;
    }
}