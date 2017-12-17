package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.util.CharSequenceList;
import org.junit.Test;

public class ButPatternTest {


	@Test
	public void test() {
		final Pattern<Character, Tree> anyToken = new AnyTokenPattern<>();
		final Pattern<Character, Tree> a = new ConstantPattern<>(new CharSequenceList("a"));

		new CharPatternCase<>(
				new ButPattern<>(anyToken, a),
				"bca",
				new Value<>(new ValuedLeaf(Tag.TOKEN, 'b'))
		).run();

		new CharPatternCase<>(
				new ButPattern<>(anyToken, a),
				"abc",
				new Failure<>("at pos 0: expected any token but [a]")
		).run();

		new CharPatternCase<>(
				new ButPattern<>(anyToken, a),
				"",
				new Failure<>("unexpected eof (expected any token but [a])")
		).run();
	}

}
