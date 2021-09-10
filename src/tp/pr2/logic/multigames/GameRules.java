package tp.pr2.logic.multigames;

import java.util.Random;

import tp.pr2.Board;
import tp.pr2.Cell;
import tp.pr2.Position;
import tp.pr2.util.ArrayAsList;

/**
 * The class GameRules defines a custom set of rules for a game similar to 2048,
 * how the games ends, the way cells merge, and how are cells created
 */
public interface GameRules {
	/**
	 * Adds a new cell at the desired position.
	 * @param board The board you want the cell added to.
	 * @param pos The position of the cell.
	 * @param rand A Random to get the value of the cell.
	 */
	void addNewCellAt(Board board, Position pos, Random rand);
	
	/**
	 * Tries to merge 2 cells.
	 * @param self The first cell, where you want them to join.
	 * @param other The second cell.
	 * @return 0 if they couldn't be merged, the score obtained if they could.
	 */
	int merge(Cell self, Cell other);
	
	/**
	 * Gets the best value of the board.
	 * @param board the board that it checks.
	 * @return The best value.
	 */
	int getWinValue(Board board);
	
	/**
	 * Checks if a cell has the winning value.
	 * @param board the board that it checks.
	 * @return True if it finds a cell with the winning value, false otherwise.
	 */
	boolean win(Board board);
	
	/**
	 * Checks if you have lost the game. This occurs when the board is full
	 * and you can't perform a move.
	 * @param board The board that it checks.
	 * @return Whether you have lost or not.
	 */
	default boolean lose(Board board) 
	{
		if (board.isFull())
			return board.cantMove(this);
		else return false;
	}
	
	/**
	 * Generates a new board, with the desired size.
	 * @param size The desired size.
	 * @return The new board.
	 */
	default Board createBoard(int size)
	{
		return new Board(size);
	}
	
	/**
	 * Adds a new cell in an empty position.
	 * @param board The board where you want the new cell.
	 * @param rand A Random number generator to get the cell value.
	 */
	default void addNewCell(Board board, Random rand)
	{
		ArrayAsList posArray = board.emptyCells();
		addNewCellAt(board,(Position) ArrayAsList.choice(posArray,rand),rand);
	}
	
	/**
	 * Initializes an empty board, inserting numCells random cells.
	 * @param board The board that will be initialized.
	 * @param numCells The number of cells that will appear.
	 * @param rand The Random number generator to get the positions and values of the initial cells.
	 */
	default void initBoard(Board board, int numCells, Random rand)
	{
		ArrayAsList posArray = board.emptyCells();
		int posIndex;
		for (int i = 0;  (i < numCells); i++)
		{
			posIndex = rand.nextInt(posArray.getCont());
			addNewCellAt(board,(Position) posArray.get(posIndex), rand);
			posArray.delete(posIndex);
		}
	}
	
	/**
	 * Checks if two values of a cell could be combined.
	 * @param a The first value.
	 * @param b The second value.
	 * @return True if they could be fused, false otherwise.
	 */
	default boolean combines(int a, int b)
	{
		return a == b;
	}

}
