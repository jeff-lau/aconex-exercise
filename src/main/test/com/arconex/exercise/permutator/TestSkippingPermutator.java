package com.arconex.exercise.permutator;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.arconex.exercise.permutator.SkippingPermutator;

public class TestSkippingPermutator {

	private static final String SKIPPING_CHARACTER = "@";

	private SkippingPermutator testInstance;


	@Before
	public void setup() {
		this.testInstance = new SkippingPermutator(SKIPPING_CHARACTER);
	}

	@Test
	public void shouldGenerateCorrectPermutations(){
		// Expected Permutations
		final List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "C", SKIPPING_CHARACTER, "L", "L" });
		expected.add(new String[] { "C", "A", SKIPPING_CHARACTER, "L" });
		expected.add(new String[] { "C", "A", "L", SKIPPING_CHARACTER });
		expected.add(new String[] { "C", SKIPPING_CHARACTER, "L", SKIPPING_CHARACTER });
		expected.add(new String[] { SKIPPING_CHARACTER, "A", "L", "L" });
		expected.add(new String[] { SKIPPING_CHARACTER, "A", SKIPPING_CHARACTER, "L" });

		final List<String[]> output = this.testInstance.getPermutations("CALL");

		for (final String[] permutation : output) {
			expected.contains(permutation);
		}
	}

	@Test
	public void shouldGenerateCorrectPermutationsWithTwoCharInput() {
		// Expected Permutations
		final List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "C", SKIPPING_CHARACTER });
		expected.add(new String[] { SKIPPING_CHARACTER, "A" });
		expected.add(new String[] { "C", "A" });

		final List<String[]> output = this.testInstance.getPermutations("CA");

		for (final String[] permutation : output) {
			expected.contains(permutation);
		}
	}

	@Test
	public void shouldGenerateEmptyListWithEmptyInput() {
		// Expected Permutations
		final List<String[]> output = this.testInstance.getPermutations("");
		Assert.assertTrue(output.size() == 0);
	}

	@Test
	public void shouldGeneratePermutationsWithSingleCharInput() {
		// Expected Permutations
		final List<String[]> expected = new ArrayList<>();
		expected.add(new String[] { "C" });
		expected.add(new String[] { SKIPPING_CHARACTER });

		final List<String[]> output = this.testInstance.getPermutations("C");

		for (final String[] permutation : output) {
			expected.contains(permutation);
		}
	}
}

