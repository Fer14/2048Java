package tp.pr2.control.commands;

import java.util.Scanner;

import tp.pr2.control.exceptions.CommandExecuteException;
import tp.pr2.control.exceptions.CommandParseException;
import tp.pr2.control.exceptions.GameOverException;
import tp.pr2.logic.multigames.Game;

/**
 * The abstract class that contains every command, along with its info and what it does.
 */
public abstract class Command {
	
	private	String helpText;
	private	String commandText;
	protected String commandName;
	
	/** Constructor for this class.
	 * Initializes the commandText, helpText and commandName attributes.
	 * commandName gets the value of the name of the command
	 * @param commandInfo the initial value for commandText
	 * @param helpInfo the initial value for helpText
	 * 
	 */
	public Command(String commandInfo, String helpInfo)
	{
		commandText = commandInfo;
		helpText = helpInfo;
		String[]  commandInfoWords = commandText.split("\\s+");
		commandName = commandInfoWords[0].toLowerCase();
	}
	
	/**
	 * Execute the game according to the command read by the controller
	 * @param game The Game where the command will be executed.
	 * @throws CommandExecuteException An exception explaining the incorrect performance of execute.
	 * @throws GameOverException The exception that could be launched by a command to immediately end the game. 
	 * @return Whether the game should be drawn.  
	 */
	public abstract boolean execute(Game game) throws CommandExecuteException, GameOverException;
	
	/**
	 * Parses the command read by the controller
	 * @param commandWords is used to call the parse of Command class
	 * @param sc used to get input from the user. Not used here.
	 * @throws CommandParseException an exception if something went wrong while parsing.
	 * @return the command already parsed or null if it couldn't be parsed.
	 */
	public abstract Command parse(String[] commandWords, Scanner sc) throws CommandParseException;
	
	/**
	 * @return the helpText related to this command
	 */
	public String helpText(){
		return "  " + commandText +	": " + helpText;
	}
}
