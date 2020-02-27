import java.util.ArrayList;

/**
 * State class
 * @author Chelsea Li
 */
public class State {

    private String thestate;
    static ArrayList<State> state;

    static {
        state = new ArrayList<State>();
    }

    /**
     * Stores new state object
     * @param stat
     */
    public State(String stat) {
        this.thestate = stat;
        state.add(this);
    }

    /**
     * Gets state
     * @return thestate
     */
    public String getState() {
        return thestate; 
    }

    /**
     * Changes state
     * @param stat
     */
    public void setState(String stat) {
        thestate = stat;
    }
}