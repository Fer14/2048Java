package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.Board;
import tp.pr2.Cell;
import tp.pr2.Position;

/**
 * The class Rules2048 holds the rules for the inverse 2048.
 */
public class RulesInverse implements GameRules{


	/**
	 * Chooses the value of the cell that will be inserted, 2048 with a 90% chance
	 * and 1024 with a 10% chance.
	 * @param rand The Random to generate the value.
	 * @return The selected value.
	 */
	private int select2048or1024(Random rand)
	{
		int newCellValue = 2048;
		if (rand.nextInt(10) == 0)//90% chance newCellValue will remain 2048
			newCellValue = 1024;
		return newCellValue;
	}
	
	/**
	 * Adds a new cell at the desired position, with the value of 2048 or 1024.
	 * @param board The board you want the cell added to.
	 * @param pos The position of the cell.
	 * @param rand A Random to get the value of the cell.
	 */
	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		board.setCell(pos, select2048or1024(rand));
		
	}

	/**
	 * Overrides the merge from GameRules.
	 * Merges 2 cells if they have the same value.
	 * @param self The first cell, where you want them to join.
	 * @param other The second cell.
	 * @return 0 if they couldn't be merged, 2048 / (the new cell value) if they could.
	 */
	@Override
	public int merge(Cell self, Cell other) {
		if (other.getValue()==self.getValue())
		{	
			self.setValue(self.getValue()/2);
			other.setValue(0);
			return 2048/self.getValue(); 
		}
		return 0;
	}

	/**
	 * Gets the minimum value of the board.
	 * @param board the board that it checks.
	 * @return The minimum value.
	 */
	@Override
	public int getWinValue(Board board) {
		return board.getMin();
	}

	/**
	 * Checks if a cell has reached 1.
	 * @param board the board that it checks.
	 * @return True if it finds a cell with 1, false otherwise.
	 */
	@Override
	public boolean win(Board board) {
		if(getWinValue(board)==1){
			return true;
		}
		return false;
	}
	
	

}
