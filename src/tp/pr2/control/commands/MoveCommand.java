package tp.pr2.control.commands;



import java.util.Scanner;

import tp.pr2.Direction;
import tp.pr2.control.exceptions.CommandExecuteException;
import tp.pr2.control.exceptions.CommandParseException;
import tp.pr2.logic.multigames.Game;

/**
 * MoveCommand class is the representation of the command move and it extends Command
 */
public class MoveCommand extends Command {
		
	private Direction direction;

	/**
	 * Constructor of this class
	 * 
	 * Calls the Command constructor
	 */
	public MoveCommand()
	{
		super("move <direction>","Move in the desired direction (up, left, right, down).");
		direction = null;
	}
	
	/**
	 * Overrides the execute method of Command class
	 * Check if the direction is not null and set NoPrintGameState according to it
	 * Calls move method of game class if it is not null
	 * @param game The Game where the command will be executed.
	 * @throws CommandExecuteException An exception indicating you can't move that way.
	 * @return true, to draw the game if it could be moved.
	 */
	@Override
	public boolean execute(Game game) throws CommandExecuteException{
		if (!game.move(direction))
			throw new CommandExecuteException("Can't move " + direction);
		return true;
	}
	/**
	 * Override the parse method of Command class and
	 * Parses the command move if direction has a valid value
	 * @param commandWords is used to call the parse of Command class
	 * @param sc The scanner in order to get data from the player. Not used here	 * @return the command already parsed or null if it couldn't be parsed.
	 * @throws CommandParseException An exception if the direction isn't valid. 
	 * @return null if it isn't the valid command, or the command parsed.
	 */
	@Override
	public Command parse(String[] commandWords, Scanner sc) throws CommandParseException{
		if (commandWords.length > 0 && commandWords[0].toLowerCase().equals("move")) //&&
		{
			MoveCommand mc = new MoveCommand();
			if (commandWords.length == 2)
			{
				mc.direction = Direction.string2Dir(commandWords[1]);
			}
			else throw new CommandParseException("Error. Please introduce 'up', 'down', 'left, or 'right' after move");
			return mc;
		}
		return null;
	}
}
