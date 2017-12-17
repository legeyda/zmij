package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.pattern.Pattern;

public abstract class PatternDecorator<T, R> implements Pattern<T, R> {
	private final Pattern<T, R> wrap;

	public PatternDecorator(Pattern<T, R> wrap) {
		this.wrap = wrap;
	}

	@Override
	public String description() {
		return wrap.description();
	}

	protected Pattern<T, R> getWrap() {
		return wrap;
	}

	@Override
	public Passage<R> apply(final ParsingContext<T> input) {
		return wrap.apply(input);
	}
}
