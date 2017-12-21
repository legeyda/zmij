package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.impl.ValuelessBranch;
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
				new Value<>(new ValuelessBranch(Tag.REPEAT, Arrays.asList(
						new ValuelessBranch(Tag.CONSTANT, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'a'))),
						new ValuelessBranch(Tag.CONSTANT, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'a'))),
						new ValuelessBranch(Tag.CONSTANT, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'a'))))))
		).run();

		new CharPatternCase<>(
				new OneOrMorePattern<>(new ConstantPattern<>(new CharSequenceList("a"))),
				"ab",
				new Value<>(new ValuelessBranch(Tag.REPEAT, Collections.singletonList(
						new ValuelessBranch(Tag.CONSTANT, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'a'))))))
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
