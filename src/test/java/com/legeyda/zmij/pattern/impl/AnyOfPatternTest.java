package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.AnythingAsTree;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.tree.impl.ValuelessBranch;
import com.legeyda.zmij.util.CharSequenceList;
import org.junit.Test;

import java.util.Collections;

public class AnyOfPatternTest {


	@Test
	public void test() {
		final Pattern<Character, Tree> a = new ConstantPattern<>(new CharSequenceList("a"));
		final Pattern<Character, Tree> b = new ConstantPattern<>(new CharSequenceList("b"));

		new CharPatternCase<>(
				new AnyOfPattern<>(a, b),
				"abc",
				new Value<>(new ValuelessBranch(Tag.CONSTANT, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'a'))))
		).run();

		new CharPatternCase<>(
				new AnyOfPattern<>(a, b),
				"bcd",
				new Value<>(new ValuelessBranch(Tag.CONSTANT, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'b'))))
		).run();

		new CharPatternCase<>(
				new AnyOfPattern<>(a, b),
				"cba",
				new Failure<>("at pos 0: expected any of:\n\t[a], \n\t[b]")
		).run();

		new CharPatternCase<>(
				new AnyOfPattern<>(a, b),
				"",
				new Failure<>("unexpected eof (expected any of:\n\t[a], \n\t[b])")
		).run();
	}
}
