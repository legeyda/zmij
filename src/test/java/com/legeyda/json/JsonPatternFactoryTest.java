package com.legeyda.json;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.result.Value;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

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


	@Test
	public void testArray() {

		new CharPatternCase<>(
				new JsonPatternFactory().get(),
				"[]",
				new Value<>(new LinkedList())
		).run();

		new CharPatternCase<>(
				new JsonPatternFactory().get(),
				"[\"hello\"]",
				new Value<>(new LinkedList<>(Arrays.asList("hello")))
		).run();

		new CharPatternCase<>(
				new JsonPatternFactory().get(),
				"[\"hello\", \"world\"]",
				new Value<>(new LinkedList<>(Arrays.asList("hello", "world")))
		).run();

	}

	@Test
	public void testObject() {

		new CharPatternCase<>(
				new JsonPatternFactory().get(),
				" {} ",
				new Value<>(new HashMap())
		).run();

		final HashMap<String, Object> expected = new HashMap<>();
		expected.put("key", "value");
		new CharPatternCase<>(
				new JsonPatternFactory().get(),
				"{ \"key\"  : \"value\"  }",
				new Value<>(expected)
		).run();

		expected.put("hello", "world");
		new CharPatternCase<>(
				new JsonPatternFactory().get(),
				"{\"key\":\"value\" , \"hello\":\"world\"}",
				new Value<>(expected)
		).run();

	}


}
