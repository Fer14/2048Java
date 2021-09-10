package tp.pr2;

/**
 * The Position class represents a position in a table,
 * with the column and row values
 */

public class Position {
	private int x;
	private int y;
	
	/** Constructor for this class.
	 * Initializes the x and y attributes
	 * 
	 * @param x the initial value for x
	 * @param y the initial value for y
	 */
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/** Default constructor for this class.
	 */
	public Position()
	{
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Sets the new value for the x attribute
	 * 
	 * @param x new x value
	 */
	public void setx(int x)
	{
		this.x = x;
	}
	
	/**Sets the new value for the y attribute
	 *  
	 * @param y new y value
	 */
	public void sety(int y)
	{
		this.y = y;
	}
	
	/** Gets the value from the x attribute
	 * 
	 * @return The x value from the position
	 */
	public int getx()
	{
		return this.x;
	}
	
	/** Gets the value from the y attribute
	 * 
	 * @return The y value from the position
	 */
	public int gety()
	{
		return this.y;
	}
	
}
