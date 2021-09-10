package tp.pr2.control.commands;

import java.util.Scanner;

import tp.pr2.control.exceptions.CommandExecuteException;
import tp.pr2.control.exceptions.CommandParseException;
import tp.pr2.logic.multigames.Game;
import tp.pr2.util.MyStringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * The SaveCommand class saves a current game to the chosen file.
 */
public class SaveCommand extends Command{
	private String filename;
	private boolean filename_confirmed;
	public static final String filenameInUseMsg = "The file already exists; do you want to overwrite it ? (Y/N)";

	/**
	 * The default constructor for this class.
	 */
	public SaveCommand()
	{
		
		super("save <filename>","Save the game in the desired file.");
		filename = null;
		filename_confirmed=false;
	}
	
	/**
	 * Overrides the execute method of Command class
	 * Saves the game to a file.
	 * @param game The Game where the command will be executed.
	 * @throws CommandExecuteException an exception if the saving went wrong.
	 * @return true, if there aren't any exceptions, to draw the game.
	 */
	@Override
	public boolean execute(Game game) throws CommandExecuteException{
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
		{
			writer.write("This file stores a saved 2048 game" + MyStringUtils.NEWLINE + MyStringUtils.NEWLINE);
			game.store(writer);
			System.out.println("Game successfully saved to file " + filename + "; use load command to reload it.");
			return false;
		}
		catch (IOException e) 
		{
			throw new CommandExecuteException("Unknown error saving the file.");			
		}
		
	}

	/**
	 * Override the parse method of Command class and
	 * Parses the command save if the file name provided is valid.
	 * @param commandWords the introduce words.
	 * @param sc The scanner used to read from the user.
	 * @return the command already parsed, null if this isn't the right command.
	 * @throws CommandParseException an exception indicating an error in parsing.
	 */
	@Override
	public Command parse(String[] commandWords, Scanner sc) throws CommandParseException{
		if (commandWords.length > 0 && commandWords[0].toLowerCase().equals("save")) 
		{
			SaveCommand s = new SaveCommand();
			if (commandWords.length == 2)
			{	
				s.filename = confirmFileNameStringForWrite(commandWords[1],sc);
			}
			else if (commandWords.length > 2)
				throw new CommandParseException("Invalid filename: the filename contains spaces.");
			else 
				throw new CommandParseException("Save must be followed by a filename.");
			return s;
		}
		return null;
	}


	/**
	 * Checks if the file name is valid, and if the file exists. if it exists, it asks the user if he wants to
	 * overwrite or asks for another file name.
	 * @param filenameString the file name given by the user.
	 * @param in the scanner to read input from the user
	 * @return the finally chosen file name.
	 * @throws CommandParseException An exception if there was an error with the file name.
	 */
	private String confirmFileNameStringForWrite(String filenameString, Scanner in) throws CommandParseException{
		String loadName = filenameString;
		filename_confirmed = false;
		while (!filename_confirmed) 
		{
			if (MyStringUtils.validFileName(loadName))
			{
				File file = new File(loadName);
				if (!file.exists())
					filename_confirmed = true;
				else 
				{
					loadName = getSaveName(filenameString, in);
					if (file.getName().equals(loadName)) 
						filename_confirmed = true;
				}
			} 
			else 
			{
				throw new CommandParseException("Invalid filename: the filename contains invalid characters.");
			}
		}
		return loadName;
	}	

	/**
	 * Asks if the user wants to overwrite the existing file o for another
	 * name. In the second case it tries to confirm it.
	 * @param filenameString The file name already in use.
	 * @param in the scanner to get input from the user.
	 * @return the confirmed file name.
	 * @throws CommandParseException An exception if there was an error with the file name.
	 */
	public String getSaveName(String filenameString, Scanner in) throws CommandParseException{
		String newFilename = null;
		boolean yesOrNo = false;
		while (!yesOrNo) {
			System.out.print(filenameInUseMsg + ": ");
			String[] responseYorN = in.nextLine().toLowerCase().trim().split("\\s+");
			if (responseYorN.length == 1) {
				switch (responseYorN[0]) {
					case "y":
						yesOrNo = true;
						newFilename =filenameString;
						break;
					case "n":
						yesOrNo = true;
						System.out.print("Please enter another filename:");
						String[] aux = in.nextLine().trim().split("\\s+");
						if (aux.length == 1)
							newFilename = confirmFileNameStringForWrite(aux[0], in);
						else
							throw new CommandParseException("Invalid filename: the filename contains spaces.");
						break;
					default:
						System.out.println("Please answer 'Y' or 'N'");
						break;
				}
			} else {
				System.out.println("Please answer 'Y' or 'N'");
			}
		}
		return newFilename;
	}

}


