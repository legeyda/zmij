package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.ValuelessBranch;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.util.CharSequenceList;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class RepeatPatternTest {

	@Test
	public void test() {

		final Pattern<Character, Tree> a = new ConstantPattern<>('a');
		final Tree aTree = new ValuelessBranch(Tag.CONSTANT, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'a')));

		new CharPatternCase<>(
				new RepeatPattern<>(a, 3, 4, false),
				"aaabc",
				new Value<>(new ValuelessBranch(Tag.REPEAT, Arrays.asList(aTree, aTree, aTree)))
		).run();

		new CharPatternCase<>(
				new RepeatPattern<>(a, 3, 4, false),
				"aaaabc",
				new Value<>(new ValuelessBranch(Tag.REPEAT, Arrays.asList(aTree, aTree, aTree, aTree)))
		).run();

		new CharPatternCase<>(
				new RepeatPattern<>(a, 3, 4, false),
				"aaaaaabc",
				new Value<>(new ValuelessBranch(Tag.REPEAT, Arrays.asList(aTree, aTree, aTree, aTree)))
		).run();

		new CharPatternCase<>(
				new RepeatPattern<>(a, 3, 4, true),
				"aaaaaabc",
				new Failure<>("at pos 5: got 5 repetitions")
		).run();


		new CharPatternCase<>(
				new RepeatPattern<>(a, 3, 4, true),
				"",
				new Failure<>("unexpected eof (got 0 repetitions)")
		).run();

		new CharPatternCase<>(
				new RepeatPattern<>(a, 3, 4, true),
				"b",
				new Failure<>("at pos 0: got 0 repetitions")
		).run();

	}

}