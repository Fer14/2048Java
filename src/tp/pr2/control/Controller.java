package tp.pr2.control;
import java.util.Scanner;

import tp.pr2.control.commands.Command;
import tp.pr2.control.commands.CommandParser;
import tp.pr2.control.exceptions.CommandExecuteException;
import tp.pr2.control.exceptions.CommandParseException;
import tp.pr2.control.exceptions.GameOverException;
import tp.pr2.logic.multigames.Game;
import tp.pr2.util.MyStringUtils;

/**
 * The Controller class reads from keyboard and execute the game
 *
 */


public class Controller {
	private Game game;
	private Scanner in;
	
	/** Constructor for this class.
	 * Initializes the game,the scanner and the boolean noPrintGameState attributes
	 * 
	 * @param game the initial value for game
	 * @param sc the initial value for in
	 */
	public Controller(Game game, Scanner sc){
		this.game = game;
		in = sc;
	}
	
	/**
	 * Prints the parameter before doing reading the input.
	 * If you want nothing written, call it with ""
	 * @param beforeSc The words to that it writes before asking for input.
	 * @return a string array read with the scanner
	*/
	private String[] read()
	{
		System.out.print(MyStringUtils.NEWLINE + "Command > ");
		String input= in.nextLine().trim();
		
        String input_array[]=input.split("\\s+");
		return input_array;
	}

	/**
	 * Runs the game.
	*/
	public void run()
	{
		System.out.println(game);
		Command command;
		
		try {	
			String input[]; 
			game.hasEnded();
			while (true){//Exits when a gameOver exception is thrown
				input = read();
				try
				{
					command = CommandParser.parseCommand(input, in);
					if (command.execute(game))
						System.out.println(game);	
					game.hasEnded();
				}
				catch (CommandParseException e)
				{
					System.out.println(e);
				}
				catch (CommandExecuteException e)
				{
					System.out.println(e);
				}
			}
		}
		catch(GameOverException g) {
			System.out.println(g.toString());
		}
	}
	

	
}
