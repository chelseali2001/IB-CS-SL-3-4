import java.util.ArrayList;

/**
 * City class
 * @author Chelsea Li
 */
public class City {

    private String thecity;
    static ArrayList<City> city;

    static {
        city = new ArrayList<City>();
    }

    /**
     * Stores new city object
     * @param cit
     */
    public City(String cit) {
        this.thecity = cit;
        city.add(this);
    }

    /**
     * Gets city
     * @return thecity
     */
    public String getCity() {
        return thecity; 
    }

    /**
     * Changes city
     * @param cit
     */
    public void setCity(String cit) {
        thecity = cit;
    }
}