package tp.pr2.control.commands;

import tp.pr2.control.exceptions.GameOverException;
import tp.pr2.logic.multigames.Game;

/**
 * ExitCommand class inmmediatly ends the game.
 */
public class ExitCommand extends NoParamsCommand {
	/**
	 * Constructor of this class.
	 * Calls the NoParamsCommand constructor
	 */
	public ExitCommand()
	{
		super("exit","Terminate the program.");
		
	}
	
	/**
	 * Overrides the execute method of Command class
	 * Exits the game.
	 * @param game The Game where the command will be executed.
	 * @throws GameOverException an exception to finish the game.
	 * @return false, so the game is not drawn
	 */
	@Override
	public boolean execute(Game game) throws GameOverException {
		game.exit();	
		//It never reaches beyond this point, exit has already thrown an exception.
		return false;
	}
}
