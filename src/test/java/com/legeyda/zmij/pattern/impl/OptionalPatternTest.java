package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.tree.impl.ValuelessBranch;
import com.legeyda.zmij.tree.impl.ValuelessLeaf;
import com.legeyda.zmij.util.CharSequenceList;
import org.junit.Test;

import java.util.Collections;

public class OptionalPatternTest {

	@Test
	public void test() {

		new CharPatternCase<>(
				new OptionalPattern<>(new ConstantPattern<>(new CharSequenceList("a"))),
				"abc",
				new Value<>(new ValuelessBranch(Tag.CONSTANT, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'a'))))
		).run();

		new CharPatternCase<>(
				new OptionalPattern<>(new ConstantPattern<>(new CharSequenceList("a"))),
				"bcd",
				new Value<>(new ValuelessLeaf(Tag.OPTIONAL_ABSENT))
		).run();

		new CharPatternCase<>(
				new OptionalPattern<>(new ConstantPattern<>(new CharSequenceList("a"))),
				"",
				new Value<>(new ValuelessLeaf(Tag.OPTIONAL_ABSENT))
		).run();
	}

}
