package com.arconex.exercise.controller;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.arconex.exercise.encoding.DefaultDigitEncoding;
import com.arconex.exercise.encoding.DigitEncoding;


public class TestNumberToWordsController {

	DigitEncoding encoding;
	List<String> dictionary;

	@Before
	public void setup() {
		this.encoding = new DefaultDigitEncoding();
		this.dictionary = Arrays.asList("BALL", "CALL", "ME", "OE", "FLOWER", "TELEVISION");
	}

	@Test
	public void shouldGenerateNoMatchesWithNoInput() {

		final NumbersToWordsController matcher = new NumbersToWordsController(this.dictionary, this.encoding);
		final List<String> output = matcher.findAllWordPermutation(Collections.<String> emptyList());

		Assert.assertTrue(output.isEmpty());
	}

	@Test
	public void shouldGenerateExpectedOutputForSingleBlockInput() {
		final List<String> singleBlockInput = Arrays.asList("2255", "356937", "8353847466");
		final List<String> expectedOutput = Arrays.asList("CALL", "BALL", "FLOWER", "TELEVISION");

		final NumbersToWordsController matcher = new NumbersToWordsController(this.dictionary, this.encoding);
		final List<String> output = matcher.findAllWordPermutation(singleBlockInput);

		Assert.assertEquals(expectedOutput.size(), output.size());
		for (final String word : output) {
			expectedOutput.contains(word);
		}
	}

	@Test
	public void shouldGenerateExpectedOutputForMultiBlockInput() {
		final List<String> multiBlockInput = Arrays.asList("2255-356937", "356937-2255", "8353847466", "2255-2255");
		final List<String> expectedOutput = Arrays.asList("CALL-FLOWER", "BALL-FLOWER", "FLOWER-BALL", "FLOWER-CALL", "TELEVISION",
				"CALL-CALL", "CALL-BALL", "BALL-CALL", "BALL-BALL");

		final NumbersToWordsController matcher = new NumbersToWordsController(this.dictionary, this.encoding);
		final List<String> output = matcher.findAllWordPermutation(multiBlockInput);

		Assert.assertEquals(expectedOutput.size(), output.size());
		for (final String word : output) {
			expectedOutput.contains(word);
		}
	}

	@Test
	public void shouldGenerateNotReturnDuplicates() {
		final List<String> repeatingInput = Arrays.asList("2255-2255", "2255-2255");
		final List<String> expectedOutput = Arrays.asList("CALL-CALL", "CALL-BALL", "BALL-CALL", "BALL-BALL");

		final NumbersToWordsController matcher = new NumbersToWordsController(this.dictionary, this.encoding);
		final List<String> output = matcher.findAllWordPermutation(repeatingInput);

		Assert.assertEquals(expectedOutput.size(), output.size());
		for (final String word : output) {
			expectedOutput.contains(word);
		}
	}

	@Test
	public void shouldHandleDifferentDelimiters() {
		final List<String> multiBlockInput = Arrays.asList("2255@356937", "356937+2255", "8353847466", "2255*2255", "2255/2255",
				"2255-2255");
		final List<String> expectedOutput = Arrays.asList("CALL-FLOWER", "BALL-FLOWER", "FLOWER-BALL", "FLOWER-CALL", "TELEVISION",
				"CALL-CALL", "CALL-BALL", "BALL-CALL", "BALL-BALL");

		final NumbersToWordsController matcher = new NumbersToWordsController(this.dictionary, this.encoding);
		final List<String> output = matcher.findAllWordPermutation(multiBlockInput);

		Assert.assertEquals(expectedOutput.size(), output.size());
		for (final String word : output) {
			expectedOutput.contains(word);
		}
	}

	@Test
	public void shouldIgnoreWhitespace() {
		final List<String> whitespaceInput = Arrays.asList("22   55 .  6   3 ");
		final List<String> expectedOutput = Arrays.asList("CALL-ME", "BALL-ME", "CALL-OE", "BALL-OE");

		final NumbersToWordsController matcher = new NumbersToWordsController(this.dictionary, this.encoding);
		final List<String> output = matcher.findAllWordPermutation(whitespaceInput);

		Assert.assertEquals(expectedOutput.size(), output.size());
		for (final String word : output) {
			expectedOutput.contains(word);
		}
	}

}

