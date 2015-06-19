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

}
