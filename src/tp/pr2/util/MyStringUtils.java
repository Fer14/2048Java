package tp.pr2.util;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.File;

/**
 * 
 * MyStringUtils class is contains useful methods in the strings treatments
 */
public class MyStringUtils {
	
	public static final String NEWLINE = System.getProperty("line.separator");
	
	
	/**
	 * Repeats an element several times
	 * @param elmnt is the element we want to be repeated
	 * @param length  is the number of times 
	 * @return the string with the element repeated 'length' times
	 */
	public static String repeat(String elmnt, int length)
	{
		String result = "";
		for (int i = 0; i < length; i++) 
			result += elmnt;
		return result;
	}
	/**
	 * Center the text
	 * @param text is the test we want to be center
	 * @param len is the length of the space
	 * @return the string with the text center
	 */
	public static String centre(String text, int len)
	{
		String out = String.format(" %"+len+"s %s %"+len+"s", "",text,"");
		float mid = (out.length()/2);
		float start = mid - (len/2);
		float end = start + len;
		return out.substring((int)start, (int)end);
	}
	
	// Used to exist method: org.eclipse . core. internal . resources . OS.isNameValid(filename).
	// This method is not completely reliable since exception could also be thrown due to:
	// incorrect permissions , no space on disk , problem accessing the device ,...
	/**
	 * Validates a file name and checks for any possible errors.
	 * @param filename the file name to validate.
	 * @return true if all everything is fine, false otherwise.
	 */
	public static boolean validFileName(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			return canWriteLocal(file);
			} 
		else {
			try 
			{
				file.createNewFile();
				file.delete();
				return true;
			} catch (Exception e) 
			{
				return false;
			}
		}
	}
	
	/**
	 * Checks if the file can be written into.
	 * @param file the file to check
	 * @return Whether it was possible to write in the file
	 */
	public static boolean canWriteLocal(File file) {

		try {
			new FileOutputStream(file, true).close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}



}
