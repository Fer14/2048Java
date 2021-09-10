package tp.pr2;

import tp.pr2.control.exceptions.CommandParseException;

/**
 * The class Direction represents the 4 possible directions you can move a game of 2048.
 */
public enum Direction {
	UP,	DOWN, LEFT, RIGHT;
	
	/**
	 * Returns the corresponding Direction to the string.
	 * Expects a correct string. This should be checked with isDirection(String str).
	 * @throws CommandParseException the exception indicating you have entered a wrong direction.
	 * @param str The string you want transformed.
	 * @return The corresponding direction.
	 */
	public static Direction string2Dir (String str) throws CommandParseException
	{
		Direction dir;
		switch (str)
		{
		case "up":
			dir = UP;
			break;
		case "down":
			dir = DOWN;
			break;
		case "right":
			dir = RIGHT;
			break;
		case "left":
			dir = LEFT;
			break;
		default:
			throw new CommandParseException(str + " is not a valid direction, use 'up', 'down', 'left' or 'right'.");
		}
		return dir;
	}
	
	
}
