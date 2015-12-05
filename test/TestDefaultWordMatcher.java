import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.arconex.exercise.encoding.DefaultDigitEncoding;
import com.arconex.exercise.matcher.SkippingPermutator;
import com.arconex.exercise.matcher.SkippingWordMatcher;


public class TestDefaultWordMatcher {

	private SkippingPermutator permutator;

	@Before
	public void setup(){
		this.permutator = new SkippingPermutator("@");
	}

	@Test
	public void shouldReturnAllMatchesForSingleDigitInput() {

		// Creating an instance of the matcher for testing.
		final SkippingWordMatcher testMatcher = new SkippingWordMatcher(new DefaultDigitEncoding(), Arrays.asList("A", "B", "D"),
				this.permutator);

		final String inputNumber = "2";
		final List<String> expectedMatches = Arrays.asList("A", "B", "2");
		final List<String> output = testMatcher.getMatches(inputNumber);

		Assert.assertEquals(expectedMatches.size(), output.size());
		for (final String match : output) {
			Assert.assertTrue(expectedMatches.contains(match));
		}
	}

	@Test
	public void shouldReturnTheDigitItselfIfNoEncodingIsProvidedForDigit() {

		// Creating an instance of the matcher for testing.
		final SkippingWordMatcher testMatcher = new SkippingWordMatcher(new DefaultDigitEncoding(), Arrays.asList("A", "B", "C"),
				this.permutator);

		final String inputNumber = "1";
		final List<String> expectedMatches = Arrays.asList("1");
		final List<String> output = testMatcher.getMatches(inputNumber);

		Assert.assertEquals(expectedMatches.size(), output.size());
		for (final String match : output) {
			Assert.assertTrue(expectedMatches.contains(match));
		}
	}

	@Test
	public void shouldReturnAllMatchesWithNoSkipping() {

		// Creating an instance of the matcher for testing.
		final SkippingWordMatcher testMatcher = new SkippingWordMatcher(new DefaultDigitEncoding(), Arrays.asList("CALL", "BALL", "RANDOM"),
				this.permutator);

		final String inputNumber = "2255";
		final List<String> expectedMatches = Arrays.asList("CALL", "BALL");
		final List<String> output = testMatcher.getMatches(inputNumber);

		Assert.assertEquals(expectedMatches.size(), output.size());
		for (final String match : output) {
			Assert.assertTrue(expectedMatches.contains(match));
		}
	}

	@Test
	public void shouldReturnAllMatchesWithOneCharSkipping() {

		// Creating an instance of the matcher for testing.
		final SkippingWordMatcher testMatcher = new SkippingWordMatcher(new DefaultDigitEncoding(), Arrays.asList("CALL", "BALL", "RANDOM"),
				this.permutator);

		final String inputNumber = "22955";
		final List<String> expectedMatches = Arrays.asList("CA9LL", "BA9LL");
		final List<String> output = testMatcher.getMatches(inputNumber);

		Assert.assertEquals(expectedMatches.size(), output.size());
		for (final String match : output) {
			Assert.assertTrue(expectedMatches.contains(match));
		}
	}

	@Test
	public void shouldReturnAllMatchesSkipAndNonSkip() {

		// Creating an instance of the matcher for testing.
		final SkippingWordMatcher testMatcher = new SkippingWordMatcher(new DefaultDigitEncoding(),
				Arrays.asList("CALL", "BALL", "ALL", "RANDOM"),
				this.permutator);

		final String inputNumber = "2255";
		final List<String> expectedMatches = Arrays.asList("CALL", "BALL", "2ALL", "A2LL");
		final List<String> output = testMatcher.getMatches(inputNumber);

		Assert.assertEquals(expectedMatches.size(), output.size());
		for (final String match : output) {
			Assert.assertTrue(expectedMatches.contains(match));
		}
	}

	@Test
	public void shouldReturnAllMatchesMultipleSkip() {

		// Creating an instance of the matcher for testing.
		final SkippingWordMatcher testMatcher = new SkippingWordMatcher(new DefaultDigitEncoding(),
				Arrays.asList("CALL", "BALL", "ALL", "AL", "RANDOM"),
				this.permutator);

		final String inputNumber = "2255";
		final List<String> expectedMatches = Arrays.asList("CALL", "BALL", "2ALL", "A2LL", "A2L5", "2A5L", "2AL5");
		final List<String> output = testMatcher.getMatches(inputNumber);

		Assert.assertEquals(expectedMatches.size(), output.size());
		for (final String match : output) {
			Assert.assertTrue(expectedMatches.contains(match));
		}
	}

}

