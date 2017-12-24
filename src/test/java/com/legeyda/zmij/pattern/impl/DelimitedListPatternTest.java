package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.pattern.CharPatternCase;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Failure;
import com.legeyda.zmij.result.Value;
import com.legeyda.zmij.tree.Tag;
import com.legeyda.zmij.tree.Tree;
import com.legeyda.zmij.tree.impl.ValuedLeaf;
import com.legeyda.zmij.tree.impl.ValuelessBranch;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class DelimitedListPatternTest {



	@Test
	public void test() {

		final Pattern<Character, Tree> element = new TokenBlackListPattern<>(new HashSet<>(Arrays.asList(',','_')));
		final Pattern<Character, Tree> comma = new ConstantPattern<>(',');
		final Tree tree = new ValuelessBranch(Tag.CONSTANT, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'a')));



		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 0, 1),
				"",
				new Value<>(new ValuelessBranch(Tag.DELIMITED_LIST, Collections.emptyList()))
		).run();

		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 0, 1),
				"a",
				new Value<>(new ValuelessBranch(Tag.DELIMITED_LIST, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'a'))))
		).run();

		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 0, 1),
				"ab",
				new Value<>(new ValuelessBranch(Tag.DELIMITED_LIST, Collections.singletonList(new ValuedLeaf(Tag.TOKEN, 'a'))))
		).run();

		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 0, 1),
				"a,b",
				new Failure<>("at pos 2: expected at most 1 of any token but {,, _}")
		).run();





		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 1, 3),
				"",
				new Failure<>("unexpected eof (expected at least 1 of any token but {,, _})")
		).run();

		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 1, 3),
				"a",
				new Value<>(new ValuelessBranch(Tag.DELIMITED_LIST, Collections.singletonList(
						new ValuedLeaf(Tag.TOKEN, 'a'))))
		).run();

		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 1, 3),
				"a,b",
				new Value<>(new ValuelessBranch(Tag.DELIMITED_LIST, Arrays.asList(
						new ValuedLeaf(Tag.TOKEN, 'a'),
						new ValuedLeaf(Tag.TOKEN, 'b'))))
		).run();

		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 1, 3),
				"a,_",
				new Failure<>("at pos 2: expected any token but {,, _} after separator")
		).run();

		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 1, 3),
				"a,b,c",
				new Value<>(new ValuelessBranch(Tag.DELIMITED_LIST, Arrays.asList(
						new ValuedLeaf(Tag.TOKEN, 'a'),
						new ValuedLeaf(Tag.TOKEN, 'b'),
						new ValuedLeaf(Tag.TOKEN, 'c'))))
		).run();

		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 1, 3),
				"a,b,c,",
				new Failure<>("unexpected eof (expected at most 3 of any token but {,, _})")
		).run();

		new CharPatternCase<>(
				new DelimitedListPattern<>(element, comma, 1, 3),
				"a,b,c,d",
				new Failure<>("at pos 6: expected at most 3 of any token but {,, _}")
		).run();

	}

}