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
		encodings.put(2, new String[] { "a", "b", "c" });
		encodings.put(3, new String[] { "d", "e", "f" });
		encodings.put(4, new String[] { "g", "h", "i" });
		encodings.put(5, new String[] { "j", "k", "l" });
		encodings.put(6, new String[] { "m", "n", "o" });
		encodings.put(7, new String[] { "p", "q", "r", "s" });
		encodings.put(8, new String[] { "t", "u", "v" });
		encodings.put(9, new String[] { "w", "x", "y", "z" });
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

