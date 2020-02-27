import java.util.ArrayList;

/**
 * Hands class
 * @author Chelsea Li
 */
public class Hands {
    private int hourX;
    private int hourY;
    private int hourW;
    private int hourH;
    private int minX;
    private int minY;
    private int minW;
    private int minH;
    private int secX;
    private int secY;
    private int secW;
    private int secH;
    static ArrayList<Hands> handsList;

    static {
        handsList = new ArrayList<Hands>();
    }

    /**
     * Creates and stores new hands object
     * @param hX
     * @param hY
     * @param hW
     * @param hH
     * @param mX
     * @param mY
     * @param mW
     * @param mH
     * @param sX
     * @param sY
     * @param sW
     * @param sH
     */
    public Hands(int hX, int hY, int hW, int hH, 
                 int mX, int mY, int mW, int mH, 
                 int sX, int sY, int sW, int sH) {
        this.hourX = hX;
        this.hourY = hY;
        this.hourW = hW;
        this.hourH = hH;
        this.minX = mX;
        this.minY = mY;
        this.minW = mW;
        this.minH = mH; 
        this.secX = sX;
        this.secY = sY;
        this.secW = sW;
        this.secH = sH;      
        handsList.add(this);
    }

    /**
     * Gets x-coordinate of the hour hand
     * @return hourX
     */
    public int getHourX() {
        return hourX; 
    }

    /**
     * Gets y-coordinate of the hour hand
     * @return hourY
     */
    public int getHourY() {
        return hourY; 
    }

    /**
     * Gets width of the hour hand
     * @return hourW
     */
    public int getHourW() {
        return hourW; 
    }

    /**
     * Gets height of the hour hand
     * @return hourH
     */
    public int getHourH() {
        return hourH; 
    }

     /**
     * Gets x-coordinate of the minute hand
     * @return minX
     */
    public int getMinX() {
        return minX; 
    }

    /**
     * Gets y-coordinate of the minute hand
     * @return minY
     */
    public int getMinY() {
        return minY; 
    }

    /**
     * Gets width of the minute hand
     * @return minW
     */
    public int getMinW() {
        return minW; 
    }

    /**
     * Gets height of the minute hand
     * @return minH
     */
    public int getMinH() {
        return minH; 
    }

    /**
     * Gets x-coordinate of the second hand
     * @return secX
     */
    public int getSecX() {
        return secX; 
    }

    /**
     * Gets y-coordinate of the second hand
     * @return secY
     */
    public int getSecY() {
        return secY; 
    }

    /**
     * Gets width of the second hand
     * @return secW
     */
    public int getSecW() {
        return secW; 
    }

    /**
     * Gets height of the second hand
     * @return secH
     */
    public int getSecH() {
        return secH; 
    }
}