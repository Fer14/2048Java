package tp.pr2.control.commands;


import tp.pr2.control.exceptions.GameOverException;
import tp.pr2.logic.multigames.Game;

/**
 * ResetCommand class is the representation of the reset command and it extends NoParamsCommand 
 */
public class ResetCommand extends NoParamsCommand {
	/**
	 * Overrides the execute method of Command class
	 * Calls the reset method of Game class and resets the game. 
	 * @param game The Game where the command will be executed.
	 * @throws GameOverException the exception indicating the game ended.
	 * @return true, in order to draw the game.  
	 */
	@Override
	public boolean execute(Game game) throws GameOverException{
		game.reset();
		return true;
	}
	
	/**
	 * Constructor of this class
	 * Calls the NoParamsCommand constructor
	 */
	public ResetCommand()
	{
		super("reset", "Start a new game.");
	}
}
