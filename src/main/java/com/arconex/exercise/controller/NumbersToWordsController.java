package com.arconex.exercise.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.arconex.exercise.encoding.DigitEncoding;
import com.arconex.exercise.matcher.SkippingWordMatcher;
import com.arconex.exercise.matcher.WordMatcher;
import com.arconex.exercise.permutator.SkippingPermutator;

/**
 * A class that utilises the input dictionary to find all possible word matches to each block of numbers.
 *
 * For each input number, it is split into blocks using any non number delimiter. Matching words are found for each block (allowing
 * non-consecutive skipping of characters).
 *
 * Each block will potentially have a large number of matches. The matches for each blocks are then 'mashed' together to generate all
 * possible combinations for the number.
 *
 * As an example 2255.63 will be split into 2 blocks: 2255 and 63
 *
 * Lets Assume: Permutations for 2255 are [CALL, BALL, 2ALL] Permutations for 63 are [ME, OE]
 *
 * Then the possible outputs are all possible permutations between the 2 arrays:
 *
 * i.e. CALL-ME CALL-OE BALL-ME BALL-OE 2ALL-ME 2ALL-OE
 */
public class NumbersToWordsController {

	public static final String SKIPPING_DELIMITER = "@";
	public static final String DICTIONARY_PROPERTY = "dictionary";
	public static final String NUMBER_SPLIT_DELIMITER = "\\D+";
	public static final String BOUNDARY_CHAR = "-";

	final DigitEncoding encoding;
	final List<String> dictionary;

	public NumbersToWordsController(final List<String> dictionary, final DigitEncoding encoding) {
		this.dictionary = dictionary;
		this.encoding = encoding;
	}

	/**
	 * Returns all permutations of match words for the number. see {@link NumbersToWordsController} for details.
	 *
	 * @return
	 */
	public List<String> findAllWordPermutation(final List<String> inputPhoneNumbers) {
		final WordMatcher matcher = new SkippingWordMatcher(this.encoding, this.dictionary,
				new SkippingPermutator(SKIPPING_DELIMITER));

		final List<String> allPermutationsForNumber = new ArrayList<>();

		for (final String phoneNumber : inputPhoneNumbers) {
			final String[] phoneParts = phoneNumber.replaceAll("\\s+", "").split(NUMBER_SPLIT_DELIMITER);
			List<String> mashed = Arrays.asList("");
			for (final String phonePart : phoneParts) {
				final List<String> permutationsForPart = matcher.getMatches(phonePart);

				// If ANY part of the number does not have any permutations then nothing is returned (as per spec)
				if (permutationsForPart.isEmpty()) {
					mashed = Collections.<String> emptyList();
					break;
				}
				mashed = mash(mashed, permutationsForPart);
			}
			allPermutationsForNumber.addAll(mashed);
		}

		return dedupe(allPermutationsForNumber);
	}

	/**
	 * Mashes the right hand column into the left. Its basically an "OUTER JOIN" in SQL terms, generating all possible combinations between
	 * the 2 list.
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	private List<String> mash(final List<String> left, final List<String> right) {
		final List<String> rval = new ArrayList<>();
		for (final String strLeft : left) {
			for (final String strRight : right) {
				if (strLeft.isEmpty()) {
					rval.add(strRight.toUpperCase());
				} else {
					rval.add(strLeft + BOUNDARY_CHAR + strRight.toUpperCase());
				}
			}
		}
		return rval;
	}

	/**
	 * Deduplicates items in a list by using a set (which doesn't allow duplicates)
	 *
	 * @param input
	 * @return
	 */
	private List<String> dedupe(final List<String> input) {
		return new ArrayList<>(new HashSet<>(input));
	}

}

