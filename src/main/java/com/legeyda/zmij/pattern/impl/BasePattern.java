package com.legeyda.zmij.pattern.impl;

import com.legeyda.zmij.input.Input;
import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.pattern.Pattern;
import com.legeyda.zmij.result.Failure;

import java.util.LinkedList;

public abstract class BasePattern<T, R> implements Pattern<T, R> {

	protected Failure<R> createFailure(final Input<T> input, final String message) {
		if(input.valid()) {
			return new Failure<>(String.format("at pos %d: %s", input.get().position(), message));
		} else {
			return new Failure<>(String.format("unexpected eof (%s)", message));
		}
	}

	protected <V> Iterable<Passage<? extends V>> apply(final Iterable<Pattern<T, ? extends V>> patterns,
	                                                   final ParsingContext<T> input) {
		final LinkedList<Passage<? extends V>> result = new LinkedList<>();
		for(final Pattern<T, ? extends V> pattern: patterns) {
			result.add(pattern.apply(input));
		}
		return result;
	}

	protected abstract Passage<R> createPassage(final ParsingContext<T> input);

	@Override
	public Passage<R> apply(final ParsingContext<T> input) {
		return input.getOrCreatePassage(this, () -> this.createPassage(input));
	}

}
