package tp.pr2.control.exceptions;

/**
 * An exception produced in the execute of a command.
 */
public class CommandExecuteException extends Exception{

	/**
	 * The constructor of this exception, with the chosen message to show.
	 * @param message The message that explains the error.
	 */
	public CommandExecuteException(String message)
	{
		super(message);
	}
	
	/**
	 * Transforms the message into the desired form of string.
	 */
	@Override
	public String toString()
	{
		return this.getMessage(); 
	}
}
