package com.arconex.exercise.encoding;

public interface DigitEncoding {

	/**
	 * Return the array of characters that is mapped to this digit.
	 *
	 * Should return an empty array if the digit is valid but doesn't map to any characters. If the input is less than 0 or greater than 9
	 * IllegalArgumentException should be thrown.
	 *
	 * @param digit
	 * @return
	 */
	String[] getCharactersForDigit(int digit);

}

