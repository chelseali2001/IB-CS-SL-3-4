import java.util.ArrayList;

/**
 * Notes class
 * @author Chelsea Li
 */
public class Notes {

    private String note;
    static ArrayList<Notes> notes;

    static {
        notes = new ArrayList<Notes>();
    }

    /**
     * Stores new note object
     * @param not
     */
    public Notes(String not) {
        this.note = not;
        notes.add(this);
    }

    /**
     * Gets notes
     * @return note
     */
    public String getNotes() {
        return note; 
    }

    /**
     * Changes notes
     * @param not
     */
    public void setNotes(String not) {
        note = not;
    }
}