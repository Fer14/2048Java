package tp.pr2.util;



import java.util.Random;

/**
 * ArrasyAsList class contains an array of objects
 */
public class ArrayAsList {
	private int cont;
	private Object arrayAsList[];
	
	
	/**
	 * Adds one object in the last position
	 * @param obj goes to the last position of the array
	 */
	public void add(Object obj)
	{
		arrayAsList[cont] = obj;
		cont++;
	}
	
	/**
	 * Delete the object of the indicated position
	 * @param index is the position where the object is going to be deleted
	 */
	public void delete(int index)
	{
		for (int i = index; i < cont - 1; i++)
			arrayAsList[i] = arrayAsList[i+1];
		cont--;
	}
	
	/**
	 * Get the object of the indicated position
	 * @param index is the position of the object is going to be returned
	 * @return the object of the position index
	 */
	public Object get(int index){
		return arrayAsList[index];
		}
	/**
	 * Get the counter of the array
	 * @return the counter of the array
	 */
	public int getCont() { 
		return cont;
	}
	
	/**
	 * Constructor of the class
	 * @param dim is the dimension of the array
	 * the counter start being 0
	 */
	public ArrayAsList(int dim)
	{
		arrayAsList = new Object[dim];
		cont = 0;
	}
	/**
	 * Gets the size of the array
	 * @return the counter of the array
	 */
	public int size()
	{
		return cont;
	}
	
	/**
	 * Disorder the array 
	 * @param list is the array it is wanted to be disordered
	 * @param random is used to calculate a random number
	 */
	public static void shuffle(ArrayAsList list, Random random) {
		for (int i = list.size(); i > 1; i--) {
			swap(list.arrayAsList, i - 1, random.nextInt(i));
		}
		
	}
	/**
	 * This method is static  in order to be similar  to the "shuffle ()" method.
	 * @param list is the array in which we are going to get the object
	 * @param random is used to calculate a random number
	 * @return the object got 
	 */
	public static Object choice(ArrayAsList list, Random random) {
		return list.get(random.nextInt(list.size()));
	}
	
	/**
	 * Swaps the value of the object in the i position and the object in the j position
	 * @param anArray is the array where the swap is going to happen
	 * @param i is the position whose object is going to be change for the object in j position
	 * @param j is the position whose object is going to be change for the object in i position
	 */
	private static void	swap(Object[] anArray, int i, int j) {
	Object temp = anArray[i];
	anArray[i] = anArray[j];
	anArray[j] = temp;
	}
}
