package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.Branch;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.util.CharSequenceList;
import org.junit.Test;

import java.util.Arrays;

public class SequencePatternTest {


	@Test
	public void test() {
		final Pattern<Character, Tree> a = new ConstantPattern<>(new CharSequenceList("a"));
		final Pattern<Character, Tree> b = new ConstantPattern<>(new CharSequenceList("b"));
		final Pattern<Character, Tree> c = new ConstantPattern<>(new CharSequenceList("c"));

		new CharPatternCase<>(
				new SequencePattern<>(a, b, c),
				"abc",
				new Value<>(new Branch(Tag.SEQUENCE, Arrays.asList(
						new ValuedLeaf(Tag.TOKEN, new CharSequenceList("a")),
						new ValuedLeaf(Tag.TOKEN, new CharSequenceList("b")),
						new ValuedLeaf(Tag.TOKEN, new CharSequenceList("c")))))
		).run();

		new CharPatternCase<>(
				new SequencePattern<>(a, b, c),
				"bcd",
				new Failure<>("at pos 0: expected [a]")
		).run();

		new CharPatternCase<>(
				new SequencePattern<>(a, b, c),
				"",
				new Failure<>("unexpected eof (expected [a])")
		).run();
	}


}
