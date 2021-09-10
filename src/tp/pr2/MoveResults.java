package tp.pr2;
/**
 * The class MoveResults maintains the results of a move, keeping track
 * of the points you have acquired and whether there has been a move or not.
 */
public class MoveResults {

	private boolean moved;
	private int score;
	
	/**
	 * The default constructor for this class.
	 */
	public MoveResults(){
		moved=false;
		score=0;
	}
	
	/**
	 * @return the value of score.
	 */
	public int getScore(){ return score; }
	
	/**
	 * @return True if it has moved, false otherwise.
	 */
	public boolean hasMoved(){ return moved; }
	
	/**
	 * Adds the score to the current one, storing it.
	 * @param score The new score to add up.
	 */
	public void addScore(int score){ this.score+=score; }
	
	/**
	 * Sets moved to the desired value. Is often called with true.
	 * @param moved The new value for moved
	 */
	public void setMoved(boolean moved){ this.moved=moved; }
}
