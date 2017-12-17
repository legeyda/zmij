package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.passage.impl.MemoizingPassage;
import com.legeyda.zmij.pattern.Pattern;

/** adds to created passages ability to memoize parsing activities */
public class MemoizingPattern<T, R> extends PatternDecorator<T, R> {

	public MemoizingPattern(Pattern<T, R> wrap) {
		super(wrap);
	}

	@Override
	public Passage<R> apply(final ParsingContext<T> input) {
		return new MemoizingPassage<>(input, super.apply(input));
	}
}
