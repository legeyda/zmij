package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.impl.Branch;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.util.CharSequenceList;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class OneOrMorePatternTest {

	@Test
	public void test() {

		new CharPatternCase<>(
				new OneOrMorePattern<>(new ConstantPattern<>(new CharSequenceList("a"))),
				"aaabc",
				new Value<>(new Branch(Tag.REPEAT, Arrays.asList(
						new ValuedLeaf(Tag.RESULT, new ValuedLeaf(Tag.TOKEN, new CharSequenceList("a"))),
						new ValuedLeaf(Tag.RESULT, new ValuedLeaf(Tag.TOKEN, new CharSequenceList("a"))),
						new ValuedLeaf(Tag.RESULT, new ValuedLeaf(Tag.TOKEN, new CharSequenceList("a"))))))
		).run();

		new CharPatternCase<>(
				new OneOrMorePattern<>(new ConstantPattern<>(new CharSequenceList("a"))),
				"ab",
				new Value<>(new Branch(Tag.REPEAT, Collections.singletonList(
						new ValuedLeaf(Tag.RESULT, new ValuedLeaf(Tag.TOKEN, new CharSequenceList("a"))))))
		).run();

		new CharPatternCase<>(
				new OneOrMorePattern<>(new ConstantPattern<>(new CharSequenceList("a"))),
				"b",
				new Failure<>("at pos 0: at least single <[a]> expected")
		).run();

		new CharPatternCase<>(
				new OneOrMorePattern<>(new ConstantPattern<>(new CharSequenceList("a"))),
				"",
				new Failure<>("unexpected eof (at least single <[a]> expected)")
		).run();

	}

}
