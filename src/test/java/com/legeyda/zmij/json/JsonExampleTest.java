package com.legeyda.zmij.json;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.input.impl.context.ParsingContextImpl;
import com.legeyda.zmij.input.impl.input.restoreable.GivenRestoreableInput;
import com.legeyda.zmij.input.impl.input.util.ItemList;
import com.legeyda.zmij.result.Result;
import com.legeyda.zmij.util.CharSequenceList;
import org.junit.Test;

import java.io.IOException;

public class JsonExampleTest {

	protected void doTest(final String file) throws IOException {
		final String json = Resources.toString(
				Resources.getResource(file),
				Charsets.UTF_8);

		final ParsingContext<Character> ctx =
				new ParsingContextImpl<>(new GivenRestoreableInput<>(new ItemList<>(new CharSequenceList(json))));


		final Result<Object> result = new JsonPatternFactory().get().apply(ctx).get();

	}

	@Test
	public void test1() throws IOException {
		doTest("com/legeyda/zmij/json/example1.json");
	}
	@Test
	public void test2() throws IOException {
		doTest("com/legeyda/zmij/json/example2.json");
	}

	@Test
	public void test3() throws IOException {
		doTest("com/legeyda/zmij/json/example3.json");
	}





}
