package com.arconex.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.arconex.exercise.encoding.DefaultDigitEncoding;
import com.arconex.exercise.matcher.SkippingPermutator;
import com.arconex.exercise.matcher.SkippingWordMatcher;
import com.arconex.exercise.matcher.WordMatcher;

/**
 * A class that utilises the input dictionary find all possible word matches to each block of numbers.
 *
 * For each input number, it is split into blocks using any non number delimiter. Matching words are found for each block (allowing
 * non-consecutive skipping of characters).
 *
 * Each block will potentially a large number of matches. The matches for each blocks are then 'mashed' together to generate all possible
 * permutations for the number.
 *
 * As an example 2255.63 will be split into 2 blocks: 2255 and 63 Lets Assume: Permutations for 2255 are [CALL, BALL, 2ALL] Permutations for
 * 63 are [ME, OE]
 *
 * Then the possible outputs are all possible permutations between the 2 arrays:
 *
 * i.e. CALL-ME CALL-OE BALL-ME BALL-OE 2ALL-ME 2ALL-OE
 */
public class NumberToWordsMatcher {

	public static final String SKIPPING_DELIMITER = "@";
	public static final String DICTIONARY_PROPERTY = "dictionary";
	public static final String NUMBER_SPLIT_DELIMITER = "\\D+";
	public static final String BOUNDARY_CHAR = "-";

	final List<String> inputPhoneNumbers;
	final List<String> dictionary;

	public NumberToWordsMatcher(final List<String> inputPhoneNumbers, final List<String> dictionary) {
		this.inputPhoneNumbers = inputPhoneNumbers;
		this.dictionary = dictionary;

	}

	/**
	 * Returns all permutations of match words for the number. see {@link NumberToWordsMatcher} for details.
	 *
	 * @return
	 */
	public List<String> findAllWordPermutation() {
		final WordMatcher matcher = new SkippingWordMatcher(new DefaultDigitEncoding(), this.dictionary,
				new SkippingPermutator(SKIPPING_DELIMITER));

		final List<String> allPermutationsForNumber = new ArrayList<>();

		for (final String phoneNumber : this.inputPhoneNumbers) {

			final String[] phoneParts = phoneNumber.split(NUMBER_SPLIT_DELIMITER);
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

		return allPermutationsForNumber;
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
					rval.add(strRight);
				} else {
					rval.add(strLeft + BOUNDARY_CHAR + strRight);
				}
			}
		}
		return rval;
	}

}

