package tp.pr2;

/**
 * The GameState class represents a saved state of the game, using an array an int array
 * as a board to save up memory.
 */
public class GameState {
	private int score;
	private int highest;
	private int boardState[][];
	
	/**
	 * The default constructor for this class
	 * @param dim The size of the original board
	 */
	public GameState(int dim)
	{
		score = 0;
		highest = 0;
		boardState = new int[dim][dim];
	}
	
	/**
	 * A constructor for this class, where you specify the size of the board,
	 * the current score ant the best cell.
	 * @param board Array with the values of the cells in a Board
	 * @param score The current score
	 * @param highest The best cell
	 */
	public GameState(int[][] board, int score, int highest)
	{
		boardState = board;
		this.score = score;
		this.highest = highest;
	}
	
	/**
	 * Gets the saved board state
	 * @return The int array representing the board
	 */
	public int[][] getState()
	{
		return boardState;
	}
	
	/**
	 * Gets the saved score
	 * @return The int representing the score
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * Gets the saved best cell
	 * @return The int representing the value of the best cell
	 */
	public int getHighest()
	{
		return highest;
	}
}
