package com.arconex.exercise.matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.arconex.exercise.encoding.DigitEncoding;

/**
 * A word matcher that matches words to a number block.
 *
 * The matcher allows character skipping (i.e. if no match can be found with that digit then it will be left in place). No two consecutive
 * digits can be skipped.
 *
 */
public class SkippingWordMatcher implements WordMatcher {


	private final DigitEncoding encoding;
	private final SkippingPermutator permutator;
	private final List<String> dictionary;

	public SkippingWordMatcher(final DigitEncoding encoding, final List<String> dictionary, final SkippingPermutator permutator) {

		this.encoding = encoding;
		this.dictionary = dictionary;
		this.permutator = permutator;

	}

	/**
	 * Works out all the possible permutations of the this block using the supplied permutator. Returns all possible word matches for all
	 * permutations from words in the dictionary.
	 *
	 */
	@Override
	public List<String> getMatches(final String digitBlock) {

		validateDigitBlock(digitBlock);

		final List<String> rval = new ArrayList<>();

		// Get all permutations that we need to get matches for.
		final List<String[]> permutations = this.permutator.getPermutations(digitBlock);

		// Map them by length, this allows us to reuse the same reduced dictionary only containing words of expected length.
		final Map<Integer, List<String[]>> mapByLength = mappedByLength(permutations);

		// For each entry in the map, get the reduced dictionary for the length, and then find the matches for each permutation.
		for (final Entry<Integer, List<String[]>> entry : mapByLength.entrySet()){

			// Reduce the dictionary to only words of the expected length.
			final List<String> reducedDictionary = this.dictionary.stream().filter(s -> s.length() == entry.getKey())
					.collect(Collectors.toList());

				for (final String[] permutation : entry.getValue()) {
					// Splice the matching words into the permutation to produce the final match result.
					final List<String> matchingWords = match(permutation, reducedDictionary, 0, 0);
					rval.addAll(spliceWordIntoDigitBlock(matchingWords, permutation, digitBlock));
				}
		}

		return rval;
	}

	/**
	 * Recursively filter the list of remaining words based on the character encoding for each digit.
	 *
	 * Returns the remaining words once all the digit has been processed.
	 *
	 * @param digitBlock
	 * @param remainingWords
	 * @param digitIndex
	 * @return
	 */
	private List<String> match(final String[] input, final List<String> remainingWords, final int digitIndex, final int dictionaryIndex) {

		if (digitIndex < input.length && remainingWords.size() > 0) {

			int nextDictionaryIndex = dictionaryIndex;
			List<String> filteredRemainingWords = remainingWords;

			if (!input[digitIndex].equals(this.permutator.getSkippingCharacter())) {
				// If we are not at a SKIPPING_CHARACTER then we have to filter the dictionary based on the acceptable chars.
				final List<String> acceptableChars = Arrays
						.asList(this.encoding.getCharactersForDigit(Integer.parseInt(input[digitIndex])));

				filteredRemainingWords = remainingWords.stream()
						.filter(s -> acceptableChars.contains(s.substring(dictionaryIndex, dictionaryIndex + 1).toLowerCase())
								|| acceptableChars.contains(s.substring(dictionaryIndex, dictionaryIndex + 1).toUpperCase()))
						.collect(Collectors.toList());

				// Move the dictionary index forward by 1 (as we will be matching the next char).
				nextDictionaryIndex++;
			}

			// Recurse to next position.
			return match(input, filteredRemainingWords, digitIndex + 1, nextDictionaryIndex);
		} else {
			// All remaining words which should be the matches for this permutation of the digitblock.
			return remainingWords;
		}
	}

	/**
	 * Splices the words into the digit block (replacing the numbers with the characters in the matching word).
	 *
	 */
	private List<String> spliceWordIntoDigitBlock(final List<String> matchingWords, final String[] permutation, final String digitBlock) {
		final List<String> rval = new ArrayList<>();

		if (matchingWords.size() == 0 && permutation.length == 1 && permutation[0] == this.permutator.getSkippingCharacter()) {
			// Edge case when no match words are found, and the permutation only contains the skipping char then the number itself is a
			// valid output.
			rval.add(digitBlock);
		} else {
			for (final String word : matchingWords) {
				String output = "";
				int charIndex = 0;
				int digitIndex = 0;
				for (final String digit : permutation) {
					if (!digit.equals(this.permutator.getSkippingCharacter())) {
						output += word.charAt(charIndex);
						charIndex++;
					} else {
						output += digitBlock.charAt(digitIndex);
					}
					digitIndex++;
				}
				rval.add(output);
			}
		}
		return rval;
	}

	/**
	 * Validation method for ensuring all characters in the block are numbers.
	 *
	 * @param digitBlock
	 */
	private void validateDigitBlock(final String digitBlock) {
		for (final Character character : digitBlock.toCharArray()){
			if (!Character.isDigit(character) && !character.equals(this.permutator.getSkippingCharacter())) {
				throw new IllegalArgumentException("Block contains non digit character");
			}
		}
	}

	/**
	 * Helper method to map the different permutations by length after the the SKIPPING_CHARACTER is taken out.
	 *
	 */
	private Map<Integer, List<String[]>> mappedByLength(final List<String[]> permutations) {

		final Map<Integer, List<String[]>> rval = new HashMap<>();
		for (final String[] permutation : permutations) {
			final int count = (int) Arrays.asList(permutation).stream().filter(s -> !s.equals(this.permutator.getSkippingCharacter()))
					.count();

			List<String[]> permutationsByLength = rval.get(count);
			if (permutationsByLength == null) {
				permutationsByLength = new ArrayList<>();
				rval.put(count, permutationsByLength);
			}
			permutationsByLength.add(permutation);
		}

		return rval;
	}

}

