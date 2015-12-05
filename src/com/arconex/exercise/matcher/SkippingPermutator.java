package com.arconex.exercise.matcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Splits an input String into all
 *
 */
public class SkippingPermutator implements Permutator {

	private final String skippingCharacter;

	public SkippingPermutator(final String skippingCharacter) {
		this.skippingCharacter = skippingCharacter;
	}

	@Override
	public List<String[]> getPermutations(final String input) {
		final List<String[]> rval = new ArrayList<>();

		// Return an empty list if the input string is empty.
		if (input != null && input.length() > 0) {
			final String[] initalPermutation = new String[input.length()];
			return permutate(rval, initalPermutation, input, 0, true);
		}
		return rval;
	}

	public String getSkippingCharacter() {
		return this.skippingCharacter;
	}

	/**
	 * Recursively builds a tree of permutations of the characters in the inputString.
	 *
	 * Every character can potentially be replaced with the SKIPPING_CHARACTER as long as the previous character was not skipped.
	 *
	 * @param permutations
	 * @param permutation
	 * @param inputString
	 * @param index
	 * @param allowSkip
	 * @return
	 */
	private List<String[]> permutate(final List<String[]> permutations, final String[] permutation, final String inputString,
			final int index, final boolean allowSkip) {

		if (index < inputString.length()) {
			if (allowSkip) {
				// Start new branch if skipping.
				final String[] newPermutation = new String[inputString.length()];
				System.arraycopy(permutation, 0, newPermutation, 0, index);
				newPermutation[index] = getSkippingCharacter();
				permutate(permutations, newPermutation, inputString, index + 1, false);
			}

			// Always continue branch non skipping.
			permutation[index] = Character.toString(inputString.charAt(index));
			permutate(permutations, permutation, inputString, index + 1, true);

		} else {
			// We are at the end of the recursion, add the results to the output array!
			permutations.add(permutation);
		}
		return permutations;
	}

}

