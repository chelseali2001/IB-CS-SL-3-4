import java.util.ArrayList;

/**
 * Clock class
 * @author Chelsea Li
 */
public class Clock {
    private String clockName;
    private String clockIndex;
    private Boolean settingType;
    private String timeZone;
    private String clockType;
    private Boolean showDClock;
    private String dClockType;
    private Boolean showDate;
    private Boolean showSecondHand;
    static ArrayList<Clock> clockList;

    static {
        clockList = new ArrayList<Clock>();
    }

    /**
     * Creates and stores new clock object
     * @param cName
     * @param cIndex
     * @param sType
     * @param tZone
     * @param cType
     * @param sDClock
     * @param dCType
     * @param sDate
     * @param sSHand
     */
    public Clock(String cName, String cIndex, boolean sType, String tZone, String cType, Boolean sDClock, String dCType, Boolean sDate, Boolean sSHand) {
        this.clockName = cName;
        this.clockIndex = cIndex;
        this.settingType = sType;
        this.timeZone = tZone;
        this.clockType = cType;
        this.showDClock = sDClock;
        this.dClockType = dCType;
        this.showDate = sDate;
        this.showSecondHand = sSHand;
        clockList.add(this);
    }

    /**
     * Gets clock name
     * @return clockName
     */
    public String getName() {
        return clockName; 
    }

    /**
     * Gets clock index
     * @return clock index
     */
    public String getIndex() {
        return clockIndex; 
    }

    /**
     * Gets setting type (true for automatic, false for manual)
     * @return setting type
     */
    public boolean getSettingType() {
        return settingType; 
    }

    /**
     * Gets timezone
     * @return timeZone
     */
    public String getTimeZone() {
        return timeZone; 
    }

    /**
     * Gets clock type
     * @return clockType
     */
    public String getClockType() {
        return clockType; 
    }

    /**
     * Returns a boolean for whether or not the digital clock should show
     * @return showDClock
     */
    public boolean getShowDigitalClock() {
        return showDClock; 
    }

    /**
     * Gets digital clock type
     * @return dClockType
     */
    public String getDigitalClockType() {
        return dClockType; 
    }

    /**
     * Returns whether or not the date should show
     * @return showDate
     */
    public boolean getShowDate() {
        return showDate; 
    }

    /**
     * Returns whether or not the second hand should show
     * @return showSecondHand
     */
    public boolean getShowSecondHand() {
        return showSecondHand; 
    }
}