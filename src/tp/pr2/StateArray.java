package tp.pr2;

import tp.pr2.control.exceptions.CommandExecuteException;

/**
 * The StateArray class saves a single cyclic list of GameStates, in order to keep track of previous
 * states that you will be able to undo or redo.
 *
 */
public class StateArray {
	/**
	 * Capacity is the number of states that will be saved - 2
	 * It need 2 extra slots because it is a circular array and to keep track of the actual state
	 * plus the (capacity - 2) previous ones.
	 */
	private static final int CAPACITY = 22;
	private GameState[] stateArray;
	private int rear; 
	private int currentIndex; 
	private int front; 
	
	/**
	 * Default constructor for the class
	 */
	public StateArray()
	{
		stateArray = new GameState[CAPACITY];
		emptyArray();
	}
	
	/**
	 * Extracts the previous state and updates the index accordingly (undoes).
	 * @throws CommandExecuteException the exception indicating there are no states to undo 
	 * @return The extracted state.
	 */
	public GameState pop() throws CommandExecuteException{ 
		if (hasMoreThanOne())//No se utiliza emptyStackException, por la forma de implementar esto
		{
			currentIndex = previousIndex(currentIndex);
			return stateArray[previousIndex(currentIndex)];
		}
		else throw new CommandExecuteException("Can't undo.");
	}
	
	/**
	 * Introduces a new state at the end of the list and changes the indexes accordingly.
	 * @param gs The new state you want to store.
	 */
	public void push(GameState gs)
	{
		int nextIndex = nextIndex(currentIndex);
		stateArray[currentIndex] = gs;
		currentIndex = nextIndex;
		if (nextIndex == rear)
			rear = nextIndex(rear);
		front = currentIndex;
	}
	
	/**
	 * Undoes the last pop, assuming there was one.
	 * canRedo() should be used before this.
	 * @throws CommandExecuteException throws the exception if it can't redo.
	 * @return The State obtained from redoing
	 */
	public GameState getFromRedo() throws CommandExecuteException
	{
		if (canRedo())
		{
			currentIndex = nextIndex(currentIndex);
			return stateArray[previousIndex(currentIndex)];
		}
		else throw new CommandExecuteException("Can't redo.");
	}
	
	/**
	 * Gets the next index. It's only necessary because the list is cyclic.
	 * @param current_index The index you want the next from.
	 * @return The value of the new index.
	 */
	private int nextIndex(int current_index) {
		if (current_index == CAPACITY - 1)
			return 0;
		else return current_index + 1;
	}
	
	/**
	 * Gets the previous index. It's only necessary because the list is cyclic.
	 * @param current_index The index you want the previous from.
	 * @return The value of the new index.
	 */
	private int previousIndex(int current_index) {
		if (current_index == 0)
			return CAPACITY - 1;
		else return current_index - 1;
	}
	
	/** Checks if the array has more than one state.
	 * @return True if the array is has more that one. False otherwise.
	 */
	private boolean hasMoreThanOne()
	{
		return (currentIndex != rear) && (currentIndex != nextIndex(rear));
	}
	
	/**
	 * Checks if you have done a pop previously.
	 * @return True if you can redo an undo, false otherwise.
	 */
	private boolean canRedo() {
		return (currentIndex != front);
	}
	
	/**
	 * Empties the array, updating the indexes.
	 */
	public void emptyArray()
	{
		rear = 0;
		front = 0;
		currentIndex = 0;
	}
}
