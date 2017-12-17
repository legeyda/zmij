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

public class RepeatPatternTest {

	@Test
	public void test() {

		final Pattern<Character, Tree> a = new ConstantPattern<>('a');
		final Tree aTree = new ValuedLeaf(Tag.TOKEN, new CharSequenceList("a"));
		final Pattern<Character, Tree> repeat = new RepeatPattern<>(a, 3L, 4L, false);

		new CharPatternCase<>(
				new RepeatPattern<>(a, 3L, 4L, false),
				"aaabc",
				new Value<>(new Branch(Tag.REPEAT, Arrays.asList(aTree, aTree, aTree)))
		).run();

		new CharPatternCase<>(
				new RepeatPattern<>(a, 3L, 4L, false),
				"aaaabc",
				new Value<>(new Branch(Tag.REPEAT, Arrays.asList(aTree, aTree, aTree)))
		).run();

		new CharPatternCase<>(
				new RepeatPattern<>(a, 3L, 4L, false),
				"aaaaaabc",
				new Value<>(new Branch(Tag.REPEAT, Arrays.asList(aTree, aTree, aTree, aTree)))
		).run();

		new CharPatternCase<>(
				new RepeatPattern<>(a, 3L, 4L, true),
				"aaaaaabc",
				new Failure<>("at pos 5: got 5 repetitions")
		).run();


		new CharPatternCase<>(
				new RepeatPattern<>(a, 3L, 4L, true),
				"",
				new Failure<>("unexpected eof (got 0 repetitions)")
		).run();

		new CharPatternCase<>(
				new RepeatPattern<>(a, 3L, 4L, true),
				"b",
				new Failure<>("at pos 0: got 0 repetitions")
		).run();

	}

}