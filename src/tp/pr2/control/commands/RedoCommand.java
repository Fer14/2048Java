package tp.pr2.control.commands;


import tp.pr2.control.exceptions.CommandExecuteException;
import tp.pr2.logic.multigames.Game;

/**
 * RedoCommand class is the representation of the redo command
 * redoes a single movement.
 */
public class RedoCommand extends NoParamsCommand{
	
	/**
	 * Constructor of this class
	 * Calls the NoParamsCommand constructor
	 */
	public RedoCommand()
	{
		super("redo","Redo last movement.");
	}

	
	/**
	 * Override the execute method of Command class
	 * Check if redo can be done.
	 * @param game The Game where the command will be executed.
	 * @throws CommandExecuteException an exception indicating you can't redo
	 * @return true, so the game is drawn.
	 */
	@Override
	public boolean execute(Game game) throws CommandExecuteException{
			game.redo();
			System.out.println("Redoing...");
			return true;
	}

}
