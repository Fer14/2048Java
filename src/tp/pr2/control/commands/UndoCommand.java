package tp.pr2.control.commands;


import tp.pr2.control.exceptions.CommandExecuteException;
import tp.pr2.logic.multigames.Game;

/**
 * UndoCommand class is the representation of the undo command and
 * it undoes a the game state. 
 */
public class UndoCommand extends NoParamsCommand{
	
	/**
	 * Constructor of this class

	 * Calls the NoParamsCommand constructor
	 */
	public UndoCommand()
	{
		super("undo","Undo last movement.");
	}

	/**
	 * Override the execute method of Command class
	 * Check if undo can be done
	 * @throws CommandExecuteException The exception indicating undoing went wrong.
	 * @param game The Game where the command will be executed.
	 * @return Whether the game should be drawn.  
	 */
	@Override
	public boolean execute(Game game) throws CommandExecuteException{
		game.undo();
		System.out.println("Undoing...");
		return true;
	}
}
