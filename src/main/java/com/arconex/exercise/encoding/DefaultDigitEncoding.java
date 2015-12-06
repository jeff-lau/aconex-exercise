package com.arconex.exercise.encoding;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the default encoding for the exercise.
 */
public class DefaultDigitEncoding implements DigitEncoding {

	private static final Map<Integer, String[]> encodings = new HashMap<>();

	static {
		encodings.put(0, new String[0]);
		encodings.put(1, new String[0]);
		encodings.put(2, new String[] { "A", "B", "C" });
		encodings.put(3, new String[] { "D", "E", "F" });
		encodings.put(4, new String[] { "G", "H", "I" });
		encodings.put(5, new String[] { "J", "K", "L" });
		encodings.put(6, new String[] { "M", "N", "O" });
		encodings.put(7, new String[] { "P", "Q", "R", "S" });
		encodings.put(8, new String[] { "T", "U", "V" });
		encodings.put(9, new String[] { "W", "X", "Y", "Z" });
	}

	/**
	 * See {{@link DigitEncoding#getCharactersForDigit(int)}
	 */
	@Override
	public String[] getCharactersForDigit(final int digit) {
		if (digit >= 0 && digit <= 9) {
			return encodings.get(digit);
		}
		throw new IllegalArgumentException("Digit should be between 0 and 9");
	}

}

