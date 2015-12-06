package com.arconex.exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.arconex.exercise.controller.NumbersToWordsController;
import com.arconex.exercise.encoding.DefaultDigitEncoding;
import com.arconex.exercise.parser.FileParser;
import com.arconex.exercise.parser.WordPerLineParser;

/**
 * Main class. Doesn't really do much apart from parsing the input files and then handing all the heavy lifting to the NumberToWordsMatcher
 *
 */
public class Main {

	private static final String STDIN_INSTRUCTION = "Please input phone number(s) - use space to separate each number:";
	public static final String DICTIONARY_PROPERTY = "dictionary";
	public static final String DEFAULT_DICTIONARY_PATH = "/dictionary.txt";

	public static void main(final String[] args) {

		try {
			final File dictionaryFile = getDictionaryFile();
			final FileParser parser = new WordPerLineParser();
			final List<String> dictionary = parser.parse(dictionaryFile);
			final NumbersToWordsController matcher = new NumbersToWordsController(dictionary, new DefaultDigitEncoding());

			if (args.length < 1) {
				standardInputLoop(matcher);
			} else {
				processFile(new File(args[0]), parser, matcher);
			}

		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.exit(0);
	}

	private static File getDictionaryFile() throws URISyntaxException {

		final String inputDictionaryPath = System.getProperty(DICTIONARY_PROPERTY);

		if (inputDictionaryPath == null || inputDictionaryPath.isEmpty()) {
			System.out.println("Dictionary not supplied... using default..");
			return new File(Main.class.getResource(DEFAULT_DICTIONARY_PATH).toURI());
		} else {

			return new File(inputDictionaryPath);
		}
	}

	private static void processFile(final File inputFile, final FileParser parser, final NumbersToWordsController controller)
			throws FileNotFoundException, IOException {
		final List<String> inputPhoneNumbers = parser.parse(inputFile);
		final List<String> allPermutations = controller.findAllWordPermutation(inputPhoneNumbers);

		for (final String permutation : allPermutations) {
			System.out.println(permutation);
		}
	}

	private static void standardInputLoop(final NumbersToWordsController controller) {

		final Scanner stdin = new Scanner(System.in);
		System.out.println(STDIN_INSTRUCTION);
		while (stdin.hasNextLine()) {

        	final String line = stdin.nextLine();
            final String[] tokens = line.trim().split(" ");
            System.out.println(controller.findAllWordPermutation(Arrays.asList(tokens)));
        }
        stdin.close();

	}
}
