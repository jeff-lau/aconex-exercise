package com.arconex.exercise;

import java.io.File;
import java.util.List;

import com.arconex.exercise.parser.FileParser;
import com.arconex.exercise.parser.WordPerLineParser;

/**
 * Main class. Doesn't really do much apart from parsing the input files and then handing all the heavy lifting to the NumberToWordsMatcher
 *
 */
public class Main {

	public static final String DICTIONARY_PROPERTY = "dictionary";

	public static void main(final String[] args) {

		try {

			 final String inputDictionaryPath = System.getProperty(DICTIONARY_PROPERTY);
			 final FileParser parser = new WordPerLineParser();

			 final List<String> inputPhoneNumbers = parser.parse(new File(args[0]));
			 final List<String> dictionary = parser.parse(new File(inputDictionaryPath));

			final NumberToWordsMatcher matcher = new NumberToWordsMatcher(inputPhoneNumbers, dictionary);
			final List<String> allPermutations = matcher.findAllWordPermutation();

			for (final String permutation : allPermutations) {
				System.out.println(permutation);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.exit(0);
	}

}
