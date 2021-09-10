package tp.pr2.control.commands;


import tp.pr2.logic.multigames.Game;

/**
 * The HelpCommand class draws the help message of every command
 */

public class HelpCommand extends NoParamsCommand {

	/**
	 * Constructor of this class
	 * Calls the NoParamsCommand constructor
	 */
	public HelpCommand()
	{
		super("help","Print this help message.");
	}

	/**
	 * Override the execute method of Command class
	 * Prints the helpText of each command by calling the CommandParser class commandHelp method
	 * @param game The Game where the command will be executed.
	 * @return Whether the game should be drawn. 
	 **/
	@Override
	public boolean execute(Game game) {
		System.out.print(CommandParser.commandHelp());
		return false; 
	}

}
