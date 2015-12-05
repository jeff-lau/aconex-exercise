package com.arconex.exercise.matcher;

import java.util.List;

public interface Permutator {

	/**
	 * Splits a String input into all permutations of arrangement of letters.
	 *
	 * @param input
	 * @return
	 */
	List<String[]> getPermutations(String input);

}

