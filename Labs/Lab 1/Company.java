import java.util.ArrayList;

/**
 * Company class
 * @author Chelsea Li
 */
public class Company {

    private String company;
    static ArrayList<Company> companies;

    static {
        companies = new ArrayList<Company>();
    }

    /** 
     * Stores the new company object
     * @param comp
     */
    public Company(String comp) {
        this.company = comp;
        companies.add(this);
    }

    /**
     * Gets company
     * @return company
     */
    public String getCompany() {
        return company; 
    }

    /**
     * Changes company
     * @param comp
     */
    public void setCompany(String comp) {
        company = comp;
    }
}