package com.legeyda.zmij.input.impl.context;

import com.legeyda.zmij.input.ParsingContext;
import com.legeyda.zmij.input.RestoreableInput;
import com.legeyda.zmij.input.impl.input.restoreable.RestoreableInputDecorator;
import com.legeyda.zmij.passage.Passage;
import com.legeyda.zmij.pattern.Pattern;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ParsingContextImpl<T> extends RestoreableInputDecorator<T> implements ParsingContext<T> {

	private final Map<Pattern<T, ?>, Passage<?>> cache = new HashMap<>();

	public ParsingContextImpl(RestoreableInput<T> input) {
		super(input);
	}

	@Override
	public <R> Passage<R> getOrCreatePassage(Pattern<T, R> key, Supplier<Passage<R>> generator) {
		return (Passage<R>) this.cache.computeIfAbsent(key, k -> generator.get());
	}
}
