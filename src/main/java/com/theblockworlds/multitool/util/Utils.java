package com.theblockworlds.multitool.util;

public class Utils {
	
	/** Compares one string to an unknown number of strings
	 * 
	 * @param argument		String you want to compare other strings to
	 * @param otherStrings	Unknown number of strings
	 * @return				True if argument equals any of the other strings
	 */
	public static boolean equalsIgnoreCase(String argument, String... otherStrings) {
		for (String string : otherStrings) {
			if (argument.equalsIgnoreCase(string)) {
				return true;
			}
		}
		return false;
	}
	
	
	/** Returns a number between 0 and 15 (both inclusive)
	 * 
	 * @param number		Number you wish to have between 0 and 15
	 * @return				0 if greater than 15, 15 if less than 0 and number otherwise
	 */
	public static byte wrapAround(int number) {
		if (number > 15) {
			return 0;
		}
		else if (number < 0) {
			return 15;
		}
		return (byte) number;
	}
}
