package tp.pr2;

import java.util.Random;
import java.util.Scanner;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;
import tp.pr2.logic.multigames.GameType;

/**
 * This is the main class. 
 * Creates the controller and the game, and process to call c.run(), which starts receiving
 * commands to play the game
 * 
 * @author Miguel Angel Castillo Moreno 
 * @author Fernando Cortés Sancho
 *
 */
public class Game2048 {

	/**
	 * 
	 * @param args It should receive 2 arguments or 3,
	 * and it uses the first one to create a board of the integer size,
	 * the second one to insert that number of cells,
	 * and the possible third one to establish the seed of the Random object.
	 */
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		try
		{
		long seed;
		if (args.length == 2) 
			seed = new Random().nextInt(1000); // Only if there are 2 arguments
		else 
			seed = Long.parseLong(args[2]); // If there are 3
		
		if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[1]) < 1)
		{
			in.close();
			throw new Exception(); 
		}
			
	
		Game game = new Game(GameType.ORIG, seed, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		Controller c = new Controller(game, in);
		c.run();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("There are less arguments than expected.");
		}
		catch (NumberFormatException e) //Initial arguments are not numbers
		{
			System.out.println("The arguments must be numeric.");
		}
		catch (IllegalArgumentException e) //thrown if initialcells > size * size while generating the board
		{
			System.out.println("The numbers of init cells must me less than the number of cells on the board.");
		}
		catch (Exception e)
		{
			System.out.println("The size and initcells must be greater than 0");
		}
}
	
	

}
