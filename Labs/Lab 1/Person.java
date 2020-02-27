import java.util.ArrayList;

/**
 * Person class
 * @author Chelsea Li
 */
public class Person {

    private FirstName firstName;
    private LastName lastName;
    private Company company;
    public static ArrayList<Person> people;

    static {
        people = new ArrayList<Person>();
    }

    /**
     * Creates and stores a new Person object
     * @param fname
     * @param lname
     * @param comp
     */
    public Person(String fname, String lname, String comp) {
        firstName = new FirstName(fname);
        lastName = new LastName(lname);
        company = new Company(comp);
        people.add(this);
    }

    /**
     * Gets the first name
     * @return first name
     */
    public String getFirstName() {
        return firstName.getFirstName();
    }

    /**
     * Gets the last name
     * @return last name
     */
    public String getLastName() {
        return lastName.getLastName();
    }

    /**
     * Gets the company name
     * @return company
     */
    public String getCompany() {
        return company.getCompany();
    }
}