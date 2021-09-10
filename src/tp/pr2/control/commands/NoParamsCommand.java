package tp.pr2.control.commands;


import java.util.Scanner;


/**
 * NoParamsCommand class represents the command that have no parameters.
 */
public abstract class NoParamsCommand extends Command {
	/**
	 * Constructor of this class
	 * 
	 * @param commandInfo is used to call the constructor of Command class
	 * @param helpInfo is used to call the constructor of Command class
	 */
	public NoParamsCommand(String commandInfo, String helpInfo)
	{
		super(commandInfo, helpInfo);
	}
	/**
	 * Override the parse method of Command class and
	 * parses the command with no parameters
	 * @param commandWords The words introduced by the user
	 * @param sc The scanner, not used here.
	 */
	@Override
	public Command parse(String[] commandWords, Scanner sc) {
		if (commandWords.length == 1 && commandWords[0].equalsIgnoreCase(commandName))
				return this;
		return null;
	}
	
	
}
