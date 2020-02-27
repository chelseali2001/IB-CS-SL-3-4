/**
 * Scores class
 * @author Chelsea Li
 */
public class Scores {
	private int place;
	private String name;
	private int score;

    /**
     * Stores new Scores object
     * @param p
     * @param n
     * @param s
     */
    public Scores(int p, String n, int s) {
    	this.place = p;
        this.name = n;
        this.score = s;
    }

    /**
     * Gets place
     * @return place
     */
    public int getPlace() {
        return place; 
    }

    /**
     * Gets name
     * @return name
     */
    public String getName() {
        return name; 
    }

    /**
     * Gets score
     * @return score
     */
    public int getScore() {
        return score; 
    }
}