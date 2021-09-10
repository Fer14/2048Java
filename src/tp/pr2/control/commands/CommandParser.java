package tp.pr2.control.commands;

import java.util.Scanner;

import tp.pr2.control.exceptions.CommandParseException;
import tp.pr2.util.MyStringUtils;
/**
 * The CommandParser class is used to parse the commands read by controller.
 */
public class CommandParser{
	private static Command[] availableCommands = {new MoveCommand(),new UndoCommand(), new RedoCommand(), new HelpCommand(),new ResetCommand(),
					new PlayCommand(), new ExitCommand(), new SaveCommand(), new LoadCommand()}; 

/**
 * Calls the parse of Command Class if the command read by the controller is one of the availableCommands
 * 
 * @param commandWords is used to call the parse of Command class
 * @param sc Used to get input from the player.
 * @throws CommandParseException an exception if the command couldn't be correctly parsed.
 * @return the command already parsed.
 */
	public static Command parseCommand(String[] commandWords, Scanner sc) throws CommandParseException
	{
		Command command;
		for (Command com: availableCommands)
		{
			command = com.parse(commandWords, sc);
			if (command != null) return command;
		}
		throw new CommandParseException("Command not recognised, write help to see the available commands.");
	}
	
	/**
	 * Goes through the availableCommands array and gets the helpText for each one of them.
	 * 
	 * @return the helpText for every command in the availableCommands
	 */
	
	public static String commandHelp()
	{
		String str = "";
		for (Command com: availableCommands)
		{
			str += com.helpText();
			str += MyStringUtils.NEWLINE;
		}
		return str;
	}

}
