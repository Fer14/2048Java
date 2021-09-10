package tp.pr2.control.commands;

import java.util.Random;
import java.util.Scanner;

import tp.pr2.control.exceptions.CommandParseException;
import tp.pr2.logic.multigames.Game;
import tp.pr2.logic.multigames.GameType;

/**
 * The PlayCommand class is the representation of the command play.
 * It changes the type of game you are playing.
 */

public class PlayCommand extends Command{
	private GameType gametype;
	private int size, initcells;
	private long seed;
	private Scanner sc;
	
	

	/**
	 * Reads a line from the user.
	 * @return The line read.
	 */
	private String[] read()
	{
		String input= sc.nextLine().trim();
		
        String input_array[]=input.split("\\s+");
		return input_array;
	}
	
	/**
	 * Constructor of this class.
	 * Calls the Command constructor and sets the attributes to null.
	 */
	public PlayCommand()
	{
		super("play <game>","start a new game of one of the game types: original, fib, inverse.");
		gametype = null;
		sc = null;
	}
	
	
	/**
	 * Reads the size from keyboard
	 * @return the size read.
	 */
	private int readSize()
	{
		int returnvalue = -1; 
		String str[];
		
		while (returnvalue < 2) 
		{
			System.out.print("Please enter the size of the board: ");
			str = read();
			if (str.length == 1)
				try {
					if (str[0].equals(""))
						returnvalue = 4;
					else 
						returnvalue = Integer.parseInt(str[0]);
				} catch (NumberFormatException e) {}
			if (returnvalue < 2)
				System.out.println("Error. You must enter a single positive integer greater than 1 or press enter for the default size.");
		}
		
		return returnvalue;
	}
	
	
	/**
	 * Reads from keyboard the number of initcells.
	 * @param size the size of the board
	 * @return the number of initcells read.
	 */
	private int readInitCells(int size)
	{
		int returnvalue = -1; 
		String str[];

		while ((returnvalue < 1) || (returnvalue > size*size))
		{
			System.out.print("Please enter the number of initial cells: ");
			str = read();
			if (str.length == 1)
				try {
					if (str[0].equals(""))
						returnvalue = 2;
					else 
						returnvalue = Integer.parseInt(str[0]);
				} catch (NumberFormatException e) {}
			if ((returnvalue < 1 ) || (returnvalue > size*size))
				System.out.println("Error. You must enter a single positive integer greater than 0 and less than the number of cells in the board, or"
						+ " press enter for the default number of inti cells.");
		}
		
		return returnvalue;
	}
	
	
	/**
	 * Reads from keyboard the seed.
	 * @return the seed read.
	 */
	private long readSeed()
	{
		long returnvalue = 0; 
		boolean ready = false;
		String str[];
		System.out.print("Please enter the seed for the pseudo-random number generator: ");
		str = read();
		
		while (!ready)
		{
			
			if (str.length == 1)
			{
				try {
					if (str[0].equals(""))
						returnvalue = (new Random()).nextInt(1000);
					else 
						returnvalue = Long.parseLong(str[0]);
					ready = true;
				} catch (NumberFormatException e) {}
			}
			if (!ready)
			{
				System.out.println("You must enter a single integer or press enter for a random seed.");
				System.out.print("Please enter the seed for the pseudo-random number generator: ");
				str = read();
			}
		}
		
		//Prints the selected seed if it was chosen randomly
		if(str[0].equals(""))
		{
			System.out.println("  Using the default seed for the pseudo-random number generator: " + returnvalue);
		}
	
		return returnvalue;
	}
	
	
	/**
	 * Overrides the execute method of Command class
	 * Check if the GameType is not null and set NoPrintGameState according to it
	 * Calls move method of game class if it is not null
	 * @param game The Game where the command will be executed.
     * @return true, so the new game is drawn.
	 */
	@Override
	public boolean execute(Game game) {
			game.deepReset(size,initcells,seed,gametype);
			return true;
		}
	
	/**
	 * Override the parse method of Command class and
	 * parses the command play if GameType has a valid value
	 * @param commandWords is used to call the parse of Command class
	 * @param sc Used to read info from the player.
	 * @throws CommandParseException an exception if the command can't be parsed.
	 * @return the command already parsed or null this isn't the right command.
	 */
	@Override
	public Command parse(String[] commandWords, Scanner sc) throws CommandParseException{
		if (commandWords.length > 0 && commandWords[0].toLowerCase().equals("play")) 
		{
			PlayCommand playcommand = new PlayCommand();
			this.sc = sc; 
			if (commandWords.length == 2)
			{
			
				playcommand.gametype = GameType.parse(commandWords[1].toLowerCase());
			
				if (playcommand.gametype == null)
					throw new CommandParseException(commandWords[1].toLowerCase() + " not recognised as a gametype. Try using original, inverse or fib.");
				System.out.println("*** You have chosen to play the game: " + playcommand.gametype + " ***");
				playcommand.size = readSize();
				playcommand.initcells = readInitCells(playcommand.size);
				playcommand.seed = readSeed();
			}
			else throw new CommandParseException("Error. You must enter a single gametype after play.");
			return playcommand;
			}
		return null;

	}
}
