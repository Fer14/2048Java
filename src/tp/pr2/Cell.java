package tp.pr2;

import tp.pr2.logic.multigames.GameRules;
/**
 * The Cell class represents a single cell from our board, which will hold its value.
 * */
public class Cell {
	private int value;
	
	/**
	 * Default constructor, will initialize the cell to 0.
	 */
	public Cell(){ value = 0; }
	
	/**
	 * A secondary constructor, which will initialize the cell to the desired value.
	 * @param n The desired value for the cell
	 */
	public Cell(int n){	value = n; }
	
	/**
	 * @return The value of this cell
	 */
	public int getValue(){ return this.value; }
	
	/**
	 * Sets the value of this cell to the desired one
	 * @param value The desired value
	 */
	public void setValue(int value){ this.value=value; }
	
	/**
	 * Merges this cell with the given one if possible.
	 * @param cell The cell you want to try to merge this with.
	 * @param gr The rules which will determine how are they merge.
	 * @return Whether they could be merged or not
	 */
	public int doMerge(Cell cell,GameRules gr)
	{
		return gr.merge(this, cell);
	}
	
	/**
	 * Returns a string with the value of the cell.
	 */
	public String toString(){ return Integer.toString(this.value); }
	
	/**
	 * Checks if this cell's value to be the same to another one
	 * @param cell The cell you want to copy the value from.
	 */
	public void duplicate(Cell cell){ this.value = cell.value; }
	
	/**
	 * Checks if the value of this cell is 0.
	 * @return Whether the value of this cell is 0.
	 */
	public boolean isEmpty(){ return value == 0; }
	
}

