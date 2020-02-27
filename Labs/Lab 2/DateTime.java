import java.util.ArrayList;

/**
 * DateTime class
 * @author Chelsea Li
 */
public class DateTime {
    private int dateSetX;
    private int dateSetY;
    private int timeSetX;
    private int timeSetY;
    private int font;
    private int timeSetX24;
    static ArrayList<DateTime> datetimeList;

    static {
        datetimeList = new ArrayList<DateTime>();
    }

    /**
     * Creates and stores new date time object
     * @param dSetX
     * @param dSetY
     * @param tSetX
     * @param tSetY
     * @param f
     * @param tSetX24
     */
    public DateTime(int dSetX, int dSetY, int tSetX, int tSetY, int f, int tSetX24) {
        this.dateSetX = dSetX;
        this.dateSetY = dSetY;
        this.timeSetX = tSetX;
        this.timeSetY = tSetY;
        this.font = f;
        this.timeSetX24 = tSetX24;
        datetimeList.add(this);
    }

    /**
     * Gets x-coordinate of the date label
     * @return dateSetX
     */
    public int getDateSetX() {
        return dateSetX; 
    }

    /**
     * Gets y-coordinate of the date label
     * @return dateSetY
     */
    public int getDateSetY() {
        return dateSetY; 
    }

    /**
     * Gets x-coordinate of the digital time label
     * @return timeSetX
     */
    public int getTimeSetX() {
        return timeSetX; 
    }

    /**
     * Gets y-coordinate of the digital time label
     * @return timeSetY
     */
    public int getTimeSetY() {
        return timeSetY; 
    }

    /**
     * Gets the font size
     * @return font
     */
    public int getFont() {
        return font; 
    }

    /**
     * Gets x-coordinate of the digital time label (the the 24-hour time is chosen)
     * @return timeSet24
     */
    public int getTimeSetX24() {
        return timeSetX24; 
    }
}