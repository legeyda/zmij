package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import org.junit.Test;

public class BaseTokenPatternTest {

	@Test
	public void test() {
		new CharPatternCase<>(
				new BaseTokenPattern<>("any token", t->true),
				"abc",
				new Value<>(new ValuedLeaf(Tag.TOKEN, 'a'))
		).run();

		new CharPatternCase<>(
				new BaseTokenPattern<>("any token", t->true),
				"bcd",
				new Value<>(new ValuedLeaf(Tag.TOKEN, 'b'))
		).run();

		new CharPatternCase<>(
				new BaseTokenPattern<>("any token", t->true),
				"",
				new Failure<>("unexpected eof (expected any token)")
		).run();

		new CharPatternCase<>(
				new BaseTokenPattern<>("none", t->false),
				"abc",
				new Failure<>("at pos 0: expected none")
		).run();

		new CharPatternCase<>(
				new BaseTokenPattern<>("none", t->false),
				"",
				new Failure<>("unexpected eof (expected none)")
		).run();
	}
}
