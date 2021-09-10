package tp.pr2.util;

/**
 * 
 * MyMathsUtil class calculates the next fibonaci number
 */
public class MyMathsUtil {
	/**
	 * Calculates the next fibonnacci number from the number given
	 * @param previous is the previous fibonacci number of the one we want
	 * @return the next fibonacci number from the number given
	 */
	public static int nextFib(int previous) {
		double phi = (1 + Math.sqrt(5)) / 2;
			return (int) Math.round(phi * previous);
}
}