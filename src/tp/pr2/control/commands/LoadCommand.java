package tp.pr2.control.commands;
import java.util.Scanner;

import tp.pr2.control.exceptions.CommandExecuteException;
import tp.pr2.control.exceptions.CommandParseException;
import tp.pr2.logic.multigames.Game;
import tp.pr2.util.MyStringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * The LoadCommand class tries to load a saved game from a file.
 */
public class LoadCommand extends Command{
	
	private String filename;
		/**
	 *  The default constructor for the LoadCommand class.
	 */
	public LoadCommand()
	{
		super("load <filename>","Load the game of the desired file.");
		filename = null;
	}
	
	
	
	/**
	 * Overrides the execute method of Command class
	 * Loads the game from a file.
	 * @param game The Game where the command will be executed.
	 * @throws CommandExecuteException an exception if there was an error reading the file.
	 * @return true, if there aren't any exceptions, to draw the game.
	 */
	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
		{
			game.load(reader);
			System.out.println("Game successfully loaded from file " + filename + ": "+ game.getGameType());
			return true;
		}
		catch(IOException e)
		{
			throw new CommandExecuteException("Error reading the file.");
		}
		
	}
	
	/**
	 * Override the parse method of Command class and
	 * Parses the command load if the file name provided is valid.
	 * @param commandWords the introduce words.
	 * @param sc The scanner used to read from the user. Not used here.
	 * @return the command already parsed, null if this isn't the right command.
	 * @throws CommandParseException an exception indicating an error in parsing.
	 */
	@Override
	public Command parse(String[] commandWords, Scanner sc) throws CommandParseException{
		if (commandWords.length > 0 && commandWords[0].toLowerCase().equals("load")) 
		{
			LoadCommand s = new LoadCommand();
			if (commandWords.length == 2)
			{	
				String fileString = commandWords[1];
				if (MyStringUtils.validFileName(fileString))
				{
					File file = new File(fileString);
					if (file.exists())
					{
						s.filename = fileString;
					}
					else throw new CommandParseException("File not found!");
				}
				else throw new CommandParseException("Invalid filename: the filename contains invalid characters.");
			}
			else if (commandWords.length > 2)
				throw new CommandParseException("Invalid filename: the filename contains spaces.");
			else 
				throw new CommandParseException("Save must be followed by a filename");
			return s;
		}
		return null;
	}
	
}