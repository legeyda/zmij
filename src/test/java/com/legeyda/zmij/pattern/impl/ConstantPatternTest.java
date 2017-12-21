package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.pattern.PatternCase;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.tree.impl.ValuelessBranch;
import com.legeyda.zmij.util.CharSequenceList;
import org.junit.Test;

import java.util.Arrays;


public class ConstantPatternTest {

	@Test
	public void test() {

		new PatternCase<>(
				new ConstantPattern<>(new CharSequenceList("abc")),
				new CharSequenceList("abcde"),
				new Value<>(new ValuelessBranch(Tag.CONSTANT, Arrays.asList(
						new ValuedLeaf(Tag.TOKEN, 'a'),
						new ValuedLeaf(Tag.TOKEN, 'b'),
						new ValuedLeaf(Tag.TOKEN, 'c'))))
		).run();

		new PatternCase<>(
				new ConstantPattern<>(new CharSequenceList("abc")),
				new CharSequenceList("bcdea"),
				new Failure<>("at pos 0: [a, b, c] expected")
		).run();

		new PatternCase<>(
				new ConstantPattern<>(new CharSequenceList("abc")),
				new CharSequenceList(""),
				new Failure<>("unexpected eof ([a, b, c] expected)")
		).run();


	}

}