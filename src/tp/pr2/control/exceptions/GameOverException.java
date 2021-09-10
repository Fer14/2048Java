package tp.pr2.control.exceptions;

import tp.pr2.util.MyStringUtils;

/**
 * The GameOverException is used to end the game.
 */
public class GameOverException extends Exception{
	private boolean win;
	
	/**
	 * The constructor for this exception.
	 * @param gameWon whether the game has ended because of a win or a loss.
	 */
	public GameOverException(boolean gameWon)
	{
		win = gameWon;
	}
	
	/**
	 * Transforms the message into a string, which differs depending on having won or not.
	 */
	@Override
	public String toString()
	{
		String str = "";
		if (win)
		{
			str += "Well done! You have completed the game!" + MyStringUtils.NEWLINE;
		}
		str += "Game Over. Thanks for playing!";
		return str;
	}
}
