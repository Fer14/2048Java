package tp.pr2.logic.multigames;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

import tp.pr2.Board;
import tp.pr2.Direction;
import tp.pr2.GameState;
import tp.pr2.MoveResults;
import tp.pr2.StateArray;
import tp.pr2.control.exceptions.CommandExecuteException;
import tp.pr2.control.exceptions.GameOverException;
import tp.pr2.util.MyStringUtils;
/**
 * The class Game holds a board, the punctuation, the previous states, the rules of the game...
 * It performs the actions selected by the player. 
 */
public class Game {
	private Board _board;
	private int score;
	private int _size;
	private int _initcells;
	private int bestValue;
	private StateArray gameStates;
	private Random _myRandom;
	private GameRules gr;
	private GameType gameType;
	
	/**
	 * Constructor for the class Game, initializes all attributes and the board,
	 * and saves the initial state of the board.
	 * @param gt The game type indicating the current 2048 rules.
	 * @param seed The seed for the Random number generator.
	 * @param size The size of the board.
	 * @param initcells The number of cells you will begin with.
	 */
	public Game(GameType gt, long seed, int size,int initcells){
		this.gameType = gt;
		gr = gt.getRules();
		_board = gr.createBoard(size);
		_myRandom = new Random(seed);
		_size = size;
		_initcells = initcells;
		score = 0;
		gameStates = new StateArray();
		
		gr.initBoard(_board, _initcells, _myRandom);
		bestValue = gr.getWinValue(_board);
		gameStates.push(getState());
	}
	
	/**
	 * Undoes the last movement, if possible.
	 * @throws CommandExecuteException the exception indicating it can't undo.
	 */
	public void undo() throws CommandExecuteException
	{
		setState(gameStates.pop());
	}
	
	/**
	 * Undoes the last undo, if possible.
	 * @throws CommandExecuteException the exception indicating it can't redo.
	 */
	public void redo() throws CommandExecuteException
	{
		
		setState(gameStates.getFromRedo());
	}

	/**
	 * Reinitializes all attributes, without needing to create another instance of Game.
	 * The board is only recreated if the size has been changed.
	 * @param gt The gameType with the rules of the current 2048.
	 * @param seed The seed for the Random number generator.
	 * @param size The size of the board.
	 * @param initcells The number of cells you will begin with.
	 */
	public void deepReset(int size,int initcells, long seed, GameType gt)
	{
		gameType = gt;
		gr = gt.getRules();
		if (_size != size)
			_board = gr.createBoard(size);
		else
			_board.clear();
		_size = size;
		_myRandom = new Random(seed);
		_initcells = initcells;
		score = 0;
		gameStates.emptyArray();

		gr.initBoard(_board, _initcells, _myRandom);
		bestValue = gr.getWinValue(_board);
		gameStates.push(getState());
	
	}
	
	/**
	 * Prepares board for a new game, with the original attributes, nor the board.
	 */
	public void reset() {
		score = 0;
		_board.clear();
		gameStates.emptyArray();
		gr.initBoard(_board, _initcells, _myRandom);
		bestValue = gr.getWinValue(_board);
		gameStates.push(getState());
		
	}
	

	/**
	 * Performs a move to the board, keeping track of the punctuation.
	 * @param dir The desired direction for the move.
	 * @return Whether the move could be done.
	 */
	public boolean move(Direction dir) //throws GameOverException
	{
		MoveResults move = _board.executeMove(dir,gr);
		if (move.hasMoved())
		{
			score += move.getScore();
			bestValue = gr.getWinValue(_board);
			gr.addNewCell(_board, _myRandom);
			gameStates.push(getState());
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a string with the highest value and the current score.
	 * @return The string with the data.
	 */
	private String showScore()
	{
		String str = "Best value: " + bestValue + "\t\tScore: " + score;
		return str;
	}
	
	/**
	 * Returns the toString() of board plus the highest value and score.
	 */
	public String toString()
	{
		return _board.toString() + showScore();
	}

	/**
	 * Immediately ends the game.
	 * @throws GameOverException The exception that ends the game immediately.
	 */
	public void exit() throws GameOverException
	{
		throw new GameOverException(false);
	}
	
	/**
	 * Checks the state of the gameEnded attribute.
	 * @throws GameOverException the exception indicating the game has ended, and why.
	 */
	public void hasEnded() throws GameOverException{
		if (gr.win(_board))
		{
			throw new GameOverException(true);
		}
		else if (gr.lose(_board))
			throw new GameOverException(false);
			
	}
	
	/**
	 * Returns the current state of the game.
	 * @return gameState with the current state.
	 */
	public GameState getState()
	{
		GameState gs = new GameState(_board.getState(),score,bestValue);
		return gs;
	}
	
	/**
	 * Changes the game to a chosen state.
	 * @param aState The state you want the game changed to.
	 */
	public void setState(GameState aState)
	{
		_board.setState(aState.getState());
		score = aState.getScore();
		bestValue = aState.getHighest();
	}
	
	/**
	 * Stores a game state into a file.
	 * @param writer the file where you write.
	 * @throws IOException an exception thrown if something goes wrong while storing the game.
	 */
	public void store(BufferedWriter writer) throws IOException
	{
		
		_board.store(writer);
		writer.write(MyStringUtils.NEWLINE);
		writer.write(_initcells + "\t" + score + "\t" + gameType.externalise());
	}
	
	/**
	 * Loads a game from a file.
	 * @param reader the file you read from.
	 * @throws IOException an exception thrown if something goes wrong while loading the game.
	 * @throws CommandExecuteException an exception thrown if the format of the file isn't as excepted.
	 */
	public void load(BufferedReader reader) throws IOException, CommandExecuteException
	{
		String separador="\t";
		
		try {

			if (!reader.readLine().equals("This file stores a saved 2048 game"))
				throw new CommandExecuteException("");//Doesn't need to send a message: it will be overwritten.
			if (!reader.readLine().equals(""))
				throw new CommandExecuteException("");
			Board newBoard = new Board(1); //The size doesn't matter, the load will overwrite any created cells
			newBoard.load(reader);
		
			if (!reader.readLine().equals(""))
				throw new CommandExecuteException("");
			String[] partes = reader.readLine().trim().split(separador);
		
			String gametype = partes[2];	
			GameType nuevoGT = GameType.parse(gametype);
			if (nuevoGT == null)
				throw new CommandExecuteException("");
		
			int newInitCells = Integer.parseInt(partes[0]);
			int newSize = newBoard.getDim();
			if (newInitCells > newSize * newSize || newInitCells < 1)
				throw new CommandExecuteException("");
		
			//The order is calculated in order to avoid having to come back to the previous state.
			score= Integer.parseInt(partes[1]);
			gameType = nuevoGT;
			gr = gameType.getRules();
			_initcells= newInitCells;
			_size = newSize;
			_board = newBoard;
			bestValue = gr.getWinValue(_board);
			gameStates.emptyArray();
			gameStates.push(getState());
		}
		catch (Exception e) //Deals with any exception that might happen during the reading
		{
			throw new CommandExecuteException("Load failed: invalid file format.");
		}
		
	}	
	
	/**
	 * Return the name of the game.
	 * @return the name of the game.
	 */
	public String getGameType() {
		return gameType.toString();
	}
	

}
