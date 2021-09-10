package tp.pr2.logic.multigames;

import java.util.Random;
import tp.pr2.Board;
import tp.pr2.Cell;
import tp.pr2.Position;
import tp.pr2.util.MyMathsUtil;

/**
 * The class Rules2048 holds the rules for the 2048 with the fibonacci sequence.
 */
public class RulesFib implements GameRules{

	/**
	 * Chooses the value of the cell that will be inserted, 1 with a 90% chance
	 * and 2 with a 10% chance.
	 * @param rand The Random to generate the value.
	 * @return The selected value.
	 */
	private int select1or2(Random rand)
	{
		int newCellValue = 1;
		if (rand.nextInt(10) == 0)//90% chance newCellValue will remain 1
			newCellValue = 2;
		return newCellValue;
	}
	
	/**
	 * Adds a new cell at the desired position, with the value of 1 or 2.
	 * @param board The board you want the cell added to.
	 * @param pos The position of the cell.
	 * @param rand A Random to get the value of the cell.
	 */
	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		board.setCell(pos, select1or2(rand));
	}

	/**
	 * Overrides the merge from GameRules.
	 * Merges 2 cells if one is the next number of the fibonacci sequence than the other.
	 * @param self The first cell, where you want them to join.
	 * @param other The second cell.
	 * @return 0 if they couldn't be merged, the sum of both cells if they could.
	 */
	@Override
	public int merge(Cell self, Cell other) {
		
		//Necessary because for nextFib the next fib to 1 has to be 2. 
		if(other.getValue()==1 && self.getValue()==1){
			self.setValue(2);
			other.setValue(0);
			return self.getValue();
		}
		
		else if (other.getValue()== MyMathsUtil.nextFib(self.getValue()))
		{	
			self.setValue(MyMathsUtil.nextFib(other.getValue()));
			other.setValue(0);
			return self.getValue();
		}
		else if (self.getValue()== MyMathsUtil.nextFib(other.getValue()))
		{
			self.setValue(MyMathsUtil.nextFib(self.getValue()));
			other.setValue(0);
			return self.getValue();
		}
		
		return 0;
	
	}

	/**
	 * Gets the maximum value of the board.
	 * @param board the board that it checks.
	 * @return The maximum value.
	 */
	@Override
	public int getWinValue(Board board) {
		return board.getMax();
	}

	/**
	 * Checks if a cell has reached 144.
	 * @param board the board that it checks.
	 * @return True if it finds a cell with 144, false otherwise.
	 */
	@Override
	public boolean win(Board board) {
		if(getWinValue(board)==144){
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the value of a cell is the next number of the fibonacci sequence to the other.
	 * @param a The first value.
	 * @param b The second value.
	 * @return True if they could be fused, false otherwise.
	 */
	@Override
	public boolean combines(int a, int b)
	{
		return (((MyMathsUtil.nextFib(a) == b) || (a == MyMathsUtil.nextFib(b))) || ((a == 1) && (b == 1)));
	}

}
