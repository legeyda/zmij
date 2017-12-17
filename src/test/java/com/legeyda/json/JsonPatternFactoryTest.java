package com.legeyda.json;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.result.Value;
import org.junit.Test;

public class JsonPatternFactoryTest {

	@Test
	public void testString() {

		new CharPatternCase<>(
				new JsonPatternFactory().get(),
				"\"hello\"",
				new Value<>("hello")
		).run();

		new CharPatternCase<>(
				new JsonPatternFactory().get(),
				"  \"hello\"  ",
				new Value<>("hello")
		).run();

		new CharPatternCase<>(
				new JsonPatternFactory().get(),
				" \t \"he\\\"llo\" \n ",
				new Value<>("he\"llo")
		).run();

		new CharPatternCase<>(
				new JsonPatternFactory().get(),
				"\"hal\\u00FC\"",
				new Value<>("hal\u00FC")
		).run();


	}
}
