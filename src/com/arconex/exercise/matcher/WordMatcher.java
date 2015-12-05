package com.arconex.exercise.matcher;

import java.util.List;

public interface WordMatcher {

	/**
	 * Returns a collection of Strings which are matches for digit block.
	 *
	 * @param digitBlock
	 * @return
	 */
	List<String> getMatches(String digitBlock);
}

